package io.mapzone.controller.ui;

import java.util.Optional;

import io.mapzone.controller.um.repository.LoginCookie;
import io.mapzone.controller.um.repository.ProjectRepoProvider;
import io.mapzone.controller.um.repository.ProjectRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.dashboard.Dashboard;
import org.polymap.rhei.batik.toolkit.ConstraintLayout;
import org.polymap.rhei.batik.toolkit.LayoutSupplier;
import org.polymap.rhei.batik.tx.TxProvider;
import org.polymap.rhei.batik.tx.TxProvider.Propagation;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class StartPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( StartPanel.class );

    public static final PanelIdentifier     ID = PanelIdentifier.parse( "start" );
    public static final String              ANON_DASHBOARD_ID = "io.mapzone.controller.anonStartDashboard";
    public static final String              DASHBOARD_ID = "io.mapzone.controller.startDashboard";

    @Scope("io.mapzone.controller")
    private Context<String>                 username;
    
    @Scope("io.mapzone.controller")
    private Context<ProjectRepoProvider>    repoProvider;
    
    private TxProvider<ProjectRepository>.Tx repo;
    
    private Dashboard                       anonDashboard;
    
    
    @Override
    public boolean wantsToBeShown() {
        return getSite().getPath().size() == 1;
    }
    

    @Override
    public void init() {
        repoProvider.set( new ProjectRepoProvider() );
        repo = repoProvider.get().newTx( this ).start( Propagation.REQUIRES_NEW );
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().setPreferredWidth( 650 );
        getSite().setTitle( "Dashboard" );
        
        // login cookie
        Optional<LoginCookie> loginCookie = LoginCookie.findAndUpdate( repo.get() );
        if (loginCookie.isPresent()) {
            username.set( loginCookie.get().user.get().name.get() );
            log.info( "Cookie user: " + username.get() );
            getContext().openPanel( getSite().getPath(), DashboardPanel.ID );
//            DashboardPanel dashboardPanel = getContext().propagate( new DashboardPanel() );
//            dashboardPanel.setSite( getSite(), getContext() );
//            dashboardPanel.init();
//            dashboardPanel.createContents( parent );
        }
        // login form
        else {
            anonDashboard = new Dashboard( getSite(), ANON_DASHBOARD_ID );
            anonDashboard.addDashlet( new WelcomeMessageDashlet() );
            anonDashboard.addDashlet( new LoginDashlet( repo.get() ) );
            anonDashboard.addDashlet( new FeaturedProjectsDashlet() );
            anonDashboard.createContents( parent );

            // margins / spacing        
            ConstraintLayout layout = (ConstraintLayout)parent.getLayout();
            Rectangle displayArea = Display.getCurrent().getBounds();
            final int spacing = displayArea.width < 500 ? 5 : (int)(displayArea.width * 0.05); 
            layout.setMargins( new LayoutSupplier() {
                @Override
                public int getSpacing() {
                    return spacing;
                }
                @Override
                public int getMarginTop() {
                    return spacing;
                }
                @Override
                public int getMarginRight() {
                    return spacing;
                }
                @Override
                public int getMarginLeft() {
                    return spacing;
                }
                @Override
                public int getMarginBottom() {
                    return spacing;
                }
            });
        }
    }
    
}
