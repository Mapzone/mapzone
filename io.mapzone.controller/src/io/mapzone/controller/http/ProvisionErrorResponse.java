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
package io.mapzone.controller.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class ProvisionErrorResponse {

    private static Log log = LogFactory.getLog( ProvisionErrorResponse.class );
    
    public static void send( HttpServletResponse response, int status, String msg ) {
        try {
            response.setContentType( "text/html" );
            OutputStreamWriter out = new OutputStreamWriter( response.getOutputStream(), Charset.forName( "UTF-8" ) );
            out.write( "<html>\n" );
            out.write( "<h1>" + msg + "</h1>\n" );
            out.write( "</html>" );
            out.flush();
            response.setStatus( status );
        }
        catch (IOException e) {
            log.warn( "", e );
        }
    }
    
}
