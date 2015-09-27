package io.mapzone.controller.ops;

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.Organization;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectHolder;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredInstance;
import io.mapzone.controller.vm.repository.VmRepository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.i18n.IMessages;

/**
 * Creates a new {@link Project}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CreateProjectOperation
        extends DefaultOperation {

    private static Log log = LogFactory.getLog( CreateProjectOperation.class );

    public static final IMessages i18n = Messages.forPrefix( "CreateProjectOperation" );
    
    @Mandatory
    public Config<ProjectRepository>    repo;
    
    /** Preset, not yet commited project. */
    @Mandatory
    public Config<Project>              project;
    
    @Mandatory
    public Config<ProjectHolder>        organizationOrUser;
    
    
    public CreateProjectOperation() {
        super( i18n.get( "title" ) );
        ConfigurationFactory.inject( this );
    }


    @Override
    public IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        VmRepository vmRepo = VmRepository.instance();
        try {
            monitor.beginTask( getLabel(), 10 );
            vmRepo.lock();

            // find host to use
            List<RegisteredHost> hosts = vmRepo.allHosts();
            if (hosts.isEmpty()) {
                throw new RuntimeException( "No host!" );
            }
            if (hosts.size() > 1) {
                throw new RuntimeException( "FIXME find the most suited host to run this project" );
            }
            RegisteredHost host = hosts.get( 0 );
            monitor.worked( 1 );

            // set user/org on project
            if (organizationOrUser.get() instanceof User) {
                project.get().user.set( (User)organizationOrUser.get() );
            }
            else {
                project.get().organization.set( (Organization)organizationOrUser.get() );
            }
            
            // create new instance
            RegisteredInstance instance = vmRepo.createEntity( RegisteredInstance.class, null, (RegisteredInstance proto) -> {
                proto.organisation.set( organizationOrUser.get().name.get() );
                proto.project.set( project.get().name.get() );
                return proto;
            });
            host.instances.add( instance );
            assert instance.host.get() == host;
            monitor.worked( 1 );

            // install the instance on the host
            host.runtime.get().instance( instance ).install( project.get(), new SubProgressMonitor( monitor, 8 ) );
            
            // commit
            vmRepo.commit();
            repo.get().commit();
            EventManager.instance().publish( new EntityChangedEvent( project.get() ) );
            return Status.OK_STATUS;
        }
        catch (Exception e) {
            log.warn( "", e );
            vmRepo.rollback();
            repo.get().rollback();
            throw new ExecutionException( i18n.get( "errorMsg", e.getLocalizedMessage() ), e );
        }
    }

}
