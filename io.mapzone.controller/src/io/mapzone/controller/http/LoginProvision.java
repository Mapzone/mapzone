/* 
 * mapzone.io
 * Copyright (C) 2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.controller.http;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.rap.rwt.RWT;
import org.polymap.core.ui.UIUtils;

import io.mapzone.controller.LoginAppDesign;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.StopProvisionExecutionException;
import io.mapzone.controller.um.repository.User;

/**
 * Makes sure that the user is properly logged in.
 *
 * @author Falko Bräutigam
 */
public class LoginProvision
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( LoginProvision.class );

    public static final String          COOKIE_NAME = "lp";
    public static final String          COOKIE_PATH = ProxyServlet.SERVLET_ALIAS;
    public static final int             COOKIE_MAX_AGE = -1;  //(int)TimeUnit.DAYS.toSeconds( 1 );

    /** Maps the generated random handle into {@link User} id. */
    private static Map<String,String>   loggedIn = new ConcurrentHashMap( 256, 0.75f, 4 );

    private static Random               rand = new Random();

    private Context<LoginProvision>     checked;

    
    @Override
    public boolean init( Provision failed, Status cause ) {
        return failed instanceof ForwardRequest
                && cause == null
                && !checked.isPresent();
    }

    
    @Override
    public Status execute() throws Exception {
        checked.set( this );
        
        // find cookie
        Optional<Cookie> cookie = Arrays.stream( request.get().getCookies() )
                .filter( c -> c.getName().equals( COOKIE_NAME ) )
                .findAny();
        
        // cookie -> OK
        if (cookie.isPresent()) {
            String userId = loggedIn.get( cookie.get().getValue() );
            if (userId != null) {
                log.info( "User:" + userId );
                return OK_STATUS;
            }
        }
        
        // no cookie -> /login
        String requestUrl = request.get().getRequestURL().toString();
        String handlerId = LoginAppDesign.registerHandler( user -> {
            try {
                log.info( "Logged in: " + user );
                registerUser( user );
                UIUtils.exec( "window.location=\"", requestUrl, "\";" );
            }
            catch (Exception e) {
                throw new RuntimeException( e );
            }
        });
        response.get().sendRedirect( "/login?id=" + handlerId );
        throw new StopProvisionExecutionException();
    }

    
    protected void registerUser( User user ) {
        //
        byte[] bytes = new byte[8];
        rand.nextBytes( bytes );
        String handle = Base64.encodeBase64URLSafeString( bytes );
        
        // FIXME Leak: entries are never removed (allow just one cookie/session per user?)
        if (loggedIn.putIfAbsent( handle, (String)user.id() ) != null) {
            throw new IllegalStateException( "Handle already exists: " + handle );
        }
        
        // set cookie
        Cookie newCookie = new Cookie( COOKIE_NAME, handle );
        newCookie.setHttpOnly( true );
        newCookie.setPath( COOKIE_PATH );
        newCookie.setSecure( false ); // XXX
        newCookie.setMaxAge( COOKIE_MAX_AGE );
        RWT.getResponse().addCookie( newCookie );
    }
    
}
