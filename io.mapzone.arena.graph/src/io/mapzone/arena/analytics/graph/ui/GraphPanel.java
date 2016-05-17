/*
 * polymap.org Copyright (C) 2016, Falko Bräutigam. All rights reserved.
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

import java.io.IOException;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.FeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.polymap.core.catalog.resolve.IResolvableInfo;
import org.polymap.core.catalog.resolve.IResourceInfo;
import org.polymap.core.data.rs.catalog.RServiceInfo;
import org.polymap.core.data.util.Geometries;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.project.ILayer;
import org.polymap.core.project.IMap;
import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.PlainLazyInit;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.SelectionAdapter;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.core.ui.UIUtils;
import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;
import org.polymap.p4.layer.FeaturePanel;
import org.polymap.p4.layer.FeatureSelection;
import org.polymap.rap.openlayers.base.OlEventListener;
import org.polymap.rap.openlayers.control.ScaleLineControl;
import org.polymap.rap.openlayers.layer.VectorLayer;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.IPanelSection;

import com.google.common.collect.Maps;
import io.mapzone.arena.analytics.graph.Graph;
import io.mapzone.arena.analytics.graph.GraphFunction;
import io.mapzone.arena.analytics.graph.GraphPlugin;
import io.mapzone.arena.analytics.graph.Messages;
import io.mapzone.arena.analytics.graph.Node;
import io.mapzone.arena.analytics.graph.OrganisationPersonGraphFunction;
import io.mapzone.arena.analytics.graph.SingleSourceNodeGraphFunction;
import io.mapzone.arena.analytics.graph.algo.GephiGraph;

/**
 * Proof-of-concept for generated geometries and graph displayed in an OL map.
 *
 * @author Steffen Stundzig
 */
