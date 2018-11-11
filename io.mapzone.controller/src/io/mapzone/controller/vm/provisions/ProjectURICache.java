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
package io.mapzone.controller.vm.provisions;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.polymap.core.runtime.cache.Cache;
import org.polymap.core.runtime.cache.CacheConfig;

import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.http.ForwardRequest;
import io.mapzone.controller.vm.http.HttpProvisionRuntimeException;
import io.mapzone.controller.vm.http.HttpProxyProvision;
import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;

/**
 * Computes and caches the upstream {@link URI} of a project which is encoded in the
 * {@link HttpProxyProvision#request}.
 * 
 * @author Falko Bräutigam
 */
public class ProjectURICache
        extends HttpProxyProvision {

    private static final Log log = LogFactory.getLog( ProjectURICache.class );

    public static final String              NO_HOST = "_no_host_";

    private static Cache<ProjectInstanceIdentifier,Object> projectInstanceIds = CacheConfig.defaults().createCache();

    // instance *******************************************
    
    /** Outbound: The URI to be forwarded by {@link ForwardRequest}. */
    private Context<URI>                    projectUri;

    /** Outbound: Set if project URI was not in cache. */
    private Context<ProjectInstanceRecord>  instance;
    
    /** Outbound: Set if project URI was not in cache. */
    private Context<ProcessRecord>          process;
    
    private Context<ProjectURICache>        checked;


    @Override
    public boolean init( Provision failed, Status cause ) {        
        return failed instanceof ForwardRequest
                && !projectUri.isPresent()
                && !checked.isPresent();
    }

    
    @Override
    public Status execute() throws Exception {
        ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( request.get() );
        
        Object id = projectInstanceIds.get( pid, key -> {
            return vmUow().findInstance( pid )
                    .orElseThrow( () -> new HttpProvisionRuntimeException( 404, "No such project: " + pid ) )
                    .id();
        });
        instance.set( vmUow().entity( ProjectInstanceRecord.class, id ) );

        // keep instance and process stable
        instance.get().homePath.get();  // force (pessimistic) lock
        if (instance.get().process.isPresent()) {
            log.debug( "PROCESS is present! URI: " + instance.get().uri() );
            process.set( instance.get().process.get() );
            projectUri.set( instance.get().uri() );
        }
        
        checked.set( this );
        return OK_STATUS;
    }
    
}
