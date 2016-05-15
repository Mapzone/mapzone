/* 
 * polymap.org
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
package io.mapzone.arena.analytics.graph;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.filter.IdBuilder;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.PropertyDescriptor;
import org.opengis.filter.identity.FeatureId;
import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.SelectionAdapter;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.core.ui.UIUtils;
import org.polymap.rap.openlayers.base.OlFeature;
import org.polymap.rap.openlayers.base.OlMap;
import org.polymap.rap.openlayers.graph.OlFeatureGephiGraph;
import org.polymap.rap.openlayers.source.VectorSource;
import org.polymap.rap.openlayers.style.Base;
import org.polymap.rap.openlayers.style.StrokeStyle;
import org.polymap.rap.openlayers.style.Style;
import org.polymap.rap.openlayers.style.StyleFunction;
import org.polymap.rap.openlayers.types.Color;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import com.google.common.collect.Maps;

import io.mapzone.arena.Messages;

public class SingleSourceNodeGraphFunction
        implements GraphFunction {

    private final ServerPushSession pushSession = new ServerPushSession();

    private static Log log = LogFactory.getLog( SingleSourceNodeGraphFunction.class );

    private static final IMessages i18n = Messages.forPrefix( "SingleSourceNodeGraphFunction" );

    private FeatureSource selectedSourceFeatureSource;

    private PropertyDescriptor selectedSourcePropertyDescriptor;

    private Button fab;

    private EdgeFunction selectedEdgeFunction;

    private final static int COLUMN_2 = 5;

    private final Style edgeStyle = new Style().stroke
            .put( new StrokeStyle().color.put( new Color( "black" ) ).width.put( 1f ) ).zIndex.put( 0f );


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
    public static final Class<EdgeFunction>[] availableFunctions = new Class[] { ReferenceTableEdgeFunction.class };


    @Override
    public void createContents( final MdToolkit tk, final Composite parent, final VectorSource source,
            final OlMap olMap ) {
        try {
            final FeaturePropertySelectorUI sourcePropertiesUI = new FeaturePropertySelectorUI( tk, parent, prop -> {
                this.selectedSourcePropertyDescriptor = prop;
                checkIfConfigurationComplete();
            } );
            final FeatureSourceSelectorUI sourceFeaturesUI = new FeatureSourceSelectorUI( tk, parent, fs -> {
                this.selectedSourceFeatureSource = fs;
                sourcePropertiesUI.setFeatureSource( fs );
                checkIfConfigurationComplete();
            } );

            final TreeMap<String,EdgeFunction> edgeFunctions = Maps.newTreeMap();
            for (Class<EdgeFunction> cl : availableFunctions) {
                try {
                    EdgeFunction function = cl.newInstance();
                    function.init( this );
                    edgeFunctions.put( function.title(), function );
                }
                catch (Exception e) {
                    throw new RuntimeException( e );
                }
            }

            final Composite edgeFunctionContainer = tk.createComposite( parent, SWT.NONE );
            edgeFunctionContainer.setLayout( FormLayoutFactory.defaults().create() );

            final ComboViewer edgeFunctionsUI = new ComboViewer( parent, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN );
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
                function.createContents( tk, edgeFunctionContainer, source, olMap );
                // FormDataFactory.on( edgeFunctionContainer ).fill();

                // resize also the top container
                // XXX depends on the parent structure
                ((FormData)parent.getParent().getParent().getLayoutData()).height = preferredHeight()
                        + function.preferredHeight();
                parent.getParent().getParent().getParent().layout();

                this.selectedEdgeFunction = function;
                checkIfConfigurationComplete();
            } );

            fab = tk.createFab();
            fab.setVisible( false );
            fab.setEnabled( false );
            fab.setToolTipText( i18n.get( "fabTooltip" ) );
            fab.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {

                @Override
                public void widgetSelected( SelectionEvent e ) {
                    DefaultOperation op = new DefaultOperation( i18n.get( "title" ) ) {

                        @Override
                        public IStatus doExecute( final IProgressMonitor monitor, final IAdaptable info )
                                throws Exception {
                            try {
                                UIThreadExecutor.async( () -> pushSession.start(),
                                        error -> StatusDispatcher.handleError( "", error ) );
                                generate( tk, monitor, source, olMap );
                                return Status.OK_STATUS;
                            }
                            finally {
                                UIThreadExecutor.async( () -> pushSession.stop(),
                                        error -> StatusDispatcher.handleError( "", error ) );
                            }
                        }

                    };
                    // execute
                    OperationSupport.instance().execute2( op, true, false );
                    fab.setVisible( false );
                }
            } );

            final Label selectSourceTableLabel = tk.createLabel( parent, i18n.get( "selectSourceTable" ), SWT.NONE );
            FormDataFactory.on( selectSourceTableLabel ).top( 15 ).left( 1 );
            FormDataFactory.on( sourceFeaturesUI.control() ).top( selectSourceTableLabel, 2 ).left( 1 );
            final Label selectSourcePropertiesLabel = tk.createLabel( parent, i18n.get( "selectSourceProperties" ),
                    SWT.NONE );
            FormDataFactory.on( selectSourcePropertiesLabel ).top( sourceFeaturesUI.control(), 4 ).left( COLUMN_2 );
            FormDataFactory.on( sourcePropertiesUI.control() ).top( selectSourcePropertiesLabel, 2 ).left( COLUMN_2 );

            final Label selectEdgeFunctionLabel = tk.createLabel( parent, i18n.get( "selectEdgeFunction" ), SWT.NONE );
            FormDataFactory.on( selectEdgeFunctionLabel ).top( sourcePropertiesUI.control(), 6 ).left( 1 );
            FormDataFactory.on( edgeFunctionsUI.getCombo() ).top( selectEdgeFunctionLabel, 2 ).left( 1 );
            FormDataFactory.on( edgeFunctionContainer ).fill().top( edgeFunctionsUI.getCombo(), 4 ).left( COLUMN_2 );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }


    // XXX replace by eventhandler
    @Override
    public void edgeFunctionConfigurationDone() {
        checkIfConfigurationComplete();
    }


    private void checkIfConfigurationComplete() {
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


    private void generate( MdToolkit tk, IProgressMonitor monitor, final VectorSource source, final OlMap olMap )
            throws Exception {
        tk.createSnackbar( Appearance.FadeIn, "Generation started - stay tuned" );

        final OlFeatureGephiGraph graph = new OlFeatureGephiGraph( source, olMap );

        // selectedSourceFeatureSource.
        // disctinct on propertyColumn
        final Map<Object,Feature> distinctSourceFeatures = Maps.newHashMap();
        // for faster access
        final Map<String,Feature> featureCache = Maps.newHashMap();
        final Map<String,OlFeature> olFeatures = Maps.newHashMap();

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
            if (!distinctSourceFeatures.containsKey( key )) {
                distinctSourceFeatures.put( key, feature );
                featureCache.put( feature.getID(), feature );
                OlFeature olFeature = new OlFeature( "graph_" + feature.getID() ).name.put( key.toString() ).style
                        .put( featureStyle( 1 ) );
                olFeatures.put( feature.getID(), olFeature );
                graph.addOrUpdateNode( olFeature, 1 );
            }
        }
        tk.createSnackbar( Appearance.FadeIn, featureCount + " Nodes read" );
        
        Collection<Edge> edges = selectedEdgeFunction.generateEdges( tk, monitor, featureCache );

        for (Edge edge : edges) {
            OlFeature edgeFeature = new OlFeature(
                    edge.featureA().getIdentifier().getID() + "_" + edge.featureA().getIdentifier().getID() ).name
                            .put( edge.key() ).style.put( edgeStyle );
            graph.addOrUpdateEdge( edgeFeature, olFeatures.get( edge.featureA().getIdentifier().getID() ),
                    olFeatures.get( edge.featureB().getIdentifier().getID() ), 1 );
        }
        tk.createSnackbar( Appearance.FadeIn, featureCount + " Nodes with " + edges.size() + " Edges generated" );
        
        UIThreadExecutor.async( () -> pushSession.stop(), error -> StatusDispatcher.handleError( "", error ) );
    }


    private Base featureStyle( int weight ) {
        return new StyleFunction( circle( weight, "red" ) );
    }


    private String circle( int radius, String color ) {
        StringBuffer sb = new StringBuffer();
        // sb.append(
        // "console.log('singlefeature');console.log(feature);console.log(this);"
        // );
        sb.append( "return [new ol.style.Style({" );
        sb.append( "  zIndex: 1," );
        sb.append( "  image: new ol.style.Circle({" );
        sb.append( "      radius: " ).append( radius ).append( "," );
        sb.append( "    stroke: new ol.style.Stroke({" );
        sb.append( "      color: '" ).append( color ).append( "'" );
        sb.append( "    })," );
        sb.append( "    fill: new ol.style.Fill({" );
        sb.append( "      color: '" ).append( color ).append( "'" );
        sb.append( "    })" );
        sb.append( "  })," );
        sb.append( "  text: new ol.style.Text({" );
        sb.append( "    text: this.get('name')," );
        sb.append( "    fill: new ol.style.Fill({" );
        sb.append( "      color: '" ).append( color ).append( "'" );
        sb.append( "    })" );
        sb.append( "  })" );
        sb.append( "})];" );
        return sb.toString();
    }
}