public class GraphPanel
        extends P4Panel {

    private static Log                                  log  = LogFactory.getLog( GraphPanel.class );

    private static final IMessages                      i18n = Messages.forPrefix( "GraphPanel" );

    public static final PanelIdentifier                 ID   = PanelIdentifier.parse( "graph" );

    public static final Lazy<CoordinateReferenceSystem> CRS  = new PlainLazyInit( () -> {
                                                                 try {
                                                                     return Geometries.crs( "EPSG:3857" );
                                                                 }
                                                                 catch (Exception e) {
                                                                     throw new RuntimeException( e );
                                                                 }
                                                             } );

    // instance *******************************************

    // the map to show selected items in the graph
    @Scope( P4Plugin.Scope )
    protected Context<IMap>                             mainMap;
    // private ILayer layer;

    // /** {@link EncodedImageProducer} pipeline of {@link #layer}. */
    // private Pipeline pipeline;

    private MapViewer<VectorLayer>                      mapViewer;

    // private String servletAlias;

    // private Composite parent;

    // /** Names of the attributes that we support for chart. */
    // private List<String> availableAttributes;
    //
    // private List<MappingFunction> mappingFunctions = new ArrayList();

    private Composite                                   mapContainer;

    // private VectorSource source;

    private FeatureSource                               fs;

    private OlEventListener                             selectFeatureListener;

    // private PanelPath path;

    private Graph                                       graph;

    private VectorLayerProvider graphLayerProvider;


    @Override
    public boolean wantsToBeShown() {
        if (site().path().size() == 2) {
            site().icon.set( GraphPlugin.images().svgImage( "graph.svg", P4Plugin.HEADER_ICON_CONFIG ) );
            site().tooltip.set( i18n.get( "tooltip" ) );
            site().title.set( "" );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        site().title.set( i18n.get( "title" ) );
    }

    // XXX replace with extension point
    public static final Class<GraphFunction>[] availableFunctions = new Class[] {
            /* HiddenOrganisationPersonGraphFunction.class, */ OrganisationPersonGraphFunction.class,
            SingleSourceNodeGraphFunction.class };


    @Override
    public void createContents( final Composite parent ) {
        try {

            // this.parent = parent;
            parent.setLayout( FormLayoutFactory.defaults().create() );

            final TreeMap<String,GraphFunction> functions = Maps.newTreeMap();
            for (Class<GraphFunction> cl : availableFunctions) {
                try {
                    GraphFunction function = cl.newInstance();
                    functions.put( function.title(), function );
                }
                catch (Exception e) {
                    throw new RuntimeException( e );
                }
            }

            final Composite functionContainer = tk().createComposite( parent, SWT.NONE );

            final ComboViewer combo = new ComboViewer( parent, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN );
            combo.setContentProvider( new ArrayContentProvider() );
            combo.setInput( functions.keySet() );
            combo.addSelectionChangedListener( ev -> {
                String selected = SelectionAdapter.on( ev.getSelection() ).first( String.class ).get();
                GraphFunction function = functions.get( selected );

                FormDataFactory.on( functionContainer ).top( combo.getCombo(), 5 ).height( function.preferredHeight() ).left( 0 ).right( 100 );

                UIUtils.disposeChildren( functionContainer );
                // create panel
                IPanelSection section = tk().createPanelSection( functionContainer, function.description(), SWT.BORDER );
                section.setExpanded( true );
                section.getBody().setLayout( FormLayoutFactory.defaults().create() );

                //
                graph.clear();
                function.createContents( tk(), section.getBody(), graph );
                FormDataFactory.on( section.getBody() ).fill();

                // functionContainer.layout();
                parent.layout();
            } );

            //
            // mapContainer
            mapContainer = tk().createComposite( parent, SWT.BORDER );
            mapContainer.setLayout( new FillLayout() );
            if (mapViewer != null) {
                mapViewer.dispose();
            }
            createMapViewer();

            // layout
            final Label selectLabel = tk().createLabel( parent, i18n.get( "selectFunction" ), SWT.NONE );
            FormDataFactory.on( selectLabel ).top( 1 ).left( 1 );
            FormDataFactory.on( combo.getCombo() ).top( selectLabel, 2 ).left( 1 );
            FormDataFactory.on( functionContainer ).top( combo.getCombo(), 5 ).height( 0 ).left( 0 ).right( 100 );
            FormDataFactory.on( mapContainer ).fill().top( functionContainer, 5 );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }


    protected void createMapViewer() {
        mapViewer = new MapViewer( mapContainer );
        
        // must be global, because its used as eventlistener
        graphLayerProvider = new VectorLayerProvider( mapViewer, id -> {
            try {
                // xxx add a filter for all features with a distance of 1 to the
                // current feature
                Node selectedNode = graph.getNode( id );
                if (selectedNode != null) {
                    
                    FeatureSource selectedFS = selectedNode.featureSource();
                    String selectedFSIdentifier = resourceIdentifier( selectedFS );
                    if (featureSelection.get().layer().resourceIdentifier.get().equals( selectedFSIdentifier )) {
                        // correct layer selected
                        featureSelection.get().setClicked( selectedNode.feature() );
                    }
                    else {
                        // load all known layers and try to find the right one
                        // set them as new featureSelection
                        for (ILayer layer : mainMap.get().layers) {
                            if (layer.resourceIdentifier.get().equals( selectedFSIdentifier )) {
                                featureSelection.set( FeatureSelection.forLayer( layer ) );
                                featureSelection.get().setClicked( selectedNode.feature() );
                                break;
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                StatusDispatcher.handleError( "", e );
            }
            getContext().openPanel( site().path(), FeaturePanel.ID );
        } );
        mapViewer.layerProvider.set( graphLayerProvider );
        mapViewer.contentProvider.set( new ArrayContentProvider() );
        mapViewer.maxExtent.set( new ReferencedEnvelope( -10000, -10000, 10000, 10000, CRS.get() ) );
        // mapViewer.addMapControl( new MousePositionControl() );
        mapViewer.addMapControl( new ScaleLineControl() );

        mapViewer.setInput( new ILayer[] { null } );
        mapContainer.layout();
        
        graph = new GephiGraph( graphLayerProvider.createGraphUi(tk()) );
    }


    public String resourceIdentifier( final FeatureSource fs ) throws IOException {
        IResolvableInfo info = P4Plugin.localCatalog().localFeaturesStoreInfo();
        IResourceInfo res = ((RServiceInfo)info.getServiceInfo()).resource( fs );
        return P4Plugin.localResolver().resourceIdentifier( res );
    }
}
