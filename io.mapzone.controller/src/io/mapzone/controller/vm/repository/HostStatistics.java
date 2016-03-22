/* 
 * Copyright (C) 2015, the @authors. All rights reserved.
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
package io.mapzone.controller.vm.repository;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.polymap.model2.Composite;
import org.polymap.model2.Property;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class HostStatistics
        extends Composite {

    /** Injected, static {@link Composite} type information. */
    public static HostStatistics TYPE;
    
    /** Default initialization */
    public static ValueInitializer<HostStatistics> defaults = (HostStatistics proto) -> {
        proto.lastChecked.set( new Date() );
        return proto;
    };
    
    public Property<Date>               lastChecked;
    
    
    public boolean olderThan( int duration, TimeUnit unit ) {
        return lastChecked.get().getTime() + unit.toMillis( duration ) < System.currentTimeMillis();
    }
    
}
