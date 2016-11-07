/* 
 * polymap.org
 * Copyright (C) 2016, Falko Bräutigam. All rights reserved.
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
package io.mapzone.arena.analytics.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.io.IOException;

import org.geotools.data.FeatureStore;
import org.geotools.data.Query;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;

import org.polymap.core.data.feature.AddFeaturesRequest;
import org.polymap.core.data.feature.FeatureRenderProcessor2;
import org.polymap.core.data.feature.FeaturesProducer;
import org.polymap.core.data.feature.GetFeatureTypeRequest;
import org.polymap.core.data.feature.GetFeatureTypeResponse;
import org.polymap.core.data.feature.GetBoundsRequest;
import org.polymap.core.data.feature.GetFeaturesRequest;
import org.polymap.core.data.feature.GetFeaturesResponse;
import org.polymap.core.data.feature.GetFeaturesSizeRequest;
import org.polymap.core.data.feature.ModifyFeaturesRequest;
import org.polymap.core.data.feature.RemoveFeaturesRequest;
import org.polymap.core.data.feature.TransactionRequest;
import org.polymap.core.data.image.EncodedImageProducer;
import org.polymap.core.data.pipeline.Consumes;
import org.polymap.core.data.pipeline.DataSourceDescription;
import org.polymap.core.data.pipeline.EndOfProcessing;
import org.polymap.core.data.pipeline.Pipeline;
import org.polymap.core.data.pipeline.PipelineExecutor.ProcessorContext;
import org.polymap.core.data.pipeline.PipelineProcessorSite;
import org.polymap.core.data.pipeline.ProcessorDescription;
import org.polymap.core.data.pipeline.Produces;
import org.polymap.core.data.util.Geometries;
import org.polymap.core.mapeditor.ILayerProvider;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.mapeditor.services.SimpleWmsServer;
import org.polymap.core.project.ILayer;
import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;
import org.polymap.core.runtime.PlainLazyInit;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.SelectionAdapter;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.PanelIdentifier;

import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;
import org.polymap.p4.catalog.AllResolver;
import org.polymap.p4.data.P4PipelineIncubator;
import org.polymap.rap.openlayers.control.MousePositionControl;
import org.polymap.rap.openlayers.control.ScaleLineControl;
import org.polymap.rap.openlayers.layer.ImageLayer;
import org.polymap.rap.openlayers.layer.Layer;
import org.polymap.rap.openlayers.source.ImageWMSSource;
import org.polymap.rap.openlayers.source.WMSRequestParams;

import io.mapzone.arena.ArenaPlugin;

/**
 * Proof-of-concept for generated geometries and chart displayed in an OL map.
 *
 * @author Falko Bräutigam
 */
