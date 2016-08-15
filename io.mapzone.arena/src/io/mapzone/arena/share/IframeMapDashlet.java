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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import org.polymap.core.runtime.i18n.IMessages;
import static org.polymap.core.ui.FormDataFactory.on;
import org.polymap.p4.P4Plugin;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;

/**
 * Share the map link with facebook
 *
 * @author Steffen Stundzig
 */
public class IframeMapDashlet
        extends ShareDashlet {

    private static Log             log  = LogFactory.getLog( IframeMapDashlet.class );

    private static final IMessages i18n = Messages.forPrefix( "IframeMapDashlet" );


    @Override
    protected String title() {
        return i18n.get( "title" );
    }


    @Override
    protected void createSubContents( Composite content ) {
        final Text text = tk().createText( content, ArenaPlugin.instance().config().getProxyUrl()
                + "/arena", SWT.BORDER );
        on( text ).fill();
    }


    @Override
    protected String description() {
        return i18n.get( "description" );

    }


    @Override
    protected Image image() {
        return ArenaPlugin.images().svgImage( "ic_web_black_48px.svg", P4Plugin.TOOLBAR_ICON_CONFIG );
    }
}
