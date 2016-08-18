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
package io.mapzone.arena.csw.catalog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.geotools.data.wms.WebMapServer;
import org.geotools.data.wms.request.GetMapRequest;
import org.geotools.ows.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

/**
 * A {@link WebMapServer} for WMS provided by mapzone. Adds specific params to the
 * URL.
 *
 * @author Falko Bräutigam
 */
public class MapzoneWebMapServer
        extends WebMapServer {

    private static Log log = LogFactory.getLog( MapzoneWebMapServer.class );

    private String     paramName;

    private String     paramValue;

    
    public MapzoneWebMapServer( String url, String paramName, String paramValue ) 
            throws IOException, ServiceException {
        super( buildURL( url, paramName, paramValue ), 30 );
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    
    protected static URL buildURL( String url, String paramName, String paramValue ) throws MalformedURLException {
        // capabilities doc is requested inside this super ctor, therefore paramName/Value
        // is passed via URL instead of properties
        assert !url.contains( "?" );
        String result = Joiner.on( "" ).join( url, "?SERVICE=WMS&", paramName, "=", paramValue );
        return new URL( result );
    }
    
    
    @Override
    public GetMapRequest createGetMapRequest() {
        GetMapRequest result = super.createGetMapRequest();
        result.setVendorSpecificParameter( paramName, paramValue );
        return result;
    }

}
