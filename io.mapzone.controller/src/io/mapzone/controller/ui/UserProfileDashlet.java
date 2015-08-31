/* 
 * polymap.org
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
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

import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.md.MdListViewer;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UserProfileDashlet
        extends DefaultDashlet {

    private static Log log = LogFactory.getLog( UserProfileDashlet.class );
    
    @Scope("io.mapzone.controller")
    private Context<String>                 username;
    
    private ProjectRepository               repo = ProjectRepository.instance();
    
    private User                            user;
    
    private MdListViewer                    viewer;

    
    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( "Profile" );
        this.user = repo.findUser( username.get() ).orElseThrow( () -> new RuntimeException( "No such user: " + username.get() ) );
    }


    @Override
    public void createContents( Composite parent ) {
        MdToolkit tk = (MdToolkit)getSite().toolkit();
        tk.createFlowText( parent, Joiner.on( "<br/>\n" ).join(
                "#" + user.name.get(),
                user.email.get()
                ) );
    }
    
}
