/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.controller.plugincat;

import static org.polymap.rhei.batik.toolkit.LayoutConstraint.constraints;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;

import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class PluginCatalogDashlet
        extends DefaultDashlet {

    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( "Available Plugins" );
        site.addConstraint( constraints().priority( 10 ).minWidth( 500 ).get() );
//        site.constraints.get().add( new MinWidthConstraint( 5000, 0 ) );
    }

    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( FormLayoutFactory.defaults().create() );
        
        PluginCatalogBrowser browser = new PluginCatalogBrowser();
        browser.filterRevoked( false );
        browser.filterReleased( true );
        browser.createContents( parent, (MdToolkit)getSite().toolkit() )
                .setLayoutData( FormDataFactory.filled().create() );
    }
    
}
