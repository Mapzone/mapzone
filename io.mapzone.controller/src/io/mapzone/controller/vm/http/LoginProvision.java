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
package io.mapzone.controller.vm.http;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.eclipse.rap.rwt.RWT;

import org.polymap.core.ui.UIUtils;

import io.mapzone.controller.LoginAppDesign;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.ProvisionRuntimeException;
import io.mapzone.controller.um.repository.LoginCookie;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.User;

/**
 * Makes sure that the user is properly logged in.
 *
 * @author Falko Bräutigam
 */
public class LoginProvision
        extends HttpProxyProvision {

    private static final Log log = LogFactory.getLog( LoginProvision.class );

    public static final String          COOKIE_NAME = "lp";
    public static final String          COOKIE_PATH = ProxyServlet.SERVLET_ALIAS;
    public static final int             COOKIE_MAX_AGE = -1;  //(int)TimeUnit.DAYS.toSeconds( 1 );

    /** Maps the generated random handle into {@link User} id. */
    private static Map<String,String>   loggedIn = new ConcurrentHashMap( 256, 0.75f, 4 );

    private static Random               rand = new Random();

    private Context<LoginProvision>     checked;

    
    @Override
    public boolean init( Provision failed, Status cause ) {
        // check only UI requests (skip rwt-resources/generated/) in order to
        // not do UI login for service/API requests (/ows, /webdav)
        // XXX use Project#servletAlias
        return request.get().getPathInfo().endsWith( Project.DEFAULT_SERVLET_ALIAS ) &&
                failed instanceof ForwardRequest &&
                cause == null &&
                !checked.isPresent();
    }

    
    @Override
    public Status execute() throws Exception {
        checked.set( this );
        
        // check authToken
        AuthTokenValidator atv = new AuthTokenValidator( () -> projectUow(), request.get() );
        if (atv.checkAuthToken()) {
            String clientHost = request.get().getRemoteHost();
            log.info( "AuthToken: valid, for client: " + clientHost );
            registerUser( clientHost, response.get() );
            // who we are? does arena need a user?
            return OK_STATUS;
        }
        
        // cookie -> OK
        Optional<Cookie> cookie = Arrays.stream( request.get().getCookies() )
                .filter( c -> c.getName().equals( COOKIE_NAME ) ).findAny();
        if (cookie.isPresent()) {
            // XXX validate cookie value
            String userId = loggedIn.get( cookie.get().getValue() );
            if (userId != null) {
                return OK_STATUS;
            }
        }

        // check /dashboard login
        Optional<LoginCookie> loginCookie = LoginCookie.access( request.get(), response.get() ).findAndUpdate();
        if (loginCookie.isPresent()) {
            registerUser( (String)loginCookie.get().user.get().id(), response.get() );
            return OK_STATUS;            
        }
        
        // no cookie -> /login
        String requestUrl = Joiner.on( "" ).skipNulls().join(
                request.get().getServletPath(),
                request.get().getPathInfo(),
                "?", request.get().getQueryString() );
        String handlerId = LoginAppDesign.registerHandler( user -> {
            try {
                registerUser( (String)user.id(), RWT.getResponse() );
                UIUtils.exec( "window.location=\"", requestUrl, "\";" );
            }
            catch (Exception e) {
                throw new RuntimeException( e );
            }
        });
        // http://stackoverflow.com/questions/30844807/can-a-redirect-to-relative-path-be-sent-from-java-servlet-api
        // response.get().sendRedirect( "../../../login?id=" + handlerId );
        response.get().setStatus( HttpServletResponse.SC_MOVED_TEMPORARILY );
        response.get().setHeader( "Location", "../../../login?id=" + handlerId );
        throw new ProvisionRuntimeException();
    }

    
    protected void registerUser( String userId, @SuppressWarnings( "hiding" ) HttpServletResponse response ) {
        // cookie token
        byte[] bytes = new byte[8];
        rand.nextBytes( bytes );
        String token = Base64.encodeBase64URLSafeString( bytes );
        
        // FIXME Leak: entries are never removed (allow just one cookie/session per user?)
        if (loggedIn.putIfAbsent( token, userId ) != null) {
            throw new IllegalStateException( "Token already exists: " + token );
        }
        
        // set cookie
        Cookie newCookie = new Cookie( COOKIE_NAME, token );
        newCookie.setHttpOnly( true );
        newCookie.setPath( COOKIE_PATH );
        newCookie.setSecure( false ); // XXX
        newCookie.setMaxAge( COOKIE_MAX_AGE );
        response.addCookie( newCookie );
    }
    
}
