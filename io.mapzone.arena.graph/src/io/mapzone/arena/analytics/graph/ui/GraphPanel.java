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

import static org.polymap.core.ui.FormDataFactory.on;

import java.util.TreeMap;

import java.io.IOException;

import org.geotools.data.FeatureSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Maps;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;

import org.polymap.core.catalog.resolve.IResolvableInfo;
import org.polymap.core.catalog.resolve.IResourceInfo;
import org.polymap.core.data.rs.catalog.RServiceInfo;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.project.IMap;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.style.DefaultStyle;
import org.polymap.core.style.model.FeatureStyle;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.SelectionAdapter;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.ActionItem;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.ItemContainer;
import org.polymap.rhei.batik.toolkit.md.MdToolbar2;

import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;
import org.polymap.p4.style.LayerStylePanel;
import org.polymap.p4.style.StyleEditorInput;
import org.polymap.rap.openlayers.base.OlEventListener;
import org.polymap.rap.openlayers.control.MousePositionControl;

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

//    
//    public class LayerInput {
//
//        private static Log log = LogFactory.getLog( LayerInput.class );
//    }


    private static final String         NODE_STYLE_IDENTIFIER = "nodeStyleIdentifier";

    private static final String         EDGE_STYLE_IDENTIFIER = "edgeStyleIdentifier";

    private static Log                  log                   = LogFactory.getLog( GraphPanel.class );

    private static final IMessages      i18n                  = Messages.forPrefix( "GraphPanel" );

    public static final PanelIdentifier ID                    = PanelIdentifier.parse( "graph" );

    // instance *******************************************

    @Scope( P4Plugin.Scope )
    protected Context<IMap>             mainMap;

    @Scope( P4Plugin.Scope )
    protected Context<Node>             nodeSelection;

    private MapViewer<LayerInput>       mapViewer;

    private Composite                   mapContainer;

    private FeatureSource               fs;

    private OlEventListener             selectFeatureListener;

    private Graph                       graph;

    private GraphLayerProvider          graphLayerProvider;

    @Mandatory
    @Scope( P4Plugin.StyleScope )
    protected Context<StyleEditorInput> styleEditorInput;


    @Override
    public boolean wantsToBeShown() {
        if (site().path().size() == 2) {
            site().icon.set( GraphPlugin.images().svgImage( "chart-bubble.svg", P4Plugin.HEADER_ICON_CONFIG ) );
            site().tooltip.set( i18n.get( "tooltip" ) );
            site().title.set( "" );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        site().title.set( i18n.get( "title" ) );
        site().preferredWidth.set( 650 );
    }


    @Override
    public void dispose() {
        super.dispose();
        if (graphLayerProvider != null) {
            graphLayerProvider.dispose();
        }
    }

    // XXX replace with extension point
    public static final Class<GraphFunction>[] availableFunctions = new Class[] {
            /* HiddenOrganisationPersonGraphFunction.class, */ OrganisationPersonGraphFunction.class,
            SingleSourceNodeGraphFunction.class };


    @Override
    public void createContents( final Composite parent ) {
        try {
            if (!featureSelection.isPresent()) {
                tk().createFlowText( parent, "Select a feature table to by **active** first." );
                return;
            }
            // this.parent = parent;
            parent.setLayout( FormLayoutFactory.defaults().create() );

            MdToolbar2 toolbar = tk().createToolbar( parent, SWT.TOP );
            new NodeStylerItem( toolbar );
            new EdgeStylerItem( toolbar );

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

            final ComboViewer combo = new ComboViewer( parent, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN
                    | SWT.READ_ONLY );
            combo.setContentProvider( new ArrayContentProvider() );
            combo.setInput( functions.keySet() );
            combo.addSelectionChangedListener( ev -> {
                String selected = SelectionAdapter.on( ev.getSelection() ).first( String.class ).get();
                GraphFunction function = functions.get( selected );

                FormDataFactory.on( functionContainer ).top( combo.getCombo(), 5 ).height( function.preferredHeight() ).left( 0 ).right( 100 );

                UIUtils.disposeChildren( functionContainer );
                // create panel
//                Section section = tk().createSection( functionContainer, function.description(), ExpandableComposite.TREE_NODE, Section.SHORT_TITLE_BAR, Section.FOCUS_TITLE, SWT.BORDER );
//                section.setBackground( UIUtils.getColor( 235,  235, 235) );
//                ((Composite)section.getClient()).setLayout( FormLayoutFactory.defaults().create() );
                
                IPanelSection section = tk().createPanelSection( functionContainer, function.description(), SWT.Expand, IPanelSection.EXPANDABLE );
                section.getControl().setBackground( UIUtils.getColor( 235,  235, 235) );
                section.getBody().setBackground( UIUtils.getColor( 235,  235, 235) );
                section.setExpanded( true );
                section.getBody().setLayout( FormLayoutFactory.defaults().create() );

                //
                graph.clear();
                function.createContents( tk(), section.getBody(), graph );
                if (!section.isExpanded()) {
                    section.setExpanded( true );
                }
                
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
            on( toolbar.getControl() ).left( 0, 3 ).right( 100, -3 ).top( 0 );
            
            final Label selectLabel = tk().createLabel( parent, i18n.get( "selectFunction" ), SWT.NONE );
            on( selectLabel ).top( toolbar.getControl(), 8 ).left( 1 );
            on( combo.getCombo() ).top( selectLabel, 2 ).left( 1 );
            on( functionContainer ).top( combo.getCombo(), 5 ).height( 0 ).left( 0 ).right( 100 );
            on( mapContainer ).fill().top( functionContainer, 5 );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }


    protected void createMapViewer() {
        mapViewer = new MapViewer<LayerInput>( mapContainer );

        // must be global, because its used as eventlistener
        // ImageLayerProvider and VectorLayerProvider are supported

        graphLayerProvider = new ImageLayerProvider( tk(), mapViewer, featureSelection.get().layer(), id -> {
            try {
                // xxx add a filter for all features with a distance of 1 to the
                // current feature
                Node selectedNode = graph.getNode( id );
                if (selectedNode != null) {

                    nodeSelection.set( selectedNode );
                    getContext().openPanel( site().path(), SelectedNodePanel.ID );
                }
            }
            catch (Exception e) {
                StatusDispatcher.handleError( "", e );
            }
        } , site().memento().getString( NODE_STYLE_IDENTIFIER ), site().memento().getString( EDGE_STYLE_IDENTIFIER ) );
        mapViewer.layerProvider.set( graphLayerProvider );
        mapViewer.contentProvider.set( new ArrayContentProvider() );
        mapViewer.maxExtent.set( graphLayerProvider.referenceEnvelope() );
        mapViewer.addMapControl( new MousePositionControl() );
        // mapViewer.addMapControl( new ScaleLineControl() );

        mapViewer.setInput( graphLayerProvider.layers() );
        mapContainer.layout();

        graph = new GephiGraph( graphLayerProvider.graphUi() );
//        graph = new GraphStreamGraph( graphLayerProvider.graphUi() );
    }


    class NodeStylerItem
            extends ActionItem {

        public NodeStylerItem( ItemContainer container ) {
            super( container );
            // XXX we need a text icon here
            icon.set( P4Plugin.images().svgImage( "brush.svg", P4Plugin.TOOLBAR_ICON_CONFIG ) );
            tooltip.set( i18n.get( "nodeStylerTooltip" ) );
            action.set( ev -> {
                if (!site().memento().optString( NODE_STYLE_IDENTIFIER ).isPresent()) {
                    FeatureStyle featureStyle = P4Plugin.styleRepo().newFeatureStyle();
                    DefaultStyle.fillPointStyle( featureStyle );
                    TextStyle textStyle = DefaultStyle.fillTextStyle( featureStyle, null );
                    textStyle.property.createValue( PropertyString.defaults( "name" ) );
                    featureStyle.store();
                    site().memento().putString( NODE_STYLE_IDENTIFIER, featureStyle.id() );
                }
                styleEditorInput.set( new StyleEditorInput( site().memento().getString( NODE_STYLE_IDENTIFIER ), graphLayerProvider.graphUi().nodeSchema() ) );
                getContext().openPanel( site().path(), LayerStylePanel.ID );
            } );
        }
    }


    class EdgeStylerItem
            extends ActionItem {

        public EdgeStylerItem( ItemContainer container ) {
            super( container );
            // XXX we need a text icon here
            icon.set( P4Plugin.images().svgImage( "brush.svg", P4Plugin.TOOLBAR_ICON_CONFIG ) );
            tooltip.set( i18n.get( "edgeStylerTooltip" ) );
            action.set( ev -> {
                if (!site().memento().optString( EDGE_STYLE_IDENTIFIER ).isPresent()) {
                    FeatureStyle featureStyle = P4Plugin.styleRepo().newFeatureStyle();
                    DefaultStyle.fillLineStyle( featureStyle );
                    featureStyle.store();
                    site().memento().putString( EDGE_STYLE_IDENTIFIER, featureStyle.id() );
                }
                styleEditorInput.set( new StyleEditorInput( site().memento().getString( EDGE_STYLE_IDENTIFIER ), graphLayerProvider.graphUi().edgeSchema() ) );
                getContext().openPanel( site().path(), LayerStylePanel.ID );
            } );
        }
    }


    private String resourceIdentifier( final FeatureSource selectedFeatureSource ) throws IOException {
        IResolvableInfo info = P4Plugin.localCatalog().localFeaturesStoreInfo();
        IResourceInfo res = ((RServiceInfo)info.getServiceInfo()).resource( selectedFeatureSource );
        return P4Plugin.localResolver().resourceIdentifier( res );
    }
}
