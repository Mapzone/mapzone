package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.HttpProxyProvision;
import io.mapzone.controller.http.ForwardRequest;
import io.mapzone.controller.ops.StartProcessOperation;
import io.mapzone.controller.ops.StopProcessOperation;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.ProcessRecord;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 
 * @author Falko Bräutigam
 */
public class ProcessRunning
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( ProcessRunning.class );

    public static final String              NO_HOST = "_no_host_";

    private Context<HostRecord>             host;

    private Context<ProjectInstanceRecord>  instance;

    private Context<ProcessRecord>          process;

    /** Prevents multiple runs. */
    private Context<ProcessRunning>         checked;

    private Status                          cause;

    
    @Override
    public boolean init( Provision failed, @SuppressWarnings("hiding") Status cause ) {
        this.cause = cause;
        return failed instanceof ForwardRequest
                && cause != null
                && !checked.isPresent()
                && process.isPresent()
                /*&& cause.getCause().equals( OkToForwardRequest.NO_PROCESS )*/;
    }

    
    @Override
    public Status execute() throws Exception {
        vmRepo().lock();

        // stop
        log.warn( "Killing process without checking OS process!" );
        StopProcessOperation op = new StopProcessOperation();
        op.vmRepo.set( vmRepo() );
        op.process.set( process.get() );
        op.execute( null, null );
        
        // start
        StartProcessOperation op2 = new StartProcessOperation();
        op2.instance.set( instance.get() );
        
        op2.execute( null, null );
        process.set( op2.process.get() );

        checked.set( this );
        return OK_STATUS;
    }
    
}
