package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.http.ForwardRequest;
import io.mapzone.controller.http.ProxyServlet;
import io.mapzone.controller.ops.StartProcessOperation;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.RegisteredInstance;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProcessStarted
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( ProcessStarted.class );

    public static final String              NO_HOST = "_no_host_";

    private Context<RegisteredInstance>     instance;

    private Context<RegisteredProcess>      process;

    private Status                          cause;

    
    @Override
    public boolean init( Provision failed, @SuppressWarnings("hiding") Status cause ) {
        this.cause = cause;
        return failed instanceof ForwardRequest
                && !process.isPresent()
                && cause == null
                /*&& cause.getCause().equals( OkToForwardRequest.NO_PROCESS )*/;
    }

    
    @Override
    public Status execute() throws Exception {
        // find process
        String[] path = ProxyServlet.projectName( request.get() );
        String projectName = path[1];
        String orgName = path[0];

        // find instance -> process
        vmRepo.get().findInstance( orgName, projectName, null ).ifPresent( i -> instance.set( i ) );
        assert instance.isPresent() : "No project instance found for: " + orgName + "/" + projectName;

        process.set( instance.get().process.get() );
        
        if (!process.isPresent()) {
            // lock others while we change things
            vmRepo.get().lock();

            // start instance
            StartProcessOperation op = new StartProcessOperation()
                    .instance.put( instance.get() );
            op.execute( null, null );
            process.set( op.process.get() );
        }
        return OK_STATUS;
    }
    
}
