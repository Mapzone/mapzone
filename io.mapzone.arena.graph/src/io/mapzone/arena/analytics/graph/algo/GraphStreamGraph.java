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

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicEdge;
import org.graphstream.ui.graphicGraph.GraphicNode;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.Viewer;

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
public class GraphStreamGraph
        implements io.mapzone.arena.analytics.graph.Graph {

    private final static Log                                        log              = LogFactory.getLog( GraphStreamGraph.class );

    private static final double                                     GRAPHUNIT2COORD  = 10000;

    private static final long                                       REFRESH_INTERVAL = 500;

    private final Map<String,io.mapzone.arena.analytics.graph.Node> nodes            = Maps.newHashMap();

    private final Map<String,io.mapzone.arena.analytics.graph.Edge> edges            = Maps.newHashMap();

    private org.graphstream.graph.Graph                             graph;

    private final GraphUI                                           graphUi;

    private Viewer                                                  viewer;

    private Layout                                                  layout;


    /**
     * Creates a graph which updates automatically all features in the vector source
     * and updates also the optimal extent in the map.
     * 
     * @param vector
     * @param map
     */
    public GraphStreamGraph( final GraphUI graphUi ) {
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
            addNode( node.key() );
            graphUi.addNode( node );
        }
        // getInternalGraph().getNode( node.key() ).addAttribute( "layout.weight",
        // weight );

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
                addEdge( edge.key(), edge.nodeA().key(), edge.nodeB().key() );
                graphUi.addEdge( edge );
            }
        }
    }


    private void addNode( String id ) {
        getInternalGraph().addNode( id );
    }


    private void addEdge( String id, String srcId, String targetId ) {
        getInternalGraph().addEdge( id, srcId, targetId );
    }


    private org.graphstream.graph.Graph getInternalGraph() {
        if (graph == null) {
            graph = new MultiGraph( "" );
            viewer = new Viewer( graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
            layout = new SpringBox( false );// Eades84Layout();//HierarchicalLayout();//
                                            // //new
                                            // SpringBox( false );//
                                            // LinLog(false);//.newLayoutAlgorithm();
            layout.setStabilizationLimit( 0.8d );
            graph.addSink( layout );
            layout.addAttributeSink( graph );
            // viewer.enableAutoLayout( layout );
        }
        return graph;
    }


    @Override
    public void layout() {
        try {
            UIJob job = new UIJob( "Layout graph" ) {

                @Override
                protected void runWithException( IProgressMonitor monitor ) throws Exception {
                    updateCoordinates( layout, 50 );
                }
            };
            job.schedule();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateCoordinates( final Layout layout, final int maxSteps ) {
        try {
            log.info( "run algo" );
            int iteration = 0;
            while (layout.getStabilization() <= layout.getStabilizationLimit() && iteration <= 10) {
                iteration++;
                int currentStep = 0;
                while (layout.getStabilization() <= layout.getStabilizationLimit() && currentStep <= maxSteps) {
                    currentStep++;
                    layout.compute();
                }
                log.info( "step:quality:stabilization" + layout.getSteps() + ":" + layout.getQuality() + ":"
                        + layout.getStabilization() );
                UIThreadExecutor.syncFast( () -> {
//                    viewer.disableAutoLayout();
                    // log.info( "sending coordinates " + System.currentTimeMillis()
                    // );
                    graph.getNode( 0 ).getAttribute( "x" );
                    for (Node graphNode : viewer.getGraphicGraph().getNodeSet()) {
                        GraphicNode graphicNode = (GraphicNode)graphNode;
                        Coordinate newCoordinate = new Coordinate( graphicNode.getX()
                                * GRAPHUNIT2COORD, graphicNode.getY()
                                        * GRAPHUNIT2COORD );
                        // log.info( "sending coordinate " + graphNode.getId() + ": "
                        // + newCoordinate.x() + ";"
                        // + newCoordinate.y() );
                        final io.mapzone.arena.analytics.graph.Node featureNode = nodes.get( graphNode.getId() );
                        graphUi.updateGeometry( featureNode, newCoordinate );
                    }
                    for (Edge graphEdge : viewer.getGraphicGraph().getEdgeSet()) {
                        GraphicEdge graphicEdge = (GraphicEdge)graphEdge;
                        io.mapzone.arena.analytics.graph.Edge line = edges.get( graphEdge.getId() );
                        GraphicNode sourceNode = (GraphicNode)graphEdge.getSourceNode();
                        GraphicNode targetNode = (GraphicNode)graphEdge.getTargetNode();
                        graphUi.updateGeometry( line, new Coordinate( sourceNode.getX()
                                * GRAPHUNIT2COORD, sourceNode.getY()
                                        * GRAPHUNIT2COORD ), new Coordinate( targetNode.getX()
                                                * GRAPHUNIT2COORD, targetNode.getY()
                                                        * GRAPHUNIT2COORD ) );
                    }
                    Point3 max = viewer.getGraphicGraph().getMaxPos();
                    Point3 min = viewer.getGraphicGraph().getMinPos();
                    Extent envelope = new Extent( min.x * GRAPHUNIT2COORD, min.y * GRAPHUNIT2COORD, max.x
                            * GRAPHUNIT2COORD, max.y * GRAPHUNIT2COORD );
                    graphUi.updateEnvelope( envelope );
                    // log.info( "setting extent to " + envelope.toJson() );
                    // log.info( "sending coordinates done." );
//                    viewer.enableAutoLayout( layout );
                }, error -> StatusDispatcher.handleError( "", error ) );

                // Thread.sleep( 2000 );
            }
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
        graph = null;
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