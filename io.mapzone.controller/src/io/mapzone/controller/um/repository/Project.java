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

import org.polymap.model2.Association;
import org.polymap.model2.CollectionProperty;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.Defaults;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.runtime.ValueInitializer;
import org.polymap.model2.runtime.EntityRuntimeContext.EntityStatus;

import io.mapzone.controller.plugincat.PluginInstaller;
import io.mapzone.controller.um.launcher.ArenaLauncher;
import io.mapzone.controller.um.launcher.ProjectLauncher;

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
        proto.launcher.createValue( ArenaLauncher.defaults );
        proto.plugins.createValue( PluginInstaller.defaults );
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

    public Property<PluginInstaller>    plugins;

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
    
    @Defaults
    @Queryable
    public Property<String>             creator;

    @Defaults
    @Queryable
    public Property<Date>               created;

    @Defaults
    @Queryable
    public Property<Date>               modified;

    @Defaults
    @Queryable
    public Property<String>             publisher;

    @Defaults
    @Queryable
    public Property<String>             rights;

    @Defaults
    @Queryable
    public Property<String>             accessRights;

    @Defaults
    @Queryable
    public CollectionProperty<String>   languages;

    @Defaults
    @Queryable
    public CollectionProperty<String>   keywords;

    
    public Optional<AuthToken> serviceAuthToken() {
        return serviceAuthToken.get() != null 
                ? Optional.of( AuthToken.parse( serviceAuthToken.get() ) )
                : Optional.empty();
    }
    
    
//    public AuthToken newServiceAuthToken( IProgressMonitor monitor ) {
//        AuthToken authToken = AuthToken.create();
////        // XXX encrypt?
////        serviceAuthToken.set( authToken.toString() );
//        return authToken;
//    }

    
    @Override
    public void onLifecycleChange( State state ) {
        super.onLifecycleChange( state );
        
        if (state.equals( State.BEFORE_PREPARE )) {
            if (catalogId.get() == null && status().status < EntityStatus.REMOVED.status) {
                catalogId.set( UUID.randomUUID().toString() );
            }
        }
        
        else if (state.equals( State.BEFORE_COMMIT )) {
            if (status() == CREATED) {
                new ProjectCatalogSynchronizer.CreateCatalogEntry( this ).schedule();
            }
            else if (status() == MODIFIED) {
                new ProjectCatalogSynchronizer.UpdateCatalogEntry( this ).schedule();
            }
            else if (status() == REMOVED) {
                new ProjectCatalogSynchronizer.RemoveCatalogEntry( this ).schedule();
            }
        }
    }
    
}
