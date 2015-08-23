package io.mapzone.controller.http;

import io.mapzone.controller.provision.Provision;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class OkToForwardResponse
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( OkToForwardResponse.class );

    
    @Override
    public Status execute() throws Exception {
        return OK_STATUS;
    }


    @Override
    public boolean init( Provision failed , Status cause  ) {
        throw new RuntimeException( "must not be called" );
    }
 
}
