package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.HttpProxyProvision;
import io.mapzone.controller.http.ForwardRequest;
import io.mapzone.controller.ops.StartProcessOperation;
import io.mapzone.controller.ops.StopProcessOperation;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredInstance;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProcessRunning
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( ProcessRunning.class );

    public static final String              NO_HOST = "_no_host_";

    private Context<RegisteredHost>         host;

    private Context<RegisteredInstance>     instance;

    private Context<RegisteredProcess>      process;

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
        vmRepo.get().lock();

        // stop
        log.warn( "Killing process without checking OS process!" );
        StopProcessOperation op = new StopProcessOperation().vmRepo
                .put( vmRepo.get() )
                .process.put( process.get() );
        op.execute( null, null );
        
        // start
        StartProcessOperation op2 = new StartProcessOperation()
                .instance.put( instance.get() );
        op2.execute( null, null );
        process.set( op2.process.get() );

        checked.set( this );
        return OK_STATUS;
    }
    
}
