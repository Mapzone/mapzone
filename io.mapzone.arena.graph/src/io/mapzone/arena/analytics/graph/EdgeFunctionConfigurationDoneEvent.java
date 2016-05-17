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

public class EdgeFunctionConfigurationDoneEvent
        extends EventObject {

    private static final long serialVersionUID = 1L;

    @Immutable
    public Config<Boolean> status;


    public EdgeFunctionConfigurationDoneEvent( EdgeFunction source, Boolean status ) {
        super( source );
        ConfigurationFactory.inject( this );
        this.status.set( status );
    }


    @Override
    public EdgeFunction getSource() {
        return (EdgeFunction)super.getSource();
    }
}
