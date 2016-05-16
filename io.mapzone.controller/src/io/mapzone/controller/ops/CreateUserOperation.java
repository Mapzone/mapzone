package io.mapzone.controller.ops;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.i18n.IMessages;

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.Organization;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.um.repository.UserRole;
import io.mapzone.controller.um.xauth.PasswordEncryptor;

/**
 * Creates the given user.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CreateUserOperation
        extends DefaultOperation {

    private static Log log = LogFactory.getLog( CreateUserOperation.class );

    public static final IMessages i18n = Messages.forPrefix( "NewUserOperation" );
    
    @Mandatory
    public Config<ProjectRepository>    repo;
    
    @Mandatory
    public Config<User>                 user;

    @Mandatory
    public Config<String>               password;

    
    public CreateUserOperation( ProjectRepository repo, User user, String password  ) {
        super( i18n.get( "title" ) );
        ConfigurationFactory.inject( this );
        this.user.set( user );
        this.password.set( password );
        this.repo.set( repo );
    }


    @Override
    public IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        try {
            // userOrg
            Organization userOrg = repo.get().createEntity( Organization.class, null, (Organization proto) -> {
                Organization.defaults.initialize( proto );
                proto.name.set( user.get().name.get() );
                return proto;
            });
            repo.get().createEntity( UserRole.class, null, UserRole.defaults( user.get(), userOrg ) );
            
            // password hash
            PasswordEncryptor encryptor = PasswordEncryptor.instance();
            String hash = encryptor.encryptPassword( password.get() );
            user.get().passwordHash.set( hash );

            // commit
            repo.get().commit();
            EventManager.instance().publish( new EntityChangedEvent( user.get() ) );

            return Status.OK_STATUS;
        }
        catch (Exception e) {
            throw new ExecutionException( i18n.get( "errorMsg", e.getLocalizedMessage() ), e );
        }        
    }

}
