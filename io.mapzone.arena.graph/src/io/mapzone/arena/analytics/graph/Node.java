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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geotools.data.FeatureSource;
import org.opengis.feature.Feature;

/**
 * a single node
 * 
 *
 * @author Steffen Stundzig
 */
public class Node {

    private static Log log = LogFactory.getLog( Node.class );

    private final FeatureSource featureSource;

    private final Feature feature;

    private final String key;

    private int weight;

    private final String name;


    public Node( final String key, final FeatureSource featureSource, final Feature feature, final String name,
            final int weight ) {
        this.key = key;
        this.featureSource = featureSource;
        this.feature = feature;
        this.name = name;
        this.weight = weight;
    }


    public FeatureSource featureSource() {
        return featureSource;
    }


    public Feature feature() {
        return feature;
    }


    public String key() {
        return key;
    }


    public String name() {
        return name;
    }


    public void increaseWeight() {
        weight++;
    }


    public int weight() {
        return weight;
    }
}
