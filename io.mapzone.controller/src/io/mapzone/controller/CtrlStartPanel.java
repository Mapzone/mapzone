package io.mapzone.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.security.UserPrincipal;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.dashboard.Dashboard;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CtrlStartPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( CtrlStartPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "start" );
    public static final String          ANON_DASHBOARD_ID = "io.mapzone.controller.anonStartDashboard";
    public static final String          DASHBOARD_ID = "io.mapzone.controller.startDashboard";

    @Scope("io.mapzone.controller")
    private Context<UserPrincipal>      user;
    
    private Dashboard                   anonDashboard;
    
    
    @Override
    public boolean wantsToBeShown() {
        return getSite().getPath().size() == 1;
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().setPreferredWidth( 900 );
        
        anonDashboard = new Dashboard( getSite(), ANON_DASHBOARD_ID );
        anonDashboard.addDashlet( new WelcomeMessageDashlet() );
        anonDashboard.addDashlet( new FeaturedProjectsDashlet() );
        anonDashboard.createContents( parent );
    }
    
}
