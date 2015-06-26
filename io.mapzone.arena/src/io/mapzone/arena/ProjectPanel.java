/* 
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.IPanel;
import org.polymap.rhei.batik.PanelIdentifier;

import org.polymap.rap.openlayers.base.OlMap;

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

    private OlMap           map;

    
    @Override
    public void createContents( Composite parent ) {
        getSite().setTitle( "Project" );
        
        parent.setLayout( FormLayoutFactory.defaults().margins( 10 ).create() );

        getSite().toolkit().createLabel( parent, "Karte... (" + hashCode() + ")" )
                .setLayoutData( FormDataFactory.filled().width( 600 ).create() );
        
        
//        Bounds bounds = new Bounds( 4500000, 5550000, 4700000, 5700000 );

//        map = new OlMap( parent, SWT.NONE, new View()
//            .projection.put( new Projection( "EPSG:3857", Units.m ) )
//            //.center.put( new Coordinate( 4500000, 5550000 ) )
//            .center.put( new Coordinate( 0, 0 ) )
//            .zoom.put( 3 ) );
//
//        map.addLayer( new ImageLayer()
//            .source.put( new ImageWMSSource()
//                .url.put( "http://ows.terrestris.de/osm/service/" )
//                .params.put( new ImageWMSSource.RequestParams().layers.put( "OSM-WMS" ) ) )
//            .opacity.put( 0.5f ) );
//
//        map.setLayoutData( FormDataFactory.filled().height( 500 ).create() );
        
        //
//        map.addControl( new NavigationControl( true ) );
//        map.addControl( new PanZoomBarControl() );
//        map.addControl( new LayerSwitcherControl() );
//        map.addControl( new MousePositionControl() );
//        map.addControl( new ScaleLineControl() );

        // map.addControl( new ScaleControl() );
        // map.addControl( new LoadingPanelControl() );

        // map.setRestrictedExtend( maxExtent );
//        map.zoomToExtent( bounds, true );
        //map.zoomTo( 2 );
    }

}
