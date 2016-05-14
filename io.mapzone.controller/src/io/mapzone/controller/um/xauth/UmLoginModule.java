package io.mapzone.controller.um.xauth;

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

import java.util.Map;
import java.util.Optional;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.security.AuthorizationModule;
import org.polymap.core.security.ServicesCallbackHandler;
import org.polymap.core.security.UserPrincipal;

/**
 * Provides authentication based on the information stored in the currently active
 * user repository.
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UmLoginModule
        implements LoginModule {

    private static Log log = LogFactory.getLog( UmLoginModule.class );

    public static final IMessages           i18n = Messages.forPrefix( "UmLoginModule" );
    
    private CallbackHandler                 callbackHandler;

    private Subject                         subject;
    
    private UserPrincipal                   principal;
    
    private String                          dialogTitle = "Mapzone";
    
    private AuthorizationModule             authModule;
    
    protected ProjectRepository             repo;
    
    private boolean                         loggedIn;

    
    @Override
    @SuppressWarnings("hiding")
    public void initialize( Subject subject, CallbackHandler callbackHandler, 
            Map<String,?> sharedState, Map<String,?> options ) {
        this.repo = ProjectRepository.newInstance();
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        if (this.callbackHandler == null) {
            log.warn( "No callbackHandler!?" );
            this.callbackHandler = new ServicesCallbackHandler();
        }
        
        this.authModule = new UmAuthorizationModule();
        this.authModule.init( this );
        
        // check user/passwd settings in options
        for (Object elm : options.entrySet()) {
            Map.Entry<String,String> option = (Map.Entry)elm;
            log.debug( "option: key=" + option.getKey() + " = " + option.getValue() );
        }
    }


    @Override
    public boolean login() throws LoginException {
        Callback label = new TextOutputCallback( TextOutputCallback.INFORMATION, dialogTitle );
        NameCallback nameCallback = new NameCallback( "Username", "default" );
        PasswordCallback passwordCallback = new PasswordCallback( "Password", false );
        try {
            callbackHandler.handle( new Callback[] { label, nameCallback, passwordCallback } );
        }
        catch (Exception e) {
            log.warn( "", e );
            throw new LoginException( e.getLocalizedMessage() );
        }

        String username = nameCallback.getName();
        String password = passwordCallback.getPassword() != null
                ? String.valueOf( passwordCallback.getPassword() )
                : "empty_password_is_not_allowed";
        
        // admin
        if (username == null || username.equals( "admin" )) {
            // FIXME read password hash from persistent storage and check
            log.warn( "!!! NO PASSWORD check for admin user yet !!!!!!" );
            principal = new UserPrincipal( "admin" );
            return loggedIn = true;                
        }

        // ordinary user
        Optional<User> user = repo.findUser( username );

        if (user.isPresent()) {
            PasswordEncryptor encryptor = PasswordEncryptor.instance();
            if (encryptor.checkPassword( password, user.get().passwordHash.get() )) {
                log.info( "username: " + user.get().name.get() );
                principal = new UmUserPrincipal( user.get() );
                return loggedIn = true;
            }
        }
        throw new FailedLoginException( "Username or password not correct." );
    }


    @Override
    public boolean commit() throws LoginException {
        subject.getPrincipals().add( principal );

        subject.getPrivateCredentials().add( this );
        subject.getPrivateCredentials().add( authModule );
        
        return loggedIn;
    }


    @Override
    public boolean abort() throws LoginException {
        loggedIn = false;
        return true;
    }


    @Override
    public boolean logout() throws LoginException {
        loggedIn = false;
        return true;
    }
    
    
    /**
     * 
     */
    public class UmUserPrincipal
            extends UserPrincipal {

        public UmUserPrincipal( User user ) {
            super( user.name.get() );
        }

    }

}
