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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.rap.openlayers.base.OlFeature;
import org.polymap.rap.openlayers.base.OlMap;
import org.polymap.rap.openlayers.geom.LineStringGeometry;
import org.polymap.rap.openlayers.geom.PointGeometry;
import org.polymap.rap.openlayers.source.VectorSource;
import org.polymap.rap.openlayers.style.Base;
import org.polymap.rap.openlayers.style.StrokeStyle;
import org.polymap.rap.openlayers.style.Style;
import org.polymap.rap.openlayers.style.StyleFunction;
import org.polymap.rap.openlayers.types.Color;
import org.polymap.rap.openlayers.types.Coordinate;
import org.polymap.rap.openlayers.types.Extent;

import com.google.common.collect.Maps;

import io.mapzone.arena.analytics.graph.Edge;
import io.mapzone.arena.analytics.graph.GraphUI;
import io.mapzone.arena.analytics.graph.Node;

/**
 * Wraps a graph ui implementation. Simply construct a VectorSource in your map.
 * After that create your OlFeatures and the edges between them, by simply adding
 * them to this class.
 * 
 * @author <a href="http://stundzig.it">Steffen Stundzig</a>
 */
public class OlFeatureGraphUI
        implements GraphUI {

    private final static Log log = LogFactory.getLog( OlFeatureGraphUI.class );

    private final VectorSource vector;

    private final Map<String,OlFeature> nodes = Maps.newHashMap();

    private final Map<String,OlFeature> edges = Maps.newHashMap();
    
    private static final int                  MAXNODES           = 20000;

    private final OlMap map;

    private final Style edgeStyle = new Style().stroke
            .put( new StrokeStyle().color.put( new Color( "black" ) ).width.put( 1f ) ).zIndex.put( 0f );

    private final MdToolkit tk;

    private boolean toManyNodesMessageSent;


    /**
     * Creates a graph which updates automatically all features in the vector source
     * and updates also the optimal extent in the map.
     * 
     * @param vector
     * @param map
     */
    public OlFeatureGraphUI( final MdToolkit tk, final VectorSource vector, final OlMap map ) {
        this.tk = tk;
        this.vector = vector;
        this.map = map;
    }


    @Override
    public void updateEnvelope( Extent envelope ) {
        map.view.get().fit( envelope, null );
        map.view.get().resolution.set( 600f );
        // map.view.get().resolution.set( new Double((Math.abs( maxX ) +
        // Math.abs( minX )) / GRAPHUNIT2COORD).floatValue() );
        log.info( "setting extent to " + envelope.toJson() );
        // }
        log.info( "sending coordinates done." );
    }


    @Override
    public void updateGeometry( Edge edge, Node node, Coordinate newCoordinate ) {
        OlFeature line = edges.get( edge.key() );
        LineStringGeometry geometry = ((LineStringGeometry)line.geometry.get());
        List<Coordinate> coordinates = geometry.coordinates.get();
        coordinates.set( (edge.key().startsWith( node.key() )) ? 0 : 1, newCoordinate );
        geometry.coordinates.set( coordinates );
    }


    @Override
    public void updateGeometry( Node node, Coordinate newCoordinate ) {
        final OlFeature olFeature = nodes.get( node.key() );
        olFeature.style.set( featureStyle( node.weight() ) );
        ((PointGeometry)olFeature.geometry.get()).coordinate.set( newCoordinate );
    }


    @Override
    public void addEdge( Edge edge ) {
        if (checkSizes()) {
            OlFeature line = new OlFeature( edge.key() ).geometry.put( new LineStringGeometry( new Coordinate( 0.0, 0.0 ), new Coordinate( 0.0, 0.0 ) ) ).style.put( edgeStyle );
            edges.put( edge.key(), line );
            vector.addFeature( line );
        }
    }


    private boolean checkSizes() {
        int size = edges.size() + nodes.size();
        if (size > MAXNODES) {
            if (!toManyNodesMessageSent) {
                toManyNodesMessageSent = true;
                tk.createSnackbar( Appearance.FadeIn, "Currently only " + MAXNODES + " nodes and edges supported, skipping the rest" );
                log.info( "Currently only " + MAXNODES + " nodes and edges supported, skipping the rest" );
            }
            return false;
        }
        return true;
    }


    @Override
    public void addNode( Node node ) {
        if (checkSizes()) {
            OlFeature olFeature = new OlFeature( node.key() ).name.put( node.name() ).geometry.put( new PointGeometry( new Coordinate( 0.0, 0.0 ) ) ).style.put( featureStyle( 1 ) );
            nodes.put( node.key(), olFeature );
            vector.addFeature( olFeature );
        }
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
        sb.append( "      color: 'black'" );
        sb.append( "    })" );
        sb.append( "  })" );
        sb.append( "})];" );
        return sb.toString();
    }


    @Override
    public void clear() {
        vector.clear();
        nodes.clear();
        edges.clear();
        toManyNodesMessageSent = false;
    }
}