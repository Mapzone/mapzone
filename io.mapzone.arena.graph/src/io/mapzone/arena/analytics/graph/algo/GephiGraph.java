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

import org.gephi.graph.GraphControllerImpl;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDirection;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.io.importer.impl.ImportContainerFactoryImpl;
import org.gephi.io.importer.impl.ImportControllerImpl;
import org.gephi.io.processor.plugin.AppendProcessor;
import org.gephi.io.processor.spi.Processor;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.project.impl.ProjectControllerImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Maps;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.runtime.UIJob;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.rap.openlayers.types.Coordinate;
import org.polymap.rap.openlayers.types.Extent;

import io.mapzone.arena.analytics.graph.GraphFunction;
import io.mapzone.arena.analytics.graph.GraphUI;

/**
 * Wraps a gephi graph stream layout algorithm.
 * 
 * @author <a href="http://stundzig.it">Steffen Stundzig</a>
 */
public class GephiGraph
        implements io.mapzone.arena.analytics.graph.Graph {

    private final static Log                                        log              = LogFactory.getLog( GephiGraph.class );

    private static final double                                     GRAPHUNIT2COORD  = 100;

    private static final long                                       REFRESH_INTERVAL = 500;

    private final Map<String,io.mapzone.arena.analytics.graph.Node> nodes            = Maps.newHashMap();

    private final Map<String,io.mapzone.arena.analytics.graph.Edge> edges            = Maps.newHashMap();

    private ProjectController                                       pc;

    private Workspace                                               workspace;

    private Container                                               container;

    private ImportControllerImpl                                    importController;

    private GraphModel                                              graphModel;

    private ForceAtlas2                                             layout;

    private final GraphUI                                           graphUi;


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
    }


    /**
     * Adds or updates the feature to the graph.
     * 
     * @param node
     */
    public void addOrUpdateNode( final io.mapzone.arena.analytics.graph.Node node ) {
        if (!nodes.containsKey( node.key() )) {
            nodes.put( node.key(), node );
            addNode( node.key(), node.name() );
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
        io.mapzone.arena.analytics.graph.Edge line = new io.mapzone.arena.analytics.graph.Edge( edgeIdST, src, target );
        addOrUpdateEdge( line );
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
    public void addOrUpdateEdge( final io.mapzone.arena.analytics.graph.Edge edge ) {
        // check for duplicates, because we are undirected
        // add only if not present before
        io.mapzone.arena.analytics.graph.Edge line = edges.get( edge.key() );
        if (line == null) {
            // check other direction
            String edgeIdTS = edge.nodeB().key() + "_" + edge.nodeA().key();
            line = edges.get( edgeIdTS );
            if (line == null) {
                // add original
                edges.put( edge.key(), edge );
                addEdge( edge.key(), edge.nodeA().key(), edge.nodeB().key(), edge.name() );
                graphUi.addEdge( edge );
            }
        }
    }


    private void addNode( String id, String name ) {
        ContainerLoader loader = getContainer().getLoader();
        NodeDraft node = loader.factory().newNodeDraft( id );
        node.setLabel( name );
        loader.addNode( node );
    }


    private void addEdge( String id, String srcId, String targetId, String name ) {
        ContainerLoader loader = getContainer().getLoader();
        EdgeDraft edge = loader.factory().newEdgeDraft( id );
        edge.setSource( loader.getNode( srcId ) );
        edge.setTarget( loader.getNode( targetId ) );
        edge.setDirection( EdgeDirection.DIRECTED );
        if (edge.getSource() == null || edge.getTarget() == null) {
            throw new RuntimeException("empty edge endpoint not supported");
        }
        edge.setLabel( name );
        loader.addEdge( edge );
    }


    private Container getContainer() {
        if (container == null) {
            pc = new ProjectControllerImpl();// .getDefault().lookup(
            // ProjectController.class );
            pc.newProject();
            workspace = pc.getCurrentWorkspace();
            container = new ImportContainerFactoryImpl().newContainer();
            importController = new ImportControllerImpl();
            graphModel = new GraphControllerImpl().getGraphModel( workspace );
            processor = new AppendProcessor();
            layout = new ForceAtlas2( null );
            layout.setGraphModel( graphModel );
            layout.resetPropertiesValues();
            layout.setStrongGravityMode( true );
            layout.setOutboundAttractionDistribution( false );
            layout.setThreadsCount( 3 );
            layout.setEdgeWeightInfluence( 1.0d );
            layout.setGravity( 1.0 );
            layout.setJitterTolerance( 1.0 );
            layout.setScalingRatio( 15.0 );
        }
        return container;
    }

    private Processor processor;


    @Override
    public void layout() {
        try {
            importController.process( getContainer(), processor, workspace );
            // new ExportControllerImpl().exportFile( new File(
            // "/Users/stundzig/gephi_" + System.currentTimeMillis()
            // + ".gexf" ), workspace );
            UIJob job = new UIJob( "Layout graph") {

                @Override
                protected void runWithException( IProgressMonitor monitor ) throws Exception {
                    // final Display display = Display.getCurrent();
                    updateCoordinates( graphModel, layout, /* display, */ 2000 );
                }
            };
            job.schedule();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateCoordinates( final GraphModel graphModel, final ForceAtlas2 layout, final int maxTime ) {
        try {
            log.info( "run algo" );
            final AutoLayout autolayout = new AutoLayout( maxTime, TimeUnit.MILLISECONDS );
            autolayout.addLayout( layout, 1.0f );
            autolayout.setGraphModel( graphModel );
            // Polymap.executorService().submit( () -> {
            log.info( "autolayout.execute " + System.currentTimeMillis() );

            autolayout.execute();
            log.info( "autolayout.execute done " + System.currentTimeMillis() );
            UIThreadExecutor.async( () -> {
                log.info( "sending coordinates " + System.currentTimeMillis() );
                final Graph graph = graphModel.getDirectedGraph();
                double minX = 10000;
                double minY = 10000;
                double maxX = -10000;
                double maxY = -10000;
                for (Node graphNode : graph.getNodes()) {
                    Coordinate newCoordinate = new Coordinate( graphNode.x() * GRAPHUNIT2COORD, graphNode.y()
                            * GRAPHUNIT2COORD );
                    minX = Math.min( minX, newCoordinate.x() );
                    maxX = Math.max( maxX, newCoordinate.x() );
                    minY = Math.min( minY, newCoordinate.y() );
                    maxY = Math.max( maxY, newCoordinate.y() );
                    log.info( "sending coordinate " + graphNode.getId() + ": " + newCoordinate.x() + ";"
                            + newCoordinate.y() );
                    final io.mapzone.arena.analytics.graph.Node featureNode = nodes.get( graphNode.getId() );
                    // if (featureNode == null) {
                    // log.error( "no featureNode with id " + graphNode.getId() );
                    // }
                    // else {
                    graphUi.updateGeometry( featureNode, newCoordinate );
//                    for (Edge graphEdge : graph.getEdges( graphNode )) {
//                        io.mapzone.arena.analytics.graph.Edge line = edges.get( graphEdge.getId() );
//                        int index = line.key().indexOf( featureNode.key() );
//                        log.info( "edge with index " + index );
//                        if (index == -1) {
//                            log.info( "unknown edge" );
//                        }
//                        graphUi.updateGeometry( line, featureNode, newCoordinate );
//                    }
                    // }
                }
                for (Edge graphEdge : graph.getEdges()) {
                    io.mapzone.arena.analytics.graph.Edge line = edges.get( graphEdge.getId() );
                    graphUi.updateGeometry( line, new Coordinate( graphEdge.getSource().x() * GRAPHUNIT2COORD, graphEdge.getSource().y()
                            * GRAPHUNIT2COORD ), new Coordinate( graphEdge.getTarget().x() * GRAPHUNIT2COORD, graphEdge.getTarget().y()
                                    * GRAPHUNIT2COORD ) );
                }
                Extent envelope = new Extent( minX, minY, maxX, maxY );
                graphUi.updateEnvelope( envelope );
                log.info( "setting extent to " + envelope.toJson() );
                log.info( "sending coordinates done." );
                // clear all maps

//                clear();
            } , error -> StatusDispatcher.handleError( "", error ) );

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
        // graphUi.clear();
        nodes.clear();
        edges.clear();
        container = null;
    }


    @Override
    public io.mapzone.arena.analytics.graph.Node getNode( String key ) {
        return nodes.get( key );
    }


    @Override
    public void startGeneration( final GraphFunction function, final MdToolkit tk, final IProgressMonitor monitor )
            throws Exception {
        clear();
        graphUi.startGeneration( function, tk, monitor, this );
    }

}