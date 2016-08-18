/*
 * polymap.org Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package io.mapzone.arena.analytics.graph.ui;

import static org.polymap.core.ui.FormDataFactory.on;
import static org.apache.commons.lang3.StringUtils.abbreviate;
import static org.polymap.core.runtime.UIThreadExecutor.async;

import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;
import org.opengis.feature.type.PropertyDescriptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ArrayListMultimap;
import com.vividsolutions.jts.geom.Geometry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.polymap.core.data.unitofwork.CommitOperation;
import org.polymap.core.data.unitofwork.UnitOfWork;
import org.polymap.core.operation.OperationSupport;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.SelectionListenerAdapter;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;
import org.polymap.rhei.table.CollectionContentProvider;
import org.polymap.rhei.table.DefaultFeatureTableColumn;
import org.polymap.rhei.table.FeatureTableViewer;

import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;

import io.mapzone.arena.analytics.graph.Node;

/**
 * Displays a {@link StandardFeatureForm} for the selected feature
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 * @author Steffen Stundzig
 */
public class SelectedNodePanel
        extends P4Panel
        implements IFormFieldListener {

    private static Log                  log             = LogFactory.getLog( SelectedNodePanel.class );

    public static final PanelIdentifier ID              = PanelIdentifier.parse( "selectedNode" );

    private FeatureStore                fs;

    private Feature                     feature;

    private UnitOfWork                  uow;

    private Button                      fab;

    private BatikFormContainer          form;

    private boolean                     previouslyValid = true;

    @Scope( P4Plugin.Scope )
    protected Context<Node>             nodeSelection;


    @Override
    public void init() {
        site().title.set( "Selected nodes" );
        site().preferredWidth.set( 400 );

    }


    @Override
    public void createContents( Composite parent ) {
        try {
            parent.setLayout( FormLayoutFactory.defaults().create() );
            final Node selectedNode = nodeSelection.get();
            feature = nodeSelection.get().feature();
            Composite top = null;

            if (feature != null) {
                fs = (FeatureStore)nodeSelection.get().featureSource();
                uow = new UnitOfWork( fs );
                uow.track( feature );
                form = new BatikFormContainer( new StandardFeatureForm() );
                form.createContents( parent );
                form.addFieldListener( this );

                on( form.getContents() ).top( 2 ).left( 0 ).right( 100 ).noBottom();
                top = form.getContents();
            }

            // found all feature types and create a table for each
            ArrayListMultimap<FeatureSource,Feature> relatedFeatures = ArrayListMultimap.create();
            for (Node neighbour : nodeSelection.get().neighbours()) {
                if (neighbour.type().equals( Node.Type.virtual )) {
                    // load the neighbours of the synthetic edge
                    for (Node nextNeighbour : neighbour.neighbours()) {
                        // remove if is itself the selected node
                        if (nextNeighbour.feature() != null && (selectedNode.feature() == null
                                || !selectedNode.feature().equals( nextNeighbour.feature() ))) {
                            relatedFeatures.put( nextNeighbour.featureSource(), nextNeighbour.feature() );
                        }
                    }
                }
                else {
                    if (neighbour.feature() != null && (selectedNode.feature() == null
                            || !selectedNode.feature().equals( neighbour.feature() ))) {
                        relatedFeatures.put( neighbour.featureSource(), neighbour.feature() );
                    }
                }
            }
            for (FeatureSource relatedSource : relatedFeatures.keySet()) {

                Label label = tk().createLabel( parent, relatedFeatures.get( relatedSource ).size() + " neighbours" );
                if (top != null) {
                    on( label ).top( top, 10 );
                }
                else {
                    on( label ).top( 2 );
                }

                FeatureTableViewer table = new FeatureTableViewer( parent, SWT.H_SCROLL | SWT.V_SCROLL
                        | SWT.FULL_SELECTION );
                DefaultFeatureTableColumn first = null;
                for (PropertyDescriptor prop : relatedSource.getSchema().getDescriptors()) {
                    if (!Geometry.class.isAssignableFrom( prop.getType().getBinding() )) {
                        DefaultFeatureTableColumn column = new DefaultFeatureTableColumn( prop );
                        table.addColumn( column );
                        first = first != null ? first : column;
                    }
                }
                first.sort( SWT.DOWN );
                table.setContent( new CollectionContentProvider( relatedFeatures.get( relatedSource ) ) );
                table.setInput( relatedFeatures.get( relatedSource ) );
                on( table.getControl() ).fill().top( label, 5 ).height( 300 );
                top = (Composite)table.getControl();
            }
            on( top ).bottom( 100 );

            fab = tk().createFab();
            fab.setToolTipText( "Save changes" );
            fab.setVisible( false );
            fab.addSelectionListener( new SelectionListenerAdapter( ev -> submit() ) );
        }
        catch (Exception e) {
            createErrorContents( parent, "Unable to display feature.", e );
        }
    }


    @Override
    public void fieldChange( FormFieldEvent ev ) {
        if (ev.hasEventCode( VALUE_CHANGE )) {
            boolean isDirty = form.isDirty();
            boolean isValid = form.isValid();

            fab.setVisible( isDirty );
            fab.setEnabled( isDirty && isValid );

            if (previouslyValid && !isValid) {
                tk().createSnackbar( Appearance.FadeIn, "There are invalid settings" );
            }
            if (!previouslyValid && isValid) {
                tk().createSnackbar( Appearance.FadeIn, "Settings are ok" );
            }
            previouslyValid = isValid;
        }
    }


    protected void submit() {
        try {
            // XXX doing this inside operation cause "Invalid thread access"
            form.submit( null );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "Unable to submit form.", e );
        }

        CommitOperation op = new CommitOperation().uow.put( uow );
        OperationSupport.instance().execute2( op, false, false, ev -> {
            async( () -> {
                tk().createSnackbar( Appearance.FadeIn, ev.getResult().isOK()
                        ? "Saved" : abbreviate( "Unable to save: " + ev.getResult().getMessage(), 50 ) );
            } );
        } );
    }


    /**
     * 
     */
    class StandardFeatureForm
            extends DefaultFormPage {

        @Override
        public void createFormContents( IFormPageSite site ) {
            super.createFormContents( site );
            site.getPageBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( 3 ).create() );

            for (Property prop : SelectedNodePanel.this.feature.getProperties()) {
                if (Geometry.class.isAssignableFrom( prop.getType().getBinding() )) {
                    // skip Geometry
                }
                else {
                    site.newFormField( prop ).create();
                }
            }
        }
    }

}
