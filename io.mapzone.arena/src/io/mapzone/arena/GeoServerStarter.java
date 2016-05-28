/* 
 * polymap.org
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
package io.mapzone.arena;

import java.util.function.Supplier;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.data.pipeline.DataSourceDescription;
import org.polymap.core.data.pipeline.Pipeline;
import org.polymap.core.data.pipeline.PipelineProcessor;
import org.polymap.core.project.ILayer;
import org.polymap.core.project.IMap;

import org.polymap.service.geoserver.GeoServerServlet;
import org.polymap.service.geoserver.OnDemandServlet;

import org.polymap.p4.catalog.LocalResolver;
import org.polymap.p4.data.P4PipelineIncubator;
import org.polymap.p4.project.ProjectRepository;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class GeoServerStarter
        extends ServiceTracker {

    private static Log log = LogFactory.getLog( GeoServerStarter.class );
    
    
    public GeoServerStarter( BundleContext context ) {
        super( context, HttpService.class.getName(), null );
    }
    
    
    @Override
    public Object addingService( ServiceReference reference ) {
        HttpService service = (HttpService)super.addingService( reference );
        registerGeoServer( service );
        return service;
    }


    protected void registerGeoServer( HttpService service ) {
        IMap map = ProjectRepository.newUnitOfWork().entity( IMap.class, "root" );
        String alias = "/services";
        Supplier<GeoServerServlet> supplier = () -> {
            try {
                return createGeoServer( alias, map );             
            }
            catch (NoClassDefFoundError e) {
                log.warn( "No GeoServer plugin found!", e );
                throw e;
            }
            catch (Exception e) {
                throw new RuntimeException( e );
            }
        };
        try {
            service.registerServlet( alias, new OnDemandServlet( supplier ), null, null );
           // service.registerServlet( alias, supplier.get(), null, null );
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
    }
    
    
    protected GeoServerServlet createGeoServer( String alias, IMap map ) {
        return new GeoServerServlet( alias, map ) {
            @Override
            protected Pipeline createPipeline( ILayer layer,
                    Class<? extends PipelineProcessor> usecase ) throws Exception {
                // resolve service
                NullProgressMonitor monitor = new NullProgressMonitor();
                DataSourceDescription dsd = LocalResolver
                        .instance()
                        .connectLayer( layer, monitor )
                        .orElseThrow( () -> new RuntimeException( "No data source for layer: " + layer ) );

                // create pipeline for it
                return P4PipelineIncubator.forLayer( layer ).newPipeline( usecase, dsd, null );
            }
        };
    }

}
