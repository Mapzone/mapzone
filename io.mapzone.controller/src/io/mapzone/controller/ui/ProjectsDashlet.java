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

import java.util.stream.Collectors;

import io.mapzone.controller.ui.ProjectLabelProvider.Type;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.md.ListTreeContentProvider;
import org.polymap.rhei.batik.toolkit.md.MdListViewer;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectsDashlet
        extends DefaultDashlet {

    private static Log log = LogFactory.getLog( ProjectsDashlet.class );
    
    @Scope("io.mapzone.controller")
    private Context<String>                 username;
    
    private ProjectRepository               repo;
    
    private User                            user;
    
    private MdListViewer                    viewer;

    
    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( "Projects" );
        
        repo = ProjectRepository.instance();
        user = repo.findUser( username.get() ).orElseThrow( () -> new RuntimeException( "No such user: " + username.get() ) );
    }


    @Override
    public void createContents( Composite parent ) {
        MdToolkit tk = (MdToolkit)getSite().toolkit(); 
        viewer = tk.createListViewer( parent, SWT.NONE );
        viewer.setContentProvider( new ListTreeContentProvider() );
        
        viewer.firstLineLabelProvider.set( new ProjectLabelProvider( Type.Name ) );
        viewer.secondLineLabelProvider.set( new ProjectLabelProvider( Type.Description ) );
        viewer.setInput( user.projects.stream().collect( Collectors.toList() ) );        
    }
    
}
