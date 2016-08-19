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
package io.mapzone.arena.share.content;

import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vividsolutions.jts.geom.Coordinate;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.jmx.ArenaConfig;
import io.mapzone.arena.share.ui.ShareContext;
import io.mapzone.arena.share.ui.ShareContext.SelectionDescriptor;

/**
 * Creates a complete openlayers HTML page as string.
 *
 * @author Steffen Stundzig
 */
public class OpenLayersContentBuilder
        implements ShareableContentBuilder {

    
    public class OpenLayersContent {

        public String jsressource;
        public String cssressource;
        public String body;
        public String js;
        public String complete;

    }
    private static Log         log      = LogFactory.getLog( OpenLayersContentBuilder.class );

    public final static String MIMETYPE = "application/openlayers";

    private ShareContext  context;


    @Override
    public OpenLayersContent content() {

        OpenLayersContent content = new OpenLayersContent();
        content.jsressource = JSRESOURCE;
        content.cssressource = CSSRESOURCE;
        content.body = BODY;

        StringBuffer js = new StringBuffer(MAPJSSTART);
        
        StringJoiner layers = new StringJoiner( ",\n" );
        for (SelectionDescriptor selection : context.selectionDescriptors.get()) {
            String layer = replace( LAYERJS, "WMSURL", ArenaPlugin.instance().config().getProxyUrl() + "/ows" );
            layer = replace( layer, "LAYER", selection.layer.get().label.get() );
            if (!StringUtils.isBlank( ArenaPlugin.instance().config().getServiceAuthToken() )) {
                layer = replace( layer, "AUTH", ", 'authToken': '"
                        + ArenaPlugin.instance().config().getServiceAuthToken() + "'" );
            }
            else {
                layer = replace( layer, "AUTH", "" );
            }
            //            Envelope envelope = context.boundingBox.get();
//            StringBuffer extent = new StringBuffer().append( (int)envelope.getMinX() ).append( "," ).append( (int)envelope.getMinY() ).append( "," ).append( (int)envelope.getMaxX() ).append( "," ).append( (int)envelope.getMaxY() );
//            layer = replace( layer, "EXTENT", extent.toString() );
            layer = replace(layer, "CRS", context.crs.get().getIdentifiers().iterator().next().toString());
            layers.add( layer );
        }
        js.append( layers.toString() );

        Coordinate centreCoordinate = context.boundingBox.get().centre();
        StringBuffer centre = new StringBuffer().append( (int)centreCoordinate.x ).append( "," ).append( (int)centreCoordinate.y );
        String jsend = replace( MAPJSEND, "CENTER", centre.toString() );
        jsend = replace(jsend, "RESOLUTION", "" + context.resolution.get().intValue());
        js.append( jsend );
        
        content.js = js.toString();
        
        StringBuffer complete = new StringBuffer();
        complete.append( "<!DOCTYPE html>\n" );
        complete.append( "<html>\n" );
        complete.append( "  <head>\n" );
        complete.append( "    <title>" ).append( ArenaConfig.getAppTitle() ).append( "</title>\n" );
        complete.append( "    <link rel='stylesheet' href='" ).append( content.cssressource ).append( "' type='text/css'>\n" );
        complete.append( "    <script src='" ).append( content.jsressource ).append( "'></script>\n" );
        complete.append( "  </head>\n" );
        complete.append( "  <body>\n" );
        complete.append( "    " ).append( content.body ).append( "\n" );
        complete.append( "    <script>\n" );
        complete.append( content.js );
        complete.append( "    </script>\n" );
        complete.append( "  </body>\n" );
        complete.append( "</html>\n" );
        
        content.complete = complete.toString();
        
        return content;
    }


    @Override
    public boolean supports( final String mimeType, @SuppressWarnings( "hiding" ) final ShareContext context ) {
        this.context = context;
        return MIMETYPE.equals( mimeType ) && !context.selectionDescriptors.get().isEmpty();
    }


    private String replace( String template, String key, String value ) {
        return template.replaceAll( "_" + key + "_", value );
    }
    
    private final static String JSRESOURCE = "http://openlayers.org/en/v3.17.1/build/ol.js";
    private final static String CSSRESOURCE = "http://openlayers.org/en/v3.17.1/css/ol.css";
    private final static String BODY = "<div id='map' class='map'></div>";
    private final static String LAYERJS = new StringBuffer()
            .append( "        new ol.layer.Tile({\n")
//            .append( "          extent: [_EXTENT_],\n")
            .append( "          source: new ol.source.TileWMS({\n")
            .append( "            url: '_WMSURL_',\n")
            .append( "            params: {'LAYERS': '_LAYER_', 'SRS': '_CRS_'_AUTH_},\n")
//            .append( "            serverType: 'geoserver'\n")
            .append( "          })\n")
            .append( "        })").toString();
    private final static String MAPJSSTART = new StringBuffer()
            .append( "      var layers = [\n").toString();
    private final static String MAPJSEND = new StringBuffer()
            .append( "\n      ];\n")
            .append( "      var map = new ol.Map({\n")
            .append( "        layers: layers,\n")
            .append( "        target: 'map',\n")
            .append( "        view: new ol.View({\n")
            .append( "          center: [_CENTER_],\n")
            .append( "          resolution: _RESOLUTION_\n")
            .append( "        })\n")
            .append( "      });\n").toString();
}
