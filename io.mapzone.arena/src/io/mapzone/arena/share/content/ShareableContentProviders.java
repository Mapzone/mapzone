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

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import org.polymap.core.runtime.session.SessionSingleton;

import io.mapzone.arena.share.ui.ShareContext;

public class ShareableContentProviders
        extends SessionSingleton {

    public final static ShareableContentProviders instance() {
        return instance( ShareableContentProviders.class );
    }

    private static List<Class<? extends ShareableContentProvider>> contentProviders = Lists.newArrayList( 
            ArenaContentProvider.class, 
            ImagePngContentProvider.class,
            OpenLayersContentProvider.class, 
            WmsUrlProvider.class );


    public Optional<ShareableContentProvider> get( final String mimeType, final ShareContext context ) {
        for (Class<? extends ShareableContentProvider> builderClass : contentProviders) {
            ShareableContentProvider builder;
            try {
                builder = builderClass.newInstance();
            }
            catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException( e );
            }
            if (builder.supports( mimeType, context )) {
                return Optional.of( builder );
            }
        }
        return Optional.empty();
    }
}
