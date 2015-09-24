package io.mapzone.controller.ui;

import io.mapzone.controller.um.repository.LoginCookie;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import org.polymap.core.runtime.StreamIterable;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.security.SecurityContext;
import org.polymap.core.security.UserPrincipal;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PropertyAccessEvent;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.dashboard.Dashboard;
import org.polymap.rhei.batik.toolkit.ConstraintLayout;
import org.polymap.rhei.batik.toolkit.LayoutSupplier;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

import org.polymap.cms.ArticleDashlet;
import org.polymap.cms.ContentProvider;

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
    protected Context<UserPrincipal>        userPrincipal;
    
    private Dashboard                       anonDashboard;
    
    private ProjectRepository               cookieRepo = ProjectRepository.newInstance();

    private Composite                       parent;
    
    
    @Override
    public boolean wantsToBeShown() {
        return getSite().getPath().size() == 1;
    }
    
    
    @Override
    public void dispose() {
        if (anonDashboard != null) {
            anonDashboard.dispose();
        }
    }


    protected void createDashboardContents() {
        UIThreadExecutor.async( () -> getContext().openPanel( getSite().getPath(), DashboardPanel.ID ) );
        
//      DashboardPanel dashboardPanel = getContext().propagate( new DashboardPanel() );
//      dashboardPanel.setSite( getSite(), getContext() );
//      dashboardPanel.init();
//      dashboardPanel.createContents( parent );
    }

    protected void createFrontpageContents() {
        anonDashboard = new Dashboard( getSite(), ANON_DASHBOARD_ID );
//      anonDashboard.addDashlet( new LoginDashlet( cookieRepo ) );
//      anonDashboard.addDashlet( new FeaturedProjectsDashlet() );

      // articles
      ContentProvider cp = ContentProvider.instance();
      AtomicInteger prio = new AtomicInteger( 10 );
      StreamIterable.of( cp.listContent( "frontpage" ) ).stream()
              .sorted( (co1, co2) -> co1.name().compareToIgnoreCase( co2.name() ) )
              .forEach( co -> {
                  ArticleDashlet dashlet = new ArticleDashlet( co );
                  dashlet.addConstraint( new PriorityConstraint( prio.getAndDecrement() ) );
                  dashlet.addConstraint( new MinWidthConstraint( 350, 10 ) );
                  anonDashboard.addDashlet( dashlet );
              });
      anonDashboard.createContents( parent );

      // margins / spacing
      LayoutSupplier prefs = getSite().getLayoutPreference();
      ConstraintLayout layout = (ConstraintLayout)parent.getLayout();
      
      layout.setMargins( new LayoutSupplier() {
          private final float       factor = 2f;
          @Override
          public int getSpacing() {
              // code from DefaultAppDesign
              int spacing = -1;
              Rectangle displayArea = parent.getBounds();
              if (displayArea.width < 500) {
                  spacing = 1;
              }
              else if (displayArea.width < 1366) { // many current notebook displays?
                  spacing = (int)Math.round( displayArea.width * 0.045 );
              }
              else {
                  spacing = (int)Math.round( displayArea.width * 0.045 );
              }
              //log.info( "Frontpage: " + displayArea.width + " -> " + spacing );
              return spacing;
          }
          @Override
          public int getMarginTop() {
              return prefs.getMarginTop();
          }
          @Override
          public int getMarginRight() {
              return Math.round( getSpacing() * factor );
          }
          @Override
          public int getMarginLeft() {
              return Math.round( getSpacing() * factor );
          }
          @Override
          public int getMarginBottom() {
              return prefs.getMarginBottom() + 10;
          }
      });
    }
    
    
    @Override
    public void createContents( @SuppressWarnings("hiding") Composite parent ) {
        this.parent = parent;
        getSite().setPreferredWidth( 650 );
        getSite().setTitle( "Welcome" );
        
        // login cookie -> dashboard
        Optional<LoginCookie> loginCookie = LoginCookie.findAndUpdate( cookieRepo );
        if (loginCookie.isPresent()) {
            User user = loginCookie.get().user.get();            
            userPrincipal.set( SecurityContext.instance().loginTrusted( user.name.get() ) );
            log.info( "Cookie user: " + userPrincipal.get().getName() );
            createDashboardContents();            
        }
        // frontpage, always created in case we get back from dashboard
        createFrontpageContents();
        userPrincipal.addListener( this, (PropertyAccessEvent ev) -> ev.getType() == PropertyAccessEvent.TYPE.SET );
    }
    
    
    @EventHandler( display=true )
    protected void userLogedIn( PropertyAccessEvent ev ) {
        createDashboardContents();    
    }
    
}
