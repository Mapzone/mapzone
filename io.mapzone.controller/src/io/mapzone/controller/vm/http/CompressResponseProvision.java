package io.mapzone.controller.vm.http;

import io.mapzone.controller.provision.Provision;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CompressResponseProvision
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( CompressResponseProvision.class );


    @Override
    public boolean init( Provision failed , Status cause  ) {
        return failed == null;
    }


    @Override
    public Status execute() throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
