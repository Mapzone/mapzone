package io.mapzone.controller.provisions;

import javax.servlet.http.HttpServletRequest;

import io.mapzone.controller.http.Context;
import io.mapzone.controller.http.Provision;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.runtime.HostRuntime;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class HostRunning
        implements Provision {

    private Context<HttpServletRequest> request;
    
    private Context<RegisteredHost>     registeredHost;

    private Context<HostRuntime>        host;

    
    @Override
    public Status execute() throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
