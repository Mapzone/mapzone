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

import java.util.Optional;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Sets;

import org.polymap.core.runtime.cache.Cache;
import org.polymap.core.runtime.cache.CacheConfig;

import io.mapzone.arena.jmx.ArenaConfigMBean;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;

/**
 * Makes sure that a request of API (/ows or /webdav) has proper auth token or user set.
 *
 * @author Falko Bräutigam
 */
public class ServiceAuthProvision
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( ServiceAuthProvision.class );

    // see io.mapzone.arena.GeoServerStarter
    public static final Set<String>         SERVICE_ALIASES = Sets.newHashSet( "/ows", "/webdav" );
    
    public static final String              REQUEST_PARAM_TOKEN = "authToken";
    
    /** */
    public static final String              REQUEST_PARAM_USER = ArenaConfigMBean.REQUEST_PARAM_USER;
    
    private static Cache<CacheKey,Boolean>  loggedIn = CacheConfig.defaults().createCache();
    
    private Context<ServiceAuthProvision>   checked;

    
    @Override
    public boolean init( Provision failed, Status cause ) {
        String pathInfo = request.get().getPathInfo();
        Optional<String> serviceRequest = SERVICE_ALIASES.stream()
                .filter( alias -> pathInfo.contains( alias ) ).findAny();
        
        return serviceRequest.isPresent() &&
                failed instanceof ForwardRequest &&
                cause == null &&
                !checked.isPresent();
    }

    
    @Override
    public Status execute() throws Exception {
        checked.set( this );
        
        if (checkAuthToken()) {
            return OK_STATUS;
        }
        else if (checkUser()) {
            return OK_STATUS;            
        }
        else {
            throw new HttpProvisionRuntimeException( 401, "No auth token specified. See the project info page for more information." );            
        }
    }


    /**
     * Check for user in URL to allow mapzone client to access service of other
     * clients.
     */
    protected boolean checkUser() {
        String user = request.get().getParameter( REQUEST_PARAM_USER );
        if (user == null) {
            return false;
        }
        log.warn( "***NOT CHECKED*** User param found: " + user + "." );
        return true;
    }
    
    
    /**
     *
     */
    protected boolean checkAuthToken() {
        // find request token
        String requestToken = request.get().getParameter( REQUEST_PARAM_TOKEN );
        if (requestToken == null) {
            return false;
        }

        // check token
        ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( request.get() );
        CacheKey key = new CacheKey( requestToken, pid );
        Boolean valid = loggedIn.get( key, _key -> {
            Project project = projectUow().findProject( pid.organization(), pid.project() )
                    .orElseThrow( () -> new HttpProvisionRuntimeException( 404, "No such project: " + pid ) );
            return project.serviceAuthToken().map( t -> t.isValid( requestToken ) ).orElse( false );
        });
        
        if (!valid) {
            throw new HttpProvisionRuntimeException( 401, "Given auth token is not valid for this project." );
        }
        return true;
    }

    
    /**
     * 
     */
    protected class CacheKey {
        
        public String                       token;
        
        public ProjectInstanceIdentifier    pid;

        public CacheKey( String token, ProjectInstanceIdentifier pid ) {
            assert token != null && pid != null;
            this.token = token;
            this.pid = pid;
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = 31 * result + token.hashCode();
            result = 31 * result + pid.hashCode();
            return result;
        }

        @Override
        public boolean equals( Object other ) {
            if (other instanceof CacheKey) {
                return token.equals( ((CacheKey)other).token ) &&
                        pid.equals( ((CacheKey)other).pid );
            }
            return false;
        }        
    }
    
}
