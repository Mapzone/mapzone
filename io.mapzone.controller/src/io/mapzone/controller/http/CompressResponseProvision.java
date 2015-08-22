package io.mapzone.controller.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CompressResponseProvision
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( CompressResponseProvision.class );


    @Override
    public boolean init( Provision failed ) {
        return failed == null;
    }


    @Override
    public Status execute() throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
