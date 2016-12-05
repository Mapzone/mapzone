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

import org.apache.commons.lang3.StringUtils;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Mandatory;

import org.polymap.rhei.batik.IPanel;
import org.polymap.rhei.batik.contribution.IContributionSite;
import org.polymap.rhei.batik.contribution.IDashboardContribution;
import org.polymap.rhei.batik.dashboard.Dashboard;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.help.HelpAwarePanel;
import org.polymap.rhei.batik.help.HelpDashlet;
import org.polymap.rhei.batik.help.HelpPanel;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

import org.polymap.cms.ContentProvider;
import org.polymap.cms.ContentProvider.ContentObject;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CmsHelpContribution
        implements IDashboardContribution {

    @Override
    public void fillDashboard( IContributionSite site, Dashboard dashboard ) {
        IPanel parentPanel = site.context().getPanel( site.panel().site().path().removeLast( 1 ) );
        if (parentPanel instanceof HelpAwarePanel
                && site.tagsContain( HelpPanel.DASHBOARD_ID )) {
            String cmsPath = ((HelpAwarePanel)parentPanel).helpCmsPath();
            dashboard.addDashlet( new CmsHelpDashlet()
                    .cmsPath.put( cmsPath )
                    .addConstraint( new PriorityConstraint( 100 ) ) );
        }
    }

    
    /**
     * 
     */
    public static class CmsHelpDashlet
            extends HelpDashlet {

        @Mandatory
        Config2<CmsHelpDashlet,String>      cmsPath;
        
        
        public CmsHelpDashlet() {
            ConfigurationFactory.inject( this );
        }

        @Override
        public void init( DashletSite site ) {
            super.init( site );
            site.title.set( "Help" );
        }

        @Override
        public void createContents( Composite parent ) {
            parent.setLayout( new FillLayout() );
            
            ContentObject co = ContentProvider.instance().findContent( cmsPath.get() );
            String content = co.content();
            String title = "Help";
            if (content.startsWith( "#" )) {
                title = StringUtils.substringBefore( content, "\n" ).substring( 1 );
                content = content.substring( title.length() + 2 );
            }

            site().toolkit().createFlowText( parent, content );
            site().title.set( title );
        }
        
    }
    
}
