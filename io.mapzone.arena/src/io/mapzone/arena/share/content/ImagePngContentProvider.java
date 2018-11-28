/*
 * polymap.org 
 * Copyright (C) 2016-2018, the @authors. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.base.Joiner;
import com.vividsolutions.jts.geom.Envelope;

import org.polymap.service.geoserver.GeoServerUtils;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.GeoServerStarter;
import io.mapzone.arena.share.ui.ShareContext;
import io.mapzone.arena.share.ui.ShareContext.SelectionDescriptor;

/**
 * Creates a complete openlayers HTML page as string.
 *
 * @author Steffen Stundzig
 * @author Falko Bräutigam
 */
public class ImagePngContentProvider
        implements ShareableContentProvider {

    public final static String MIMETYPE = "image/png";
    
    public class ImagePngContent implements ShareableContent {

        public String imgResource;

        public String previewResource;

        public int    imgWidth;

        public int    imgHeight;

        public int    previewWidth;

        public int    previewHeight;
    }


    // instance *******************************************
    
    private ShareContext       context;


    @Override
    public ImagePngContent get() {
        ImagePngContent content = new ImagePngContent();

        StringJoiner layers = new StringJoiner( "," );
        for (SelectionDescriptor selection : context.selectionDescriptors.get()) {
            layers.add( GeoServerUtils.simpleName( selection.layer.get().label.get() ) );
            // FIXME, if multilayers are working, remove this break!!!
            //break;
        }
        Envelope bbox = context.boundingBox.get();
        String extent = Joiner.on( "," ).join( (int)bbox.getMinX(), (int)bbox.getMinY(), (int)bbox.getMaxX(), (int)bbox.getMaxY() );

        String imageUrl = ArenaPlugin.instance().config().getProxyUrl() + GeoServerStarter.ALIAS;
        List<NameValuePair> params = new ArrayList() {{
            add( nameValue( "SERVICE", "WMS" ) );
            add( nameValue( "VERSION", "1.3.0" ) );
            add( nameValue( "REQUEST", "GetMap" ) );
            add( nameValue( "FORMAT", "image/png") );
            add( nameValue( "CRS", "EPSG:3857" ) );
            add( nameValue( "BBOX", extent.toString() ) );
            add( nameValue( "LAYERS", layers.toString() ) );
        }};
        if (!StringUtils.isBlank( ArenaPlugin.instance().config().getServiceAuthToken() )) {
            params.add( nameValue( "authToken", ArenaPlugin.instance().config().getServiceAuthToken() ) );
        }
        content.imgWidth = context.displaySize.get().x;
        content.imgHeight = context.displaySize.get().y;

        content.previewWidth = 320;
        content.previewHeight = content.previewWidth * content.imgHeight / content.imgWidth;

        ArrayList previewParams = new ArrayList( params ) {{
            add( nameValue( "WIDTH", content.previewWidth ) );
            add( nameValue( "HEIGHT", content.previewHeight ) );
        }};
        content.previewResource = imageUrl + "?" + URLEncodedUtils.format( previewParams, "UTF-8" );

        ArrayList imgParams = new ArrayList( params ) {{
            add( nameValue( "WIDTH", content.imgWidth ) );
            add( nameValue( "HEIGHT", content.imgHeight ) );
        }};
        content.imgResource = imageUrl + "?" + URLEncodedUtils.format( imgParams, "UTF-8" );
        return content;
    }

    
    protected NameValuePair nameValue( String name, Object value ) {
        return new BasicNameValuePair( name, value.toString() );
    }

    
    @Override
    public boolean supports( final String mimeType, @SuppressWarnings( "hiding" ) final ShareContext context ) {
        this.context = context;
        return MIMETYPE.equals( mimeType ) && !context.selectionDescriptors.get().isEmpty();
    }
}
