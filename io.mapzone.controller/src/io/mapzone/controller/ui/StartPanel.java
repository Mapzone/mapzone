/* 
 * mapzone.io
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
package io.mapzone.controller.ui;

import io.mapzone.controller.um.repository.LoginCookie;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

import static org.apache.commons.lang3.StringUtils.substringBefore;
import static org.polymap.core.ui.FormDataFactory.on;

import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.polymap.core.runtime.StreamIterable;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.security.SecurityContext;
import org.polymap.core.security.UserPrincipal;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.FormDataFactory.Alignment;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PropertyAccessEvent;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.ConstraintLayout;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.LayoutSupplier;
import org.polymap.cms.ContentProvider;

/**
 * Landing page or open {@link DashboardPanel} if {@link LoginCookie} is set.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class StartPanel
        extends CtrlPanel {

    private static Log log = LogFactory.getLog( StartPanel.class );

    public static final PanelIdentifier     ID = PanelIdentifier.parse( "start" );

    @Scope("io.mapzone.controller")
    protected Context<UserPrincipal>        userPrincipal;
    
    private ProjectRepository               cookieRepo = ProjectRepository.newInstance();

    private Composite                       parent;
    
    
    @Override
    public boolean wantsToBeShown() {
        return getSite().getPath().size() == 1;
    }
    
    
    @Override
    public void createContents( @SuppressWarnings("hiding") Composite parent ) {
        this.parent = parent;
        getSite().setPreferredWidth( 700 );
        getSite().setTitle( "Welcome to mapzone" );
        
        // login cookie -> dashboard
        Optional<LoginCookie> loginCookie = LoginCookie.findAndUpdate( cookieRepo );
        if (loginCookie.isPresent()) {
            User user = loginCookie.get().user.get();            
            userPrincipal.set( SecurityContext.instance().loginTrusted( user.name.get() ) );
            log.info( "Cookie user: " + userPrincipal.get().getName() );
            openDashboard();
        }
        // frontpage, always created in case we get back from dashboard
        // XXX delay to avoid frontpage showing short before dashboard
        createFrontpageContents();
        userPrincipal.addListener( this, (PropertyAccessEvent ev) -> ev.getType() == PropertyAccessEvent.TYPE.SET );
    }


    protected void createFrontpageContents() {
        parent.setLayout( FormLayoutFactory.defaults().spacing( 10 ).margins( 0, 0, 10, 0 ).create() );
        
        ContentProvider cp = ContentProvider.instance();
        
        // banner
        Composite banner = on( tk().createComposite( parent ) ).fill().noBottom().height( 220 ).control();
//        banner.setBackground( UIUtils.getColor( 0xf0, 0xf0, 0xf0 ));
        banner.setLayout( FormLayoutFactory.defaults().margins( 0, 0 ).create() );
        on( tk().createFlowText( banner, cp.findContent( "frontpage/1welcome.md" ).content() ) ).fill().left( 10 ).right( 60 );
        
        Composite btnContainer = on( tk().createComposite( banner ) ).fill().left( 60 ).right( 90 ).control();
        btnContainer.setLayout( FormLayoutFactory.defaults().create() );
        Control filled = on( tk().createComposite( btnContainer )).fill().control();
        Button btn = on( tk().createButton( btnContainer, "Projekt starten..." ) )
                .top( filled, 0, Alignment.CENTER )
                .right( 90 ).height( 45 ).width( 200 ).control();
        btn.setToolTipText( "Ein Datenprojekt anlegen zum Testen und Probieren<br/>Es ist kein Login notwendig. Die Daten werden nicht dauerhaft gespeichert." );
        btn.moveAbove( null );
        
        // article grid
        Composite grid = on( tk().createComposite( parent ) ).fill().top( banner ).control();
        grid.setLayout( ColumnLayoutFactory.defaults().columns( 1, 3 ).spacing( 20 ).margins( 3, 3 ).create() );

        // articles
        StreamIterable.of( cp.listContent( "frontpage" ) ).stream()
                .filter( co -> co.contentType().startsWith( "text" ) )
                .filter( co -> !co.name().equals( "1welcome" ) )
                .sorted( (co1,co2) -> co1.name().compareToIgnoreCase( co2.name() ) )
                .forEach( co -> {
                    String content = co.content();
                    String title = co.title();
                    if (content.startsWith( "#" )) {
                        title = substringBefore( content, "\n" ).substring( 1 );
                        content = content.substring( title.length() + 2 );
                    }

                    IPanelSection article = tk().createPanelSection( grid, title, SWT.BORDER );
                    article.getControl().setLayoutData( ColumnDataFactory.defaults().heightHint( 300 ).widthHint( 380 ).create() );
                    tk().createFlowText( article.getBody(), content );
                });
        
//        createPageLayout();
    }

    
    /**
     * Page layout: 800px width    
     */
    protected void createPageLayout() {
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
    
    
    protected void openDashboard() {
        UIThreadExecutor.async( () -> getContext().openPanel( getSite().getPath(), DashboardPanel.ID ) );
    }


    @EventHandler( display=true )
    protected void userLogedIn( PropertyAccessEvent ev ) {
        openDashboard();    
    }
    
}
