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
package io.mapzone.controller.ui.admin;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.contribution.ContributionManager;
import org.polymap.rhei.batik.dashboard.Dashboard;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.ui.DashboardPanel;

/**
 *
 *
 * @author Falko Bräutigam
 */
public class AdminDashboardPanel
        extends CtrlPanel {

    public static final PanelIdentifier ID = PanelIdentifier.parse( "adminDashboard" );
    
    public static final String DASHBOARD_ID = "adminDashboard";


    @Override
    public boolean wantsToBeShown() {
        if (SecurityUtils.isAdmin() && parentPanel().get() instanceof DashboardPanel) {
            site().title.set( "" );
            site().tooltip.set( "Administration" );
            site().icon.set( ControllerPlugin.images().svgImage( "worker.svg", ControllerPlugin.HEADER_ICON_CONFIG ) );
            return true;
        }
        return false;
    }

    
    @Override
    public void init() {
        super.init();
        site().title.set( "Administration" );
        site().setSize( SIDE_PANEL_WIDTH2, 1000, 1000 );
    }        

    
    @Override
    public void createContents( Composite parent ) {
        Dashboard dashboard = new Dashboard( getSite(), DASHBOARD_ID );
        dashboard.addDashlet( new AdminPluginsDashlet() );
        dashboard.addDashlet( new AdminUsersDashlet() );
        dashboard.createContents( parent );
        ContributionManager.instance().contributeTo( dashboard, this, DASHBOARD_ID );

        ContributionManager.instance().contributeTo( this, this );
    }
    
}
