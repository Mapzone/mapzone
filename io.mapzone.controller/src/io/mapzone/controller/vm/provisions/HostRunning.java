package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.provision.Provision;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class HostRunning
        extends DefaultProvision {

    @Override
    public boolean init( Provision failed, Status cause ) {
        return failed instanceof ProcessStarted;
    }


    @Override
    public Status execute() throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
