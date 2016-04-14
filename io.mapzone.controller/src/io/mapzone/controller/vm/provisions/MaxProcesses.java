/* 
 * Copyright (C) 2015-2016, the @authors. All rights reserved.
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
package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.HttpProxyProvision;
import io.mapzone.controller.http.ForwardRequest;
import io.mapzone.controller.ops.StopProcessOperation;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProcessRecord;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This simulates a check for available memory on the host by limiting the number of
 * processes.
 *
 * @author Falko Bräutigam
 */
public class MaxProcesses
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( MaxProcesses.class );

    public static final int             MAX_PROCESSES = Integer.valueOf( System.getProperty( "io.mapzone.controller.maxProcesses", "1" ) );
    
    private Context<ProcessRecord>      process;

    private Context<MaxProcesses>       checked;

    
    @Override
    public boolean init( Provision failed, Status cause ) {
        return failed instanceof ForwardRequest
                && cause == null
                && process.isPresent()  // check only if ProcessStarted did not used a cached fast-forward targetUri
                && !checked.isPresent();
    }


    @Override
    public Status execute() throws Exception {
        checked.set( this );
        assert process.isPresent() : "No process in context. Make sure that MaxProcesses executes after ProcessStarted.";
        
        HostRecord host = process.get().instance.get().host.get();
        if (host.statistics.get() == null || host.statistics.get().olderThan( 10, TimeUnit.SECONDS )) {

            host.updateStatistics();
            
            // lowest start time (oldest) first
            LinkedList<ProcessRecord> sortedProcesses = host.instances.stream()
                    .filter( i -> i.process.get() != null )
                    .map( i -> i.process.get() )
                    .sorted( (p1, p2) -> p1.started.get().compareTo( p2.started.get() ))
                    .collect( Collectors.toCollection( LinkedList::new ) );
            log.info( "    PROCESSES RUNNING: " + sortedProcesses.size() + " (" + host.statistics.get().lastChecked.get() + ")" );
            
            // stop processes, oldest first
            while (sortedProcesses.size() > MAX_PROCESSES) {
                ProcessRecord p = sortedProcesses.remove( 0 );
                
                log.info( "    stopping process: " + p.instance.get().project.get() + " -- started at " + p.started.get() );
                StopProcessOperation op = new StopProcessOperation();
                op.process.set( p );
                op.vmRepo.set( vmRepo() );
                op.execute( null, null );
            }
        }
        
        return OK_STATUS;
    }
    
}
