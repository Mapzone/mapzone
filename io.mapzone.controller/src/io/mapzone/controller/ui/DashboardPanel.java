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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;

import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.contribution.ContributionManager;
import org.polymap.rhei.batik.dashboard.Dashboard;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

import org.polymap.cms.ArticlePanel;

import io.mapzone.controller.ui.project.ProjectsDashlet;
import io.mapzone.controller.um.repository.LoginCookie;
import io.mapzone.controller.um.repository.ProjectRepository;

/**
 * The user's dashboard.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class DashboardPanel
        extends CtrlPanel {

    private static Log log = LogFactory.getLog( DashboardPanel.class );

    public static final PanelIdentifier     ID = PanelIdentifier.parse( "dashboard" );
    
    public static final String              DASHBOARD_ID = "io.mapzone.controller.dashboard";

    private Dashboard                       dashboard;
    
    
    @Override
    public void init() {
        super.init();

        // initialize panel context
        uow.compareAndSet( null, ProjectRepository.session() );
        user.set( uow.get().findUser( userPrincipal.get().getName() )
                .orElseThrow( () -> new RuntimeException( "No such user: " + userPrincipal.get() ) ) );
    }


    @Override
    public void dispose() {
        super.dispose();
        
        // XXX logout to work around correct user and panel size setting
        LoginCookie.access().destroy();
        JavaScriptExecutor executor = RWT.getClient().getService( JavaScriptExecutor.class );
        executor.execute( "window.location.reload(true);" );
    }


    @Override
    public void createContents( Composite parent ) {
        site().title.set( "Dashboard" );
        site().setSize( SIDE_PANEL_WIDTH, 650, Integer.MAX_VALUE );
        ContributionManager.instance().contributeTo( this, this );
        
        dashboard = new Dashboard( getSite(), DASHBOARD_ID );
        dashboard.addDashlet( new ProjectsDashlet().addConstraint( new PriorityConstraint( 5 ) ) );
//        dashboard.addDashlet( new ActivitiesDashlet().addConstraint( new PriorityConstraint( 10 ) ) );
//        dashboard.addDashlet( new UserProfileDashlet() );
        
        if (user.get().projects().isEmpty()) {
            getContext().openPanel( site().path(), ArticlePanel.ID )
                    .ifPresent( panel -> ((ArticlePanel)panel).setArticle( "ui/dashboard-welcome.md" ) ); 

//            dashboard.addDashlet( new WelcomeDashlet( "ui/dashboard-welcome.md")
//                    .addConstraint( new PriorityConstraint( 10 ) ) );
        }
        
        dashboard.createContents( parent );
        ContributionManager.instance().contributeTo( dashboard, this );
    }

}
