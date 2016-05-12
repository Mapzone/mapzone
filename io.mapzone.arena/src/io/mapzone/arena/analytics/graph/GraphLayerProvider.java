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
import org.polymap.core.mapeditor.ILayerProvider;
import org.polymap.rap.openlayers.layer.Layer;
import org.polymap.rap.openlayers.layer.VectorLayer;
import org.polymap.rap.openlayers.source.VectorSource;
import org.polymap.rap.openlayers.style.FillStyle;
import org.polymap.rap.openlayers.style.StrokeStyle;
import org.polymap.rap.openlayers.style.Style;
import org.polymap.rap.openlayers.types.Color;

public class GraphLayerProvider
        implements ILayerProvider<VectorLayer> {

    private static Log         log = LogFactory.getLog( GraphLayerProvider.class );

    private final VectorSource source;

    private VectorLayer vector;


    public GraphLayerProvider( final VectorSource source ) {
        this.source = source;
    }


    @Override
    public Layer getLayer( VectorLayer elm ) {
        if (vector == null) {
            vector = new VectorLayer().style
                .put( new Style().fill.put( new FillStyle().color.put( new Color( 0, 0, 255, 0.1f ) ) ).stroke
                        .put( new StrokeStyle().color.put( new Color( "red" ) ).width.put( 1f ) ) ).source
                                .put( source );
        }
        return vector;
    }


    @Override
    public int getPriority( VectorLayer elm ) {
        return 0;
    }
}
