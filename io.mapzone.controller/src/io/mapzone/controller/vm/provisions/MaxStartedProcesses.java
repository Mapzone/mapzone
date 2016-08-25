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

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.mapzone.controller.ops.StopProcessOperation;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.http.HttpProvisionRuntimeException;
import io.mapzone.controller.vm.http.HttpProxyProvision;
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;

/**
 * This simulates a check for available memory on the host by limiting the number of
 * started processes on the host of the project targeted by the current request.
 * <p/>
 * Allowing {@link #MAX_PROCESSES} + 1 processes actually running. See code coment.
 *
 * @author Falko Bräutigam
 */
public class MaxStartedProcesses
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( MaxStartedProcesses.class );

    public static final int             MAX_PROCESSES = Integer.valueOf( System.getProperty( "io.mapzone.controller.maxProcesses", "1" ) );
    
    private Context<MaxStartedProcesses>    checked;

    
    @Override
    public boolean init( Provision failed, Status cause ) {
        return failed instanceof ReStartProcess
                && cause == null            // check before ReStartProcess
                && !checked.isPresent();    // run just ones
    }


    @Override
    public Status execute() throws Exception {
        ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( request.get() );
        ProjectInstanceRecord instance = vmUow().findInstance( pid )
                .orElseThrow( () -> new HttpProvisionRuntimeException( 404, "No such project: " + pid ) );
        
        HostRecord host = instance.host.get();
        if (host.statistics.get() == null || host.statistics.get().olderThan( 10, TimeUnit.SECONDS )) {

            host.updateStatistics();
            
            // lowest start time (oldest) first
            LinkedList<ProcessRecord> oldestFirst = host.instances.stream()
                    .filter( i -> i.process.get() != null )
                    .map( i -> i.process.get() )
                    .sorted( (p1, p2) -> p1.started.get().compareTo( p2.started.get() ))
                    .collect( Collectors.toCollection( LinkedList::new ) );
            log.info( "    PROCESSES RUNNING: " + oldestFirst.size() + " (" + host.statistics.get().lastChecked.get() + ")" );

            // XXX check if instance has a process registerd -> ReStartProcess will probably
            // actually start a process
            
            // stop processes
            while (oldestFirst.size() > MAX_PROCESSES) {
                ProcessRecord p = oldestFirst.remove( 0 );
                
                log.info( "    Stopping process: " + p.instance.get().project.get() + " -- started at " + p.started.get() );
                StopProcessOperation op = new StopProcessOperation();
                op.kill.set( false );
                op.process.set( p );
                op.vmUow.set( vmUow() );
                op.execute( null, null );
            }
        }
        
        checked.set( this );
        return OK_STATUS;
    }
    
}
