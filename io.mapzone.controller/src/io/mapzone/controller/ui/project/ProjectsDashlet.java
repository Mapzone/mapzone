/* 
 * mapzone.io
 * Copyright (C) 2015-2016, Falko Bräutigam. All rights reserved.
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
package io.mapzone.controller.ui.project;

import static org.polymap.core.runtime.event.TypeEventFilter.ifType;
import static org.polymap.core.ui.ColumnDataFactory.Alignment.CENTER;
import static org.polymap.rhei.batik.Propagate.Propagation.ONESHOT;
import static org.polymap.rhei.batik.Propagate.Propagation.UP;
import static org.polymap.rhei.batik.app.SvgImageRegistryHelper.NORMAL24;
import static org.polymap.rhei.batik.toolkit.LayoutConstraint.constraints;

import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;

import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.SelectionAdapter;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.Propagate;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.md.ListTreeContentProvider;
import org.polymap.rhei.batik.toolkit.md.MdListViewer;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.cms.ContentProvider;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.ui.project.ProjectLabelProvider.Type;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.User;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectsDashlet
        extends DefaultDashlet {

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<User>                 user;
    
    @Propagate( {UP, ONESHOT} )
    @Scope( "io.mapzone.controller" )
    protected Context<Project>              selected;
    
    private MdListViewer                    viewer;

    private Composite                       parent;

    private MdToolkit                       tk;
    
    private ProjectInfoPanel                openedPanel;

    
    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( "Projects" );
        site.addConstraint( constraints().priority( 100 ).minWidth( 500 ).get() );
//        site.constraints.get().add( new MinHeightConstraint( dp(72)*3 , 10 ) );
        
        EventManager.instance().subscribe( this, ifType( EntityChangedEvent.class, ev -> 
                ev.getEntity() instanceof Project ) );
    }


    @Override
    public void dispose() {
        EventManager.instance().unsubscribe( this ); 
    }


    @EventHandler( display=true )
    protected void projectChanged( EntityChangedEvent ev ) {
        if (parent.isDisposed() || viewer != null && viewer.getControl().isDisposed()) {
            return;
        }
        
        List<Project> projects = user.get().projects();
        
        // first project created
        if (viewer == null && !projects.isEmpty()) {
            UIUtils.disposeChildren( parent );
            createListContents();
            parent.layout( true );
        }
        // last project removed
        else if (viewer != null && projects.isEmpty()) {
            UIUtils.disposeChildren( parent );
            viewer = null;
            createWelcomeContents();
            parent.layout( true );
        }
        // both empty!?
        else if (viewer == null && projects.isEmpty()) {
            // just keep the welcome message
        }
        else {
            // just update list
            viewer.setInput( projects );
            parent.layout( true );
        }
    }

    
    @Override
    public void createContents( @SuppressWarnings("hiding") Composite parent ) {
        this.parent = parent;
        this.tk = (MdToolkit)getSite().toolkit();

        // margin: shadows
        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( 5 ).margins( 0, 3 ).create() );
        
        if (user.get().projects().isEmpty()) {
            createWelcomeContents();
        }
        else {
            createListContents();            
        }
        
        Button btn = tk.createButton( parent, "New project...", SWT.PUSH );
        btn.setImage( ControllerPlugin.images().svgImage( "plus-circle.svg", SvgImageRegistryHelper.WHITE24 ) );
        btn.setLayoutData( ColumnDataFactory.defaults().widthHint( 200 ).horizAlign( CENTER ).create() );
        btn.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                selected.set( null );
                BatikApplication.instance().getContext().openPanel( getSite().panelSite().getPath(), CreateProjectPanel.ID );                        
            }
        });
    }

    
    protected void createWelcomeContents() {
        tk.createFlowText( parent, ContentProvider.instance().findContent( "ui/projects-welcome.md").content() );
    }
    
    
    protected void createListContents() {
        viewer = tk.createListViewer( parent, SWT.FULL_SELECTION );
        viewer.setContentProvider( new ListTreeContentProvider() );
        
        viewer.firstLineLabelProvider.set( new ProjectLabelProvider( Type.Name ) );
        viewer.secondLineLabelProvider.set( new ProjectLabelProvider( Type.Description ) );
        viewer.iconProvider.set( new CellLabelProvider() {
            @Override public void update( ViewerCell cell ) {
                cell.setImage( ControllerPlugin.images().svgImage( "map.svg", NORMAL24 ) );
            }
        });
//        viewer.firstSecondaryActionProvider.set( new ActionProvider() {
//            @Override public void update( ViewerCell cell ) {
//                cell.setImage( BatikPlugin.images().svgImage( "chevron-right.svg", SvgImageRegistryHelper.DISABLED24 ) );
//            }
//            @Override public void perform( MdListViewer _viewer, Object elm ) {
//                selected.set( (Project)elm );
//                BatikApplication.instance().getContext().openPanel( getSite().panelSite().getPath(), ProjectInfoPanel.ID );                        
//            }
//        });
        viewer.setComparator( new ViewerComparator() {
            @Override public int compare( Viewer _viewer, Object elm1, Object elm2 ) {
                Date modified1 = ((Project)elm1).modified.get();
                Date modified2 = ((Project)elm2).modified.get();
                return modified2.compareTo( modified1 );
            }
        });
        
        viewer.addOpenListener( new IOpenListener() {
            @Override public void open( OpenEvent ev ) {
                SelectionAdapter.on( ev.getSelection() ).forEach( elm -> {
                    selected.set( (Project)elm );
                    openedPanel = (ProjectInfoPanel)BatikApplication.instance().getContext()
                            .openPanel( getSite().panelSite().getPath(), ProjectInfoPanel.ID )
                            .orElse( null );                        
                });
            }
        } );
        viewer.setInput( user.get().projects() );
        
        //viewer.getControl().setLayoutData( FormDataFactory.filled()/*.height( dp(72)*2  )*/.create() );
    }
    
}
