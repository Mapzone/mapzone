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

import static org.polymap.model2.runtime.EntityRuntimeContext.EntityStatus.CREATED;
import static org.polymap.model2.runtime.EntityRuntimeContext.EntityStatus.MODIFIED;
import static org.polymap.model2.runtime.EntityRuntimeContext.EntityStatus.REMOVED;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.runtime.UIJob;

import org.polymap.model2.Association;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.query.Expressions;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.ValueInitializer;

import io.mapzone.controller.catalog.CatalogPlugin;
import io.mapzone.controller.catalog.CatalogRepositoryContext;
import io.mapzone.controller.catalog.model.CatalogEntry;
import io.mapzone.controller.um.launcher.ProjectLauncher;
import io.mapzone.controller.vm.http.ProxyServlet;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class Project
        extends Named {

    public static Project               TYPE;
    
    public static final String          DEFAULT_SERVLET_ALIAS = "/arena";
    
    public static final ValueInitializer<Project> defaults = (Project proto) -> { 
        return proto; 
    };
    
    /**
     * The {@link Organization} this project belongs to. Bidirectional association
     * with {@link Organization#projects}.
     */
    @Nullable
    public Association<Organization>    organization;
    
    /**
     * The servlet path to access this project. For URL "http://localhost:8080/p4"
     * this is "/p4". Starts with "/".
     */
    @DefaultValue( DEFAULT_SERVLET_ALIAS )
    public Property<String>             servletAlias;
    
    public Property<ProjectLauncher>    launcher;

    /**
     * @see AuthToken
     */
    @Nullable
    @Queryable
    public Property<String>             serviceAuthToken;
    
    /**
     *
     */
    @Nullable
    public Property<String>             catalogId;
    
    
    public Optional<AuthToken> serviceAuthToken() {
        return serviceAuthToken.get() != null 
                ? Optional.of( AuthToken.parse( serviceAuthToken.get() ) )
                : Optional.empty();
    }
    
    
    public AuthToken newServiceAuthToken( IProgressMonitor monitor ) {
        AuthToken authToken = AuthToken.create();
        // XXX encrypt?
        serviceAuthToken.set( authToken.toString() );
        return authToken;
    }


    @Override
    public void onLifecycleChange( State state ) {
        super.onLifecycleChange( state );
        
        if (state.equals( State.BEFORE_PREPARE )) {
            if (catalogId.get() == null) {
                catalogId.set( UUID.randomUUID().toString() );
            }
        }
        
        else if (state.equals( State.BEFORE_COMMIT )) {
            if (status() == CREATED) {
                new CreateCatalogEntry( this ).schedule();
            }
            else if (status() == MODIFIED) {
                new UpdateCatalogEntry( this ).schedule();
            }
            else if (status() == REMOVED) {
                new RemoveCatalogEntry( this ).schedule();
            }
        }
    }

    
    /**
     * Synchronizes the content of a {@link Project} with one entry in the
     * {@link CatalogPlugin}.
     */
    protected static abstract class CatalogSynchronizer
            extends UIJob {
        
        protected Project               project;
        
        protected UnitOfWork            uow;

        public CatalogSynchronizer( Project project ) {
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
            entry.format.set( "Service" );
            entry.onlineResource.set( ProxyServlet.projectUrlBase( project ) + "/ows" );
            entry.title.set( project.name.get() );
            entry.description.set( project.description.get() );
            entry.modified.set( new Date() );
//            entry.subject.clear();
//            project.keywords.stream()
//                    .map( l -> l.getContent().get( 0 ) )
//                    .forEach( s -> entry.subject.add( s ) );
        }
    }
    
    
    /**
     * 
     */
    protected static class CreateCatalogEntry
            extends CatalogSynchronizer {
        
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
    protected static class UpdateCatalogEntry
            extends CatalogSynchronizer {
        
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
    protected static class RemoveCatalogEntry
            extends CatalogSynchronizer {
        
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
