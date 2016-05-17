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

import java.util.function.Consumer;

import org.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.mapeditor.MapViewer;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.rap.openlayers.base.OlEvent;
import org.polymap.rap.openlayers.base.OlEventListener;
import org.polymap.rap.openlayers.format.GeoJSONFormat;
import org.polymap.rap.openlayers.interaction.SelectInteraction;
import org.polymap.rap.openlayers.layer.Layer;
import org.polymap.rap.openlayers.layer.VectorLayer;
import org.polymap.rap.openlayers.source.VectorSource;
import org.polymap.rap.openlayers.style.FillStyle;
import org.polymap.rap.openlayers.style.StrokeStyle;
import org.polymap.rap.openlayers.style.Style;
import org.polymap.rap.openlayers.types.Color;

import io.mapzone.arena.analytics.graph.GraphUI;

public class VectorLayerProvider
        implements GraphLayerProvider<VectorLayer>, OlEventListener {

    private static Log             log = LogFactory.getLog( VectorLayerProvider.class );

    private final VectorSource     source;

    private VectorLayer            vector;

    private final MapViewer        mapViewer;

    private final Consumer<String> nodeSelectionHandler;


    public VectorLayerProvider( final MapViewer mapViewer, final Consumer<String> nodeSelectionHandler ) {
        this.mapViewer = mapViewer;
        this.nodeSelectionHandler = nodeSelectionHandler;
        this.source = new VectorSource().format.put( new GeoJSONFormat() );
    }


    private void init() {
        SelectInteraction selectInteraction = new SelectInteraction( (VectorLayer)getLayer( null ) );

        selectInteraction.addEventListener( SelectInteraction.Event.select, this );
        mapViewer.addMapInteraction( selectInteraction );
    }


    @Override
    public Layer getLayer( VectorLayer elm ) {
        if (vector == null) {
            vector = new VectorLayer().style.put( new Style().fill.put( new FillStyle().color.put( new Color( 0, 0, 255, 0.1f ) ) ).stroke.put( new StrokeStyle().color.put( new Color( "red" ) ).width.put( 1f ) ) ).source.put( source );
        }
        return vector;
    }


    @Override
    public int getPriority( VectorLayer elm ) {
        return 0;
    }


    @Override
    public GraphUI createGraphUi( final MdToolkit tk ) {
        return new OlFeatureGraphUI( tk, source, mapViewer.getMap() );
    }


    @Override
    public void handleEvent( OlEvent event ) {
        log.info( "Selected: " + event.properties().get( "selected" ).toString() );
        JSONArray ids = event.properties().getJSONArray( "selected" );
        if (ids != null && ids.length() > 0) {
            String id = ids.getString( 0 );
            nodeSelectionHandler.accept( id );
        }
    }
}
