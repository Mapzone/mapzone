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
import org.eclipse.swt.widgets.Label;

import org.polymap.core.runtime.StreamIterable;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.security.SecurityContext;
import org.polymap.core.security.UserPrincipal;

import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PropertyAccessEvent;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.dashboard.Dashboard;
import org.polymap.rhei.batik.toolkit.ConstraintData;
import org.polymap.rhei.batik.toolkit.ConstraintLayout;
import org.polymap.rhei.batik.toolkit.IPanelToolkit;
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
        IPanelToolkit tk = site().toolkit();
        
        // banner
        ContentProvider cp = ContentProvider.instance();
        Label banner = tk.createFlowText( parent, cp.findContent( "frontpage/1welcome.md" ).content() );
        banner.setLayoutData( new ConstraintData( new MinWidthConstraint( 650, 10 ) ) );
        
        // dashboard
        anonDashboard = new Dashboard( getSite(), ANON_DASHBOARD_ID )
                .defaultBorder.put( true );

        // articles
        AtomicInteger prio = new AtomicInteger( 10 );
        StreamIterable.of( cp.listContent( "frontpage" ) ).stream()
                .filter( co -> co.contentType().startsWith( "text" ) )
                .filter( co -> !co.name().equals( "1welcome" ) )
                .sorted( (co1,co2) -> co1.name().compareToIgnoreCase( co2.name() ) )
                .forEach( co -> {
                    ArticleDashlet dashlet = new ArticleDashlet( co );
                    dashlet.addConstraint( new PriorityConstraint( prio.getAndDecrement() ) );
                    dashlet.addConstraint( new MinWidthConstraint( 350, 10 ) );
                    anonDashboard.addDashlet( dashlet );
                });
        ConstraintLayout dashboardLayout = new ConstraintLayout( site().layoutPreferences() );
        anonDashboard.createContents( tk.createComposite( parent, dashboardLayout ) );

        // page layout
        ((ConstraintLayout)parent.getLayout()).setMargins( new LayoutSupplier() {
            LayoutSupplier layoutPrefs = site().layoutPreferences();
            LayoutSupplier appLayoutPrefs = BatikApplication.instance().getAppDesign().getAppLayoutSettings();
            @Override
            public int getSpacing() {
                return 0; //layoutPrefs.getSpacing() * 2;
            }
            protected int margins() {
                Rectangle bounds = parent.getParent().getBounds();
                int availWidth = bounds.width-(appLayoutPrefs.getMarginLeft()+appLayoutPrefs.getMarginRight());
                return Math.max( (availWidth-800)/2, layoutPrefs.getMarginLeft());
            }
            @Override
            public int getMarginTop() { return layoutPrefs.getMarginTop(); }
            @Override
            public int getMarginRight() { return margins(); }
            @Override
            public int getMarginLeft() { return margins(); }
            @Override
            public int getMarginBottom() { return layoutPrefs.getMarginBottom() /*+ 10*/; }
        });
    }
    
    
    @Override
    public void createContents( @SuppressWarnings("hiding") Composite parent ) {
        this.parent = parent;
        getSite().setPreferredWidth( 650 );
        getSite().setTitle( "Welcome to..." );
        
        // login cookie -> dashboard
        Optional<LoginCookie> loginCookie = LoginCookie.findAndUpdate( cookieRepo );
        if (loginCookie.isPresent()) {
            User user = loginCookie.get().user.get();            
            userPrincipal.set( SecurityContext.instance().loginTrusted( user.name.get() ) );
            log.info( "Cookie user: " + userPrincipal.get().getName() );
            createDashboardContents();            
        }
        // frontpage, always created in case we get back from dashboard
        // XXX delay to avoid frontpage showing short before dashboard
        createFrontpageContents();
        userPrincipal.addListener( this, (PropertyAccessEvent ev) -> ev.getType() == PropertyAccessEvent.TYPE.SET );
    }
    
    
    @EventHandler( display=true )
    protected void userLogedIn( PropertyAccessEvent ev ) {
        createDashboardContents();    
    }
    
}
