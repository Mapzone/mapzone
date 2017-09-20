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
package io.mapzone.controller.webcat.webdav;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.polymap.model2.Composite;

/**
 * Denotes a {@link Composite} property to be part of the catalog WebDAV API. 
 *
 * @author Falko Bräutigam
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD } )
@Documented
public @interface WebJsonApi {

    /** The name of the field in the JSON object. */
    public String name() default "";
}
