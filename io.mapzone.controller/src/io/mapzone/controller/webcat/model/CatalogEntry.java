/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
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
package io.mapzone.controller.webcat.model;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.runtime.event.EventManager;

import org.polymap.model2.DefaultValue;
import org.polymap.model2.Entity;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.runtime.EntityRuntimeContext.EntityStatus;
import org.polymap.model2.runtime.Lifecycle;
import org.polymap.model2.runtime.ValueInitializer;

import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.webcat.webdav.WebJsonApi;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class CatalogEntry
        extends Entity
        implements Lifecycle {

    private static final Log log = LogFactory.getLog( CatalogEntry.class );

    public static final ValueInitializer<CatalogEntry> defaults = (proto) -> {
        Date now = new Date();
        proto.created.set( now );
        proto.updated.set( now );
        proto.isReleased.set( false );
        proto.isRevoked.set( false );
        proto.fee.set( 0f );
        return proto;
    };
    
    // instance *******************************************
    
    @Override
    public String id() {
        return (String)super.id();
    }

    @Nullable
    @Queryable
    @WebJsonApi
    public Property<String>         title;

    @Nullable
    @Queryable
    @WebJsonApi
    public Property<String>         description;
    
    @WebJsonApi
    public Property<Date>           created;
    
    @WebJsonApi
    public Property<Date>           updated;

    /**
     * Human readable name of the vendor. Connected to an actual {@link User} via
     * {@link #issuerId}.
     */
    @Nullable
    @Queryable
    @WebJsonApi
    public Property<String>         vendor;
    
    @Nullable
    @Queryable
    @WebJsonApi
    public Property<String>         vendorUrl;
    
    @Nullable
    @Queryable
    @WebJsonApi
    public Property<String>         projectUrl;
    
    /**
     * The id of the {@link User} who provides this entry.
     */
//    @Nullable
    public Property<String>         vendorId;

//    /** {@link #defaults} to false */
//    @WebJsonApi
//    @DefaultValue( "false" )
//    public Property<Boolean>        isFree;
    
    /** {@link #defaults} to false */
    @WebJsonApi
    @DefaultValue( "false" )
    public Property<Boolean>        isReleased;
    
    /**
     * Instead of beeing removed from repository a catalog entry is 'revoked' in
     * order to keep external references to this entry working.
     * <p/>
     * {@link #defaults} to false
     */
    @WebJsonApi
    @DefaultValue( "false" )
    public Property<Boolean>        isRevoked;
    
    @Nullable
    @WebJsonApi
    public Property<String>         license;

    /** The vendor fee (in EUR?). {@link #defaults} to 0. */
    @Nullable
    @WebJsonApi
    @DefaultValue( "0" )
    public Property<Float>          fee;

    private EntityStatus            beforeCommitStatus;

    
    public boolean isFree() {
        try {
            return fee.get().equals( 0f );
        }
        catch (Exception e) {
            log.warn( "", e );
            return true;
        }
    }
    
    
    @Override
    public void onLifecycleChange( State state ) {
        if (status() != EntityStatus.REMOVED && state == State.BEFORE_PREPARE) {
            updated.set( new Date() );
        }
        
        if (state == State.BEFORE_COMMIT || state == State.BEFORE_ROLLBACK) {
            beforeCommitStatus = status();
        }
        else if (state == State.AFTER_COMMIT || state == State.AFTER_ROLLBACK) {
            EventManager.instance().publish( new EntityChangedEvent( this, beforeCommitStatus ) );
        }
    }

}
