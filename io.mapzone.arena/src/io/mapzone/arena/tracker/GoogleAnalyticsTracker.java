/*
 * polymap.org Copyright (C) 2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package io.mapzone.arena.tracker;

import java.util.EventObject;
import java.util.List;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.common.collect.Lists;

import org.polymap.core.runtime.event.Event.Scope;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;

/**
 * This tracker, tracks servlet requests to mapzone into the configured tracker id.
 *
 * @author Steffen Stundzig
 */
public class GoogleAnalyticsTracker
        implements AutoCloseable {

    private static final Log    log = LogFactory.getLog( GoogleAnalyticsTracker.class );

    private final String        trackerId;

    private CloseableHttpClient httpClient;


    public GoogleAnalyticsTracker( final String trackerId ) {
        this.trackerId = trackerId;
    }


    public void start() {
        EventManager.instance().subscribe( this, ev -> ev instanceof ServletRequestEvent );
    }


    @Override
    public void close() {
        EventManager.instance().unsubscribe( this );
    }

    // delay introduced a async background job here
    @EventHandler( delay = 100, scope = Scope.JVM )
    public void track( List<EventObject> events ) {
        List<NameValuePair> params = Lists.newArrayList();
        for (EventObject event : events) {
            // add more types here
            if (event instanceof ServletRequestEvent) {
                extractParams( params, (ServletRequestEvent)event );
            }
            send( params );
            params.clear();
        }
    }


    private void extractParams( List<NameValuePair> params, ServletRequestEvent event ) {
        params.add( new BasicNameValuePair( "t", "event" ) );
        params.add( new BasicNameValuePair( "ec", "externalRequest" ) );
        HttpServletRequest request = (HttpServletRequest)event.getServletRequest();
        if (request != null) {
            try {
                // TODO, add user or session or something else here authToken
                String clientId = "anonymous";
                String authToken = request.getParameter( "authToken" );
                if (!StringUtils.isBlank( authToken )) {
                    clientId = authToken;
                }
                addEncoded( params, "cid", clientId );

                // request.get
                String context = request.getServletPath();
                int index = context.lastIndexOf( "/" );
                if (index != -1) {
                    context = context.substring( index + 1 );
                }
                addEncoded( params, "ea", context );
                if (!StringUtils.isBlank( request.getContentType() )) {
                    index = request.getContentType().toLowerCase().lastIndexOf( "charset=" );
                    if (index != -1) {
                        addEncoded( params, "de", request.getContentType().substring( index + 8 ) );
                    }
                }
                addEncoded( params, "dl", request.getServletPath() + "?" + request.getQueryString() );
                addEncoded( params, "el", request.getQueryString() );
                addEncoded( params, "dr", request.getHeader( "Referer" ) );
                addEncoded( params, "ua", request.getHeader( "User-Agent" ) );
                addEncoded( params, "uip", request.getRemoteAddr() );
            }
            catch (UnsupportedEncodingException e) {
                // do nothing
                log.error( e );
            }

        }
    }


    private void addEncoded( List<NameValuePair> params, String key, String value )
            throws UnsupportedEncodingException {
        if (!StringUtils.isBlank( value )) {
            params.add( new BasicNameValuePair( key, URLEncoder.encode( value, "utf-8" ) ) );
        }
    }


    private void send( List<NameValuePair> params ) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost( "https://www.google-analytics.com/collect" );
            params.add( new BasicNameValuePair( "v", "1" ) );
            params.add( new BasicNameValuePair( "tid", trackerId ) );
            httpPost.setEntity( new UrlEncodedFormEntity( params ) );
            response = httpClient().execute( httpPost );
            EntityUtils.consume( response.getEntity() );
        }
        catch (IOException e) {
            // do nothing
            log.error( e );
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                }
                catch (IOException e) {
                    log.error( e );
                }
            }
        }
    }


    private CloseableHttpClient httpClient() {
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }
}
