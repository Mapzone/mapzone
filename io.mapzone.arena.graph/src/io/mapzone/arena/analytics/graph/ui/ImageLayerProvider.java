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

import static org.polymap.p4.layer.FeatureLayer.ff;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.styling.Style;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import org.polymap.core.data.feature.FeatureRenderProcessor2;
import org.polymap.core.data.image.EncodedImageProducer;
import org.polymap.core.data.pipeline.DataSourceDescriptor;
import org.polymap.core.data.pipeline.Pipeline;
import org.polymap.core.data.pipeline.PipelineProcessorSite;
import org.polymap.core.data.pipeline.PipelineProcessorSite.Params;
import org.polymap.core.data.pipeline.ProcessorDescriptor;
import org.polymap.core.data.util.Geometries;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.mapeditor.services.SimpleWmsServer;
import org.polymap.core.project.ILayer;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.p4.P4Plugin;
import org.polymap.p4.catalog.AllResolver;
import org.polymap.p4.data.P4PipelineBuilder;
import org.polymap.rap.openlayers.base.OlEvent;
import org.polymap.rap.openlayers.base.OlEventListener;
import org.polymap.rap.openlayers.base.OlMap.Event;
import org.polymap.rap.openlayers.layer.Layer;
import org.polymap.rap.openlayers.layer.TileLayer;
import org.polymap.rap.openlayers.source.TileWMSSource;
import org.polymap.rap.openlayers.source.WMSRequestParams;

import io.mapzone.arena.analytics.graph.GraphUI;

