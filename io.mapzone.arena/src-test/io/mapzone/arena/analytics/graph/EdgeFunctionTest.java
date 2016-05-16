package io.mapzone.arena.analytics.graph;
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

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.geotools.feature.simple.SimpleFeatureImpl;
import org.geotools.filter.identity.FeatureIdImpl;
import org.junit.Test;
import org.opengis.feature.Feature;

import com.google.common.collect.ArrayListMultimap;

/**
 * @author Steffen Stundzig
 */
public class EdgeFunctionTest {

    @Test
    public void testTransform() {
        
        final ArrayListMultimap<Object,Feature> edgesByKeyProperty = ArrayListMultimap.create();
        edgesByKeyProperty.put( "k1", new SimpleFeatureImpl( null, null, new FeatureIdImpl( "k1_1" ), false, null ) );
        edgesByKeyProperty.put( "k1", new SimpleFeatureImpl( null, null, new FeatureIdImpl( "k1_2" ), false, null ) );
        edgesByKeyProperty.put( "k1", new SimpleFeatureImpl( null, null, new FeatureIdImpl( "k1_3" ), false, null ) );
        edgesByKeyProperty.put( "k1", new SimpleFeatureImpl( null, null, new FeatureIdImpl( "k1_4" ), false, null ) );
        edgesByKeyProperty.put( "k2", new SimpleFeatureImpl( null, null, new FeatureIdImpl( "k2_1" ), false, null ) );
        edgesByKeyProperty.put( "k2", new SimpleFeatureImpl( null, null, new FeatureIdImpl( "k2_2" ), false, null ) );
        edgesByKeyProperty.put( "k3", new SimpleFeatureImpl( null, null, new FeatureIdImpl( "k3_1" ), false, null ) );
        edgesByKeyProperty.put( "k4", new SimpleFeatureImpl( null, null, new FeatureIdImpl( "k4_1" ), false, null ) );
        edgesByKeyProperty.put( "k4", new SimpleFeatureImpl( null, null, new FeatureIdImpl( "k4_2" ), false, null ) );
        
        List<Edge> edges = (List<Edge>)new CompareColumnEdgeFunction().transform( edgesByKeyProperty );
        assertEquals( 8, edges.size() );
        assertEquals( "k1_1", edges.get( 0 ).featureA().getIdentifier().getID() );
        assertEquals( "k1_2", edges.get( 0 ).featureB().getIdentifier().getID() );
        assertEquals( "k1_1", edges.get( 2 ).featureA().getIdentifier().getID() );
        assertEquals( "k1_4", edges.get( 2 ).featureB().getIdentifier().getID() );
        assertEquals( "k4_1", edges.get( 7 ).featureA().getIdentifier().getID() );
        assertEquals( "k4_2", edges.get( 7 ).featureB().getIdentifier().getID() );
    }
}
