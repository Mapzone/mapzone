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
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.i18n.IMessages;

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.Organization;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.um.repository.UserRole;
import io.mapzone.controller.um.xauth.PasswordEncryptor;

/**
 * Creates the given user.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CreateUserOperation
        extends UmOperation {

    private static Log log = LogFactory.getLog( CreateUserOperation.class );

    public static final IMessages i18n = Messages.forPrefix( "NewUserOperation" );
    
    @Mandatory
    @Immutable
    public Config<User>                 user;

    @Mandatory
    public Config<String>               password;

    
    /**
     * Creates a new instance wirh {@link ProjectRepository#session()} set.
     */
    public CreateUserOperation() {
        super( i18n.get( "title" ) );
        umUow.set( ProjectRepository.session() );
    }

    /** Testing. */
    public CreateUserOperation( ProjectUnitOfWork umUow ) {
        super( i18n.get( "title" ) );
        this.umUow.set( umUow );
    }

    
    public User createEntity() {
        user.set( umUow.get().createEntity( User.class, null, User.defaults ) );
        return user.get();
    }

    
    @Override
    public IStatus doWithCommit( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        // userOrg
        Organization userOrg = umUow.get().createEntity( Organization.class, null, (Organization proto) -> {
            Organization.defaults.initialize( proto );
            proto.name.set( user.get().name.get() );
            return proto;
        });
        umUow.get().createEntity( UserRole.class, null, UserRole.defaults( user.get(), userOrg ) );

        // password hash
        PasswordEncryptor encryptor = PasswordEncryptor.instance();
        String hash = encryptor.encryptPassword( password.get() );
        user.get().passwordHash.set( hash );

        return Status.OK_STATUS;
    }

    
    @Override
    protected void onSuccess() {
        EventManager.instance().publish( new EntityChangedEvent( user.get() ) );
    }

}
