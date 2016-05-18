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

import java.util.List;
import java.util.function.Consumer;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.json.JSONArray;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;

import org.polymap.core.data.util.Geometries;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.project.ILayer;
import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.PlainLazyInit;

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
        implements GraphLayerProvider<ILayer>, OlEventListener {

    private static Log                                  log = LogFactory.getLog( VectorLayerProvider.class );

    public static final Lazy<CoordinateReferenceSystem> CRS = new PlainLazyInit( () -> {
                                                                try {
                                                                    return Geometries.crs( "EPSG:3857" );
                                                                }
                                                                catch (Exception e) {
                                                                    throw new RuntimeException( e );
                                                                }
                                                            } );

    private final VectorSource                          source;

    private VectorLayer                                 vector;

    private final MapViewer                             mapViewer;

    private final Consumer<String>                      nodeSelectionHandler;

    private final ILayer                                baseLayer;

    private final MdToolkit                             tk;


    public VectorLayerProvider( final MdToolkit tk, final MapViewer mapViewer,
            final ILayer baseLayer, final Consumer<String> nodeSelectionHandler ) {
        this.tk = tk;
        this.mapViewer = mapViewer;
        this.baseLayer = baseLayer;
        this.nodeSelectionHandler = nodeSelectionHandler;
        this.source = new VectorSource().format.put( new GeoJSONFormat() );
    }


    @Override
    public Layer getLayer( ILayer elm ) {
        if (vector == null) {
            vector = new VectorLayer().style.put( new Style().fill.put( new FillStyle().color.put( new Color( 0, 0, 255, 0.1f ) ) ).stroke.put( new StrokeStyle().color.put( new Color( "red" ) ).width.put( 1f ) ) ).source.put( source );
            
            SelectInteraction selectInteraction = new SelectInteraction( vector );
            selectInteraction.addEventListener( SelectInteraction.Event.select, this );
            mapViewer.addMapInteraction( selectInteraction );
        }
        return vector;
    }


    @Override
    public int getPriority( ILayer elm ) {
        return 0;
    }


    @Override
    public GraphUI graphUi() {
        return new OlFeatureGraphUI( tk, source, mapViewer );
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


    @Override
    public ReferencedEnvelope referenceEnvelope() {
        return new ReferencedEnvelope( -10000, -10000, 10000, 10000, CRS.get() );
    }


    @Override
    public List<ILayer> layers() {
        return Lists.newArrayList( baseLayer );
    }


    @Override
    public void dispose() {
    }
}