public class ChartPanel
        extends P4Panel {

    private static Log log = LogFactory.getLog( ChartPanel.class );
    
    public static final PanelIdentifier ID = PanelIdentifier.parse( "chart" );

    public static final Lazy<CoordinateReferenceSystem> CRS = new PlainLazyInit( () -> {
        try { return Geometries.crs( "EPSG:3857" ); } catch (Exception e) { throw new RuntimeException( e ); }
    });
    
    
    // instance *******************************************
    
    private ILayer          layer;
    
    /** {@link EncodedImageProducer} pipeline of {@link #layer}. */
    private Pipeline        pipeline;

    private MapViewer       mapViewer;

    private String          servletAlias;

    private Composite       parent;

    /** Names of the attributes that we support for chart. */
    private List<String>    availableAttributes;
    
    private List<MappingFunction> mappingFunctions = new ArrayList();

    private Composite       mapContainer;

    
    @Override
    public boolean wantsToBeShown() {
        if (site().path().size() == 2) {
            site().icon.set( ArenaPlugin.images().svgImage( "chart-bar.svg", P4Plugin.HEADER_ICON_CONFIG ) );
            site().tooltip.set( "Experimental charts :)" );
            site().title.set( "" );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        site().title.set( "Chart experiment" );
    }


    @Override
    public void dispose() {
        try {
            if (servletAlias != null) {
                P4Plugin.instance().httpService().unregister( servletAlias );
            }
        }
        catch (Exception e) {
            log.warn( "", e );
        }
    }


    @Override
    public void createContents( @SuppressWarnings("hiding") Composite parent ) {
        if (!featureLayer.isPresent()) {
            tk().createFlowText( parent, "Select a layer to by **active** first." );
            return;
        }
        this.layer = featureLayer.get().layer();
        this.parent = parent;
        parent.setLayout( FormLayoutFactory.defaults().spacing( 5 ).margins( 0, 5 ).create() );
        
        try {
            // availableAttributes
            FeatureStore fs = featureLayer.get().featureSource();
            SimpleFeatureType schema = (SimpleFeatureType)fs.getSchema();
            availableAttributes = new ArrayList();
            for (AttributeDescriptor prop : schema.getAttributeDescriptors()) {
                if (Number.class.isAssignableFrom( prop.getType().getBinding() )
                        || String.class.isAssignableFrom( prop.getType().getBinding() )) {
                    availableAttributes.add( prop.getLocalName() );
                }
            }
            if (availableAttributes.isEmpty()) {
                tk().createFlowText( parent, "No number attributes in the active layer." );
                return;
            }
            // combo
            ComboViewer combo = new ComboViewer( parent );
            combo.setContentProvider( new ArrayContentProvider() );
            combo.setInput( availableAttributes.toArray() );
            combo.addSelectionChangedListener( ev -> {
                NumberMappingFunction mappingFunction = new NumberMappingFunction();
                mappingFunction.init( SelectionAdapter.on( ev.getSelection() ).first( String.class ).get(), fs );
                mappingFunctions.add( mappingFunction );
                
                if (mapViewer != null) {
                    mapViewer.dispose();
                }
                createMapViewer();
            });
            //combo.setSelection( new StructuredSelection( availableAttributes.get( 0 ) ) );

            // mapContainer
            mapContainer = tk().createComposite( parent, SWT.BORDER );
            mapContainer.setLayout( new FillLayout() );
            
            // layout
            FormDataFactory.on( combo.getCombo() ).fill().noBottom().control();
            FormDataFactory.on( mapContainer ).fill().top( combo.getCombo() );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }
    
    
    protected void createMapViewer() {
        mapViewer = new MapViewer( mapContainer );
        mapViewer.layerProvider.set( new ChartLayerProvider() );
        mapViewer.contentProvider.set( new ArrayContentProvider() );
        mapViewer.maxExtent.set( new ReferencedEnvelope( 10000, 10100, 10000, 10100, CRS.get() ) );
        mapViewer.addMapControl( new MousePositionControl() );
        mapViewer.addMapControl( new ScaleLineControl() );
        mapViewer.setInput( new ILayer[] {layer} );
        mapContainer.layout();
    }

    
    /**
     * 
     */
    class ChartLayerProvider
            implements ILayerProvider<ILayer> {

        @Override
        public Layer getLayer( @SuppressWarnings("hiding") ILayer layer ) {
            try {
                // XXX don't connect again (use some cache on layer)
                DataSourceDescription dsd = AllResolver.instance().connectLayer( layer, null )
                        .orElseThrow( () -> new RuntimeException( "No data source for layer: " + layer ) );

                // create pipeline for it
                // XXX do not use layer specific things like caching; build extra pipeline
                pipeline = P4PipelineIncubator.forLayer( layer )
                        .newPipeline( EncodedImageProducer.class, dsd, null );
                assert pipeline != null && pipeline.length() > 0 : "Unable to build pipeline for: " + dsd;

                // inject ChartGeometryProcessor
                FeatureRenderProcessor2 featureRenderProc = (FeatureRenderProcessor2)pipeline.get( pipeline.length()-1 ).processor();
                Map<String,Object> props = new HashMap();
                props.put( "mappingFunction", mappingFunctions.get( 0 ) );
                ProcessorDescription proc = new ProcessorDescription( ChartGeometryProcessor.class, props );
                PipelineProcessorSite procSite = new PipelineProcessorSite( props );
                proc.processor().init( procSite );
                featureRenderProc.pipeline().add( 0, proc );
                log.info( "FeatureRender pipeline: " + featureRenderProc.pipeline() );
                
                // register WMS servlet
                servletAlias = "/chart" + hashCode();
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
                
                //
                return new ImageLayer()
                        .source.put( new ImageWMSSource()
                                .url.put( "." + servletAlias )
                                .params.put( new WMSRequestParams()
                                        .version.put( "1.1.1" )  // send "SRS" param
                                        .layers.put( (String)layer.id() )
                                        .format.put( "image/png" ) ) );
            }
            catch (Exception e) {
                StatusDispatcher.handleError( "Unable to create data layer.", e );
                throw new RuntimeException( e );
            }
        }

        @Override
        public int getPriority( @SuppressWarnings("hiding") ILayer layer ) {
            return 0;
        }
    }

    
    /**
     * 
     */
    public static class ChartGeometryProcessor
            implements FeaturesProducer {

        private GeometryFactory             gf = new GeometryFactory();

        private Lazy<SimpleFeatureType>     schema = new LockedLazyInit();
        
        private MappingFunction             mappingFunction;
        
        @Override
        public void init( PipelineProcessorSite site ) throws Exception {
//            this.mappingFunction = site.getProperty( "mappingFunction" );
            assert mappingFunction != null;
        }

        @Override
        public void getFeatureTypeRequest( GetFeatureTypeRequest request, ProcessorContext context ) throws Exception {
            context.sendRequest( request );
        }

        @Produces( GetFeatureTypeResponse.class )
        @Consumes( GetFeatureTypeResponse.class )
        public void handleFeatureType( GetFeatureTypeResponse response, ProcessorContext context ) throws Exception {
            context.sendResponse( new GetFeatureTypeResponse( schema.get( () -> {
//                SimpleFeatureType srcSchema = (SimpleFeatureType)response.getFeatureType();
                SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
                builder.setName( "ChartGeometry" );
                builder.add( "geom", Point.class, CRS.get() );
                builder.setDefaultGeometry( "geom" );
                return builder.buildFeatureType();
            })));
        }
        
        @Override
        public void getFeatureRequest( GetFeaturesRequest request, ProcessorContext context ) throws Exception {
            log.info( "getFeatureRequest(): " + request.getQuery().getFilter() );
            context.sendRequest( new GetFeaturesRequest( new Query( "", Filter.INCLUDE ) ) );
        }

        private Random rand = new Random();
        
        @Produces( GetFeaturesResponse.class )
        @Consumes( GetFeaturesResponse.class )
        public void handleFeatures( GetFeaturesResponse response, ProcessorContext context ) throws Exception {
            List<Feature> result = new ArrayList( response.count() );
            for (Feature feature : response) {
                SimpleFeatureBuilder builder = new SimpleFeatureBuilder( schema.get() );
                double x = mappingFunction.mapFeatureValue( (SimpleFeature)feature );
                builder.set( "geom", gf.createPoint( new Coordinate( x, 0 ) ) );
                result.add( builder.buildFeature( feature.getIdentifier().getID() ) );
            }
            context.sendResponse( new GetFeaturesResponse( result ) );
        }

        
        @Produces( EndOfProcessing.class )
        @Consumes( EndOfProcessing.class )
        public void handleEndOfProcessing( EndOfProcessing response, ProcessorContext context ) throws Exception {
            context.sendResponse( response );
        }
        
        
        @Override
        public void getFeatureSizeRequest( GetFeaturesSizeRequest request, ProcessorContext context ) throws Exception {
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public void getFeatureBoundsRequest( GetBoundsRequest request, ProcessorContext context ) throws Exception {
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public void setTransactionRequest( TransactionRequest request, ProcessorContext context ) throws Exception {
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public void addFeaturesRequest( AddFeaturesRequest request, ProcessorContext context ) throws Exception {
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public void modifyFeaturesRequest( ModifyFeaturesRequest request, ProcessorContext context ) throws Exception {
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public void removeFeaturesRequest( RemoveFeaturesRequest request, ProcessorContext context ) throws Exception {
            throw new RuntimeException( "not yet implemented." );
        }
    }

    
    /**
     * 
     */
    public static abstract class MappingFunction {
    
        protected String        propName;
        
        @SuppressWarnings("hiding")
        public void init( String propName, FeatureStore fs ) {
            this.propName = propName;
            
        }
        
        public abstract double mapFeatureValue( SimpleFeature feature );
    }
    
    
    /**
     * 
     */
    class NumberMappingFunction
            extends MappingFunction {

        protected Pair<Double,Double>   range;
        
        @Override
        @SuppressWarnings("hiding")
        public void init( String propName, FeatureStore fs ) {
            super.init( propName, fs );
            try {
                double min=Double.MAX_VALUE, max=Double.MIN_VALUE;
                try (
                    FeatureIterator features = fs.getFeatures().features();
                ){
                    while (features.hasNext()) {
                        double value = mapFeatureValue( (SimpleFeature)features.next() );
                        min = Math.min( min, value );
                        max = Math.max( max, value );
                    }
                };
                assert min < max;
                range = Pair.of( min, max );
            }
            catch (IOException e) {
                throw new RuntimeException( e );
            }
        }

        @Override
        public double mapFeatureValue( SimpleFeature feature ) {
            double number = 0;
            Object value = feature.getAttribute( propName );
            if (value == null) {
                number = 0;
            }
            else if (value instanceof String) {
                number = Double.parseDouble( (String)value );
            }
            else if (value instanceof Number) {
                number = ((Number)value).doubleValue();
            }
            else {
                throw new RuntimeException( "Unhandled value type: " + value );
            }
            return number;
        }
    }
}
