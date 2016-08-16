package io.mapzone.controller.ops;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.i18n.IMessages;

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.launcher.ArenaLauncher;
import io.mapzone.controller.um.repository.Organization;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.VmRepository;

/**
 * Initializes a newly created {@link Project} by finding a {@link HostRecord host}
 * for it and building and installing a {@link ProjectInstanceRecord instance} on
 * that host.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CreateProjectOperation
        extends UmOperation {

    private static final Log log = LogFactory.getLog( CreateProjectOperation.class );

    public static final IMessages i18n = Messages.forPrefix( "CreateProjectOperation" );
    
    /** Inbound: */
    @Mandatory
    public Config<Organization>         organization;

    /** Outbound: the newly created {@link Project}. */
    @Mandatory
    @Immutable
    public Config<Project>              project;
    
    /** Outbound: */
    @Mandatory
    @Immutable
    public Config<User>                 user;
    
    
    /**
     * Creates a new instance wirh {@link ProjectRepository#session()} set.
     */
    public CreateProjectOperation( String username ) {
        this( ProjectRepository.session(), username );
    }

    
    /** Testing. */
    public CreateProjectOperation( ProjectUnitOfWork umUow, String username ) {
        super( i18n.get( "title" ) );
        this.umUow.set( umUow );
        this.vmUow.set( VmRepository.newUnitOfWork() );
        
        user.set( umUow.findUser( username )
                .orElseThrow( () -> new RuntimeException( "No such user: " + username ) ) );
    }
    
    /** 
     * Creates the preset, not yet committed project. 
     */
    public Project createProject() {
        project.set( umUow.get().createEntity( Project.class, null, Project.defaults ) );        
        project.get().launcher.createValue( ArenaLauncher.defaults );
        return project.get();
    }
    

    @Override
    protected IStatus doWithCommit( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        assert project.isPresent();
        
        monitor.beginTask( getLabel(), 10 );

        // find host to use
        List<HostRecord> hosts = vmUow.get().allHosts();
        if (hosts.isEmpty()) {
            throw new RuntimeException( "No host!" );
        }
        if (hosts.size() > 1) {
            throw new RuntimeException( "FIXME find the most suited host to run this project" );
        }
        HostRecord host = hosts.get( 0 );
        monitor.worked( 1 );

        // set user/org on project
        project.get().organization.set( organization.get() );

        // create new instance
        ProjectInstanceRecord instance = vmUow.get().createEntity( ProjectInstanceRecord.class, null, (ProjectInstanceRecord proto) -> {
            proto.organisation.set( organization.get().name.get() );
            proto.project.set( project.get().name.get() );
            proto.host.set( host );
            return proto;
        });
        assert instance.host.get() == host;
        assert host.instances.stream().anyMatch( elm -> elm==instance );
        monitor.worked( 1 );

        // install the instance on the host
        project.get().launcher.get().install( instance, new SubProgressMonitor( monitor, 8 ) );
        return Status.OK_STATUS;
    }

        
    @Override
    protected void onSuccess() {
        vmUow.get().close();
        super.onSuccess();
    }


    @Override
    protected void onError( Throwable e ) {
        vmUow.get().close();
        super.onError( e );
    }

}
