package io.mapzone.controller.http;

import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ForwardResponse
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( ForwardResponse.class );

    private Context<HttpRequestForwarder>   requestForwarder;

    
    @Override
    public boolean init( Provision failed, Status cause  ) {
        return failed == null;
    }


    @Override
    public Status execute() throws Exception {
        try (
            HttpResponseForwarder forwarder = new HttpResponseForwarder( requestForwarder.get() );
        ) {
            forwarder.service( request.get(), response.get() );
        }
        return OK_STATUS;
    }
 
}
