package io.mapzone.controller.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import org.apache.commons.io.FileUtils;

import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.model2.Property;
import org.polymap.model2.runtime.PropertyInfo;
import org.polymap.model2.runtime.ValueInitializer;

import io.mapzone.controller.ops.CreateProjectOperation;
import io.mapzone.controller.ops.CreateUserOperation;
import io.mapzone.controller.ops.DeleteProjectOperation;
import io.mapzone.controller.um.launcher.ProjectLauncher;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.VmRepository;
import io.mapzone.controller.vm.repository.VmRepository.VmUnitOfWork;
import io.mapzone.controller.vm.runtime.HostRuntime;

/**
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OperationsTest {
   
    protected static ProjectUnitOfWork      uow;
    
    protected static User                   user;
    
    protected static Project                project;

    
    @BeforeClass
    public static void setup() throws Exception {
        File basedir = new File( "/tmp/mapzone-test" );
        basedir.mkdirs();
        FileUtils.cleanDirectory( basedir );
        
        VmRepository.init( basedir );
        ProjectRepository.init( basedir );
        
        uow = ProjectRepository.newUnitOfWork();
    }


    @Test
    public void _1_createUser() throws Exception {
        CreateUserOperation op = new CreateUserOperation( uow );
        user = op.createEntity();
        op.user.get().name.set( "test" );
        op.password.set( "geheim" );

        op.doExecute( new NullProgressMonitor(), null );
    }

    
//    @Test
    public void _4_deleteUser() {
        // yet to be implemented and tested
    }


    @Test
    public void _2_createProjectOperation() throws Exception {
       // check host
        VmUnitOfWork vmUow = VmRepository.newUnitOfWork();
        assertEquals( 1, vmUow.allHosts().size() );
        HostRecord host = vmUow.allHosts().get( 0 );

        // mock runtime
        HostRuntime mockedHostRuntime = mock( HostRuntime.class );
        vmUow.allHosts().stream()
                .forEach( h -> h.runtime = new LockedLazyInit( () -> mockedHostRuntime ) );

        //       User user = nested.entity( defaultUser );

        // operation
        CreateProjectOperation op = new CreateProjectOperation( uow, user.name.get() );

        // prepare project
        project = op.createProject();
        op.project.get().organization.set( user.userOrg() );
        op.project.get().name.set( "cool" );
        op.project.get().launcher = new Property<ProjectLauncher>() {
            ProjectLauncher mock = mock( ProjectLauncher.class );
            public PropertyInfo info() { throw new RuntimeException( "not yet implemented." ); }
            public ProjectLauncher get() { return mock; }
            public <U extends ProjectLauncher> U createValue( ValueInitializer<U> initializer ) { throw new RuntimeException( "not yet implemented." ); }
            public void set( ProjectLauncher value ) { throw new RuntimeException( "not yet implemented." ); }
        };
        op.doExecute( new NullProgressMonitor(), null );

        // check launcher call
        verify( project.launcher.get() ).install( 
                Matchers.argThat( instanceOfProject( project ) ), 
                Matchers.any() );

        // check project
        assertSame( user.userOrg(), project.organization.get() );
        assertSame( project, user.userOrg().projects.iterator().next() );

        // check host
        assertEquals( 1, host.instances.size() );

        // check instance
        ProjectInstanceRecord instance = host.instances.iterator().next();
        assertTrue( host.instances.contains( instance ) );
        assertSame( host, instance.host.get() );
        assertEquals( project.name.get(), instance.project.get() );

        // check committed
        ProjectUnitOfWork uow2 = ProjectRepository.newUnitOfWork();
        Project project2 = uow2.entity( project );
        assertNotSame( project, project2 );
        
        vmUow.commit();
    }


    protected ArgumentMatcher<ProjectInstanceRecord> instanceOfProject( Project p ) {
        return arg -> {
            ProjectInstanceRecord instance = (ProjectInstanceRecord)arg;
            return instance.organisation.get().equals( p.organization.get().name.get() ) &&
                    instance.project.get().equals( p.name.get() );
        };
    }

   
    @Test
    public void _3_deleteProjectOperation() throws Exception {
        // operation (no process to stop)
        DeleteProjectOperation op = new DeleteProjectOperation();
        op.umUow.set( uow );
        op.project.set( project );
        op.doExecute( new NullProgressMonitor(), null );

        // check user projects
        assertEquals( 0, user.userOrg().projects.size() );

        // check host instances
        VmUnitOfWork vmUow = VmRepository.newUnitOfWork();
        HostRecord host = vmUow.allHosts().get( 0 );
        assertTrue( host.instances.isEmpty() );

        // check launcher call
        verify( project.launcher.get() ).uninstall( 
                Matchers.argThat( instanceOfProject( project ) ), 
                Matchers.any() );
        
        vmUow.commit();
    }


    // @Test
    public void stopProcessOperation() {
    }


    // @Test
    public void startProcessOperation() {
    }
   
}
