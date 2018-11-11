/* 
 * mapzone.io
 * Copyright (C) 2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.controller.vm.http;

import java.util.concurrent.atomic.AtomicLong;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.DefaultManagedHttpClientConnection;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.entity.LaxContentLengthStrategy;
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;

import org.polymap.core.runtime.config.ConfigurationFactory;

/**
 * This allows to intercept processing of an {@link HttpRequest} to an upstream
 * project instance *right after* request was send - *before* start waiting on
 * response. A bit rough way to really split the execution of the REQUEST and
 * RESPONSE provision. {@link HttpClient} does not provide a more natural way to do
 * this.
 *
 * @author Falko Bräutigam
 */
class InterceptableHttpClientConnectionFactory
        extends ManagedHttpClientConnectionFactory {

    static final Log log = LogFactory.getLog( InterceptableHttpClientConnectionFactory.class );

    private static final AtomicLong COUNTER = new AtomicLong();
    

    protected void onRequestSubmitted( HttpRequest request ) {
    }

    
    protected void onResponseReceived( HttpResponse reponse ) {
    }
    

    @Override
    public ManagedHttpClientConnection create( HttpRoute route, ConnectionConfig config ) {
        ConfigurationFactory.inject( this );
        log.debug( "CONNECTION: " + config );

        ConnectionConfig cconfig = config != null ? config : ConnectionConfig.DEFAULT;
        CharsetDecoder chardecoder = null;
        CharsetEncoder charencoder = null;
        Charset charset = cconfig.getCharset();
        
        CodingErrorAction malformedInputAction = cconfig.getMalformedInputAction() != null ?
                cconfig.getMalformedInputAction() : CodingErrorAction.REPORT;
        
        CodingErrorAction unmappableInputAction = cconfig.getUnmappableInputAction() != null ?
                cconfig.getUnmappableInputAction() : CodingErrorAction.REPORT;
                
        if (charset != null) {
            chardecoder = charset.newDecoder();
            chardecoder.onMalformedInput( malformedInputAction );
            chardecoder.onUnmappableCharacter( unmappableInputAction );
            charencoder = charset.newEncoder();
            charencoder.onMalformedInput( malformedInputAction );
            charencoder.onUnmappableCharacter( unmappableInputAction );
        }

        return new DefaultManagedHttpClientConnection(
                "http-outgoing-" + COUNTER.getAndIncrement(),
                cconfig.getBufferSize(),
                cconfig.getFragmentSizeHint(),
                chardecoder,
                charencoder,
                cconfig.getMessageConstraints(),
                LaxContentLengthStrategy.INSTANCE,
                StrictContentLengthStrategy.INSTANCE,
                DefaultHttpRequestWriterFactory.INSTANCE,
                DefaultHttpResponseParserFactory.INSTANCE ) {

            @Override
            protected void onResponseReceived( HttpResponse response ) {
                log.debug( "RECEIVED: " + response );
                InterceptableHttpClientConnectionFactory.this.onResponseReceived( response );
            }

            @Override
            protected void onRequestSubmitted( HttpRequest request ) {
                log.debug( "SUBMITTED: " 
                        + "[" + StringUtils.right( Thread.currentThread().getName(), 2 ) + "] " 
                        + request );
                InterceptableHttpClientConnectionFactory.this.onRequestSubmitted( request );
            }

//            @Override
//            public void shutdown() throws IOException {
//                HttpRequestForwarder.log.info( "SHUTDOWN: " + toString() );
//                super.shutdown();
//            }
//
//            @Override
//            public void flush() throws IOException {
//                HttpRequestForwarder.log.info( "FLUSH: " + toString() );
//                super.flush();
//            }
//            
//            @Override
//            public void close() throws IOException {
//                HttpRequestForwarder.log.info( "CLOSE: " + toString() );
//                super.close();
//            }
        };
    }
    
}