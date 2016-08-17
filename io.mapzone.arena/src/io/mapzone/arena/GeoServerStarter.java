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

import static com.google.common.base.Throwables.propagate;

import java.util.EventObject;
import java.util.List;
import java.util.function.Supplier;

import org.geotools.styling.Style;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.data.feature.DefaultStyles;
import org.polymap.core.data.feature.FeatureRenderProcessor2;
import org.polymap.core.data.pipeline.DataSourceDescription;
import org.polymap.core.data.pipeline.Pipeline;
import org.polymap.core.data.pipeline.PipelineProcessor;
import org.polymap.core.project.ILayer;
import org.polymap.core.project.IMap;
import org.polymap.core.project.ProjectNode.ProjectNodeCommittedEvent;
import org.polymap.core.runtime.event.Event.Scope;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.style.model.FeatureStyleCommitedEvent;

import org.polymap.service.geoserver.GeoServerServlet;
import org.polymap.service.geoserver.OnDemandServlet;

import org.polymap.p4.P4Plugin;
import org.polymap.p4.catalog.LocalResolver;
import org.polymap.p4.data.P4PipelineIncubator;
import org.polymap.p4.project.ProjectRepository;

import io.mapzone.arena.jmx.ArenaConfigMBean;

/**
 * Track {@link HttpService} instances and register {@link OnDemandServlet} with
 * {@link GeoServerServlet} when found.
 * <p/>
 * Listens to {@link ProjectNodeCommittedEvent} and {@link FeatureStyleCommitedEvent}
 * in order to restart servlet.
 * 
 * @author Falko Bräutigam
 */
public class GeoServerStarter
        extends ServiceTracker {

    private static Log log = LogFactory.getLog( GeoServerStarter.class );
    
    public static final String          ALIAS = ArenaConfigMBean.GEOSERVER_ALIAS;

    private OnDemandServlet             onDemandServlet;
    
    
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
        Supplier<GeoServerServlet> supplier = () -> {
            try {
                return createGeoServer( ALIAS );             
            }
            catch (NoClassDefFoundError e) {
                log.warn( "No GeoServer plugin found!", e );
                throw e;
            }
            catch (Exception e) {
                throw propagate( e );
            }
        };
        try {
            assert onDemandServlet == null;
            onDemandServlet = new OnDemandServlet( supplier );
            service.registerServlet( ALIAS, onDemandServlet, null, null );
           // service.registerServlet( alias, supplier.get(), null, null );
            
            EventManager.instance().subscribe( GeoServerStarter.this, ev ->
                    ev instanceof ProjectNodeCommittedEvent ||
                    ev instanceof FeatureStyleCommitedEvent );
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
    }
    
    
    @Override
    public void close() {
        EventManager.instance().unsubscribe( this );
        super.close();
    }


    @EventHandler( delay=100, scope=Scope.JVM )
    protected void projectChanged( List<EventObject> evs ) {
        if (onDemandServlet != null) {
            onDemandServlet.destroyDelegate();
        }
    }
    
    
    protected GeoServerServlet createGeoServer( String alias ) {
        return new GeoServerServlet( alias ) {
            @Override
            protected IMap createMap() {
                return ProjectRepository.unitOfWork().entity( IMap.class, "root" );
            }
            @Override
            protected Pipeline createPipeline( ILayer layer,
                    Class<? extends PipelineProcessor> usecase ) throws Exception {
                // resolve service
                DataSourceDescription dsd = LocalResolver
                        .instance()
                        .connectLayer( layer, new NullProgressMonitor() )
                        .orElseThrow( () -> new RuntimeException( "No data source for layer: " + layer ) );

                // get style
                Supplier<Style> styleSupplier = () -> {
                    String styleId = layer.styleIdentifier.get();
                    return styleId != null
                            ? P4Plugin.styleRepo().serializedFeatureStyle( styleId, Style.class ).get()
                            : new DefaultStyles().createAllStyle();
                };

                // create pipeline for it
                return P4PipelineIncubator.forLayer( layer )
                        .addProperty( FeatureRenderProcessor2.STYLE_SUPPLIER, styleSupplier )
                        .newPipeline( usecase, dsd, null );
            }
            @Override
            public String createSLD( ILayer layer ) {
                String styleId = layer.styleIdentifier.get();
                return styleId != null
                        ? P4Plugin.styleRepo().serializedFeatureStyle( styleId, String.class ).get()
                        : null; //new DefaultStyles().createAllStyle();
            }
        };
    }

}
