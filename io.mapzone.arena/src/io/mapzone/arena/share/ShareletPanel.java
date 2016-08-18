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

import org.polymap.core.runtime.config.Mandatory;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Scope;

import org.polymap.p4.P4Panel;

/**
 * Base Sharelet Panel
 *
 * @author Steffen Stundzig
 */
public abstract class ShareletPanel
        extends P4Panel {

    @Mandatory
    @Scope( SharePanel.SCOPE )
    protected Context<SharePanelContext> sharePanelContext;

    protected final static int           width = 350;


    @Override
    public void init() {
        site().minWidth.set( width );
        site().preferredWidth.set( 500 );
        site().title.set( title() );
    }


    protected abstract String title();
}
