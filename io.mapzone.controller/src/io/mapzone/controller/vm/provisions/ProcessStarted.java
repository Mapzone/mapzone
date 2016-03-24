package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.HttpProxyProvision;
import io.mapzone.controller.http.ForwardRequest;
import io.mapzone.controller.http.ProxyServlet;
import io.mapzone.controller.ops.StartProcessOperation;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.ProcessRecord;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Checks if the process for the project was started at all,
 * {@link StartProcessOperation} if not. Finds the {@link ProjectInstanceRecord} and
 * its {@link ProcessRecord} for the org and project name in the request. Puts both
 * into the provision {@link Context}.
 * 
 * @author Falko Bräutigam
 */
public class ProcessStarted
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( ProcessStarted.class );

    public static final String              NO_HOST = "_no_host_";

    private Context<ProjectInstanceRecord>  instance;

    private Context<ProcessRecord>          process;

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
        // XXX store in HTTP session context?
        instance.set( vmRepo().findInstance( orgName, projectName, null )
                .orElseThrow( () -> new IllegalStateException( "No project instance found for: " + orgName + "/" + projectName ) ) );

        instance.get().homePath.get();  // force pessimistic lock
        process.set( instance.get().process.get() );
        
        if (!process.isPresent()) {
            // start instance
            StartProcessOperation op = new StartProcessOperation();
            op.instance.set( instance.get() );
                    
            op.execute( null, null );
            process.set( op.process.get() );
        }
        return OK_STATUS;
    }
    
}
