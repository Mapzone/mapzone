package io.mapzone.controller.http;

import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.Provision.Status.Severity;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import java.util.Collections;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ForwardRequest
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( ForwardRequest.class );

    public static final String              BAD_RESPONSE = "_bad_response_";
    public static final String              IO_ERROR = "_io_error_";

    public static CloseableHttpClient       httpclient = HttpClients.createDefault();

    private Context<RegisteredProcess>      process;
    
    private Context<Project>                project;
    
    
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
        HttpServletRequest r = request.get();
        String method = r.getMethod();
        
        String path = StringUtils.substringAfter( r.getPathInfo(), process.instance.get().project.get() );
        @SuppressWarnings("deprecation")
        URI proxyUri = new URIBuilder().setScheme( "http")
                .setHost( process.instance.get().host.get().inetAddress.get() )
                .setPort( process.port.get() )
                .setPath( path )
                .setQuery( request.get().getQueryString() )
                .build();
        
        // GET
        if (method.equals( HttpGet.METHOD_NAME )) {
            proxyRequest.set( new HttpGet( proxyUri ) );
        }
        // POST
        else if (method.equals( HttpPost.METHOD_NAME )) {
            HttpEntityEnclosingRequest entityRequest = new BasicHttpEntityEnclosingRequest( method, proxyUri.toString() );
            // container handles closing streams
            entityRequest.setEntity( new InputStreamEntity( r.getInputStream(), r.getContentLength() ) );
            proxyRequest.set( entityRequest );
        }
        else {
            throw new RuntimeException( "Unhandled request method: " + r.getMethod() );
        }
        copyRequest( r, proxyRequest.get() );
        log.info( "REQUEST: " + proxyRequest.get() );
        
        // execute
        HttpHost host = new HttpHost( process.instance.get().host.get().inetAddress.get(), process.port.get(), "http" ); 
        proxyResponse.set( httpclient.execute( host, proxyRequest.get() ) );
        log.info( "RESPONSE: " + proxyResponse.get().getStatusLine() );
    }
    
    
    protected void copyRequest( HttpServletRequest from, HttpRequest to ) {
        for (String header : Collections.list( from.getHeaderNames() )) {
            String value = from.getHeader( header );
            if (header.toLowerCase().equals( "content-length" )) {
                log.debug( "    omitting header: " + header + " = " + value );
            }
            else {
                log.debug( "    request header: " + header + " = " + value );
                to.setHeader( header, value );
            }
        }
    }
    
}
