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

import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * Provides common metadata corresponding to IMetadata.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class Named
        extends ProjectEntity {

    @Nullable
    @Queryable
    public Property<String>             name;

    @Nullable
    @Queryable
    public Property<String>             description;

    @Nullable
    @Queryable
    public Property<String>             website;
    
    @Nullable
    @Queryable
    public Property<String>             email;
    
    @Nullable
    @Queryable
    public Property<String>             location;
    
}
