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

import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.project.ILayer;
import org.polymap.core.runtime.i18n.IMessages;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.dashboard.Dashboard;
import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;
import org.polymap.p4.map.ProjectMapPanel;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;

/**
 * Show all available sharelets.
 *
 * @author Falko Bräutigam
 * @author Steffen Stundzig
 */
public class SharePanel
        extends P4Panel {

    private static Log                   log                   = LogFactory.getLog( SharePanel.class );

    public static final String           SCOPE                 = "share";

    public static final PanelIdentifier  ID                    = PanelIdentifier.parse( SCOPE );

    private static final IMessages       i18n                  = Messages.forPrefix( "SharePanel" );

    private static final String          SOURCES_ID            = "sourcesDashboard";

    private Dashboard                    sourcesDashboard;

    @Scope( SharePanel.SCOPE )
    protected Context<SharePanelContext> sharePanelContext;

    private boolean                      defaultContextCreated = false;


    @Override
    public boolean beforeInit() {
        if (parentPanel().orElse( null ) instanceof ProjectMapPanel) {
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
        site().minWidth.set( 150 );
        site().preferredWidth.set( 450 );
        
        // set useful defaults into the context
        final MapViewer<ILayer> mapViewer = ((ProjectMapPanel)parentPanel().get()).mapViewer;
        if (!sharePanelContext.isPresent()) {
            defaultContextCreated = true;
            sharePanelContext.set( new SharePanelContext() );
        }
        if (!sharePanelContext.get().boundingBox.isPresent()) {
            sharePanelContext.get().boundingBox.set( mapViewer.mapExtent.get() );
        }
        if (!sharePanelContext.get().crs.isPresent()) {
            sharePanelContext.get().crs.set( mapViewer.maxExtent.get().getCoordinateReferenceSystem() );
        }
        if (!sharePanelContext.get().resolution.isPresent()) {
            //sharePanelContext.get().resolution.set( mapViewer.resolution.get() );
            // FIXME, remove this
            sharePanelContext.get().resolution.set( 5000.0f );
        }
        if (sharePanelContext.get().selectionDescriptors.get().isEmpty()) {
            mapViewer.getLayers().stream()
            .sorted( ( elm1, elm2 ) -> (elm1.orderKey.get() - elm2.orderKey.get()) )
            .filter( l -> mapViewer.isVisible( l ) )
            .forEach( layer -> sharePanelContext.get().add( new SharePanelContext.SelectionDescriptor( layer ) ) );
        }
    }


    @Override
    public void createContents( Composite parent ) {
        sourcesDashboard = new Dashboard( getSite(), SOURCES_ID );
        sourcesDashboard.addDashlet( new Sharelet( "EmbedSharelet", EmbedShareletPanel.ID, ArenaPlugin.images().svgImage( "ic_web_black_48px.svg", P4Plugin.TOOLBAR_ICON_CONFIG ) ) );
        sourcesDashboard.addDashlet( new Sharelet( "FacebookSharelet", FacebookShareletPanel.ID, null ) );
        sourcesDashboard.createContents( parent );
    }


    @Override
    public void dispose() {
        super.dispose();
        if (defaultContextCreated) {
            sharePanelContext.set( null );
        }
    }

}
