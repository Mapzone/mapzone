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
package io.mapzone.arena.share;

import java.util.List;

import org.opengis.feature.Feature;
import org.opengis.filter.Filter;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Envelope;

import org.polymap.core.project.ILayer;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;

/**
 * Context to transport feature selections from different layers and the current
 * bounding box to the sharelets.
 * 
 *
 * @author Steffen Stundzig
 */
public class SharePanelContext
        extends Configurable {

    public Config<Envelope>                  boundingBox;

    public Config<Float>                     resolution;

    public Config<List<SelectionDescriptor>> selectionDescriptors;

    public Config<CoordinateReferenceSystem> crs;


    public SharePanelContext() {
        super();
        this.selectionDescriptors.set( Lists.newArrayList() );
    }
    //
    // public SharePanelContext( Envelope mapExtent ) {
    // this.mapExtent.set( mapExtent );
    // }


    public static class SelectionDescriptor
            extends Configurable {

        public SelectionDescriptor( ILayer layer ) {
            this.layer.set( layer );
        }

        public Config<ILayer>  layer;

        public Config<Filter>  filter;

        public Config<Feature> singleFeature;
    }


    public void add( SelectionDescriptor selectionDescriptor ) {
        selectionDescriptors.get().add( selectionDescriptor );
    }

    //
    // public String baseUrl() {
    // return baseUrl;
    // }

}
