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
package io.mapzone.arena.analytics.graph.ui;

import java.util.List;
import java.util.Map;

import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.data.util.Geometries;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;
import org.polymap.core.runtime.PlainLazyInit;
import org.polymap.core.runtime.UIJob;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.rap.openlayers.types.Coordinate;
import org.polymap.rap.openlayers.types.Extent;

import io.mapzone.arena.analytics.graph.Edge;
import io.mapzone.arena.analytics.graph.Graph;
import io.mapzone.arena.analytics.graph.GraphFunction;
import io.mapzone.arena.analytics.graph.GraphUI;
import io.mapzone.arena.analytics.graph.Node;

/**
 * Wraps a graph ui implementation. Simply construct a VectorSource in your map.
 * After that create your OlFeatures and the edges between them, by simply adding
 * them to this class.
 * 
 * @author <a href="http://stundzig.it">Steffen Stundzig</a>
 */
public class SimpleFeatureGraphUI
        implements GraphUI {

    public static final Lazy<CoordinateReferenceSystem> CRS             = new PlainLazyInit( () -> {
                                                                            try {
                                                                                return Geometries.crs( "EPSG:3857" );
                                                                            }
                                                                            catch (Exception e) {
                                                                                throw new RuntimeException( e );
                                                                            }
                                                                        } );

    private final static GeometryFactory                GEOMETRYFACTORY = new GeometryFactory();

    private final static Log                            log             = LogFactory.getLog( SimpleFeatureGraphUI.class );

    private final Map<String,SimpleFeature>             nodes           = Maps.newHashMap();

    private final Map<String,SimpleFeature>             edges           = Maps.newHashMap();

    private Lazy<SimpleFeatureType>                     nodeSchema      = new LockedLazyInit();

    private Lazy<SimpleFeatureType>                     edgeSchema      = new LockedLazyInit();

    private final MapViewer                             map;

    private final MdToolkit                             tk;

    private boolean                                     toManyNodesMessageSent;


    /**
     * Creates a graph which updates automatically all features in the vector source
     * and updates also the optimal extent in the map.
     * 
     * @param vector
     * @param map
     */
    public SimpleFeatureGraphUI( final MdToolkit tk, final MapViewer map ) {
        this.tk = tk;
        this.map = map;
    }


    @Override
    public void updateEnvelope( Extent envelope ) {
        map.getMap().view.get().fit( envelope, null );
        map.getMap().view.get().resolution.set( 600f );
        // map.view.get().resolution.set( new Double((Math.abs( maxX ) +
        // Math.abs( minX )) / GRAPHUNIT2COORD).floatValue() );
        log.info( "setting extent to " + envelope.toJson() );
        // }
        log.info( "sending coordinates done." );
        //
        // map.render();
    }


    @Override
    public void addNode( Node node ) {
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder( nodeSchema() );
        builder.set( "geom", GEOMETRYFACTORY.createPoint( new com.vividsolutions.jts.geom.Coordinate( 0, 0 ) ) );
        builder.set( "name", node.name() );
        builder.set( "weight", node.weight() );
        nodes.put( node.key(), builder.buildFeature( node.key() ) );
    }


    @Override
    public void updateGeometry( Node node, Coordinate newCoordinate ) {
        final SimpleFeature feature = nodes.get( node.key() );
        feature.setAttribute( "weight", node.weight() );
        feature.setDefaultGeometry( GEOMETRYFACTORY.createPoint( new com.vividsolutions.jts.geom.Coordinate( newCoordinate.x(), newCoordinate.y() ) ) );
    }


    @Override
    public void addEdge( Edge edge ) {
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder( edgeSchema() );
        builder.set( "geom", GEOMETRYFACTORY.createLineString( new com.vividsolutions.jts.geom.Coordinate[] {
                new com.vividsolutions.jts.geom.Coordinate( 0, 0 ),
                new com.vividsolutions.jts.geom.Coordinate( 0, 0 ) } ) );
        builder.set( "name", edge.name() );
        // builder.set( "weight", edge.weight() );
        edges.put( edge.key(), builder.buildFeature( edge.key() ) );
    }


    @Override
    public void updateGeometry( Edge edge, Node node, Coordinate newCoordinate ) {

        final SimpleFeature line = edges.get( edge.key() );
        LineString geometry = (LineString)line.getDefaultGeometry();
        com.vividsolutions.jts.geom.Coordinate[] coordinates = geometry.getCoordinates();
        int index = (edge.key().startsWith( node.key() )) ? 0 : 1;
        coordinates[index] = new com.vividsolutions.jts.geom.Coordinate( newCoordinate.x(), newCoordinate.y() );
        line.setDefaultGeometry( GEOMETRYFACTORY.createLineString( coordinates ) );
    }


    @Override
    public void clear() {
        nodes.clear();
        edges.clear();
    }


    public SimpleFeatureType nodeSchema() {
        return nodeSchema.get( () -> {
            SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
            builder.setName( "NodeGeometry" );
            builder.add( "geom", Point.class, CRS.get() );
            builder.setDefaultGeometry( "geom" );
            builder.add( "name", String.class );
            builder.add( "weight", Integer.class );
            return builder.buildFeatureType();
        } );
    }


    public SimpleFeatureType edgeSchema() {
        return edgeSchema.get( () -> {
            SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
            builder.setName( "EdgeGeometry" );
            builder.add( "geom", LineString.class, CRS.get() );
            builder.setDefaultGeometry( "geom" );
            builder.add( "name", String.class );
            // builder.add( "weight", Integer.class );
            return builder.buildFeatureType();
        } );
    }


    public List<Feature> nodes() {
        return Lists.newArrayList( nodes.values() );
    }


    public List<Feature> edges() {
        return Lists.newArrayList( edges.values() );
    }


    public CoordinateReferenceSystem crs() {
        return CRS.get();
    }


    @Override
    public void startGeneration( GraphFunction function, MdToolkit tk, IProgressMonitor monitor, Graph graph )
            throws Exception {
        new UIJob( "generate graph" ) {

            @Override
            protected void runWithException( IProgressMonitor monitor ) throws Exception {
                function.generate( tk, monitor, graph );
            }
        }.schedule();
    }
}