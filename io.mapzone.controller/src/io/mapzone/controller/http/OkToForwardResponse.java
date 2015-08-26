package io.mapzone.controller.http;

import java.io.InputStream;
import java.io.OutputStream;

import io.mapzone.controller.provision.Provision;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class OkToForwardResponse
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( OkToForwardResponse.class );

    
    @Override
    public boolean init( Provision failed, Status cause  ) {
        return failed == null;
    }


    @Override
    public Status execute() throws Exception {
        // copy headers
        for (Header header : downResponse.get().getAllHeaders() ) {
            log.info( "    header: " + header.getName() + " = " + header.getValue() );
            response.get().addHeader( header.getName(), header.getValue() );
        }

        // copy stream
        try (
            InputStream in = downResponse.get().getEntity().getContent();
            OutputStream out = response.get().getOutputStream();
        ){
            IOUtils.copy( in, out );
        }
        response.get().flushBuffer();
        return OK_STATUS;
    }
 
}
