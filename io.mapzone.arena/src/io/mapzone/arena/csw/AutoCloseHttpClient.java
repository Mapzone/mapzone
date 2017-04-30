/* 
 * polymap.org
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
package io.mapzone.arena.csw;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/**
 * 
 *
 * @author Falko Bräutigam
 */
@SuppressWarnings( "deprecation" )
public class AutoCloseHttpClient
        implements HttpClient {

    private static final Log log = LogFactory.getLog( AutoCloseHttpClient.class );

    private CloseableHttpClient     delegate;

    
    public AutoCloseHttpClient( CloseableHttpClient delegate ) {
        this.delegate = delegate;
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }

    public void close() throws IOException {
        log.info( "Closing CSW HttpClient..." );
        delegate.close();
    }

    @Override
    public HttpParams getParams() {
        return delegate.getParams();
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return delegate.getConnectionManager();
    }

    @Override
    public CloseableHttpResponse execute( HttpHost target, HttpRequest request, HttpContext context )
            throws IOException, ClientProtocolException {
        return delegate.execute( target, request, context );
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public CloseableHttpResponse execute( HttpUriRequest request, HttpContext context )
            throws IOException, ClientProtocolException {
        return delegate.execute( request, context );
    }

    @Override
    public CloseableHttpResponse execute( HttpUriRequest request ) throws IOException, ClientProtocolException {
        return delegate.execute( request );
    }

    @Override
    public CloseableHttpResponse execute( HttpHost target, HttpRequest request )
            throws IOException, ClientProtocolException {
        return delegate.execute( target, request );
    }

    @Override
    public <T> T execute( HttpUriRequest request, ResponseHandler<? extends T> responseHandler )
            throws IOException, ClientProtocolException {
        return delegate.execute( request, responseHandler );
    }

    @Override
    public boolean equals( Object obj ) {
        return delegate.equals( obj );
    }

    @Override
    public <T> T execute( HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context )
            throws IOException, ClientProtocolException {
        return delegate.execute( request, responseHandler, context );
    }

    @Override
    public <T> T execute( HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler )
            throws IOException, ClientProtocolException {
        return delegate.execute( target, request, responseHandler );
    }

    @Override
    public <T> T execute( HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler,
            HttpContext context ) throws IOException, ClientProtocolException {
        return delegate.execute( target, request, responseHandler, context );
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
    
}
