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
package io.mapzone.arena.analytics.graph;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;

/**
 * Abstract base class for edge functions.
 *
 * @author Steffen Stundzig
 */
public abstract class AbstractEdgeFunction
        implements EdgeFunction {

    private static Log log = LogFactory.getLog( EdgeFunctionTest.class );


    protected Collection<Edge> transform( final ArrayListMultimap<Object,Node> edgesByKeyProperty ) {

        Collection<Edge> allEdges = Lists.newArrayList();

        // now iterate on all edgesByKeyProperty and select all with more than one
        // value
        for (Object key : edgesByKeyProperty.keySet()) {
            List<Node> bundledFeatures = edgesByKeyProperty.get( key );
            if (bundledFeatures != null && bundledFeatures.size() > 1) {
                // step into the features and create an edge for each pair
                int allFeaturesCount = bundledFeatures.size();
                for (int aFeatureCount = 0; aFeatureCount < allFeaturesCount; aFeatureCount++) {
                    Node featureA = bundledFeatures.get( aFeatureCount );
                    for (int bFeatureCount = aFeatureCount + 1; bFeatureCount < allFeaturesCount; bFeatureCount++) {
                        Node featureB = bundledFeatures.get( bFeatureCount );
                        allEdges.add( new Edge( key.toString() + "_" + featureA.key() + "_" + featureB.key(), featureA, featureB ) );
                    }
                }
            }
        }
        return allEdges;
    }
}
