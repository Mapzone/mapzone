/* 
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.IPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.dashboard.Dashboard;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class StartPanel
        extends DefaultPanel
        implements IPanel {

    private static Log log = LogFactory.getLog( StartPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "start" );

    public static final String          DASHBOARD_ID = "start-dashboard";

    private Dashboard                   dashboard;
    
    
    @Override
    public boolean wantsToBeShown() {
        return getSite().getPath().size() == 1;
    }


    @Override
    public void createContents( Composite parent ) {
        // FIXME
        UIUtils.activateCallback( "fix-flowtext-link-actions" );
        
        getSite().setTitle( "Dashboard" );
        //getSite().setIcon( BatikPlugin.instance().imageForName( "resources/icons/house.png" ) );

        dashboard = new Dashboard( getSite(), DASHBOARD_ID );
        dashboard.addDashlet( new ProjectsDashlet() );
        dashboard.addDashlet( new ActivitiesDashlet() );
//        dashboard.addDashlet( new MapDashlet() );
        
        dashboard.createContents( parent );
    }

}
