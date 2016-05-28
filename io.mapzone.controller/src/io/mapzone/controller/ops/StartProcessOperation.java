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
package io.mapzone.controller.ops;

import java.util.Date;

import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.ProcessRecord;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;

/**
 * Starts a {@link ProcessRecord process} for a {@link ProjectInstanceRecord project
 * instance}.
 *
 * @author Falko Bräutigam
 */
public class StartProcessOperation
        extends DefaultOperation {

    private static Log log = LogFactory.getLog( StartProcessOperation.class );

    /** Inbound: */
    @Immutable
    @Mandatory
    public Config<ProjectInstanceRecord>    instance;

    /** Outbound: The started process. */
    public Config<ProcessRecord>            process;
    
    
    public StartProcessOperation() {
        super( "Start instance" );
        ConfigurationFactory.inject( this );
    }


    @Override
    public IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        assert !process.isPresent();
        
        // find host and free port
        HostRecord host = instance.get().host.get();
        int port = host.runtime.get().findFreePort();
        int jmxPort = host.runtime.get().findFreePort();
        
        // create process record
        process.set( instance.get().uow().createEntity( ProcessRecord.class, null, (ProcessRecord proto) -> {
            proto.instance.set( instance.get() );
            proto.port.set( port );
            proto.jmxPort.set( jmxPort );
            proto.started.set( new Date() );
            return proto;
        }));
        
        // start instance
        instance.get().executeLauncher( launcher -> launcher.start( instance.get(), monitor ) );
        
        // sanity check
        assert process.get().instance.get() == instance.get();
        assert instance.get().process.get() == process.get();
        
        return Status.OK_STATUS;
    }

}
