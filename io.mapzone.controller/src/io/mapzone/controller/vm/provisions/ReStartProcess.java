/* Copyright (C) 2018 Falko Bräutigam. All rights reserved. */
package io.mapzone.controller.vm.provisions;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.runtime.cache.Cache;
import org.polymap.core.runtime.cache.CacheConfig;

import io.mapzone.controller.ops.StartProcessOperation;
import io.mapzone.controller.ops.StopProcessOperation;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.Provision.Status.Severity;
import io.mapzone.controller.vm.http.ForwardRequest;
import io.mapzone.controller.vm.http.HttpProvisionRuntimeException;
import io.mapzone.controller.vm.http.HttpProxyProvision;
import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;

/**
 * Checks if the process for the project has been started at all, and starts
 * {@link StartProcessOperation} if not. Finds the {@link ProjectInstanceRecord} and
 * its {@link ProcessRecord} for the org and project name in the request. Puts both
 * into the provision {@link Context}.
 * 
 * @author Falko Bräutigam
 */
public class ReStartProcess
        extends HttpProxyProvision {

    private static final Log log = LogFactory.getLog( ReStartProcess.class );

    public static final String              NO_HOST = "_no_host_";

    static Cache<ProjectInstanceIdentifier,URI> targetUris = CacheConfig.defaults().createCache();

    private Context<URI>                    projectUri;

    private Context<ProjectInstanceRecord>  instance;

    private Context<ProcessRecord>          process;
    
    private Context<ReStartProcess>         checked;
    
    
    @Override
    public boolean init( Provision failed, Status cause ) {
        return failed instanceof ForwardRequest
                && cause != null            // ForwardRequest failed
                && !checked.isPresent();    // start ones per request
    }

    
    @Override
    public Status execute() throws Exception {
        // find instance -> process
        ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( request.get() );
        instance.set( vmUow().findInstance( pid )
                .orElseThrow( () -> new HttpProvisionRuntimeException( 404, "No such project: " + pid ) ) );

        instance.get().homePath.get();  // force (pessimistic) lock on instance
        
        process.set( instance.get().process.get() );

        // stop pending process that did not properly responde to failed ForwardRequest
        if (instance.get().process.isPresent()) {
            log.info( "    Stopping process: " + instance.get().project.get() + " -- started at " + process.get().started.get() );
            StopProcessOperation op = new StopProcessOperation();
            op.kill.set( true );
            op.process.set( process.get() );
            op.vmUow.set( vmUow() );
            op.execute( null, null );
            
            assert !instance.get().process.isPresent();
        }

        // try starting process
        StartProcessOperation op = new StartProcessOperation();
        op.vmUow.set( vmUow() );
        op.instance.set( instance.get() );
        op.execute( null, null );

        checked.set( this );            

        // actually started?
        if (op.process.isPresent()) {
            process.set( op.process.get() );
            projectUri.set( instance.get().uri() );
            return OK_STATUS;
        }
        else {
            return new Status( Severity.FAILED, "Unable to start process." );
        }
    }

}
