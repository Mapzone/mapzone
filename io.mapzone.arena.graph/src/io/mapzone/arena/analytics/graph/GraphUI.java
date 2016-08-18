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

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.type.FeatureType;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.rap.openlayers.types.Coordinate;
import org.polymap.rap.openlayers.types.Extent;

/**
 * Base interface for graph UIs e.g. OlFeature based or image layer based.
 * 
 * @author Steffen Stundzig
 */
public interface GraphUI {

    void updateEnvelope( Extent envelope );


    void updateGeometry( Edge line, Node node, Coordinate newCoordinate );


    void updateGeometry( Edge line, Coordinate srcCoordinate, Coordinate targetCoordinate );


    void updateGeometry( Node featureNode, Coordinate newCoordinate );


    void addEdge( Edge edge );


    void addNode( Node node );


    void clear();


    void startGeneration( final GraphFunction function, final MdToolkit tk, final IProgressMonitor monitor,
            final Graph graph ) throws Exception;


    FeatureType nodeSchema();

    FeatureCollection nodeCollection();

    FeatureType edgeSchema();
    
    FeatureCollection edgeCollection();

}
