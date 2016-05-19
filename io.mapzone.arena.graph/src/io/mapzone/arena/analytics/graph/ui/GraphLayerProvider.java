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
package io.mapzone.arena.analytics.graph.ui;

import java.util.Set;

import org.geotools.geometry.jts.ReferencedEnvelope;

import org.polymap.core.mapeditor.ILayerProvider;
import org.polymap.core.project.ILayer;

import io.mapzone.arena.analytics.graph.GraphUI;

/**
 * Base interface for layer provider to show the graph ui.
 * 
 * @author Steffen Stundzig
 */
public interface GraphLayerProvider<CL>
        extends ILayerProvider<CL> {

    GraphUI graphUi();

    ReferencedEnvelope referenceEnvelope();

    Set<ILayer> layers();
    
    void dispose();
}
