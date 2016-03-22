package io.mapzone.controller.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import io.mapzone.controller.ops.CreateProjectOperation;
import io.mapzone.controller.ops.DeleteProjectOperation;
import io.mapzone.controller.um.launcher.ProjectLauncher;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.VmRepository;
import io.mapzone.controller.vm.runtime.HostRuntime;
import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import org.apache.commons.io.FileUtils;

import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.model2.Property;
import org.polymap.model2.runtime.PropertyInfo;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class OperationsTest {
   
    protected VmRepository              vmRepo;
    
    protected ProjectRepository         repo;
    
    protected ProjectRepository         nested;

    protected User                      defaultUser;
    
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
       HostRecord host = vmRepo.allHosts().get( 0 );
       
       // mock runtime
       mockedHostRuntime = mock( HostRuntime.class );
       vmRepo.allHosts().stream()
               .forEach( h -> h.runtime = new LockedLazyInit( () -> mockedHostRuntime ) );

       // prepare project
       project = nested.createEntity( Project.class, null, (Project proto) -> {
           proto.name.set( "cool" );
           proto.launcher = new Property<ProjectLauncher>() {
               ProjectLauncher mock = mock( ProjectLauncher.class );
               public PropertyInfo info() { throw new RuntimeException( "not yet implemented." ); }
               public ProjectLauncher get() { return mock; }
               public <U extends ProjectLauncher> U createValue( ValueInitializer<U> initializer ) { throw new RuntimeException( "not yet implemented." ); }
               public void set( ProjectLauncher value ) { throw new RuntimeException( "not yet implemented." ); }
           };
           return Project.defaults.initialize( proto );
       });
       
       User user = nested.entity( defaultUser );
       
       // operation
       CreateProjectOperation op = new CreateProjectOperation();
       op.repo.set( nested );
       op.project.set( project );
       op.organizationOrUser.set( user );
       op.doExecute( new NullProgressMonitor(), null );

       // check launcher call
       verify( project.launcher.get() ).install( 
               Matchers.argThat( instanceOfProject( project ) ), 
               Matchers.any() );

       // check project
       assertSame( user, project.user.get() );
       assertSame( user, project.organizationOrUser() );
       assertSame( project, user.projects.iterator().next() );
       
       // check host
       assertEquals( 1, host.instances.size() );
       
       // check instance
       ProjectInstanceRecord instance = host.instances.iterator().next();
       assertTrue( host.instances.contains( instance ) );
       assertSame( host, instance.host.get() );
       assertEquals( project.name.get(), instance.project.get() );
       
       // check committed
       ProjectRepository repo2 = ProjectRepository.newInstance();
       Project project2 = repo2.entity( project );
       assertNotSame( project, project2 );
       
       assertFalse( vmRepo.lock.isWriteLockedByCurrentThread() );
   }

   
   protected ArgumentMatcher<ProjectInstanceRecord> instanceOfProject( Project p ) {
       return arg -> {
           ProjectInstanceRecord instance = (ProjectInstanceRecord)arg;
           return instance.organisation.get().equals( p.organizationOrUser().name.get() ) &&
                   instance.project.get().equals( p.name.get() );
       };
   }

   
   protected void deleteProjectOperation() throws Exception {
       HostRecord host = vmRepo.allHosts().get( 0 );
       ProjectInstanceRecord instance = host.instances.iterator().next();
       
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
       
       // check launcher call
       verify( project.launcher.get() ).uninstall( 
               Matchers.argThat( instanceOfProject( project ) ), 
               Matchers.any() );
   }
   
   
   @Test
   public void stopProcessOperation() {
   }

   
   @Test
   public void startProcessOperation() {
   }
   
}
