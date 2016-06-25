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

import io.mapzone.controller.catalog.csw.GetRecordByIdResponse;
import io.mapzone.controller.catalog.csw.GetRecordsResponse;
import io.mapzone.controller.catalog.csw.Params;
import io.mapzone.controller.catalog.csw.TransactionResponse;
import net.opengis.cat.csw.v_2_0_2.GetRecordByIdType;
import net.opengis.cat.csw.v_2_0_2.GetRecordsType;
import net.opengis.cat.csw.v_2_0_2.RequestBaseType;
import net.opengis.cat.csw.v_2_0_2.TransactionType;

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
        responseTypes.put( GetRecordByIdResponse.REQUEST, GetRecordByIdResponse.class );
        responseTypes.put( TransactionResponse.REQUEST, TransactionResponse.class );
    }
    
    
    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        CatalogRequest request = new CatalogRequest( req, resp );
        try {
            request.parseParameters();
            
            String requestParam = Params.REQUEST_PARAM.get( request );
            Class<? extends Response> responseType = responseTypes.get( requestParam );
            if (responseType == null) {
                throw new MalformedRequestException( "Unhandled request type: " + requestParam );
            }
            doService( request, responseType );
        }
        catch (Throwable e) {
            handleException( e, request );
        }
    }


    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        CatalogRequest request = new CatalogRequest( req, resp );
        try {
            RequestBaseType body = request.parseBody();

            if (body instanceof GetRecordsType) {
                doService( request, GetRecordsResponse.class );
            }
            else if (body instanceof GetRecordByIdType) {
                doService( request, GetRecordByIdResponse.class );
            }
            else if (body instanceof TransactionType) {
                doService( request, TransactionResponse.class );
            }
            else {
                throw new MalformedRequestException( "Unhandled request type: " + body );
            }
        }
        catch (Throwable e) {
            handleException( e, request );
        }
    }


    protected void doService( CatalogRequest request,Class<? extends Response> responseType ) throws Exception {
        Response response = responseType.newInstance();
        response.execute( request );
    }


    protected void handleException( Throwable e, CatalogRequest request ) throws ServletException, IOException {
        if (e instanceof ServletException) {
            throw (ServletException)e;
        }
        else if (e instanceof MalformedRequestException) {
            request.httpResponse().sendError( 400, e.getMessage() );
        }
        throw new UnsupportedOperationException( "XXX: handle exception", e );
    }
    
}
