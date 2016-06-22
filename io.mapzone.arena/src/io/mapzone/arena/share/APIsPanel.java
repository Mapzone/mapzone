/* 
 * polymap.org
 * Copyright (C) 2016, the @authors. All rights reserved.
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
package io.mapzone.arena.share;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.contribution.ContributionManager;
import org.polymap.rhei.batik.dashboard.Dashboard;

import org.polymap.p4.P4Panel;

/**
 * Promote public APIs.
 *
 * @author Falko Bräutigam
 */
public class APIsPanel
        extends P4Panel {

    private static Log log = LogFactory.getLog( APIsPanel.class );

    public static final String      DASHBOARD_ID = "apis";

    private Dashboard               dashboard;
    
    
    @Override
    public void createContents( Composite panelBody ) {
        dashboard = new Dashboard( getSite(), DASHBOARD_ID );
        dashboard.addDashlet( new AuthTokenDashlet() );
        ContributionManager.instance().contributeTo( dashboard, this, DASHBOARD_ID );
    }
    
}
