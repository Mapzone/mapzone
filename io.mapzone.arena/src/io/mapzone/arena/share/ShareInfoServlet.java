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

import java.util.Enumeration;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;

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

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.GeoServerStarter;
import io.mapzone.arena.jmx.ArenaConfig;

/**
 * Servlet to expose the share infos for facebook, google, twitter and co.
 * 
 * This information can't be handled by the normal RAP index.html page.
 *
 * @author Steffen Stundzig
 */
public class ShareInfoServlet
        extends HttpServlet {

    private static Log         log                 = LogFactory.getLog( ShareInfoServlet.class );

    /** serialVersionUID */
    private static final long  serialVersionUID    = 1L;

    public final static String PARAMETER_LAYERS    = "layers";

    public final static String PARAMETER_BBOX      = "bbox";

    public final static String PARAMETER_AUTHTOKEN = "authToken";


    public ShareInfoServlet() {
    }


    // http://localhost:8080/shareinfo?layers=OSM-WMS%2CAV&bbox=-3225406%2C5087116%2C5995406%2C8282883

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        try {

            Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                log.info( "HEADER '" + header + "': '" + req.getHeader( header ) + "'" );
            }
            EventManager.instance().publish( new ServletRequestEvent( getServletContext(), req ) );

            if (req.getParameterMap().isEmpty() || StringUtils.isBlank( req.getParameter( PARAMETER_LAYERS ) )
                    || StringUtils.isBlank( req.getParameter( PARAMETER_BBOX ) )) {
                resp.sendError( 400, "No parameters found! Please specify at least '" + PARAMETER_LAYERS + "' and '"
                        + PARAMETER_BBOX + "'." );
                return;
            }

            final String layers = req.getParameter( PARAMETER_LAYERS );
            final String bbox = req.getParameter( PARAMETER_BBOX );
            final String authToken = req.getParameter( PARAMETER_AUTHTOKEN );

            resp.setStatus( HttpStatus.SC_OK );
            resp.setContentType( "text/html;charset=utf-8" );

            final String projectName = ArenaConfig.getAppTitle();
            // FIXME add the project description here
            final String description = ArenaConfig.getAppTitle();
            final String arenaUrl = ArenaPlugin.instance().config().getProxyUrl() + ArenaPlugin.ALIAS;
            final StringBuilder imageUrl = new StringBuilder( ArenaPlugin.instance().config().getProxyUrl() );
            imageUrl.append( GeoServerStarter.ALIAS );
            imageUrl.append( "?SERVICE=WMS&VERSION=1.3.0&REQUEST=GetMap&FORMAT=image%2Fpng&CRS=EPSG%3A3857&STYLES=&WIDTH=1200&HEIGHT=630" );
            imageUrl.append( "&LAYERS=" ).append( URLEncoder.encode( layers, "utf-8" ) );
            imageUrl.append( "&BBOX=" ).append( URLEncoder.encode( bbox, "utf-8" ) );
            if (!StringUtils.isBlank( authToken )) {
                imageUrl.append( "&authToken=" ).append( URLEncoder.encode( authToken, "utf-8" ) );
            }

            // convert addresses to result json
            OutputStreamWriter writer = new OutputStreamWriter( resp.getOutputStream() );
            writer.write( "<html>" );
            writer.write( " <head>" );
            writer.write( "  <title>mapzone - " + projectName + "</title>" );
            writer.write( "  <meta name='author' content='mapzone' />" );
            writer.write( "  <meta name='description' content='" + description + "' />" );
            writer.write( "  <meta name='keywords' content='location, geo, web, osm, map, maps, styling, wms, csv, xls, georeference, geofence, geocode' />" );
            writer.write( "  <meta name='robots' content='index,follow' />" );
            writer.write( "  <meta name='audience' content='all' />" );
            // writer.write( " <meta name='revisit-after' content='5 days' />" );
            // facebook/opengraph
            writer.write( "  <meta property='og:locality' content='Leipzig'/>" );
            writer.write( "  <meta property='og:country-name' content='Germany'/>" );
            writer.write( "  <meta property='og:latitude' content='51.32794'/>" );
            writer.write( "  <meta property='og:longitude' content='12.33126'/>" );
            writer.write( "  <meta property='og:image:url' content='" + imageUrl.toString() + "' />" );
            // writer.write( " <meta property='og:image:type' content='image/png' />" );
            writer.write( "  <meta property='og:image:width' content='1200' />" );
            writer.write( "  <meta property='og:image:height' content='630' />" );
            writer.write( "  <meta property='og:type' content='article' />" );
            writer.write( "  <meta property='og:site_name' content='mapzone - " + projectName + "' />" );
            // wird grad nicht von Facebook unterstützt
            // writer.write( " <meta property='fb:app_id' content='1754931524765083'
            // />" );
            // writer.write( " <meta property='fb:admins' content='739545402735248'
            // />" );
            writer.write( "  <meta property='article:publisher' content='https://www.facebook.com/mapzoneio-1401853630109662' />" );
            writer.write( "  <meta property='article:author' content='https://www.facebook.com/stundzig' />" );

            writer.write( "  <meta property='og:url' content='" + arenaUrl + "' />" );

            // perform a redirect
            // writer.write( " <script
            // type='text/javascript'>window.setTimeout(function(){
            // window.location.href = '"
            // + arenaUrl + "'; },10000);</script>" );
            writer.write( " </head>" );
            writer.write( " <body>" );
            // writer.write( " <iframe src='" + arenaUrl
            // + "' width='100%' height='520' frameborder='0'
            // allowfullscreen='allowfullscreen'></iframe>" );
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
}
