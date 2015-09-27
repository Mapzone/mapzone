package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.http.ForwardRequest;
import io.mapzone.controller.ops.StopProcessOperation;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This simulates a check for available memory on the host by limiting the number of
 * processes.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class MaxProcesses
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( MaxProcesses.class );

    public static final int                 MAX_PROCESSES = 2;
    
    private Context<RegisteredProcess>      process;

    private Context<MaxProcesses>           checked;

    
    @Override
    public boolean init( Provision failed, Status cause ) {
        return failed instanceof ForwardRequest
                && cause == null
                && !checked.isPresent();
    }


    @Override
    public Status execute() throws Exception {
        checked.set( this );
        assert process.isPresent() : "No process in context. Make sure that MaxProcesses executes after ProcessStarted.";
        
        RegisteredHost host = process.get().instance.get().host.get();
        if (host.statistics.get() == null || host.statistics.get().olderThan( 10, TimeUnit.SECONDS )) {

            vmRepo.get().lock();
            host.updateStatistics();
            
            // lowest start time (oldest) first
            LinkedList<RegisteredProcess> sortedProcesses = host.instances.stream()
                    .filter( i -> i.process.get() != null )
                    .map( i -> i.process.get() )
                    .sorted( (p1, p2) -> p1.started.get().compareTo( p2.started.get() ))
                    .collect( Collectors.toCollection( LinkedList::new ) );
            log.info( "    PROCESSES RUNNING: " + sortedProcesses.size() + " (" + host.statistics.get().lastChecked.get() + ")" );
            
            // stop processes, oldest first
            while (sortedProcesses.size() > MAX_PROCESSES) {
                RegisteredProcess p = sortedProcesses.remove( 0 );
                
                log.info( "    stopping process: " + p.instance.get().project.get() + " -- started at " + p.started.get() );
                StopProcessOperation op = new StopProcessOperation()
                        .process.put( p )
                        .vmRepo.put( vmRepo.get() );
                op.execute( null, null );
            }
        }
        
        return OK_STATUS;
    }
    
}
