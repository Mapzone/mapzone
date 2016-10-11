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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.share.ui.ShareContext;

/**
 * Creates an URL of the ows.
 *
 * @author Steffen Stundzig
 */
public class WmsUrlProvider
        implements ShareableContentProvider {

    
    private static final Log log = LogFactory.getLog( WmsUrlProvider.class );

    public final static String MIMETYPE = "application/wms";

    /**
     * 
     */
    public class WmsUrlContent implements ShareableContent {

        public URL url;

        public WmsUrlContent( URL url ) {
            this.url = url;
        }
    }


    @Override
    public WmsUrlContent get() {
        try {
            return new WmsUrlContent(new URL( ArenaPlugin.instance().config().getProxyUrl() + "/ows" ));
        }
        catch (MalformedURLException e) {
            throw new RuntimeException( e );
        }
    }


    @Override
    public boolean supports( final String mimeType, ShareContext context ) {
        return MIMETYPE.equals( mimeType );
    }
}
