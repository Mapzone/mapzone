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

import org.polymap.model2.Property;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * Mapzone specific extensions. 
 *
 * @author Falko Bräutigam
 */
public class CatalogEntry
        extends CatalogCoreEntry {

    @SuppressWarnings( "hiding" )
    public static final ValueInitializer<CatalogEntry> defaults = (CatalogEntry proto) -> {
        CatalogCoreEntry.defaults.initialize( proto );
        return proto;
    };

    // instance *******************************************
    
    /**
     * URL: the base URL of the service this entry describes.
     */
    public Property<String>                 onlineResource;
    
//    /**
//     * Keywords, tags.
//     */
//    @Queryable
//    public CollectionProperty<String>       tags;
    
}
