package io.mapzone.controller.um.repository;

import static org.polymap.model2.query.Expressions.eq;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.rap.rwt.RWT;

import org.polymap.model2.Association;
import org.polymap.model2.Entity;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class LoginCookie
        extends Entity {

    private static Log log = LogFactory.getLog( LoginCookie.class );

    public static LoginCookie       TYPE;

    public static final String      COOKIE_NAME = "user_token";
    public static final String      COOKIE_PATH = "/dashboard";
    
    private static Random           rand = new Random();
    

    public static String newValue() {
        byte[] bytes = new byte[8];
        rand.nextBytes( bytes );
        return Base64.encodeBase64URLSafeString( bytes );
    }

    /**
     * 
     */
    public static Optional<LoginCookie> findAndUpdate( ProjectRepository repo ) {
        return Arrays.stream( RWT.getRequest().getCookies() )
                .filter( c -> c.getName().equals( COOKIE_NAME ) )
                .findAny()
                .map( cookie -> {
                    log.info( "Found: " + cookie.getValue() );
                    
                    List<LoginCookie> storedCookies = repo.query( LoginCookie.class )
                            .where( eq( LoginCookie.TYPE.value, cookie.getValue() ) )
                            .execute().stream().collect( Collectors.toList() );

                    if (storedCookies.isEmpty()) {
                        log.info( "    No such cookie stored!" );
                        return null;
                    }
                    else if (storedCookies.size() > 1) {
                        log.warn( "    More than one cooky stored!" );
                    }

                    LoginCookie storedCookie = storedCookies.get( 0 );
                    String newValue = newValue();
                    assert repo.query( LoginCookie.class ).where( eq( LoginCookie.TYPE.value, newValue ) ).execute().size() == 0;

                    storedCookie.value.set( newValue );
                    repo.commit();

                    sendCookie( newValue );
                    return storedCookie;
                });
    }

    /**
     * 
     */
    public static void create( ProjectRepository repo, String username ) {
        User _user = repo.findUser( username ).orElseThrow( () -> new RuntimeException( "No such user: " + username ) );
        String newValue = newValue();
        assert repo.query( LoginCookie.class ).where( eq( LoginCookie.TYPE.value, newValue ) ).execute().size() == 0;
        
        repo.createEntity( LoginCookie.class, null, (LoginCookie proto) -> {
            proto.value.set( newValue );
            proto.user.set( _user );
            return proto;
        });
        repo.commit();

        sendCookie( newValue );
    }
    
    /**
     * 
     */
    public static void destroy( ProjectRepository repo ) {
        Arrays.stream( RWT.getRequest().getCookies() )
                .filter( cookie -> cookie.getName().equals( COOKIE_NAME ) )
                .forEach( cookie -> {
                    log.info( "Found: " + cookie.getValue() );
                    
                    Iterable<LoginCookie> storedCookies = repo.query( LoginCookie.class )
                            .where( eq( LoginCookie.TYPE.value, cookie.getValue() ) )
                            .execute();

                    for (LoginCookie storedCookie : storedCookies) {
                        repo.removeEntity( storedCookie );
                    }
                    repo.commit();
                });
    }

    /**
     * 
     */
    protected static void sendCookie( String value ) {
        Cookie cookie = new Cookie( COOKIE_NAME, value );
        cookie.setHttpOnly( true );
        cookie.setPath( COOKIE_PATH );
        cookie.setSecure( false ); // XXX
        cookie.setMaxAge( 30 * 24 * 3600 );
        RWT.getResponse().addCookie( cookie );
        log.info( "Set: value=" + cookie.getValue() + ", path=" + cookie.getPath() + ", maxAge=" + cookie.getMaxAge() );
        
    }
    
    
    // instance *******************************************
    
    @Queryable
    public Property<String>         value;
    
    public Association<User>        user;
    
}
