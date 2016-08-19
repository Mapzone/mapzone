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

import com.vividsolutions.jts.geom.Envelope;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.share.ShareInfoServlet;
import io.mapzone.arena.share.ShareServletsStarter;
import io.mapzone.arena.share.ui.ShareContext;
import io.mapzone.arena.share.ui.ShareContext.SelectionDescriptor;

/**
 * Creates an url to the arena client.
 *
 * @author Steffen Stundzig
 */
public class ArenaContentBuilder
        implements ShareableContentBuilder {

    public class ArenaContent {

        public String arena;

        public String shareInfo;

    }

    public final static String MIMETYPE = "application/arena";

    private ShareContext       context;


    @Override
    public ArenaContent content() {
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
        
        StringBuffer shareInfo = new StringBuffer( ArenaPlugin.instance().config().getProxyUrl() );
        shareInfo.append( ShareServletsStarter.ALIAS_SHAREINFO ).append( "?" );
        try {
            shareInfo.append( ShareInfoServlet.PARAMETER_LAYERS ).append( "=" ).append( URLEncoder.encode( layers.toString(), "utf-8") );
            shareInfo.append( "&" ).append( ShareInfoServlet.PARAMETER_BBOX ).append( "=" ).append( URLEncoder.encode(extent.toString(), "utf-8" ));
            if (!StringUtils.isBlank( ArenaPlugin.instance().config().getServiceAuthToken() )) {
                shareInfo.append( "&" ).append( ShareInfoServlet.PARAMETER_AUTHTOKEN ).append( "=" ).append( URLEncoder.encode(ArenaPlugin.instance().config().getServiceAuthToken(), "utf-8" ));
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }

        ArenaContent content = new ArenaContent();
        content.arena = ArenaPlugin.instance().config().getProxyUrl() + ArenaPlugin.ALIAS;
        content.shareInfo = shareInfo.toString();
        return content;
    }


    @Override
    public boolean supports( final String mimeType, @SuppressWarnings( "hiding" ) final ShareContext context ) {
        this.context = context;
        return MIMETYPE.equals( mimeType );
    }
}
