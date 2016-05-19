/*
 * polymap.org Copyright (C) 2016, the @authors. All rights reserved.
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
package io.mapzone.arena.analytics.graph;

import static org.polymap.core.runtime.event.TypeEventFilter.ifType;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.PropertyDescriptor;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.SelectionAdapter;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.core.ui.UIUtils;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import io.mapzone.arena.analytics.graph.edgefunctions.CompareColumnsWithDirectEdgeFunction;
import io.mapzone.arena.analytics.graph.edgefunctions.CompareColumnsWithEdgeNodesFunction;
import io.mapzone.arena.analytics.graph.edgefunctions.ReferenceTableWithDirectEdgeFunction;
import io.mapzone.arena.analytics.graph.edgefunctions.ReferenceTableWithEdgeNodesFunction;
import io.mapzone.arena.analytics.graph.ui.FeaturePropertySelectorUI;
import io.mapzone.arena.analytics.graph.ui.FeatureSourceSelectorUI;

import com.google.common.collect.Maps;

public class SingleSourceNodeGraphFunction
        extends AbstractGraphFunction {

    private static Log              log         = LogFactory.getLog( SingleSourceNodeGraphFunction.class );

    private static final IMessages  i18n        = Messages.forPrefix( "SingleSourceNodeGraphFunction" );

    private FeatureSource           selectedSourceFeatureSource;

    private PropertyDescriptor      selectedSourcePropertyDescriptor;

    private EdgeFunction            selectedEdgeFunction;

    private final static int        COLUMN_2    = 5;


    @Override
    public String title() {
        return i18n.get( "title" );
    }


    @Override
    public String description() {
        return i18n.get( "description" );
    }


    @Override
    public int preferredHeight() {
        return 250;
    }

    // XXX replace with extension point
    public static final Class<EdgeFunction>[] availableFunctions = new Class[] {
            CompareColumnsWithDirectEdgeFunction.class, CompareColumnsWithEdgeNodesFunction.class,
            ReferenceTableWithEdgeNodesFunction.class, ReferenceTableWithDirectEdgeFunction.class };


    @Override
    public void createContents( final MdToolkit tk, final Composite parent, final Graph graph ) {
        try {
            super.createContents( tk, parent, graph );
            final FeaturePropertySelectorUI sourcePropertiesUI = new FeaturePropertySelectorUI( tk, parent, prop -> {
                this.selectedSourcePropertyDescriptor = prop;
                EventManager.instance().publish( new GraphFunctionConfigurationChangedEvent( (GraphFunction)this, "sourcePropertyDescriptor", prop ) );
            } );
            final FeatureSourceSelectorUI sourceFeaturesUI = new FeatureSourceSelectorUI( tk, parent, fs -> {
                this.selectedSourceFeatureSource = fs;
                EventManager.instance().publish( new GraphFunctionConfigurationChangedEvent( (GraphFunction)this, "sourceFeatureSource", fs ) );
                sourcePropertiesUI.setFeatureSource( fs );
            } );

            final TreeMap<String,EdgeFunction> edgeFunctions = Maps.newTreeMap();
            for (Class<EdgeFunction> cl : availableFunctions) {
                try {
                    EdgeFunction function = cl.newInstance();
                    edgeFunctions.put( function.title(), function );
                }
                catch (Exception e) {
                    throw new RuntimeException( e );
                }
            }

            final Composite edgeFunctionContainer = tk.createComposite( parent, SWT.NONE );
            edgeFunctionContainer.setLayout( FormLayoutFactory.defaults().create() );

            final ComboViewer edgeFunctionsUI = new ComboViewer( parent, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY );
            edgeFunctionsUI.setContentProvider( new ArrayContentProvider() );
            edgeFunctionsUI.setInput( edgeFunctions.keySet() );
            edgeFunctionsUI.addSelectionChangedListener( ev -> {
                String selected = SelectionAdapter.on( ev.getSelection() ).first( String.class ).get();
                EdgeFunction function = edgeFunctions.get( selected );

                // FormDataFactory.on( edgeFunctionContainer ).top(
                // edgeFunctionsUI.getCombo(), 4 )
                // .height( function.preferredHeight() ).left( COLUMN_2 ).right( 100
                // );
                FormDataFactory.on( edgeFunctionContainer ).height( function.preferredHeight() );

                UIUtils.disposeChildren( edgeFunctionContainer );
                // create panel
                function.createContents( tk, edgeFunctionContainer, selectedSourceFeatureSource );
                // FormDataFactory.on( edgeFunctionContainer ).fill();

                // resize also the top container
                // XXX depends on the parent structure
                ((FormData)parent.getParent().getParent().getLayoutData()).height = preferredHeight()
                        + function.preferredHeight();
                parent.getParent().getParent().getParent().layout();

                this.selectedEdgeFunction = function;
            } );

            final Label selectSourceTableLabel = tk.createLabel( parent, i18n.get( "selectSourceTable" ), SWT.NONE );
            FormDataFactory.on( selectSourceTableLabel ).top( 15 ).left( 1 );
            FormDataFactory.on( sourceFeaturesUI.control() ).top( selectSourceTableLabel, 2 ).left( 1 );
            final Label selectSourcePropertiesLabel = tk.createLabel( parent, i18n.get( "selectSourceProperties" ), SWT.NONE );
            FormDataFactory.on( selectSourcePropertiesLabel ).top( sourceFeaturesUI.control(), 4 ).left( COLUMN_2 );
            FormDataFactory.on( sourcePropertiesUI.control() ).top( selectSourcePropertiesLabel, 2 ).left( COLUMN_2 );

            final Label selectEdgeFunctionLabel = tk.createLabel( parent, i18n.get( "selectEdgeFunction" ), SWT.NONE );
            FormDataFactory.on( selectEdgeFunctionLabel ).top( sourcePropertiesUI.control(), 6 ).left( 1 );
            FormDataFactory.on( edgeFunctionsUI.getCombo() ).top( selectEdgeFunctionLabel, 2 ).left( 1 );
            FormDataFactory.on( edgeFunctionContainer ).fill().top( edgeFunctionsUI.getCombo(), 4 ).left( COLUMN_2 );

            // event listener
            EventManager.instance().subscribe( this, ifType( EdgeFunctionConfigurationDoneEvent.class, ev -> ev.status.get() == Boolean.TRUE
                    && ev.getSource().equals( selectedEdgeFunction ) ) );

            EventManager.instance().subscribe( this, ifType( GraphFunctionConfigurationChangedEvent.class, ev -> ev.getSource().equals( this ) ) );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }


    @EventHandler( display = true )
    protected void onEdgeFunctionConfigurationDone( EdgeFunctionConfigurationDoneEvent ev ) {
        onGraphFunctionConfigurationChanged( null );
    }


    @EventHandler( display = true )
    protected void onGraphFunctionConfigurationChanged( GraphFunctionConfigurationChangedEvent ev ) {
        if (isConfigurationDone()) {
            // show the fab
            fab.setVisible( true );
            fab.setEnabled( true );
        }
        else {
            fab.setVisible( false );
            fab.setEnabled( false );
        }
    }


    private boolean isConfigurationDone() {
        return selectedSourceFeatureSource != null && selectedSourcePropertyDescriptor != null
                && selectedEdgeFunction != null && selectedEdgeFunction.isConfigurationComplete();
    }


    @Override
    public void generate( MdToolkit tk, IProgressMonitor monitor, final Graph graph ) throws Exception {
        if (!tk.isClosed()) {
            tk.createSnackbar( Appearance.FadeIn, "Generation started - stay tuned..." );
        }

        // selectedSourceFeatureSource.
        // disctinct on propertyColumn
        final Map<Object,Feature> distinctSourceFeatures = Maps.newHashMap();
        final Map<String,Node> nodes = Maps.newHashMap();

        // iterate on features
        // XXX replace with a Distinct on property Filter
        FeatureCollection allFeatures = selectedSourceFeatureSource.getFeatures();
        FeatureIterator iterator = allFeatures.features();
        int featureCount = 0;
        while (iterator.hasNext()) {
            featureCount++;
            SimpleFeature feature = (SimpleFeature)iterator.next();
            Object key = feature.getAttribute( selectedSourcePropertyDescriptor.getName() );
            if (key == null || "".equals( key.toString() )) {
                continue;
            }
//            if (!distinctSourceFeatures.containsKey( key )) {
                distinctSourceFeatures.put( key, feature );
                Node node = new Node( Node.Type.real, "graph_"
                        + feature.getID(), selectedSourceFeatureSource, feature, key.toString(), 1 );
                nodes.put( feature.getID(), node );
                graph.addOrUpdateNode( node );
//            }
        }
        if (!tk.isClosed()) {
            tk.createSnackbar( Appearance.FadeIn, featureCount + " Nodes read" );
        }

        int edges = selectedEdgeFunction.generateEdges( tk, monitor, nodes, graph );

        if (!tk.isClosed()) {
            tk.createSnackbar( Appearance.FadeIn, featureCount + " Nodes with " + edges
                    + " Edges analysed, starting layout process" );
        }
        graph.layout();
    }
}
