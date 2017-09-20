/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
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
package io.mapzone.controller.ui.admin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;

import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.md.ListTreeContentProvider;
import org.polymap.rhei.batik.toolkit.md.MdListViewer;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.model2.query.ResultSet;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.um.repository.User;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class AdminUsersDashlet
        extends DefaultDashlet {

    private MdListViewer            viewer;


    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( "Users" );
    }


    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( FormLayoutFactory.defaults().create() );
        
        MdToolkit tk = (MdToolkit)site().toolkit();
        viewer = tk.createListViewer( parent, SWT.FULL_SELECTION, SWT.SINGLE, SWT.V_SCROLL );
        viewer.linesVisible.set( true );
        viewer.setContentProvider( new ListTreeContentProvider() );
        viewer.iconProvider.set( new CellLabelProvider() {
            @Override public void update( ViewerCell cell ) {
                cell.setImage( ControllerPlugin.images().svgImage( "account.svg", SvgImageRegistryHelper.NORMAL24 ) );
            }            
        });
        viewer.firstLineLabelProvider.set( new CellLabelProvider() {
            @Override public void update( ViewerCell cell ) {
                User user = (User)cell.getElement(); 
                cell.setText( user.name.get() + " - " + user.fullname.get() );
            }
        });
        viewer.setComparator( new ViewerComparator() {
            @Override public int compare( Viewer _viewer, Object e1, Object e2 ) {
                return ((User)e1).name.get().compareToIgnoreCase( ((User)e2).name.get() );
            }
        });
        
        ProjectUnitOfWork puow = ProjectRepository.session();
        ResultSet<User> rs = puow.query( User.class ).execute();
        viewer.setInput( rs );
        
//        viewer.addOpenListener( ev -> {
//            SelectionAdapter.on( ev.getSelection() ).first( User.class ).get();
//            selected.set( (ILayer)elm );
//                BatikApplication.instance().getContext().openPanel( site().panelSite().getPath(), UserInfoPanel.ID );
//        });
        
        FormDataFactory.on( viewer.getControl() ).fill().noBottom().height( 300 );
    }
    
}
