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

import java.util.Set;

import org.geotools.data.FeatureSource;
import org.opengis.feature.Feature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Sets;

/**
 * a single node
 * 
 *
 * @author Steffen Stundzig
 */
public class Node {

    // node could be a real node or a *synthetic* node which represents an edge
    public enum Type {
        real, virtual;
    }

    private static Log          log        = LogFactory.getLog( Node.class );

    private final FeatureSource featureSource;

    private final Feature       feature;

    private final String        key;

    private final Type          type;

    private int                 weight;

    private final String        name;

    private Set<Node>           neighbours = Sets.newHashSet();


    public Node( final Type type, final String key, final FeatureSource featureSource, final Feature feature,
            final String name,
            final int weight ) {
        this.type = type;
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


    public Type type() {
        return type;
    }


    public void increaseWeight() {
        weight++;
    }


    public int weight() {
        return weight;
    }


    void addNeighbour( Node neighbour ) {
        neighbours.add( neighbour );
    }


    public Set<Node> neighbours() {
        return neighbours;
    }
}
