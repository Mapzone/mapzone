package io.mapzone.controller.http;

import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.Provision.Status.Severity;

import static org.apache.commons.lang3.StringUtils.substringAfter;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class ForwardRequest
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( ForwardRequest.class );

    public static final String              BAD_RESPONSE = "_bad_response_";
    public static final String              IO_ERROR = "_io_error_";

    public static CloseableHttpClient       httpclient = HttpClients.createDefault();

    private Context<URI>                    targetUri;
    
    private Context<HttpRequestForwarder>   forwarder;
    
    
    @Override
    public boolean init( Provision failed, Status cause ) {
        return targetUri.isPresent();
    }


    @Override
    public Status execute() throws Exception {
        assert targetUri.isPresent();
        
        try {
            forwardRequest( targetUri.get() );
            return OK_STATUS;

//            StatusLine statusLine = proxyResponse.get().getStatusLine();
//            log.warn( "Forwarding request failed. (" + statusLine.getReasonPhrase() + ")" );
//            return new Status( Severity.FAILED_CHECK_AGAIN, BAD_RESPONSE );
        }
        catch (IOException e) {
            log.warn( "Forwarding request failed. (" + e .getMessage() + ")" );
            return new Status( Severity.FAILED, IO_ERROR, e );                
        }
    }
 
    
    /**
     * 
     * @see HttpForwarder
     * @param process
     * @throws ClientProtocolException
     * @throws IOException
     * @throws Exception
     */
    protected void forwardRequest( @SuppressWarnings("hiding") URI targetUri )
            throws ClientProtocolException, IOException, Exception {
        
        HttpRequestForwarder _forwarder = new HttpRequestForwarder() {
            @Override
            protected String rewritePath( String path ) {
                // /org/name/servletAlias
                return "/" + substringAfter( substringAfter( substringAfter( path, "/" ), "/" ), "/" );
            }
        };

        int targetPort = targetUri.getPort();
        forwarder.set( _forwarder
                .targetUri.put( targetUri.toString() )
                .cookieNamePrefix.put( targetPort + "_" ) );
        
        forwarder.get().service( request.get(), response.get() );
        
        proxyResponse.set( forwarder.get().proxyResponse );
        proxyRequest.set( forwarder.get().proxyRequest );
    }
    
}
