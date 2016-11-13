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

import org.polymap.core.catalog.CatalogProvider;
import org.polymap.core.catalog.IMetadataCatalog;

import io.mapzone.arena.jmx.ArenaConfig;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CswCatalogProvider
        implements CatalogProvider {

    @Override
    public IMetadataCatalog get() {
        CswMetadataCatalog csw = new CswMetadataCatalog();
        csw.baseUrl.set( () -> ArenaConfig.instance().getCatalogServerUrl() );
        
        return new ConnectedCswMetadataCatalog( csw );
    }
    
}
