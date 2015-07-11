/* 
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.data.util.Geometries;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.mapeditor.OlContentProvider;
import org.polymap.core.mapeditor.OlLayerProvider;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.IPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rap.openlayers.base.OlMap;
import org.polymap.rap.openlayers.layer.ImageLayer;
import org.polymap.rap.openlayers.layer.Layer;
import org.polymap.rap.openlayers.source.ImageSource;
import org.polymap.rap.openlayers.source.ImageWMSSource;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectPanel
        extends DefaultPanel
        implements IPanel {

    private static Log log = LogFactory.getLog( ProjectPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "project" );
    
//    private static final IMessages      i18n = Messages.forPrefix( "ProjectPanel" );

    private OlMap                       map;

    
    @Override
    public void init() {
        getSite().setPreferredWidth( 650 );
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().setTitle( "Project" );
        
        parent.setLayout( FormLayoutFactory.defaults().margins( 0 ).create() );

//        getSite().toolkit().createLabel( parent, "Karte... (" + hashCode() + ")" )
//                .setLayoutData( FormDataFactory.filled().noBottom().width( 600 ).create() );
        
        try {
            Layer<ImageSource> osm = new ImageLayer().source.put( new ImageWMSSource()
                    .url.put( "http://ows.terrestris.de/osm/service/" )
                    .params.put( new ImageWMSSource.RequestParams().layers.put( "OSM-WMS" ) ) );
            
            CoordinateReferenceSystem epsg3857 = Geometries.crs( "EPSG:3857" );
            
            MapViewer<Layer> mapViewer = new MapViewer<Layer>( parent )
                    .maxExtent.put( new ReferencedEnvelope( 1380000, 1390000, 6680000, 6690000, epsg3857 ) )
                    .contentProvider.put( new OlContentProvider() )
                    .layerProvider.put( new OlLayerProvider() );
            
            mapViewer.setInput( osm );
            mapViewer.getMap().setLayoutData( FormDataFactory.filled().height( 500 ).create() );
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
        
//        Bounds bounds = new Bounds( 4500000, 5550000, 4700000, 5700000 );

//        map = new OlMap( parent, SWT.NONE, new View()
//            .projection.put( new Projection( "EPSG:3857", Units.m ) )
//            .center.put( new Coordinate( 1387648, 6688702 ) )
//            .zoom.put( 5 ) );
//
//        map.addLayer( new ImageLayer()
//            .source.put( new ImageWMSSource()
//                .url.put( "http://ows.terrestris.de/osm/service/" )
//                .params.put( new ImageWMSSource.RequestParams().layers.put( "OSM-WMS" ) ) )
//            .opacity.put( 0.5f ) );
//
//        VectorSource vectorSource = new VectorSource()
//            .format.put( new GeoJSONFormat() )
//            .attributions.put( Arrays.asList( new Attribution( "Mapzone" ) ) );
//
//        VectorLayer vectorLayer = new VectorLayer()
//            .style.put( new Style()
//                .fill.put( new FillStyle().color.put( new Color( 0, 0, 255, 0.1f ) ) )
//                .stroke.put( new StrokeStyle().color.put( new Color( "red" ) ).width.put( 1f ) ) )
//                .source.put( vectorSource );
//
//        map.addLayer( vectorLayer );
//
//        OlFeature feature = new OlFeature();
//        feature.name.set( "Test1!" );
//        feature.labelPoint.set( map.view.get().center.get() );
//        feature.geometry.set( new PointGeometry( map.view.get().center.get() ) );
//        feature.style.put( new Style()
//                .text.put( new TextStyle()
//                        .text.put( "MY MESSAGE" )
//                        .font.put( new Font()
//                        .family.put( Font.Family.CourierNew )
//                        .weight.put( Font.Weight.bold )
//                        .size.put( 24 ) )
//                        .stroke.put( new StrokeStyle()
//                        .color.put( new Color( "green" ) )
//                        .width.put( 2f ) ) )
//                        .image.put( new CircleStyle( 5.0f )
//                        .fill.put( new FillStyle()
//                        .color.put( new Color( "red" ) ) ) ) );
//        vectorSource.addFeature( feature );
//
//        map.setLayoutData( FormDataFactory.filled().height( 500 ).create() );
//        
//        //
////        map.addControl( new NavigationControl( true ) );
////        map.addControl( new PanZoomBarControl() );
////        map.addControl( new LayerSwitcherControl() );
////        map.addControl( new MousePositionControl() );
//        map.addControl( new ScaleLineControl( null, null ) );
//        map.addControl( new ZoomSliderControl() );
//        map.addControl( new ZoomControl() );
//
//        // map.addControl( new ScaleControl() );
//        // map.addControl( new LoadingPanelControl() );
//
//        // map.setRestrictedExtend( maxExtent );
////        map.zoomToExtent( bounds, true );
//        //map.zoomTo( 2 );
    }

}
