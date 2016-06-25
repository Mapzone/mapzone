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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.polymap.model2.Property;

/**
 * Denotes the OGC name to query a {@link Property} of an catalog entry.
 *
 * @author Falko Bräutigam
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD } )
@Documented
public @interface OGCQueryable {

    /** The OGC name to query this {@link Property}. */
    public String value();
    
}
