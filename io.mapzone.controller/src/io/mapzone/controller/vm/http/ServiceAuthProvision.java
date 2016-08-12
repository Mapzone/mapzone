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
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.runtime.cache.Cache;
import org.polymap.core.runtime.cache.CacheConfig;

import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;

/**
 * Makes sure that a request of API (/ows or /webdav) has proper auth token set.
 *
 * @author Falko Bräutigam
 */
public class ServiceAuthProvision
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( ServiceAuthProvision.class );

    public static final String[]            SERVICE_ALIASES = {"/ows", "/webdav"};
    
    public static final String              TOKEN_REQUEST_PARAM = "authToken";
    
    private static Cache<CacheKey,Boolean>  loggedIn = CacheConfig.defaults().createCache();
    
    private Context<ServiceAuthProvision>   checked;

    
    @Override
    public boolean init( Provision failed, Status cause ) {
        String pathInfo = request.get().getPathInfo();
        Optional<String> serviceRequest = Arrays.stream( SERVICE_ALIASES )
                .filter( alias -> pathInfo.contains( alias ) ).findAny();
        
        return serviceRequest.isPresent() &&
                failed instanceof ForwardRequest &&
                cause == null &&
                !checked.isPresent();
    }

    
    @Override
    public Status execute() throws Exception {
        checked.set( this );
        
        // find request token
        String requestToken = request.get().getParameter( TOKEN_REQUEST_PARAM );
        if (requestToken == null) {
            throw new HttpProvisionRuntimeException( 401, "No auth token specified. See the project info page for more information." );
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
        return OK_STATUS;
    }

    
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
