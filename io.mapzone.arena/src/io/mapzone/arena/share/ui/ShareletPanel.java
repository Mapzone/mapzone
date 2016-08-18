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
package io.mapzone.arena.share.ui;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.runtime.config.Mandatory;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;

import org.polymap.p4.P4Panel;

/**
 * A Panel to show the content of a sharelet.
 *
 * @author Steffen Stundzig
 */
public class ShareletPanel
        extends P4Panel {

    public static final PanelIdentifier     ID    = PanelIdentifier.parse( "sharelet" );

    @Mandatory
    @Scope( SharePanel.SCOPE )
    protected Context<ShareletPanelContext> shareletPanelContext;

    protected final static int              width = 350;


    @Override
    public void init() {
        site().minWidth.set( shareletPanelContext.get().sharelet.get().site().preferredWidth.get() );
        site().preferredWidth.set( 500 );
        site().title.set( shareletPanelContext.get().sharelet.get().site().title.get() );
    }


    @Override
    public void createContents( Composite panelBody ) {
        shareletPanelContext.get().sharelet.get().createContents( panelBody );
    }
}
