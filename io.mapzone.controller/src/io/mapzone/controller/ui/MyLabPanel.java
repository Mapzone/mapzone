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
package io.mapzone.controller.ui;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.contribution.ContributionManager;
import org.polymap.rhei.batik.dashboard.Dashboard;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.plugincat.MyPluginsDashlet;

/**
 * My plugins and other developed artifacts.
 *
 * @author Falko Bräutigam
 */
public class MyLabPanel
        extends CtrlPanel {

    public static final PanelIdentifier ID = PanelIdentifier.parse( "myLab" );
    
    public static final String DASHBOARD_ID = "myLab";


    @Override
    public boolean wantsToBeShown() {
        if (parentPanel().get() instanceof DashboardPanel) {
            site().title.set( "" );
            site().tooltip.set( "My plugins and other development stuff" );
            site().icon.set( ControllerPlugin.images().svgImage( "flask-outline.svg", ControllerPlugin.HEADER_ICON_CONFIG ) );
            return true;
        }
        return false;
    }

    
    @Override
    public void init() {
        super.init();
        site().title.set( "My Lab" );
        site().setSize( SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH2, SIDE_PANEL_WIDTH2 );
    }        

    
    @Override
    public void createContents( Composite parent ) {
        Dashboard dashboard = new Dashboard( getSite(), DASHBOARD_ID );
        dashboard.addDashlet( new MyPluginsDashlet() );
        dashboard.createContents( parent );
        ContributionManager.instance().contributeTo( dashboard, this, DASHBOARD_ID );

        ContributionManager.instance().contributeTo( this, this );
    }
    
}
