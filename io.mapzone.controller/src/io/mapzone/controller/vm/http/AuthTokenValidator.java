/* 
 * mapzone.io
 * Copyright (C) 2016-2018, the @authors. All rights reserved.
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

import java.util.Enumeration;
import java.util.Optional;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.polymap.core.runtime.cache.Cache;
import org.polymap.core.runtime.cache.CacheConfig;

import io.mapzone.controller.um.repository.AuthToken;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;

/**
 * Validates the {@link AuthToken} in a {@link HttpServletRequest}.
 *
 * @author Falko Bräutigam
 */
public class AuthTokenValidator {

    private static final Log log = LogFactory.getLog( AuthTokenValidator.class );

    public static final String              REQUEST_PARAM_TOKEN = "authToken";
    
    private static Cache<CacheKey,Boolean>  validTokens = CacheConfig.defaults().createCache();


    public static void revoke( String token ) {
        for (CacheKey key : validTokens.keySet()) {
            if (key.token.equals( token )) {
                validTokens.remove( key );
                break;
            }
        }
    }

    // instance *******************************************
    
    private Supplier<ProjectUnitOfWork>     uow;
    
    private HttpServletRequest              request;
    
    
    public AuthTokenValidator( Supplier<ProjectUnitOfWork> uow, HttpServletRequest request ) {
        this.uow = uow;
        this.request = request;
    }


    /**
     * 
     *
     * @throws HttpProvisionRuntimeException If project does not exists.
     */
    public boolean checkAuthToken() {
        ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( request );
        
        // check param token; ignore parameter char case
        Optional<String> requestToken = requestParameter( REQUEST_PARAM_TOKEN );
        if (requestToken.isPresent()) {
            if (!isValidToken( requestToken.get(), pid )) {
                throw new HttpProvisionRuntimeException( 401, "Given auth token is not valid for this project." );
            }
            return true;
        }
        
        // check path parts
        String path = StringUtils.defaultString( request.getPathInfo() );
        for (String part : StringUtils.split( path, '/' )) {
            if (isValidToken( part, pid )) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     *
     * @param token
     * @param pid
     * @return
     * @throws HttpProvisionRuntimeException If project does not exists.
     */
    protected Boolean isValidToken( String token, ProjectInstanceIdentifier pid ) {
        CacheKey key = new CacheKey( token, pid );
        return validTokens.get( key, _key -> {
            Project project = uow.get().findProject( pid.organization(), pid.project() )
                    .orElseThrow( () -> new HttpProvisionRuntimeException( 404, "No such project: " + pid ) );
            Optional<AuthToken> projectToken = project.serviceAuthToken();
            return projectToken.isPresent() ? projectToken.get().isValid( token ) : false;
        });    
    }

    
    /**
     * Case insensive param search. geotools's WebMapServer automatically changes all
     * params to upper case.
     *
     * @param name
     * @return
     */
    protected Optional<String> requestParameter( String name ) {
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (paramName.equalsIgnoreCase( name )) {
                return Optional.ofNullable( request.getParameter( name ) );
            }
        }
        return Optional.empty();
    }
    
    
    /**
     * 
     */
    protected static class CacheKey {
        
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
