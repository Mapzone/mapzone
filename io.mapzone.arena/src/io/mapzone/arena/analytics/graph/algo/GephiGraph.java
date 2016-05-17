/*
 * polymap.org Copyright (C) 2016 individual contributors as indicated by
 * the @authors tag. All rights reserved.
 * 
 * This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package io.mapzone.arena.analytics.graph.algo;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Display;
import org.gephi.graph.GraphControllerImpl;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDirection;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.impl.ImportContainerFactoryImpl;
import org.gephi.io.importer.impl.ImportControllerImpl;
import org.gephi.io.processor.plugin.AppendProcessor;
import org.gephi.io.processor.spi.Processor;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.project.impl.ProjectControllerImpl;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.rap.openlayers.types.Coordinate;
import org.polymap.rap.openlayers.types.Extent;

import com.google.common.collect.Maps;

import io.mapzone.arena.analytics.graph.GraphUI;

/**
 * Wraps a gephi graph stream layout algorithm.
 * 
 * @author <a href="http://stundzig.it">Steffen Stundzig</a>
 */
public class GephiGraph
        implements io.mapzone.arena.analytics.graph.Graph {

    private final static Log log = LogFactory.getLog( GephiGraph.class );

    private static final double GRAPHUNIT2COORD = 100;

    private static final long REFRESH_INTERVAL = 500;

    private final Map<String,io.mapzone.arena.analytics.graph.Node> nodes = Maps.newHashMap();

    private final Map<String,io.mapzone.arena.analytics.graph.Edge> edges = Maps.newHashMap();

    private final ProjectController pc;

    private final Workspace workspace;

    private Container container;

    private ImportControllerImpl importController;

    private GraphModel graphModel;

    private ForceAtlas2 layout;

    private final GraphUI graphUi;


    /**
     * Creates a graph which updates automatically all features in the vector source
     * and updates also the optimal extent in the map.
     * 
     * @param vector
     * @param map
     */
    public GephiGraph( final GraphUI graphUi ) {
        // this.vector = vector;
        // this.map = map;
        this.graphUi = graphUi;
        pc = new ProjectControllerImpl();// .getDefault().lookup(
                                         // ProjectController.class );
        pc.newProject();
        workspace = pc.getCurrentWorkspace();
    }


    /**
     * Adds or updates the feature to the graph.
     * 
     * @param node
     */
    public void addOrUpdateNode( final io.mapzone.arena.analytics.graph.Node node ) {
        if (!nodes.containsKey( node.key() )) {
            nodes.put( node.key(), node );
            addNode( node.key() );
            graphUi.addNode( node );
        }
    }


    /**
     * Creates or updates an undirected edge between both nodes.
     * 
     * The id of the edge is constructed with src.id + "_" + target.id
     */
    public void addOrUpdateEdge( final io.mapzone.arena.analytics.graph.Node src,
            final io.mapzone.arena.analytics.graph.Node target ) {
        String edgeIdST = src.key() + "_" + target.key();
        io.mapzone.arena.analytics.graph.Edge line = edges.get( edgeIdST );
        if (line == null) {
            // check other direction
            String edgeIdTS = target.key() + "_" + src.key();
            line = edges.get( edgeIdTS );
            if (line == null) {
                // add original
                line = new io.mapzone.arena.analytics.graph.Edge( edgeIdST, src, target );
                addOrUpdateEdge( line, src, target );
            }
            else {
                line = new io.mapzone.arena.analytics.graph.Edge( edgeIdTS, target, src );
                addOrUpdateEdge( line, target, src );
            }
        }
        else {
            addOrUpdateEdge( line, src, target );
        }
    }


    /**
     * Creates or updates a predefined edge between both features with the specified
     * weight.
     * 
     * If an edge is added twice, than the weight of both are added.
     * 
     * <strong>The ID of the edge must be build with src.id + '_' +
     * target.id.</strong>
     */
    public void addOrUpdateEdge( final io.mapzone.arena.analytics.graph.Edge edge,
            final io.mapzone.arena.analytics.graph.Node src, final io.mapzone.arena.analytics.graph.Node target ) {
        final String edgeId = edge.key();
        if (!edges.containsKey( edgeId )) {
            edges.put( edgeId, edge );
            addEdge( edgeId, src.key(), target.key() );
            graphUi.addEdge( edge );
            // getEdge( edgeId ).addAttribute( "layout.weight", weight );
        }
        else {
            // weight += getEdge( edgeId ).getAttribute( "layout.weight",
            // Integer.class );
            // getEdge( edgeId ).setAttribute( "layout.weight", weight );
        }
    }


    private void addNode( String id ) {
        ContainerLoader loader = getContainer().getLoader();
        loader.addNode( loader.factory().newNodeDraft( id ) );
    }


    private void addEdge( String id, String srcId, String targetId ) {
        ContainerLoader loader = getContainer().getLoader();
        EdgeDraft edge = loader.factory().newEdgeDraft( id );
        edge.setSource( loader.getNode( srcId ) );
        edge.setTarget( loader.getNode( targetId ) );
        edge.setDirection( EdgeDirection.UNDIRECTED );
        loader.addEdge( edge );
    }


    private Container getContainer() {
        if (container == null) {
            container = new ImportContainerFactoryImpl().newContainer();
            importController = new ImportControllerImpl();
            graphModel = new GraphControllerImpl().getGraphModel( workspace );
            layout = new ForceAtlas2( null );
            layout.setGraphModel( graphModel );
            layout.resetPropertiesValues();
            layout.setOutboundAttractionDistribution( true );
            layout.setEdgeWeightInfluence( 1.0d );
            layout.setGravity( 1.0 );
            layout.setJitterTolerance( 1.0 );
            layout.setScalingRatio( 15.0 );
        }
        return container;
    }

    private Processor processor = new AppendProcessor();


    @Override
    public void layout() {
        try {
            importController.process( container, processor, workspace );
            UIThreadExecutor.async( () -> {
                final Display display = Display.getCurrent();
                readCoordinates( graphModel, layout, display, 2000 );
            }, error -> StatusDispatcher.handleError( "", error ) );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void readCoordinates( final GraphModel graphModel, final ForceAtlas2 layout, final Display display,
            final int maxTime ) {
        try {
            log.info( "run algo" );
            final AutoLayout autolayout = new AutoLayout( maxTime, TimeUnit.MILLISECONDS );
            autolayout.addLayout( layout, 1.0f );
            autolayout.setGraphModel( graphModel );
            // Polymap.executorService().submit( () -> {
            // log.info( "vor display async" );
            display.asyncExec( () -> {
                autolayout.execute();
                log.info( "sending coordinates" );
                final Graph graph = graphModel.getUndirectedGraph();
                double minX = 10000;
                double minY = 10000;
                double maxX = -10000;
                double maxY = -10000;
                for (Node graphNode : graph.getNodes()) {
                    Coordinate newCoordinate = new Coordinate( graphNode.x() * GRAPHUNIT2COORD,
                            graphNode.y() * GRAPHUNIT2COORD );
                    minX = Math.min( minX, newCoordinate.x() );
                    maxX = Math.max( maxX, newCoordinate.x() );
                    minY = Math.min( minY, newCoordinate.y() );
                    maxY = Math.max( maxY, newCoordinate.y() );
                    log.info( "sending coordinate " + graphNode.getId() + ": " + newCoordinate.x() + ";"
                            + newCoordinate.y() );
                    final io.mapzone.arena.analytics.graph.Node featureNode = nodes.get( graphNode.getId() );
                    graphUi.updateGeometry( featureNode, newCoordinate );
                    for (Edge graphEdge : graph.getEdges( graphNode )) {
                        io.mapzone.arena.analytics.graph.Edge line = edges.get( graphEdge.getId().toString() );
                        graphUi.updateGeometry( line, featureNode, newCoordinate );
                    }
                }
                Extent envelope = new Extent( minX, minY, maxX, maxY );
                graphUi.updateEnvelope( envelope );
                log.info( "setting extent to " + envelope.toJson() );
                log.info( "sending coordinates done." );
            } );
            // } ).get();
            // Thread.sleep( REFRESH_INTERVAL );
            // }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( e );
        }
    }


    @Override
    public void clear() {
        graphUi.clear();
        nodes.clear();
        edges.clear();
        container = null;
    }


    @Override
    public io.mapzone.arena.analytics.graph.Node getNode( String key ) {
        return nodes.get( key );
    }

}