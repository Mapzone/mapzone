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
package io.mapzone.arena.geocode;

import java.util.List;
import java.util.Locale;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import org.polymap.core.runtime.event.EventManager;

/**
 * Servlet to expose the geocode service.
 * 
 * Parameters should be encoded with iso-8859-1
 *
 * @author Steffen Stundzig
 */
public class GeocodeServlet
        extends HttpServlet {

    /** serialVersionUID */
    private static final long    serialVersionUID = 1L;

    private final GeocodeService service;


    public GeocodeServlet( final GeocodeService service ) {
        this.service = service;
    }


    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        try {
            
            EventManager.instance().publish( new ServletRequestEvent( getServletContext(), req ));
            
            GeocodeQuery query = extractQuery( req );

            // perform search
            List<Address> addresses = service.geocode( query );

            resp.setStatus( HttpStatus.SC_OK );
            resp.setContentType( "application/json;charset=utf-8" );
            handleCors( req, resp );

            // convert addresses to result json
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( resp.getOutputStream() );
            JSONWriter writer = new JSONWriter( outputStreamWriter );
            writer.object();
            writer.key( "results" );
            writer.array();
            for (Address address : addresses) {
                writer.value( toJSON( address ) );
            }
            writer.endArray();
            writer.endObject();
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            resp.sendError( HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage() );
        }
    }


    private void handleCors( HttpServletRequest req, HttpServletResponse resp ) {
        String origin = req.getHeader( "Origin" );
        resp.addHeader( "Access-Control-Allow-Origin", StringUtils.isBlank( origin ) ? "*" : origin );
        resp.addHeader( "Access-Control-Allow-Headers", req.getHeader( "Access-Control-Request-Headers" ) );
        resp.addHeader( "Access-Control-Allow-Methods", "GET" );
    }


    private GeocodeQuery extractQuery( HttpServletRequest req ) throws Exception {
        GeocodeQuery query = new GeocodeQuery();
        // extract params
        if (!StringUtils.isBlank( req.getParameter( "country" ) )) {
            query.country.set( new Locale("", req.getParameter( "country" ) ) );
        }
        if (!StringUtils.isBlank( req.getParameter( "targetLanguage" ) )) {
            query.targetLanguage.set( new Locale( req.getParameter( "targetLanguage" ) ) );
        }
        if (!StringUtils.isBlank( req.getParameter( "text" ) )) {
            query.text.set( URLEncoder.encode( req.getParameter( "text" ), "iso-8859-1" ) );
        }
        else {
            if (!StringUtils.isBlank( req.getParameter( "postalCode" ) )) {
                query.postalCode.set( URLEncoder.encode( req.getParameter( "postalCode" ), "iso-8859-1" ) );
            }
            if (!StringUtils.isBlank( req.getParameter( "city" ) )) {
                query.city.set( URLEncoder.encode( req.getParameter( "city" ), "iso-8859-1" ) );
            }
            if (!StringUtils.isBlank( req.getParameter( "street" ) )) {
                query.street.set( URLEncoder.encode( req.getParameter( "street" ), "iso-8859-1" ) );
            }
            if (!StringUtils.isBlank( req.getParameter( "houseNumber" ) )) {
                query.houseNumber.set( URLEncoder.encode( req.getParameter( "houseNumber" ), "iso-8859-1" ) );
            }
        }
        return query;
    }


    private JSONObject toJSON( Address address ) {
        JSONObject json = new JSONObject();
        if (!StringUtils.isBlank( address.id )) {
            json.put( "id", address.id );
        }
        if (!StringUtils.isBlank( address.label )) {
            json.put( "label", address.label );
        }
        if (!StringUtils.isBlank( address.country )) {
            json.put( "country", address.country );
        }
        if (!StringUtils.isBlank( address.state )) {
            json.put( "state", address.state );
        }
        if (!StringUtils.isBlank( address.county )) {
            json.put( "county", address.county );
        }
        if (!StringUtils.isBlank( address.city )) {
            json.put( "city", address.city );
        }
        if (!StringUtils.isBlank( address.district )) {
            json.put( "district", address.district );
        }
        if (!StringUtils.isBlank( address.street )) {
            json.put( "street", address.street );
        }
        if (!StringUtils.isBlank( address.houseNumber )) {
            json.put( "houseNumber", address.houseNumber );
        }
        if (!StringUtils.isBlank( address.postalCode )) {
            json.put( "postalCode", address.postalCode );
        }
        if (address.position != null) {
            JSONObject position = new JSONObject();
            json.put( "position", position );
            position.put( "longitude", address.position.getX() );
            position.put( "latitude", address.position.getY() );
        }
        return json;
    }
}
