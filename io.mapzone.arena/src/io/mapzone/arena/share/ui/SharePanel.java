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
import io.mapzone.arena.share.Sharelet;
import io.mapzone.arena.share.ShareletSite;
import io.mapzone.arena.share.Sharelets;

/**
 * The main panel, which shows all known sharelets, packed in dashlets in a
 * dashboard.
 *
 * @author Steffen Stundzig
 */
public class SharePanel
        extends P4Panel {

    private static Log                  log                   = LogFactory.getLog( SharePanel.class );

    public static final String          SCOPE                 = "share";

    public static final PanelIdentifier ID                    = PanelIdentifier.parse( SCOPE );

    private static final IMessages      i18n                  = Messages.forPrefix( "SharePanel" );

    private static final String         SOURCES_ID            = "sourcesDashboard";

    private Dashboard                   sourcesDashboard;

    @Scope( SharePanel.SCOPE )
    protected Context<ShareContext>     shareContext;

    private boolean                     defaultContextCreated = false;


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
        if (!shareContext.isPresent()) {
            defaultContextCreated = true;
            shareContext.set( new ShareContext() );
        }
        if (!shareContext.get().boundingBox.isPresent()) {
            shareContext.get().boundingBox.set( mapViewer.mapExtent.get() );
        }
        if (!shareContext.get().crs.isPresent()) {
            shareContext.get().crs.set( mapViewer.maxExtent.get().getCoordinateReferenceSystem() );
        }
        if (!shareContext.get().resolution.isPresent()) {
            shareContext.get().resolution.set( mapViewer.resolution.get() );
        }
        if (shareContext.get().selectionDescriptors.get().isEmpty()) {
            mapViewer.getLayers().stream()
            .sorted( ( elm1, elm2 ) -> (elm1.orderKey.get() - elm2.orderKey.get()) )
            .forEach( layer -> shareContext.get().add( new ShareContext.SelectionDescriptor( layer ) ) );
        }
    }


    @Override
    public void createContents( Composite parent ) {
        sourcesDashboard = new Dashboard( getSite(), SOURCES_ID );
        for (Sharelet sharelet : Sharelets.instance().get()) {
            ShareletSite site = new ShareletSite();
            site.tk.set( site().toolkit() );
            site.context.set( shareContext.get() );
            site.preferredWidth.set( 350 );
            sharelet.init( site );
            sourcesDashboard.addDashlet( new ShareletDashlet( sharelet ) );
        }
        sourcesDashboard.createContents( parent );
    }


    @Override
    public void dispose() {
        super.dispose();
        if (defaultContextCreated) {
            shareContext.set( null );
        }
    }

}
