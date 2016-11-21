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
package io.mapzone.arena;

import org.polymap.rhei.batik.contribution.IContributionSite;
import org.polymap.rhei.batik.contribution.IDashboardContribution;
import org.polymap.rhei.batik.dashboard.Dashboard;
import org.polymap.rhei.batik.help.HelpPanel;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

import io.mapzone.arena.jmx.ArenaConfigMBean;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class EMailHelpContribution
        implements IDashboardContribution {

    @Override
    public void fillDashboard( IContributionSite site, Dashboard dashboard ) {
        if (site.tagsContain( HelpPanel.DASHBOARD_ID )) {
            dashboard.addDashlet( new EMailHelpDashlet()
                    .smtpUser.put( ArenaConfigMBean.SMTP_USER )
                    .smtpPassword.put( ArenaConfigMBean.SMTP_PWD )
                    .smtpHost.put( ArenaConfigMBean.SMTP_HOST )
                    .to.put( "support@mapzone.io" )
                    .addConstraint( new PriorityConstraint( 100 ) ) );
        }
    }

}
