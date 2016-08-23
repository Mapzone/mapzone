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
package io.mapzone.controller.vm.repository;

import java.util.Date;

import io.mapzone.controller.ops.CreateProjectOperation;
import io.mapzone.controller.ops.StartProcessOperation;
import io.mapzone.controller.vm.runtime.HostRuntime;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.model2.Computed;
import org.polymap.model2.ComputedBidiManyAssocation;
import org.polymap.model2.Concerns;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.Defaults;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Property;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
@Concerns( NestedOneReaderPessimisticLocking.class )
public class HostRecord
        extends VmEntity {

    public static HostRecord        TYPE;
    
    public static ValueInitializer<HostRecord> defaults = (HostRecord proto) -> {
        proto.hostType.set( HostType.JCLOUDS );
        proto.hostId.set( "local" );
        proto.inetAddress.set( "localhost" );
        proto.updateStatistics();
        return proto;
    };
    
    public enum HostType {
        JCLOUDS
    }
    
    // instance *******************************************
    
    public Property<HostType>                   hostType;
    
    /**
     * The address or id that identifies this hosts and allows to access it.
     */
    public Property<String>                     hostId;
    
    /**
     * The host's IP or DNS name. This is used to build URIs for the instances.
     */
    public Property<String>                     inetAddress;
    
    /**
     * The next free port on the host.
     * <p/>
     * XXX This prevents multiple {@link StartProcessOperation}s run in parallel.
     */
    @DefaultValue( "32768" )
    public Property<Integer>                    portCount;
    
    /**
     * The instances running on this host currently.
     * <p/>
     * This is computed (instead of direct association) in order to prevent blocking of
     * multiple {@link CreateProjectOperation} instances.
     */
    @Defaults
    @Computed( ComputedBidiManyAssocation.class )
    public ManyAssociation<ProjectInstanceRecord> instances;
    
    public Property<HostStatistics>             statistics;
    
    public Lazy<HostRuntime>                    runtime = new LockedLazyInit( () -> HostRuntime.forHost( this ) ); 
    
    
    public void updateStatistics() {
        statistics.get().lastChecked.set( new Date() );
    }

}
