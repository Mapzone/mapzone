package io.mapzone.controller.um.repository;

import static org.polymap.model2.query.Expressions.eq;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public static final String      COOKIE_PATH = "/";
    public static final int         COOKIE_MAX_AGE = (int)TimeUnit.DAYS.toSeconds( 30 );
    
    private static Random           rand = new Random();

    
    public static Access access( ) {
        return new Access( ProjectRepository.newInstance(), RWT.getRequest(), RWT.getResponse() );
    }

    public static Access access( HttpServletRequest request, HttpServletResponse response ) {
        return new Access( ProjectRepository.newInstance(), request, response );
    }

    /**
     * 
     */
    public static class Access {

        private ProjectRepository           repo;
        
        private HttpServletRequest          request;
        
        private HttpServletResponse         response;
        
        
        public Access( ProjectRepository repo, HttpServletRequest request, HttpServletResponse response ) {
            this.repo = repo;
            this.request = request;
            this.response = response;
        }


        public Optional<LoginCookie> findAndUpdate(  ) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals( COOKIE_NAME )) {
                    log.info( "Found: " + cookie.getValue() );

                    List<LoginCookie> storedCookies = repo.query( LoginCookie.class )
                            .where( eq( LoginCookie.TYPE.value, cookie.getValue() ) )
                            .execute().stream().collect( Collectors.toList() );

                    if (storedCookies.size() > 1) {
                        log.warn( "    More than one cookie stored!" );
                    }
                    else if (!storedCookies.isEmpty()) {
                        LoginCookie storedCookie = storedCookies.get( 0 );
                        String token = newToken();
                        assert repo.query( LoginCookie.class ).where( eq( LoginCookie.TYPE.value, token ) ).execute().size() == 0;

                        storedCookie.value.set( token );
                        repo.commit();

                        sendCookie( token );
                        return Optional.of( storedCookie );
                    }
                }
            }
            return Optional.empty();
        }


        public void create( String username ) {
            User _user = repo.findUser( username ).orElseThrow( () -> new RuntimeException( "No such user: " + username ) );
            String token = newToken();
            assert repo.query( LoginCookie.class ).where( eq( LoginCookie.TYPE.value, token ) ).execute().size() == 0;

            repo.createEntity( LoginCookie.class, null, (LoginCookie proto) -> {
                proto.value.set( token );
                proto.user.set( _user );
                return proto;
            });
            repo.commit();

            sendCookie( token );
        }


        public void destroy() {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals( COOKIE_NAME )) {
                    log.info( "Found: " + cookie.getValue() );

                    Iterable<LoginCookie> storedCookies = repo.query( LoginCookie.class )
                            .where( eq( LoginCookie.TYPE.value, cookie.getValue() ) )
                            .execute();

                    for (LoginCookie storedCookie : storedCookies) {
                        repo.removeEntity( storedCookie );
                    }
                    repo.commit();
                }
            }
        }

        
        protected void sendCookie( String cookieValue ) {
            Cookie cookie = new Cookie( COOKIE_NAME, cookieValue );
            cookie.setHttpOnly( true );
            cookie.setPath( COOKIE_PATH );
            cookie.setSecure( false ); // XXX
            cookie.setMaxAge( COOKIE_MAX_AGE );
            response.addCookie( cookie );
            log.info( "Set: value=" + cookie.getValue() + ", path=" + cookie.getPath() + ", maxAge=" + cookie.getMaxAge() );
        }


        protected String newToken() {
            byte[] bytes = new byte[8];
            rand.nextBytes( bytes );
            return Base64.encodeBase64URLSafeString( bytes );
        }
    }    
    
    
    // instance *******************************************
    
    /** The token of the cookie. */
    @Queryable
    public Property<String>         value;
    
    public Association<User>        user;
    
}
