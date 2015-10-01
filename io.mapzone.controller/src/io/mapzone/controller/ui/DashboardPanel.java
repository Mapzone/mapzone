package io.mapzone.controller.ui;

import io.mapzone.controller.ui.project.ProjectsDashlet;
import io.mapzone.controller.ui.user.UserProfileDashlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.dashboard.Dashboard;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class DashboardPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( DashboardPanel.class );

    public static final PanelIdentifier     ID = PanelIdentifier.parse( "dashboard" );
    
    public static final String              DASHBOARD_ID = "io.mapzone.controller.dashboard";

    private Dashboard                       dashboard;
    
    
    @Override
    public void createContents( Composite parent ) {
        getSite().setPreferredWidth( 650 );
        getSite().setTitle( "Dashboard" );
        
        dashboard = new Dashboard( getSite(), DASHBOARD_ID );
        dashboard.addDashlet( new ProjectsDashlet().addConstraint( new PriorityConstraint( 5 ) ) );
        dashboard.addDashlet( new WelcomeDashlet( "ui/dashboard-welcome.md")
                .addConstraint( new PriorityConstraint( 10 ) ) );
//        dashboard.addDashlet( new ActivitiesDashlet().addConstraint( new PriorityConstraint( 10 ) ) );
        dashboard.addDashlet( new UserProfileDashlet() );
        dashboard.createContents( parent );        
    }

}
