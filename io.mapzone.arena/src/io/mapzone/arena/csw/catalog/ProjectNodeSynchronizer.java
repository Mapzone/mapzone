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
package io.mapzone.arena.csw.catalog;

import static org.polymap.p4.project.MetadataReference.TYPE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.HttpHostConnectException;

import org.polymap.core.catalog.IMetadataCatalog;
import org.polymap.core.catalog.IUpdateableMetadata;
import org.polymap.core.catalog.IUpdateableMetadataCatalog.Updater;
import org.polymap.core.data.wms.catalog.WmsResourceResolver;
import org.polymap.core.project.ILayer;
import org.polymap.core.project.IMap;
import org.polymap.core.project.ProjectNode;
import org.polymap.core.project.ProjectNode.ProjectNodeCommittedEvent;
import org.polymap.core.runtime.event.Event;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;

import org.polymap.service.geoserver.GeoServerUtils;

import org.polymap.model2.query.Expressions;
import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.p4.project.MetadataReference;
import org.polymap.p4.project.ProjectRepository;

import io.mapzone.arena.GeoServerStarter;
import io.mapzone.arena.jmx.ArenaConfig;

/**
 * Listens to {@link ProjectNodeCommittedEvent}s and synchronizes {@link IMap}
 * and {@link ILayer} with their corresponding metadata in an {@link IMetadataCatalog}.
 *
 * @author Falko Bräutigam
 */
public class ProjectNodeSynchronizer
        implements AutoCloseable {

    private static Log log = LogFactory.getLog( ProjectNodeSynchronizer.class );
    
    private CswMetadataCatalog      catalog;
    
    
    public ProjectNodeSynchronizer() {
        // FIXME deaktivated, just sync layers to the record of the project
//        // allow P4 to startup and initialize catalogs
//        new Job( "Start delay" ) {
//            @Override
//            protected IStatus run( IProgressMonitor monitor ) {
//                catalog = (CswMetadataCatalog)P4Plugin.catalogs().stream().filter( c -> c instanceof CswMetadataCatalog )
//                        .findAny().orElseThrow( () -> new IllegalStateException( "No CswMetadataCatalog found." ) );
//                
//                EventManager.instance().subscribe( ProjectNodeSynchronizer.this );
//                return Status.OK_STATUS;
//            }
//        }.schedule( 5000 );
    }

    
    protected String serviceBaseUrl() {
        // do not cache, always use current config
        return ArenaConfig.instance().getProxyUrl() + GeoServerStarter.ALIAS;
    }

    
    @Override
    public void close() throws Exception {
        EventManager.instance().unsubscribe( this );
    }


    @EventHandler( delay=1000, scope=Event.Scope.JVM )
    protected void projectNodeCommitted( List<ProjectNodeCommittedEvent> evs ) throws Exception {
        // the delay causes this to be executed inside an UIJob, so
        // this update runs asynchronously and does no effect main thread
        try (
            Updater updater = catalog.prepareUpdate();
            UnitOfWork uow = ProjectRepository.newUnitOfWork();
        ){
            for (ProjectNodeCommittedEvent ev : evs) {
                ProjectNode entity = ev.getEntity( uow );

                if (entity instanceof ILayer) {
                    Optional<MetadataReference> mdRef = findMetadataRefs( entity );
                    if (mdRef.isPresent()) {
                        String identifier = mdRef.get().metadataId.get();
                        updater.updateEntry( identifier, update( entity ) );
                    }
                    else {
                        String identifier = UUID.randomUUID().toString();
                        createMetadataRefs( entity, identifier );
                        updater.newEntry( md -> {
                            md.setIdentifier( identifier );
                            update( entity ).accept( md );
                        });
                    }
                }
            }
            updater.commit();
            uow.commit();
        }
        catch (HttpHostConnectException e) {
            // just a short message for better developing
            log.warn( "Unable to connect CSW server: " + e.getLocalizedMessage() );
        }
    }
    
    
    protected Consumer<IUpdateableMetadata> update( ProjectNode entity ) {
        return md -> {
            md.setTitle( entity.label.get() );
            md.setDescription( entity.description.get() );
            md.setKeywords( new HashSet( entity.keywords ) );
            if (entity instanceof ILayer) {
                md.setConnectionParams( layerConnectionParams( (ILayer)entity ) );
            }
        };
    }

    
    protected Map<String,String> layerConnectionParams( ILayer layer ) {
        String wmsUrl = serviceBaseUrl() + "?service=WMS";
        Map<String,String> params = new HashMap();
        params.putAll( WmsResourceResolver.createParams( wmsUrl, GeoServerUtils.simpleName( layer.label.get() ) ) );
        //params.put( "WMS", "This service provides image data. Style and data cannot be modified." );
        return params;
    }


    protected Optional<MetadataReference> findMetadataRefs( ProjectNode projectNode ) {
        UnitOfWork uow = projectNode.belongsTo();
        ResultSet<MetadataReference> rs = uow.query( MetadataReference.class )
                .where( Expressions.eq( TYPE.projectNodeId, projectNode.id() ) )
                .maxResults( 2 )
                .execute();
        assert rs.size() < 2 : "Multiple entries for projectNodeId: " + projectNode.id();
        return rs.stream().findAny();
    }

    
    protected MetadataReference createMetadataRefs( ProjectNode projectNode, String identifier ) {
        UnitOfWork uow = projectNode.belongsTo();
        return uow.createEntity( MetadataReference.class, null, proto -> {
            proto.projectNodeId.set( projectNode.id() );
            proto.metadataId.set( identifier );
            return proto;
        });
    }
    
}
