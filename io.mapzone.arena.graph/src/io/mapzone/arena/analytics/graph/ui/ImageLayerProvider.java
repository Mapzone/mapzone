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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.polymap.core.data.feature.FeatureRenderProcessor2;
import org.polymap.core.data.image.EncodedImageProducer;
import org.polymap.core.data.pipeline.DataSourceDescription;
import org.polymap.core.data.pipeline.Pipeline;
import org.polymap.core.data.pipeline.PipelineProcessorSite;
import org.polymap.core.data.pipeline.ProcessorDescription;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.mapeditor.services.SimpleWmsServer;
import org.polymap.core.project.ILayer;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.p4.P4Plugin;
import org.polymap.p4.catalog.LocalResolver;
import org.polymap.p4.data.P4PipelineIncubator;
import org.polymap.rap.openlayers.layer.ImageLayer;
import org.polymap.rap.openlayers.layer.Layer;
import org.polymap.rap.openlayers.source.ImageWMSSource;
import org.polymap.rap.openlayers.source.WMSRequestParams;

import io.mapzone.arena.analytics.graph.GraphUI;

public class ImageLayerProvider
        implements GraphLayerProvider<ILayer> {

    private final static Log           log            = LogFactory.getLog( ImageLayerProvider.class );

    private final MapViewer                             mapViewer;

    private final Consumer<String>                      nodeSelectionHandler;

    private Set<String>                                 servletAliases = Sets.newHashSet();

    private final MdToolkit                             tk;

    private final SimpleFeatureGraphUI                  graphUi;

    // only used for constructing the pipelines
    private final ILayer                                baseLayer;


    public ImageLayerProvider( final MdToolkit tk, final MapViewer mapViewer,
            final ILayer baseLayer, final Consumer<String> nodeSelectionHandler ) {
        this.tk = tk;
        this.mapViewer = mapViewer;
        this.baseLayer = baseLayer;
        this.nodeSelectionHandler = nodeSelectionHandler;
        this.graphUi = new SimpleFeatureGraphUI( tk, mapViewer );
        // SelectInteraction selectInteraction = new SelectInteraction(
        // (VectorLayer)getLayer( null ) );
        // selectInteraction.addEventListener( SelectInteraction.Event.select, this
        // );
        // mapViewer.addMapInteraction( selectInteraction );
    }

    // should be called twice

    private boolean isNodesLayer = true;


    @Override
    public Layer getLayer( ILayer layer ) {
        try {
            // XXX don't connect again (use some cache on layer)
            DataSourceDescription dsd = LocalResolver.instance().connectLayer( layer, null ).orElseThrow( () -> new RuntimeException( "No data source for layer: "
                    + layer ) );

            // create pipeline for it
            // XXX do not use layer specific things like caching; build extra
            // pipeline
            final Pipeline pipeline = P4PipelineIncubator.forLayer( layer ).newPipeline( EncodedImageProducer.class, dsd, null );
            assert pipeline != null && pipeline.length() > 0 : "Unable to build pipeline for: " + dsd;

            // inject ChartGeometryProcessor
            FeatureRenderProcessor2 featureRenderProc = (FeatureRenderProcessor2)pipeline.get( pipeline.length()
                    - 1 ).processor();
            Map<String,Object> props = new HashMap();
            props.put( "graphUi", graphUi );
            props.put( "isNodesLayer", isNodesLayer );
            ProcessorDescription proc = new ProcessorDescription( GraphGeometryProcessor.class, props );
            PipelineProcessorSite procSite = new PipelineProcessorSite( props );
            proc.processor().init( procSite );
            featureRenderProc.pipeline().add( 0, proc );
            log.info( "FeatureRender pipeline: " + featureRenderProc.pipeline() );

            // register WMS servlet
            String servletAlias = "/" + (isNodesLayer ? "nodes" : "edges") + hashCode();
            P4Plugin.instance().httpService().registerServlet( servletAlias, new SimpleWmsServer() {

                @Override
                protected String[] layerNames() {
                    throw new RuntimeException( "not yet implemented." );
                }


                @Override
                protected Pipeline createPipeline( String layerName ) {
                    return pipeline;
                }
            }, null, null );
            servletAliases.add( servletAlias );

            isNodesLayer = false;

            return new ImageLayer().source.put( new ImageWMSSource().url.put( "."
                    + servletAlias ).params.put( new WMSRequestParams().version.put( "1.1.1" ).layers.put( (String)layer.id() ).format.put( "image/png" ) ) );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "Unable to create graph layer.", e );
            throw new RuntimeException( e );
        }
    }


    @Override
    public int getPriority( ILayer elm ) {
        return 0;
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
    public List<ILayer> layers() {
        return Lists.newArrayList( baseLayer, baseLayer );
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
}
