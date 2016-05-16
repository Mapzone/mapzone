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
package io.mapzone.arena.analytics.graph;

import java.util.EventObject;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Immutable;


public class GraphFunctionConfigurationChangedEvent
        extends EventObject {

    private static final long serialVersionUID = 1L;

    @Immutable
    public Config<String> propertyName;

    @Immutable
    public Config<Object> value;


    public GraphFunctionConfigurationChangedEvent( final GraphFunction source, final String propertyName, final Object value ) {
        super( source );
        ConfigurationFactory.inject( this );
        this.propertyName.set( propertyName );
        this.value.set( value );
    }


    @Override
    public GraphFunction getSource() {
        return (GraphFunction)super.getSource();
    }
}
