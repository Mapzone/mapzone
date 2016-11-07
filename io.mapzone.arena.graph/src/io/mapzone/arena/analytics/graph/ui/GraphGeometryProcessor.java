package io.mapzone.arena.analytics.graph.ui;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.opengis.feature.simple.SimpleFeature;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vividsolutions.jts.geom.GeometryFactory;

import org.polymap.core.data.feature.AddFeaturesRequest;
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
import org.polymap.core.data.pipeline.Consumes;
import org.polymap.core.data.pipeline.EndOfProcessing;
import org.polymap.core.data.pipeline.PipelineExecutor.ProcessorContext;
import org.polymap.core.data.pipeline.PipelineProcessorSite;
import org.polymap.core.data.pipeline.ProcessorResponse;
import org.polymap.core.data.pipeline.Produces;

/**
 * Used by the ImageLayerProvider to create the features.
 * 
 * @author Steffen Stundzig
 */
public class GraphGeometryProcessor
        implements FeaturesProducer {

    private final static Log     log = LogFactory.getLog( GraphGeometryProcessor.class );

    private GeometryFactory      gf  = new GeometryFactory();

    private SimpleFeatureGraphUI graphUi;

    private Boolean              isNodesLayer;


    @Override
    public void init( PipelineProcessorSite site ) throws Exception {
        this.graphUi = site.getProperty( "graphUi" );
        this.isNodesLayer = site.getProperty( "isNodesLayer" );
    }


    @Override
    public void getFeatureTypeRequest( GetFeatureTypeRequest request, ProcessorContext context ) throws Exception {
        context.sendResponse( new GetFeatureTypeResponse( isNodesLayer.booleanValue()
                ? graphUi.nodeSchema() : graphUi.edgeSchema() ) );
    }


    @Produces( GetFeatureTypeResponse.class )
    @Consumes( GetFeatureTypeResponse.class )
    public void handleFeatureType( GetFeatureTypeResponse response, ProcessorContext context ) throws Exception {
        throw new IllegalStateException( "must not be called" );
    }


    @Override
    public void getFeatureRequest( GetFeaturesRequest request, ProcessorContext context ) throws Exception {
        List<SimpleFeature> results = isNodesLayer.booleanValue() ? graphUi.nodes()
                : graphUi.edges();
        context.sendResponse( new GetFeaturesResponse( results.stream().filter( f -> request.getQuery().getFilter().evaluate( f ) ).collect( Collectors.toList() ) ) );
        context.sendResponse( ProcessorResponse.EOP );
    }

    private Random rand = new Random();


    @Produces( GetFeaturesResponse.class )
    @Consumes( GetFeaturesResponse.class )
    public void handleFeatures( GetFeaturesResponse response, ProcessorContext context ) throws Exception {
        throw new IllegalStateException( "must not be called" );
    }


    @Produces( EndOfProcessing.class )
    @Consumes( EndOfProcessing.class )
    public void handleEndOfProcessing( EndOfProcessing response, ProcessorContext context ) throws Exception {
        throw new IllegalStateException( "must not be called" );
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
