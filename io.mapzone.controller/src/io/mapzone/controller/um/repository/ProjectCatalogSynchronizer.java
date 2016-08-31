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
package io.mapzone.controller.um.repository;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.runtime.UIJob;

import org.polymap.model2.query.Expressions;
import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.arena.jmx.ArenaConfigMBean;
import io.mapzone.controller.catalog.CatalogPlugin;
import io.mapzone.controller.catalog.CatalogRepositoryContext;
import io.mapzone.controller.catalog.model.CatalogEntry;
import io.mapzone.controller.vm.http.ProxyServlet;

/**
 * Synchronizes the content of a {@link Project} with one entry in the
 * {@link CatalogPlugin}.
 *
 * @author Falko Bräutigam
 */
abstract class ProjectCatalogSynchronizer
        extends UIJob {

    private static Log log = LogFactory.getLog( ProjectCatalogSynchronizer.class );
    
    protected Project               project;

    protected UnitOfWork            uow;

    public ProjectCatalogSynchronizer( Project project ) {
        super( "Catalog synchronizer" );
        setSystem( true );
        this.project = project;
    }


    protected abstract void doWithUnitOfWork();

    @Override
    protected void runWithException( IProgressMonitor monitor ) throws Exception {
        CatalogRepositoryContext catalog = CatalogPlugin.instance().catalog();
        assert uow == null;
        uow = catalog.unitOfWork();
        try {
            doWithUnitOfWork();        
            uow.commit();
        }
        finally {
            uow.close();
            uow = null;
        }
    }


    protected Optional<CatalogEntry> findEntry() {
        return uow.query( CatalogEntry.class )
                .where( Expressions.eq( CatalogEntry.TYPE.identifier, project.catalogId.get() ) )
                .maxResults( 1 )
                .execute().stream().findAny();
    }


    protected void update( CatalogEntry entry ) {
        String serviceUrl = ProxyServlet.projectUrlBase( project ) + ArenaConfigMBean.GEOSERVER_ALIAS;

        entry.connectionParams.clear();
        entry.connectionParams.createElement( uri -> {
            uri.description.set( ArenaConfigMBean.CONNECTION_PARAM_NAME );
            // org.polymap.core.catalog.resolve.IMetadataResourceResolver
            uri.name.set( "_type_" );
            uri.value.set( ArenaConfigMBean.MAPZONE_SERVICE_TYPE );
            return uri;
        });
        entry.connectionParams.createElement( uri -> {
            uri.description.set( ArenaConfigMBean.CONNECTION_PARAM_NAME );
            uri.name.set( "_url_" );
            uri.value.set( serviceUrl );
            return uri;
        });
        
        // standard type and formats
        entry.onlineResource.set( serviceUrl );
        entry.title.set( project.name.get() );
        entry.description.set( project.description.get() );
        entry.modified.set( project.modified.get() );
        entry.created.set( project.created.get() );
        entry.creator.set( project.creator.get() );
        entry.publisher.set( project.publisher.get() );
        entry.rights.set( project.rights.get() );
        entry.accessRights.set( project.accessRights.get() );
        
        entry.subject.clear();
        entry.subject.addAll( project.keywords );
    }

    
    /**
     * 
     */
    static class CreateCatalogEntry
            extends ProjectCatalogSynchronizer {
        
        public CreateCatalogEntry( Project project ) {
            super( project );
        }

        @Override
        protected void doWithUnitOfWork() {
            if (findEntry().isPresent()) {
                throw new IllegalStateException( "There is an entry for: " + project.catalogId.get() );
            }
                    
            CatalogEntry entry = uow.createEntity( CatalogEntry.class, null, (CatalogEntry proto) -> {
                proto.identifier.set( project.catalogId.get() );
                return proto;
            });
            update( entry );
        }
    }
    
    
    /**
     * 
     */
    static class UpdateCatalogEntry
            extends ProjectCatalogSynchronizer {
        
        public UpdateCatalogEntry( Project project ) {
            super( project );
        }

        @Override
        protected void doWithUnitOfWork() {
            Optional<CatalogEntry> entry = findEntry();
            if (entry.isPresent()) {
                update( entry.get() );
            }
            else {
                log.warn( "No catalog entry for id: " + project.catalogId.get() );
                CreateCatalogEntry create = new CreateCatalogEntry( project );
                create.uow = uow;
                create.doWithUnitOfWork();
            }
        }
    }
    
    
    /**
     * 
     */
    static class RemoveCatalogEntry
            extends ProjectCatalogSynchronizer {
        
        public RemoveCatalogEntry( Project project ) {
            super( project );
        }

        @Override
        protected void doWithUnitOfWork() {
            Optional<CatalogEntry> entry = findEntry();
            if (entry.isPresent()) {
                uow.removeEntity( entry.get() );
            }
            else {
                log.warn( "No entry for: " + project.catalogId.get() );
            }
        }
    }

}