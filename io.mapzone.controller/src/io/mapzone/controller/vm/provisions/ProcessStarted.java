package io.mapzone.controller.vm.provisions;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.utils.URIBuilder;

import org.polymap.core.runtime.cache.Cache;
import org.polymap.core.runtime.cache.CacheConfig;

import io.mapzone.controller.ops.StartProcessOperation;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.http.ForwardRequest;
import io.mapzone.controller.vm.http.HttpProvisionRuntimeException;
import io.mapzone.controller.vm.http.HttpProxyProvision;
import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;

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

    static Cache<ProjectInstanceIdentifier,URI> targetUris = CacheConfig.defaults().createCache();

    private Context<URI>                    targetUri;

    private Context<ProjectInstanceRecord>  instance;

    private Context<ProcessRecord>          process;
    
    private Status                          cause;

    
    @Override
    public boolean init( Provision failed, @SuppressWarnings("hiding") Status cause ) {
        this.cause = cause;
        
        // check cached targetUri
        ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( request.get() );
        URI cached = targetUris.get( pid );
        if (cached != null) {
            targetUri.set( cached );
        }
        
        return failed instanceof ForwardRequest
                && !targetUri.isPresent()  // FAST-FORWARD!
                && !process.isPresent()
                && cause == null
                /*&& cause.getCause().equals( OkToForwardRequest.NO_PROCESS )*/;
    }

    
    @Override
    public Status execute() throws Exception {
        // find instance -> process
        ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( request.get() );
        instance.set( vmUow().findInstance( pid )
                .orElseThrow( () -> new HttpProvisionRuntimeException( 404, "No such project: " + pid ) ) );

        instance.get().homePath.get();  // force (pessimistic) lock on instance
        process.set( instance.get().process.get() );
        
        // XXX make sure that OS process is not running
        
        assert !process.isPresent();

        // start instance
        StartProcessOperation op = new StartProcessOperation();
        op.vmUow.set( vmUow() );
        op.instance.set( instance.get() );
        op.execute( null, null );
        process.set( op.process.get() );
        
        targetUri.set( new URIBuilder().setScheme( "http" )
                .setHost( instance.get().host.get().inetAddress.get() )
                .setPort( process.get().port.get() )
                .build() );
        
        //
        targetUris.putIfAbsent( pid, targetUri.get() );

        return OK_STATUS;
    }
    
}
