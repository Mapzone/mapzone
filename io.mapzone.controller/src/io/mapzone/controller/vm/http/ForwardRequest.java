/* Copyright (C) 2018 Falko Bräutigam. All rights reserved. */
package io.mapzone.controller.vm.http;

import static org.apache.commons.lang3.StringUtils.substringAfter;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;

import com.google.common.base.Joiner;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;

import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.Provision.Status.Severity;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class ForwardRequest
        extends HttpProxyProvision {

    private static final Log log = LogFactory.getLog( ForwardRequest.class );

    private static final Pattern            NO_COOKIE_CHAR = Pattern.compile( "[^A-Za-z0-9_+-]" );
    
    public static final String              BAD_RESPONSE = "_bad_response_";
    public static final String              IO_ERROR = "_io_error_";
    public static final String              NO_URI = "_no_uri_";

    /**
     * Executed when the request is send - before start waiting on response. See
     * {@link InterceptableHttpClientConnectionFactory} for detail.
     */
    public Config<Consumer<HttpRequest>>    onRequestSend;
    
    private Context<URI>                    targetUri;
    
    private Context<HttpRequestForwarder>   forwarder;
    

    public ForwardRequest() {
        ConfigurationFactory.inject( this );
    }


    @Override
    public boolean init( Provision failed, Status cause ) {
        // always run -> execute() fails if no targetUri present
        // -> allow ReStartProcess to kick in
        return true;
    }


    @Override
    public Status execute() throws Exception {
        if (!targetUri.isPresent()) {
            return new Status( Severity.FAILED, NO_URI );                
        }

        try {
            forwardRequest( targetUri.get() );
            return OK_STATUS;

//            StatusLine statusLine = proxyResponse.get().getStatusLine();
//            log.warn( "Forwarding request failed. (" + statusLine.getReasonPhrase() + ")" );
//            return new Status( Severity.FAILED_CHECK_AGAIN, BAD_RESPONSE );
        }
        catch (IOException e) {
            log.warn( "Forwarding request failed. (" + e .getMessage() + ") (" + Thread.currentThread().getName() + ")" );
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
            @Override protected String rewritePath( String path ) {
                // -> /org/name/servletAlias
                return "/" + substringAfter( substringAfter( substringAfter( path, "/" ), "/" ), "/" );
            }
            @Override protected void onRequestSubmitted( HttpRequest _request ) {
                onRequestSend.get().accept( _request );
            }
        };

        ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( request.get() );
        // XXX readable names are good for debugging but to a bit expensive? better use #hashcode
        String cookiePrefix = Joiner.on( "_" ).join( pid.organization(), pid.project(), "" );
        cookiePrefix = NO_COOKIE_CHAR.matcher( cookiePrefix ).replaceAll( "+" );

        forwarder.set( _forwarder
                .targetUri.put( targetUri.toString() )
                .cookieNamePrefix.put( cookiePrefix ) );
        
        _forwarder.service( request.get(), response.get() );
        
        proxyResponse.set( _forwarder.proxyResponse );
        proxyRequest.set( _forwarder.proxyRequest );
    }
    
}
