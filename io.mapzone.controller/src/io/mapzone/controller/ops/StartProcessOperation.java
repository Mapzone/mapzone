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

import org.eclipse.core.runtime.IProgressMonitor;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;

import org.polymap.model2.runtime.UnitOfWork;

/**
 * Starts a {@link ProcessRecord process} for a {@link ProjectInstanceRecord project
 * instance}.
 *
 * @author Falko Bräutigam
 */
public class StartProcessOperation
        extends VmOperation {

    private static final Log log = LogFactory.getLog( StartProcessOperation.class );

    /** Inbound: */
    @Immutable
    @Mandatory
    public Config<ProjectInstanceRecord>    instance;

    /** Outbound: The started process, or empty if unable to start. */
    public Config<ProcessRecord>            process;
    
    protected ProcessRecord                 newProcess;
    
    
    public StartProcessOperation() {
        super( "Start instance" );
    }


    @Override
    protected void doWithException( IProgressMonitor monitor, UnitOfWork uow ) throws Exception {
        assert !process.isPresent();
        
        // the instance of the nested uow
        ProjectInstanceRecord _instance = uow.entity( instance.get() );
        
        // find host and free port
        HostRecord host = _instance.host.get();
        int port = host.runtime.get().findFreePort();
        int jmxPort = host.runtime.get().findFreePort();

        // create process record
        newProcess = uow.createEntity( ProcessRecord.class, null, (ProcessRecord proto) -> {
            proto.instance.set( _instance );
            proto.port.set( port );
            proto.jmxPort.set( jmxPort );
            proto.started.set( new Date() );
            return proto;
        });

        // start instance
        _instance.executeLauncher( launcher -> launcher.start( _instance, monitor ) );
    }


    @Override
    protected void onSuccess( IProgressMonitor monitor, UnitOfWork uow ) throws Exception {
        super.onSuccess( monitor, uow );
        
        process.set( vmUow.get().entity( newProcess ) );

        // sanity check
        assert process.get().instance.get() == instance.get();
        assert instance.get().process.get() == process.get();
    }


    @Override
    protected void onError( IProgressMonitor monitor, Throwable e ) throws Exception {
        process.set( null );
        
        // launcher failed: make sure that there is no OS process
        if (newProcess != null) {
            log.info( "    Error while starting process. Killing." );
            instance.get().executeLauncher( launcher -> launcher.stop( instance.get(), true, monitor ) );
            // no Exception allow provision to handle
        }
        // else is illegal state
        else {
            super.onError( monitor, e );
        }
    }

}
