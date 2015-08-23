package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.runtime.HostRuntime;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class HostRunning
        extends DefaultProvision {

    private Context<RegisteredHost>     registeredHost;

    private Context<HostRuntime>        host;

    
    @Override
    public boolean init( Provision failed ) {
        return failed instanceof ProcessRunning;
    }


    @Override
    public Status execute() throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
