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

import org.eclipse.core.runtime.IProgressMonitor;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

/**
 * The graph backend itself.
 * 
 * @author Steffen Stundzig
 */
public interface Graph {

    void addOrUpdateNode( final Node node );


    void addOrUpdateEdge( final Node a, final Node b );


    void addOrUpdateEdge( final Edge edge );


    void layout();


    void clear();


    Node getNode( String key );


    void startGeneration( final GraphFunction function, final MdToolkit tk, IProgressMonitor nullProgressMonitor )
            throws Exception;
}
