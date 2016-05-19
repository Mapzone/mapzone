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

/**
 * Placeholder for real layers to support ordering.
 *
 * @author Steffen Stundzig
 */
public class LayerInput {

    private String id;

    private int    priority;


    public LayerInput( final String id, final int priority ) {
        this.id = id;
        this.priority = priority;

    }


    public String id() {
        return id;
    }


    public int priority() {
        return priority;
    }
}
