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
package io.mapzone.controller.catalog.model;

import static io.mapzone.controller.catalog.csw.Namespaces.DC;
import static io.mapzone.controller.catalog.csw.Namespaces.DCT;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.model2.CollectionProperty;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.Entity;
import org.polymap.model2.MinOccurs;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * Dublin Core / CSW core catalog entry.
 * <p/>
 * Property names are Dublin Core. The OGC queryables are given by the
 * {@link OGCQueryable} annotation. The doc text is taken from OGC spec.
 *
 * @see <a href="http://docs.opengeospatial.org/is/12-168r6/12-168r6.html">OGC
 *      Catalog Service 3.0</a>
 * @see <a href="http://www.ogcnetwork.net/node/630">Basic CSW in a nutshell</a>
 * @author Falko Bräutigam
 */
public class CatalogCoreEntry
        extends Entity {

    private static Log log = LogFactory.getLog( CatalogCoreEntry.class );
    
    public static final ValueInitializer<CatalogCoreEntry> defaults = (CatalogCoreEntry proto) -> {
        proto.identifier.set( UUID.randomUUID().toString() );
        proto.modified.set( new Date() );
        return proto;
    };

    // instance *******************************************
    
    /**
     * A unique reference to the record within the catalogue.
     */
    @Queryable
    @XML( namespace=DC )
    @OGCQueryable( "Identifier" )
    public Property<String>         identifier;
    
    /**
     * A name given to the resource. Also known as “Name”.
     */
    @Queryable
    @XML( namespace=DC )
    @OGCQueryable( "Title" )
    public Property<String>         title;

    /**
     * An entity primarily responsible for making the content of the resource.
     */
    public Property<String>         creator;
    
    /**
     * A topic of the content of the resource. This is a place where a Topic Category
     * or other taxonomy could be applied.
     */
    @MinOccurs( 0 )
    @Queryable
    @XML( namespace=DC )
    @OGCQueryable( "Subject" )
    public CollectionProperty<String> subject;
    
    /**
     * An account of the content of the resource. This is also known as the
     * “Abstract” in other aspects of OGC, FGDC, and ISO metadata.
     */
    @Nullable
    @Queryable
    @XML( namespace=DCT )
    @OGCQueryable( "Abstract" )
    public Property<String>         description;
    
    /**
     * An entity responsible for making the resource available. This would equate to
     * the Distributor in ISO and FGDC metadata.
     */
    @Nullable
    public Property<String>         publisher;
    
    /**
     * An entity responsible for making contributions to the content of the resource.
     */
    @Nullable
    public Property<String>         contributor;
    
    /**
     * The date of a creation or update event of the catalogue record.
     */
    @Nullable
    @Queryable
    @XML( namespace=DCT )
    @OGCQueryable( "Modified" )
    public Property<Date>           modified;
    
    /**
     * The nature or genre of the content of the resource.
     */
    @Nullable
    @Queryable
    @XML( namespace=DC )
    @OGCQueryable( "Type" )
    @DefaultValue( "Service" )
    public Property<String>         type;
    
    /**
     * Spatial characteristics of the resource.
     */
    @MinOccurs( 0 )
    @Queryable
    @XML( namespace=DC )
    @OGCQueryable( "Spatial" )
    public CollectionProperty<String> spatial;
    
    /**
     * The physical or digital manifestation of the resource.
     */
    @Nullable
    @Queryable
    @OGCQueryable( "Format" )
    public Property<String>         format;
    
    /**
     * URI: A reference to the full metadata from which the present resource is derived.
     */
    @Nullable
    @Queryable
    @OGCQueryable( "Source" )
    public Property<String>         source;
    
    /**
     * The spatial and temporal extent or scope of the content of the resource.
     */
    @Nullable
    @Queryable
    @OGCQueryable( "BoundingBox" )
    public Property<Extent>         coverage;
    
    /**
     * 
     */
    @Nullable
    public Property<String>         rights;
    
}
