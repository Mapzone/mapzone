package io.mapzone.controller.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import io.mapzone.controller.ops.CreateProjectOperation;
import io.mapzone.controller.ops.DeleteProjectOperation;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredInstance;
import io.mapzone.controller.vm.repository.VmRepository;
import io.mapzone.controller.vm.runtime.HostRuntime;
import io.mapzone.controller.vm.runtime.InstanceRuntime;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import org.apache.commons.io.FileUtils;

import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.runtime.LockedLazyInit;

/**
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class OperationsTest {
   
    protected VmRepository              vmRepo;
    
    protected ProjectRepository         repo;
    
    protected ProjectRepository         nested;

    protected User                      defaultUser;
    
    protected InstanceRuntime           mockedInstanceRuntime;
    
    protected HostRuntime               mockedHostRuntime;

    protected Project                   project;

    
    @Before
    public void setup() throws Exception {
        File basedir = new File( "/tmp/mapzone-test" );
        basedir.mkdirs();
        FileUtils.cleanDirectory( basedir );
        
        VmRepository.init( basedir );
        ProjectRepository.init( basedir );
        
        vmRepo = VmRepository.instance();
        repo = ProjectRepository.newInstance();

        nested = repo.newNested();
        defaultUser = nested.createEntity( User.class, null, User.defaults );
        nested.commit();
    }


   @Test
   public void createDeleteProjectOperation() throws Exception {
       createProjectOperation();
       deleteProjectOperation();
   }

   
   protected void createProjectOperation() throws Exception {
       // check host
       assertEquals( 1, vmRepo.allHosts().size() );
       RegisteredHost host = vmRepo.allHosts().get( 0 );
       
       // mock runtime
       mockedInstanceRuntime = mock( InstanceRuntime.class );
       mockedHostRuntime = mock( HostRuntime.class );
       when( mockedHostRuntime.instance( any() ) ).thenReturn( mockedInstanceRuntime );
       vmRepo.allHosts().stream()
               .forEach( h -> h.runtime = new LockedLazyInit( () -> mockedHostRuntime ) );

       // prepare project
       project = nested.createEntity( Project.class, null, (Project proto) -> {
           proto.name.set( "cool" );
           return Project.defaults.initialize( proto );
       });
       
       User user = nested.entity( defaultUser );
       
       // operation
       CreateProjectOperation op = new CreateProjectOperation();
       op.repo.set( nested );
       op.project.set( project );
       op.organizationOrUser.set( user );
       op.doExecute( new NullProgressMonitor(), null );

       //check runtime
       verify( mockedInstanceRuntime ).install( Matchers.eq( project ), Matchers.any() );

       // check project
       assertSame( user, project.user.get() );
       assertSame( user, project.organizationOrUser() );
       assertSame( project, user.projects.iterator().next() );
       
       // check host
       assertEquals( 1, host.instances.size() );
       
       // check instance
       RegisteredInstance instance = host.instances.iterator().next();
       assertTrue( host.instances.contains( instance ) );
       assertSame( host, instance.host.get() );
       assertEquals( project.name.get(), instance.project.get() );
       
       // check committed
       ProjectRepository repo2 = ProjectRepository.newInstance();
       Project project2 = repo2.entity( project );
       assertNotSame( project, project2 );
       
       assertFalse( vmRepo.lock.isWriteLockedByCurrentThread() );
   }

   
   protected void deleteProjectOperation() throws Exception {
       RegisteredHost host = vmRepo.allHosts().get( 0 );
       RegisteredInstance instance = host.instances.iterator().next();
       
       // operation (no process to stop)
       DeleteProjectOperation op = new DeleteProjectOperation();
       op.repo.set( nested );
       op.project.set( project );
       op.execute( new NullProgressMonitor(), null );

       // check user
       assertEquals( 0, defaultUser.projects.size() );
       
       // check host
       assertTrue( host.instances.isEmpty() );
       assertNull( instance.host.get() );
       
       //check runtime
       verify( mockedInstanceRuntime ).uninstall( Matchers.any() );
   }
   
   
   @Test
   public void stopProcessOperation() {
   }

   
   @Test
   public void startProcessOperation() {
   }
   
}
