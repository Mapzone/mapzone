package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.http.OkToForwardRequest;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProcessCleared
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( ProcessCleared.class );

    public static final String              NO_HOST = ProcessRunning.NO_HOST;

    private Context<Project>                project;
    
    private Context<RegisteredHost>         host;

    private Context<RegisteredProcess>      process;

    private Status                          cause;

    
    @Override
    public boolean init( Provision failed, @SuppressWarnings("hiding") Status cause ) {
        this.cause = cause;
        return failed instanceof OkToForwardRequest
                && cause.getCause().equals( OkToForwardRequest.BAD_RESPONSE );
    }

    
    @Override
    public Status execute() throws Exception {
        throw new RuntimeException( "Not yet implemented" );
    }
    
}
