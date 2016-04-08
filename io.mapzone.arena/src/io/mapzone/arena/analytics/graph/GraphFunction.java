package io.mapzone.arena.analytics.graph;

import org.geotools.feature.FeatureCollection;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.rap.openlayers.graph.OlFeatureGephiGraph;
import org.polymap.rap.openlayers.layer.VectorLayer;
import org.polymap.rap.openlayers.source.VectorSource;

public abstract class GraphFunction {

    protected VectorSource           source;

    protected MapViewer<VectorLayer> map;

    protected OlFeatureGephiGraph    graph;


    public void init( final VectorSource source, final MapViewer<VectorLayer> map ) {
        this.source = source;
        this.map = map;
        this.graph = new OlFeatureGephiGraph( source, map.getMap() );
    }


    public abstract void addFeatures( FeatureCollection features ) throws Exception;
}