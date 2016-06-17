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
package io.mapzone.controller.catalog.http;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CatalogRequest {

    public static CatalogRequest read( HttpServletRequest request, HttpServletResponse response ) throws IOException {
        return new CatalogRequest( request, response );
    }
    
    
    // instance *******************************************
    
    private HttpServletRequest          httpRequest;
    
    private HttpServletResponse         httpResponse;
    
    private String                      body;
    
    /** Lower case param name into param values. */
    private ListMultimap<String,String> params = MultimapBuilder.hashKeys( 16 ).arrayListValues( 4 ).build();

    
    protected CatalogRequest( HttpServletRequest request, HttpServletResponse response ) throws IOException {
        this.httpRequest = request;
        this.httpResponse = response;
        
        // read body
        try (
            BufferedReader in = request.getReader();
        ){
            StringBuilder buf = new StringBuilder( 1024 );
            String line = null;
            while ((line = in.readLine()) != null) {
                buf.append( line );
            }
            body = buf.toString();
        }
        
        // read parameters (no XML POST or SOAP requests)
        for (Enumeration<String> it=request.getParameterNames(); it.hasMoreElements(); ) {
            String name = normalize( it.nextElement() );
            String[] values = request.getParameterValues( name );
            Arrays.stream( values ).forEach( value -> params.put( name, value ) );
        }
    }

    
    public Optional<String> parameter( String name ) {
        assert params.get( normalize( name ) ).size() <= 1;
        return params.get( normalize( name ) ).stream().findAny();
    }


    public List<String> parameters( String name ) {
        return params.get( normalize( name ) );
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
