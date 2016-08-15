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

    private static final String         MAP_ID   = "mapDashboard";

    private static final String         DATA_ID  = "dataDashboard";

    private static final String         IMAGE_ID = "imageDashboard";

    private Dashboard                   mapDashboard;

    private Dashboard                   imageDashboard;

    private Dashboard                   dataDashboard;


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
//        Section section = tk().createSection( parent, 
//                i18n.get( "mapDashboard_title" ), 
//                TREE_NODE, Section.SHORT_TITLE_BAR, Section.FOCUS_TITLE, SWT.BORDER );
//        section.setToolTipText( i18n.get( "mapDashboard_tooltip" ) );
//        section.setExpanded( true );
////        section.setBackground( UIUtils.getColor( sectionBackgroundColor.getRed() * (11-level) /10, sectionBackgroundColor.getGreen()* (11-level) /10, sectionBackgroundColor.getBlue()* (11-level) /10) );
//        ((Composite)section.getClient()).setLayout( parent.getLayout());
//        
        
        mapDashboard = new Dashboard( getSite(), MAP_ID );
        mapDashboard.addDashlet( new MapDashlet() );
        mapDashboard.createContents( parent );
//        ContributionManager.instance().contributeTo( mapDashboard, this, ID.id() );

        dataDashboard = new Dashboard( getSite(), DATA_ID );
//        dataDashboard.addDashlet( new AuthTokenDashlet() );
        dataDashboard.createContents( parent );
//        ContributionManager.instance().contributeTo( dataDashboard, this, DATA_ID );

        imageDashboard = new Dashboard( getSite(), IMAGE_ID );
//        imageDashboard.addDashlet( new AuthTokenDashlet() );
        imageDashboard.createContents( parent );
//        ContributionManager.instance().contributeTo( imageDashboard, this, IMAGE_ID );
    }

}
