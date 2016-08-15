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
import org.eclipse.swt.widgets.Label;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;

import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.IPanelToolkit;

/**
 * @author Steffen Stundzig
 */
public abstract class ShareDashlet
        extends DefaultDashlet {

    private static Log log = LogFactory.getLog( ShareDashlet.class );

    @Override
    public void init( DashletSite site ) {
        site.isExpandable.set( true );
        site.border.set( true );
        site.title.set( title() );
        super.init( site );
    }
    

    @Override
    public final void createContents( Composite parent ) {
        parent.setLayout( FormLayoutFactory.defaults().create() );
        Label title = getSite().toolkit().createLabel( parent, "", SWT.NONE );
        title.setImage( image() );
        
        Label description = getSite().toolkit().createLabel( parent, description() );

        Composite content = getSite().toolkit().createComposite( parent, SWT.BORDER );
        content.setLayout( FormLayoutFactory.defaults().create() );

        createSubContents( content );
        
        FormDataFactory.on( title ).width( 48 ).left( 45 ).right( 55 ).top( 10 );
        FormDataFactory.on( description ).left(5).right( 95 ).top( title, 10 );
        FormDataFactory.on( content ).left(5).right( 95 ).top( description, 10 ).bottom( 100 );
        //composite.layout();
    }

    protected final IPanelToolkit tk() {
        return getSite().toolkit();
    }
    
    protected abstract String title();
    
    /**
     * @param content is setup with FormLayout
     */
    protected abstract void createSubContents( Composite content );

    protected abstract String description();

    /**
     * @return the image should be 48x48px. Its displayed on a white background.
     */
    protected abstract Image image();
}
