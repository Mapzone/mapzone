/*
 * mapzone.io Copyright (C) 2017, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package io.mapzone.controller.webcat.webdav;

import com.google.common.base.Strings;

import org.polymap.model2.runtime.CompositeStateVisitor;
import org.polymap.model2.runtime.PropertyInfo;

/**
 * 
 * @author Falko Bräutigam
 */
public abstract class Transformer
        extends CompositeStateVisitor {

    protected boolean isAnnotated( PropertyInfo info ) {
        return info.getAnnotation( WebJsonApi.class ) != null;
    }

    protected String propName( PropertyInfo info ) {
        WebJsonApi a = (WebJsonApi)info.getAnnotation( WebJsonApi.class );
        return a != null && !Strings.isNullOrEmpty( a.name() ) ? a.name() : info.getName();
    }

}