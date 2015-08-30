package io.mapzone.controller.um.operations;

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.um.xauth.PasswordEncryptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.i18n.IMessages;

/**
 * Creates the given user in the ...
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class NewUserOperation
        extends AbstractOperation
        implements IUndoableOperation {

    private static Log log = LogFactory.getLog( NewUserOperation.class );

    public static final IMessages i18n = Messages.forPrefix( "NewUserOperation" );

    private Config<User>            user;

    private Config<String>          password;

    
    public NewUserOperation( User user, String password  ) {
        super( i18n.get( "title" ) );
        ConfigurationFactory.inject( this );
        this.user.set( user );
        this.password.set( password );
    }


    @Override
    public IStatus execute( IProgressMonitor monitor, IAdaptable info ) throws ExecutionException {
        try {
            // password hash
            PasswordEncryptor encryptor = PasswordEncryptor.instance();
            String hash = encryptor.encryptPassword( password.get() );
            user.get().passwordHash.set( hash );

            // commit
            user.get().unitOfWork().commit();        
            return Status.OK_STATUS;
        }
        catch (Exception e) {
            // rollback
            user.get().unitOfWork().rollback();
            throw new ExecutionException( i18n.get( "errorMsg", e.getLocalizedMessage() ), e );
        }        
    }

    
    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public IStatus undo( IProgressMonitor monitor, IAdaptable info ) throws ExecutionException {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public IStatus redo( IProgressMonitor monitor, IAdaptable info ) throws ExecutionException {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

}
