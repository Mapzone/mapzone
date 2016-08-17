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
package io.mapzone.arena.share;

import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

import org.polymap.core.runtime.event.EventManager;

/**
 * Servlet to expose the share infos for facebook, google, twitter and co.
 * 
 * This information can't be handled by the normal RAP index.html page.
 *
 * @author Steffen Stundzig
 */
public class ShareInfoServlet
        extends HttpServlet {

    private static Log        log              = LogFactory.getLog( ShareInfoServlet.class );

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;


    public ShareInfoServlet() {
    }


    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        try {

            EventManager.instance().publish( new ServletRequestEvent( getServletContext(), req ) );
            //
            // if (req.getParameterMap().isEmpty()) {
            // resp.sendError( 400, "No parameters found! Please specify at least
            // 'text'." );
            // return;
            // }

            resp.setStatus( HttpStatus.SC_OK );
            resp.setContentType( "text/html;charset=utf-8" );
            handleCors( req, resp );

            final String projectName = "organisation/projectname";
            final String description = "description of the project";
            final String arenaUrl = req.getRequestURL().toString().replace( ShareServletsStarter.ALIAS, "/arena" );
            final StringBuilder imageUrl = new StringBuilder(req.getRequestURL().toString().replace( ShareServletsStarter.ALIAS, "/ows" ));
            imageUrl.append( "?SERVICE=WMS&VERSION=1.3.0&REQUEST=GetMap&FORMAT=image%2Fpng&LAYERS=" +encodedLayers + "&CRS=EPSG%3A3857&STYLES=&WIDTH=400&HEIGHT=300&BBOX=" + encodedBBox + "" );

            // convert addresses to result json
            OutputStreamWriter writer = new OutputStreamWriter( resp.getOutputStream() );
            writer.write( "<html>" );
            writer.write( " <head>" );
            writer.write( "  <title>mapzone.io " + projectName + "</title>" );
            writer.write( "  <meta name='author' content='mapzone GmbH' />" );
            writer.write( "  <meta name='description' content='" + description + "' />" );
            writer.write( "  <meta name='keywords' content='' />" );
            writer.write( "  <meta name='robots' content='index,follow' />" );
            writer.write( "  <meta name='audience' content='all' />" );
            writer.write( "  <meta name='revisit-after' content='5 days' />" );
            // facebook/opengraph
            writer.write( "  <meta name='og:image:url' content='" + imageUrl.toString() + "' />" );
            writer.write( "  <meta name='og:image:type' content='image/png' />" );
            writer.write( "  <meta name='og:image:width' content='400' />" );
            writer.write( "  <meta name='og:image:height' content='300' />" );
            writer.write( "  <meta name='og:type' content='website' />" );
            writer.write( "  <meta name='og:url' content='" + arenaUrl + "' />" );

            // perform a redirect
            writer.write( " <script type='text/javascript'>window.setTimeout(function(){ window.location.href = '"
                    + arenaUrl + "'; },10000);</script>" );
            writer.write( " </head>" );
            writer.write( " <body>" );
            writer.write( "  <iframe src='" + arenaUrl + "' width='100%' height='520' frameborder='0' allowfullscreen webkitallowfullscreen mozallowfullscreen oallowfullscreen msallowfullscreen></iframe>" );
            writer.write( " </body>" );
            writer.write( "<head>" );
            writer.flush();
            writer.close();
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
}
