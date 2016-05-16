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
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.VmRepository;
import io.mapzone.controller.vm.runtime.HostRuntime;

/**
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OperationsTest {
   
    protected static ProjectRepository      repo;
    
    protected static ProjectRepository      nested;

    protected static User                   user;
    
    protected static Project                project;

    
    @BeforeClass
    public static void setup() throws Exception {
        File basedir = new File( "/tmp/mapzone-test" );
        basedir.mkdirs();
        FileUtils.cleanDirectory( basedir );
        
        VmRepository.init( basedir );
        ProjectRepository.init( basedir );
        
        repo = ProjectRepository.newInstance();
        nested = repo.newNested();
    }


    @Test
    public void _1_createUser() throws Exception {
        user = nested.createEntity( User.class, null, (User proto) -> {
            User.defaults.initialize( proto );
            proto.name.set( "test" );
            return proto;
        });
        CreateUserOperation op = new CreateUserOperation( nested, user, "geheim" );

        op.doExecute( new NullProgressMonitor(), null );
        assertEquals( "test", user.userOrg().name.get() );
    }

    
//    @Test
    public void _4_deleteUser() {
        // yet to be implemented and tested
    }


    @Test
    public void _2_createProjectOperation() throws Exception {
       // check host
        VmRepository vmRepo = VmRepository.newInstance();
        assertEquals( 1, vmRepo.allHosts().size() );
        HostRecord host = vmRepo.allHosts().get( 0 );

        // mock runtime
        HostRuntime mockedHostRuntime = mock( HostRuntime.class );
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

        //       User user = nested.entity( defaultUser );

        // operation
        CreateProjectOperation op = new CreateProjectOperation();
        op.repo.set( nested );
        op.project.set( project );
        op.organization.set( user.userOrg() );
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
        ProjectRepository repo2 = ProjectRepository.newInstance();
        Project project2 = repo2.entity( project );
        assertNotSame( project, project2 );
        
        vmRepo.commit();
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
        op.repo.set( nested );
        op.project.set( project );
        op.doExecute( new NullProgressMonitor(), null );

        // check user projects
        assertEquals( 0, user.userOrg().projects.size() );

        // check host instances
        VmRepository vmRepo = VmRepository.newInstance();
        HostRecord host = vmRepo.allHosts().get( 0 );
        assertTrue( host.instances.isEmpty() );

        // check launcher call
        verify( project.launcher.get() ).uninstall( 
                Matchers.argThat( instanceOfProject( project ) ), 
                Matchers.any() );
        
        vmRepo.commit();
    }


    // @Test
    public void stopProcessOperation() {
    }


    // @Test
    public void startProcessOperation() {
    }
   
}
