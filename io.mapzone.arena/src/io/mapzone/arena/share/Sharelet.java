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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnDataFactory.Alignment;
import org.polymap.core.ui.ColumnLayoutFactory;

import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.IPanelToolkit;

import io.mapzone.arena.Messages;

/**
 * @author Steffen Stundzig
 */
public class Sharelet
        extends DefaultDashlet
        implements MouseListener {

    private static final long serialVersionUID = 1L;

    private static Log        log              = LogFactory.getLog( Sharelet.class );

    private IMessages         i18n;

    private Image             image;

    private PanelIdentifier   shareletPanelId;


    public Sharelet( String i18nKey, PanelIdentifier shareletPanelId, Image image ) {
        this.i18n = Messages.forPrefix( i18nKey );
        this.shareletPanelId = shareletPanelId;
        this.image = image;

    }


    @Override
    public void init( DashletSite site ) {
        site.isExpandable.set( false );
        site.border.set( true );
        site.title.set( i18n.get( "title" ) );
        // site.constraints.get().add( new PriorityConstraint( 100 ) );
        // FIXME, doesnt work correctly
        // site.constraints.get().add( new MinWidthConstraint( 15, 100 ) );
        // site.constraints.get().add( new MaxWidthConstraint( 250, 1 ) );
        super.init( site );
    }


    @Override
    public final void createContents( Composite parent ) {
        parent.addMouseListener( this );

        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 10, 0, 0, 0 ).create() );
        Label title = getSite().toolkit().createLabel( parent, "", SWT.NONE );
        title.setImage( image );
        title.addMouseListener( this );

        Label description = getSite().toolkit().createLabel( parent, i18n.get( "description" ), SWT.WRAP );
        description.addMouseListener( this );

        ColumnDataFactory.on( title ).widthHint( 48 ).heightHint( 48 ).horizAlign( Alignment.CENTER );
        ColumnDataFactory.on( description ).widthHint( 150 ).heightHint( 50 ).horizAlign( Alignment.CENTER );
    }


    protected final IPanelToolkit tk() {
        return getSite().toolkit();
    }

    // MouseListener stuff

    @Override
    public void mouseUp( MouseEvent e ) {
        openPanel();
    }


    @Override
    public void mouseDown( MouseEvent e ) {
        // ignore
    }


    @Override
    public void mouseDoubleClick( MouseEvent e ) {
        openPanel();
    }


    private void openPanel() {
        BatikApplication.instance().getContext().openPanel( dashletSite.panelSite().getPath(), shareletPanelId );
    }
}
