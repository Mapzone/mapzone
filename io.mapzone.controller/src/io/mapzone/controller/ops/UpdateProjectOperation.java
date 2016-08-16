package io.mapzone.controller.ops;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.i18n.IMessages;

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;

/**
 * Initializes a newly created {@link Project} by finding a {@link HostRecord host}
 * for it and building and installing a {@link ProjectInstanceRecord instance} on
 * that host.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UpdateProjectOperation
        extends UmOperation {

    private static final Log log = LogFactory.getLog( UpdateProjectOperation.class );

    public static final IMessages i18n = Messages.forPrefix( "CreateProjectOperation" );
    
    /** Inbound: the {@link Project} to be updated. */
    @Mandatory
    public Config<Project>              project;
    
    /** Outbound: */
    @Mandatory
    @Immutable
    public Config<User>                 user;

    
    /**
     * Creates a new instance wirh {@link ProjectRepository#session()} set.
     */
    public UpdateProjectOperation( String username ) {
        super( i18n.get( "title" ) );
        umUow.set( ProjectRepository.session() );

        user.set( umUow.get().findUser( username )
                .orElseThrow( () -> new RuntimeException( "No such user: " + username ) ) );
    }

    
    @Override
    protected IStatus doWithCommit( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        assert project.isPresent();
        assert project.get().belongsTo( umUow.get() );
        
        return Status.OK_STATUS;
    }
        
}
