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
package io.mapzone.controller.webcat.ui;

import static org.polymap.core.runtime.event.TypeEventFilter.ifType;
import static org.polymap.rhei.batik.app.SvgImageRegistryHelper.NORMAL24;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerCell;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.event.Event.Scope;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.SelectionAdapter;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.toolkit.md.ListTreeContentProvider;
import org.polymap.rhei.batik.toolkit.md.MdListViewer;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.model2.query.Expressions;
import org.polymap.model2.query.grammar.BooleanExpression;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.webcat.model.CatalogEntry;

/**
 * Searching/browsing the catalog. Used by dashlets and panels.
 *
 * @author Falko Bräutigam
 */
public abstract class CatalogBrowser
        extends Configurable 
        implements IOpenListener {

    private static final Log log = LogFactory.getLog( CatalogBrowser.class );
    
    public Config2<CatalogBrowser,BooleanExpression> baseFilter;

    protected MdListViewer          viewer;

//    protected ActionText            search;
    
    
    public CatalogBrowser() {
        baseFilter.set( Expressions.TRUE );
        BatikApplication.instance().getContext().propagate( this );
        
        EventManager.instance().subscribe( this, ifType( EntityChangedEvent.class, 
                ev -> ev.getEntity() instanceof CatalogEntry ) );
    }
    
    
    public void dispose() {
        EventManager.instance().unsubscribe( this );
    }
    
    
    @EventHandler( scope=Scope.JVM, display=true, delay=500 )
    protected void onEntityCommit( List<EntityChangedEvent> evs ) {
        if (viewer != null && !viewer.getControl().isDisposed()) {
            viewer.refresh();
        }
    }
    
    
    public Composite createContents( Composite parent, MdToolkit tk ) {
        Composite container = tk.createComposite( parent );
        container.setLayout( FormLayoutFactory.defaults().margins( 0, 0 ).create() );
        
        // tree/list viewer
        viewer = tk.createListViewer( container, SWT.FULL_SELECTION, SWT.SINGLE, SWT.V_SCROLL );
        viewer.linesVisible.set( true );
        viewer.setContentProvider( new ListTreeContentProvider() );
        viewer.iconProvider.set( new CellLabelProvider() {
            @Override public void update( ViewerCell cell ) {
                cell.setImage( ControllerPlugin.images().svgImage( "package-variant-closed.svg", NORMAL24 ) );
            }            
        });
        viewer.firstLineLabelProvider.set( new CellLabelProvider() {
            @Override public void update( ViewerCell cell ) {
                cell.setText( ((CatalogEntry)cell.getElement()).title.get() );
            }
        });
        viewer.secondLineLabelProvider.set( new CellLabelProvider() {
            @Override public void update( ViewerCell cell ) {
                CatalogEntry entry = (CatalogEntry)cell.getElement();
                cell.setText( entry.description.get() );
            }
        });
//        viewer.thirdLineLabelProvider.set( new CellLabelProvider() {
//            @Override public void update( ViewerCell cell ) {
//                CatalogEntry entry = (CatalogEntry)cell.getElement();
//                StringBuilder buf = new StringBuilder( 256 ).append( entry.id() );
//                buf.append( " - " ).append( entry.vendor.opt().orElse( "??" ) );
//                buf.append( " - " ).append( entry.fee.get() ).append( " EUR" );
//                cell.setText( buf.toString() );
//            }
//        });
        viewer.addOpenListener( this );
        customizeViewer();
        search( "" );

        // search field
//        search = tk.createActionText( container, "" );
//        search.getText().setFont( UIUtils.bold( search.getText().getFont() ) );
//        new TextActionItem( search, Type.DEFAULT )
//                .action.put( ev -> search( search.getText().getText() ) )
//                .text.put( "Search..." )
//                .tooltip.put( "Fulltext search. Use * as wildcard.<br/>&lt;ENTER&gt; starts the search." )
//                .icon.put( BatikPlugin.images().svgImage( "magnify.svg", SvgImageRegistryHelper.DISABLED12 ) );
//        new ClearTextAction( search );
        
//        new TextProposalDecorator( search.getText() ) {
//            @Override
//            protected String[] proposals( String text, int maxResults, IProgressMonitor monitor ) {
//                monitor.beginTask( "Proposals", catalogs.size()*10 );
//                Set<String> result = new TreeSet();
//                for (IMetadataCatalog catalog : catalogs) {
//                    try {
//                        SubMonitor submon = SubMonitor.on( monitor, 10 );
//                        Iterables.addAll( result, catalog.propose( text, 10, submon ) );
//                        submon.done();
//                    }
//                    catch (Exception e) {
//                        log.warn( "", e );
//                    }
//                }
//                return FluentIterable.from( result ).limit( maxResults ).toArray( String.class );
//            }
//        };

        // layout
//        search.getControl().setLayoutData( FormDataFactory.filled().noBottom().height( 32 ).create() );
        // fill the entiry space as items are expandable; scrollbar would not adopted otherwise
        viewer.getTree().setLayoutData( FormDataFactory.filled()/*.top( search.getControl() )*/.create() );
        
        return container;
    }
    
    
    /**
     * Sub-classes may override in order to customize UI elements. 
     */
    protected void customizeViewer() {
    }

    
    protected void search( String query ) {
        try {
            // otherwise preserveSelection() fails because of no getParent()
            viewer.setSelection( new StructuredSelection() );
            doSearch( query );
            viewer.expandToLevel( 2 );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "Unable to search the catalog.", e );
        }
    }

    /**
     * Do the search and input the results to the {@link #viewer}.
     * 
     * @param query The lucen query string.
     * @throws Exception
     */
    protected abstract void doSearch( String query ) throws Exception;

    
    @Override
    public void open( OpenEvent ev ) {
        SelectionAdapter.on( ev.getSelection() ).forEach( elm -> {
            doOpen( (CatalogEntry)elm );
            //viewer.setSelection( new StructuredSelection() );
            viewer.collapseAllNotInPathOf( elm );
            viewer.toggleItemExpand( elm );
        });
    }

    protected abstract void doOpen( CatalogEntry elm );

}