public class ImageLayerProvider
        implements GraphLayerProvider<LayerInput> {

    private final static Log           log            = LogFactory.getLog( ImageLayerProvider.class );

    private final MapViewer            mapViewer;

    private final Consumer<String>     nodeSelectionHandler;

    private Set<String>                servletAliases = Sets.newHashSet();

    private final MdToolkit            tk;

    private final SimpleFeatureGraphUI graphUi;

    // only used for constructing the pipelines
    private final ILayer               baseLayer;

    private final String               nodeStyleId;

    private final String               edgeStyleId;


    public ImageLayerProvider( final MdToolkit tk, final MapViewer mapViewer,
            final ILayer baseLayer, final Consumer<String> nodeSelectionHandler, final String nodeStyleId,
            final String edgeStyleId ) {
        this.tk = tk;
        this.mapViewer = mapViewer;
        this.baseLayer = baseLayer;
        this.nodeSelectionHandler = nodeSelectionHandler;
        this.edgeStyleId = edgeStyleId;
        this.nodeStyleId = nodeStyleId;
        this.graphUi = new SimpleFeatureGraphUI( tk, mapViewer );
    }

    private OlEventListener clickListener;


    // should be called twice
    @Override
    public Layer getLayer( LayerInput layer ) {
        if (layer.id().equals( "edges" )) {
            return getLayer0( edgeStyleId, false, "edges" );
        }
        else {
            return getLayer0( nodeStyleId, true, "nodes" );
        }
    }


    private Layer getLayer0( final String styleId, final boolean isNodesLayer,
            final String servletPath ) {
        try {
            // XXX don't connect again (use some cache on layer)
            DataSourceDescriptor dsd = AllResolver.instance().connectLayer( baseLayer, null ).orElseThrow( () -> new RuntimeException( "No data source for layer: "
                    + baseLayer ) );

            // create pipeline for it
            // XXX do not use layer specific things like caching; build extra
            // pipeline
            Supplier<Style> styleSupplier = () -> {
                return P4Plugin.styleRepo().serializedFeatureStyle( styleId, Style.class ).get();
            };

            P4PipelineBuilder builder = P4PipelineBuilder.forLayer( baseLayer );
            FeatureRenderProcessor2.STYLE_SUPPLIER.rawput( builder, styleSupplier );
            Pipeline pipeline = builder.createPipeline( EncodedImageProducer.class, dsd )
                    .orElseThrow( () -> new RuntimeException( "Unable to build pipeline for: " + dsd ) );

            // inject ChartGeometryProcessor
            FeatureRenderProcessor2 featureRenderProc = (FeatureRenderProcessor2)pipeline.get( pipeline.length()-1).processor();
            Params params = new Params();
            params.put( "graphUi", graphUi );
            params.put( "isNodesLayer", isNodesLayer );
            ProcessorDescriptor proc = new ProcessorDescriptor( GraphGeometryProcessor.class, params );
            PipelineProcessorSite procSite = new PipelineProcessorSite( params );
            proc.processor().init( procSite );
            featureRenderProc.pipeline().add( 0, proc );
            log.info( "FeatureRender pipeline: " + featureRenderProc.pipeline() );

            // register WMS servlet
            String servletAlias = "/" + servletPath + hashCode();
            P4Plugin.instance().httpService().registerServlet( servletAlias, new SimpleWmsServer() {

                @Override
                protected String[] layerNames() {
                    return new String[] { servletPath };
                }


                @Override
                protected Pipeline createPipeline( String layerName ) {
                    return pipeline;
                }
            }, null, null );
            servletAliases.add( servletAlias );

            if (isNodesLayer) {
                clickListener = new OlEventListener() {

                    @Override
                    public void handleEvent( OlEvent ev ) {
                        try {
                            // FIXME
                            throw new RuntimeException( "Handling of Event/Payload has been changed! See code." );
//                            log.info( "event: " + ev.properties() );
//                            JSONArray coord = ev.properties().getJSONObject( "feature" ).getJSONArray( "coordinate" );
//                            double x = coord.getDouble( 0 );
//                            double y = coord.getDouble( 1 );
//                            handleClick( graphUi, new Coordinate( x, y ) );
                        }
                        catch (Exception e) {
                            StatusDispatcher.handleError( "Unable to create graph layer.", e );
                        }
                    }
                };
                mapViewer.map().addEventListener( Event.CLICK, clickListener );
            }

            return new TileLayer().source.put( new TileWMSSource().url.put( "."
                    + servletAlias ).params.put( new WMSRequestParams().version.put( "1.1.1" ).layers.put( servletPath ).format.put( "image/png" ) ) );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "Unable to create graph layer.", e );
            throw new RuntimeException( e );
        }
    }


    @Override
    public int getPriority( LayerInput elm ) {
        return elm.priority();
    }


    @Override
    public GraphUI graphUi() {
        return graphUi;
    }


    @Override
    public ReferencedEnvelope referenceEnvelope() {
        return new ReferencedEnvelope( -10000, -10000, 10000, 10000, graphUi.crs() );
    }


    @Override
    public Set<LayerInput> layers() {
        return Sets.newHashSet( new LayerInput("nodes", 3), new LayerInput("edges", 2) );
    }


    @Override
    public void dispose() {
        try {
            servletAliases.forEach( alias -> P4Plugin.instance().httpService().unregister( alias ) );
            servletAliases.clear();
            graphUi.clear();
        }
        catch (Exception e) {
            log.warn( "", e );
        }

    }


    private void handleClick( final SimpleFeatureGraphUI graphUi, final Coordinate coordinate ) throws Exception {
        GeometryFactory gf = new GeometryFactory();

        Point point = gf.createPoint( coordinate );

        // buffer: 500m
        double buffer = 50;
        Point norm = Geometries.transform( point, mapViewer.getMapCRS(), Geometries.crs( "EPSG:3857" ) );
        ReferencedEnvelope buffered = new ReferencedEnvelope( norm.getX() - buffer, norm.getX() + buffer, norm.getY()
                - buffer, norm.getY() + buffer, Geometries.crs( "EPSG:3857" ) );

        // transform -> dataCrs
        CoordinateReferenceSystem dataCrs = graphUi.crs();
        buffered = buffered.transform( dataCrs, true );

        // get feature
        Filter filter = ff.intersects( ff.property( "" ), ff.literal( JTS.toGeometry( (Envelope)buffered ) ) );
        List<SimpleFeature> selected = Lists.newArrayList();
        for (Feature feature : graphUi.nodes()) {
            if (filter.evaluate( feature )) {
                selected.add( (SimpleFeature)feature );
            }
        }
        if (selected.isEmpty()) {
            return; // nothing found
        }
        if (selected.size() > 1) {
            log.info( "Multiple features found: " + selected.size() );
        }
        // Feature any = (Feature)Features.stream( selected ).findAny().get();
        // featureSelection.get().setClicked( any );
        log.info( "clicked: " + selected.get( 0 ) + " with key " + (String)selected.get( 0 ).getAttribute( "key" ) );
        nodeSelectionHandler.accept( (String)selected.get( 0 ).getAttribute( "key" ) );
    }
}
