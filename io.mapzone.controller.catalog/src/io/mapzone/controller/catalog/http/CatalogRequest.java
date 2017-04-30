/* 
 * polymap.org
 * Copyright (C) 2016-2017, the @authors. All rights reserved.
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
package io.mapzone.controller.catalog.http;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.input.TeeInputStream;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import io.mapzone.controller.catalog.csw.CswResponse;

/**
 * Represents a HTTP catalog request from a client. CatalogRequests are handled by
 * the {@link CatalogServlet}.
 *
 * @author Falko Bräutigam
 */
public class CatalogRequest {

    private HttpServletRequest          httpRequest;
    
    private HttpServletResponse         httpResponse;

    private Object                      parsedBody;
    
    /** Lower case param name into param values. */
    private ListMultimap<String,String> params = MultimapBuilder.hashKeys( 16 ).arrayListValues( 4 ).build();

    
    protected CatalogRequest( HttpServletRequest request, HttpServletResponse response ) {
        this.httpRequest = request;
        this.httpResponse = response;
    }

    
    <T> T parseBody() throws Exception {
        Unmarshaller unmarshaller = CswResponse.jaxbContext.get().createUnmarshaller();
        InputStream in = httpRequest.getInputStream();
        System.out.println( "REQUEST: " );
        TeeInputStream tee = new TeeInputStream( in, System.out );
        parsedBody = ((JAXBElement)unmarshaller.unmarshal( new StreamSource( tee ) )).getValue();
        return (T)parsedBody;
    }

    
    void parseParameters() {
        for (Enumeration<String> it=httpRequest.getParameterNames(); it.hasMoreElements(); ) {
            String name = normalize( it.nextElement() );
            String[] values = httpRequest.getParameterValues( name );
            Arrays.stream( values ).forEach( value -> params.put( name, value ) );
        }
    }
    
    
    /** 
     * The parsed request body if this is a POST request. 
     */
    public <T> Optional<T> parsedBody() {
        return Optional.ofNullable( (T)parsedBody );
    }
    
    
    public Optional<String> parameter( String name ) {
        assert params.get( normalize( name ) ).size() <= 1;
        return params.get( normalize( name ) ).stream().findAny();
    }


    public List<String> parameters( String name ) {
        return params.get( normalize( name ) );
    }

    public ListMultimap<String,String> parameters() {
        return params;
    }

    
    /** 
     * Normalizes a parameter name.
     */
    public String normalize( String name ) {
       return name.toLowerCase();    
    }
    
    
    public HttpServletRequest httpRequest() {
        return httpRequest;
    }

    public HttpServletResponse httpResponse() {
        return httpResponse;
    }

    public void flushResponse() throws IOException {
        httpResponse.flushBuffer();
    }
    
}
