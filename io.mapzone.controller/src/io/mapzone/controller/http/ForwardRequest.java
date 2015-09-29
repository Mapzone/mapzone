package io.mapzone.controller.http;

import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.Provision.Status.Severity;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.RegisteredInstance;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ForwardRequest
        extends HttpProxyProvision {

    private static Log log = LogFactory.getLog( ForwardRequest.class );

    public static final String              BAD_RESPONSE = "_bad_response_";
    public static final String              IO_ERROR = "_io_error_";

    public static CloseableHttpClient       httpclient = HttpClients.createDefault();

    private Context<RegisteredProcess>      process;
    
    private Context<Project>                project;
    
    private Context<HttpRequestForwarder>   forwarder;
    
    @Override
    public boolean init( Provision failed, Status cause ) {
        return process.isPresent();
    }


    @Override
    public Status execute() throws Exception {
        assert process.isPresent();
        
        try {
            forwardRequest( process.get() );
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
    protected void forwardRequest( @SuppressWarnings("hiding") RegisteredProcess process ) throws ClientProtocolException, IOException, Exception {
        RegisteredInstance instance = process.instance.get();
        URI targetUri = new URIBuilder().setScheme( "http")
                .setHost( instance.host.get().inetAddress.get() )
                .setPort( process.port.get() )
                .build();

        HttpRequestForwarder _forwarder = new HttpRequestForwarder() {
            @Override
            protected String rewritePath( String path ) {
                return StringUtils.substringAfter( path, instance.project.get() );
            }
        };

        forwarder.set( _forwarder
                .targetUri.put( targetUri.toString() )
                .cookieNamePrefix.put( process.port.get().toString() + "_" ) );
        
        forwarder.get().service( request.get(), response.get() );
        
        proxyResponse.set( forwarder.get().proxyResponse );
        proxyRequest.set( forwarder.get().proxyRequest );
    }
    
}
