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

import io.mapzone.arena.ArenaPlugin;

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

    private SharePanelContext context;


    @Override
    public ArenaContent content() {
            ArenaContent content = new ArenaContent();
            content.arena = ArenaPlugin.instance().config().getProxyUrl() + ArenaPlugin.ALIAS;
            // FIXME
            content.shareInf o = "";
            return content;
    }


    @Override
    public boolean supports( final String mimeType, final SharePanelContext context ) {
        this.context = context;
        return MIMETYPE.equals( mimeType );
    }
}
