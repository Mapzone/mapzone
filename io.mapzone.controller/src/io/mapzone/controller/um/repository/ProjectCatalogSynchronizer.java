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

import java.util.Date;
import java.util.Optional;

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

        entry.spatial.clear();
        entry.spatial.add( "_type_=Mapzone Project Service" );  // see io.mapzone.arena.csw.catalog.MapzoneProjectResolver
        entry.spatial.add( "_url_=" + serviceUrl );
        entry.format.set( "Service" );
        entry.onlineResource.set( serviceUrl );
        entry.title.set( project.name.get() );
        entry.description.set( project.description.get() );
        entry.modified.set( new Date() );
        //    entry.subject.clear();
        //    project.keywords.stream()
        //            .map( l -> l.getContent().get( 0 ) )
        //            .forEach( s -> entry.subject.add( s ) );
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
            update( findEntry()
                    .orElseThrow( () -> new IllegalStateException( "No entry for: " + project.catalogId.get() ) ) );
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
            uow.removeEntity( findEntry()
                    .orElseThrow( () -> new IllegalStateException( "No entry for: " + project.catalogId.get() ) ) );
        }
    }

}