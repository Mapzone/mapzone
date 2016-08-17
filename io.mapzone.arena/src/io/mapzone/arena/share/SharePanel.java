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

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.dashboard.Dashboard;
import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;

/**
 * Promote public APIs.
 *
 * @author Falko Bräutigam
 * @author Steffen Stundzig
 */
public class SharePanel
        extends P4Panel {

    private static Log                  log      = LogFactory.getLog( SharePanel.class );

    public static final PanelIdentifier ID       = PanelIdentifier.parse( "share" );

    private static final IMessages      i18n     = Messages.forPrefix( "SharePanel" );

    private static final String         SOURCES_ID   = "sourcesDashboard";

    private Dashboard                   sourcesDashboard;


    @Override
    public boolean wantsToBeShown() {
        if (site().path().size() == 2) {
            site().icon.set( ArenaPlugin.images().svgImage( "share.svg", P4Plugin.HEADER_ICON_CONFIG ) );
            site().tooltip.set( i18n.get( "tooltip" ) );
            site().title.set( "" );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        site().title.set( i18n.get( "title" ) );
    }


    @Override
    public void createContents( Composite parent ) {
        
        sourcesDashboard = new Dashboard( getSite(), SOURCES_ID );
        sourcesDashboard.addDashlet( new BlogSharelet() );
        sourcesDashboard.addDashlet( new FacebookSharelet() );
        sourcesDashboard.createContents( parent );
    }

}
