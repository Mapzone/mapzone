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

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.mapzone.controller.catalog.csw.GetRecordsResponse;
import io.mapzone.controller.catalog.csw.Params;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CatalogServlet
        extends HttpServlet {

    private static Log log = LogFactory.getLog( CatalogServlet.class );

    /**
     * Maps OGC request param string into handler.
     */
    private static final Map<String,Class<? extends Response>>  responseTypes;
    
    static {
        responseTypes = new HashMap();
        responseTypes.put( GetRecordsResponse.REQUEST, GetRecordsResponse.class );
    }
    
    
    @Override
    protected void service( HttpServletRequest httpRequest, HttpServletResponse httpResponse ) 
            throws ServletException, IOException {
        try {
            // parse request
            CatalogRequest request = CatalogRequest.read( httpRequest, httpResponse );
            
            // dispatch requests
            String requestParam = Params.REQUEST_PARAM.get( request );
            Class<? extends Response> responseType = responseTypes.get( requestParam );
            if (responseType == null) {
                throw new MalformedRequestException( "Unhandled request: " + requestParam );
            }
            Response response = responseType.newInstance();
            response.execute( request );
        }
        catch (ServletException e) {
            throw e;
        }
        catch (MalformedRequestException e) {
            httpResponse.sendError( 400, e.getMessage() );
        }
        catch (Exception e) {
            throw new UnsupportedOperationException( "XXX: handle exception", e );
        }
    }
    
}
