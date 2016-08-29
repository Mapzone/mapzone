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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vividsolutions.jts.geom.Envelope;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.GeoServerStarter;
import io.mapzone.arena.share.ui.ShareContext;
import io.mapzone.arena.share.ui.ShareContext.SelectionDescriptor;

/**
 * Creates a complete openlayers HTML page as string.
 *
 * @author Steffen Stundzig
 */
public class ImagePngContentBuilder
        implements ShareableContentBuilder {

    public class ImagePngContent {

        public String imgResource;

        public String previewResource;

        public int    imgWidth;

        public int    imgHeight;

        public int    previewWidth;

        public int    previewHeight;
    }

    private static Log         log      = LogFactory.getLog( ImagePngContentBuilder.class );

    public final static String MIMETYPE = "image/png";

    private ShareContext       context;


    @Override
    public ImagePngContent content() {
        ImagePngContent content = new ImagePngContent();
        try {

            StringJoiner layers = new StringJoiner( "," );
            for (SelectionDescriptor selection : context.selectionDescriptors.get()) {
                layers.add( selection.layer.get().label.get() );
                // FIXME, if multilayers are working, remove this break!!!
                break;
            }
            Envelope envelope = context.boundingBox.get();
            StringJoiner extent = new StringJoiner( "," );
            extent.add( String.valueOf( (int)envelope.getMinX() ) );
            extent.add( String.valueOf( (int)envelope.getMinY() ) );
            extent.add( String.valueOf( (int)envelope.getMaxX() ) );
            extent.add( String.valueOf( (int)envelope.getMaxY() ) );

            final StringBuilder imageUrl = new StringBuilder( ArenaPlugin.instance().config().getProxyUrl() );
            imageUrl.append( GeoServerStarter.ALIAS );
            imageUrl.append( "?SERVICE=WMS&VERSION=1.3.0&REQUEST=GetMap&FORMAT=image%2Fpng&CRS=EPSG%3A3857&STYLES=" );
            imageUrl.append( "&LAYERS=" ).append( URLEncoder.encode( layers.toString(), "utf-8" ) );
            imageUrl.append( "&BBOX=" ).append( URLEncoder.encode( extent.toString(), "utf-8" ) );
            if (!StringUtils.isBlank( ArenaPlugin.instance().config().getServiceAuthToken() )) {
                imageUrl.append( "&authToken=" ).append( URLEncoder.encode( ArenaPlugin.instance().config().getServiceAuthToken(), "utf-8" ) );
            }
            content.imgWidth = context.displaySize.get().x;
            content.imgHeight = context.displaySize.get().y;

            content.previewWidth = 320;
            content.previewHeight = content.previewWidth * content.imgHeight / content.imgWidth;

            content.previewResource = new StringBuffer( imageUrl.toString() ).append( "&WIDTH=" ).append( content.previewWidth ).append( "&HEIGHT=" ).append( content.previewHeight ).toString();

            content.imgResource = imageUrl.append( "&WIDTH=" ).append( content.imgWidth ).append( "&HEIGHT=" ).append( content.imgHeight ).toString();
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }

        return content;
    }


    @Override
    public boolean supports( final String mimeType, @SuppressWarnings( "hiding" ) final ShareContext context ) {
        this.context = context;
        return MIMETYPE.equals( mimeType ) && !context.selectionDescriptors.get().isEmpty();
    }
}
