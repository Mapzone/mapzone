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

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opengis.feature.Feature;

/**
 * a single edge.
 * 
 *
 * @author Steffen Stundzig
 */
public class Edge {

    private static Log log = LogFactory.getLog( Edge.class );
    
    private Feature featureA;
    private Feature featureB;

    // private Map<String, String> properties;
    private String key;
    
    
    public Edge( String key, Feature featureA, Feature featureB ) {
        this.key = key;
        this.featureA = featureA;
        this.featureB = featureB;
    }

    public Feature featureA() {
        return featureA;
    }
    
    public Feature featureB() {
        return featureB;
    }
//    
//    public Map<String,String> properties() {
//        return properties;
//    }
    
    public String key() {
        return key;
    }
} 
