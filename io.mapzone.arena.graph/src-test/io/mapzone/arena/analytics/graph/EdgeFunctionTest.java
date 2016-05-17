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
        
        final ArrayListMultimap<Object,Node> edgesByKeyProperty = ArrayListMultimap.create();
        edgesByKeyProperty.put( "k1", new Node("k1_1" , null, null, null, 1));
        edgesByKeyProperty.put( "k1", new Node("k1_2" , null, null, null, 1));
        edgesByKeyProperty.put( "k1", new Node("k1_3" , null, null, null, 1));
        edgesByKeyProperty.put( "k1", new Node("k1_4" , null, null, null, 1));
        edgesByKeyProperty.put( "k2", new Node("k2_1" , null, null, null, 1));
        edgesByKeyProperty.put( "k2", new Node("k2_2" , null, null, null, 1));
        edgesByKeyProperty.put( "k3", new Node("k3_1" , null, null, null, 1));
        edgesByKeyProperty.put( "k4", new Node("k4_1" , null, null, null, 1));
        edgesByKeyProperty.put( "k4", new Node( "k4_2", null, null, null, 1 ) );
        
        List<Edge> edges = (List<Edge>)new CompareColumnEdgeFunction().transform( edgesByKeyProperty );
        assertEquals( 8, edges.size() );
        assertEquals( "k1_1", edges.get( 0 ).nodeA().key() );
        assertEquals( "k1_2", edges.get( 0 ).nodeB().key() );
        assertEquals( "k1_1", edges.get( 2 ).nodeA().key() );
        assertEquals( "k1_4", edges.get( 2 ).nodeB().key() );
        assertEquals( "k4_1", edges.get( 7 ).nodeA().key() );
        assertEquals( "k4_2", edges.get( 7 ).nodeB().key() );
    }
}
