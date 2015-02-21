/* 
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.IPanel;
import org.polymap.rhei.batik.PanelIdentifier;

import org.polymap.rap.openlayers.OpenLayersWidget;
import org.polymap.rap.openlayers.base_types.Bounds;
import org.polymap.rap.openlayers.base_types.OpenLayersMap;
import org.polymap.rap.openlayers.base_types.Projection;
import org.polymap.rap.openlayers.controls.LayerSwitcherControl;
import org.polymap.rap.openlayers.controls.MousePositionControl;
import org.polymap.rap.openlayers.controls.NavigationControl;
import org.polymap.rap.openlayers.controls.ScaleLineControl;
import org.polymap.rap.openlayers.layers.WMSLayer;

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

    private OpenLayersWidget        olwidget;

    private OpenLayersMap           map;

    
    @Override
    public void createContents( Composite parent ) {
        getSite().setTitle( "Waldbesitzer" );
        
        parent.setLayout( FormLayoutFactory.defaults().margins( 10 ).create() );

//        getSite().toolkit().createLabel( parent, "Karte..." )
//                .setLayoutData( FormDataFactory.filled().width( 500 ).create() );
        
        olwidget = new OpenLayersWidget( parent, SWT.MULTI | SWT.WRAP | SWT.BORDER );
        olwidget.setLayoutData( FormDataFactory.filled().height( 500 ).create() );

        String srs = "EPSG:31468";// Geometries.srs( getCRS() );
        Projection proj = new Projection( srs );
        String units = srs.equals( "EPSG:4326" ) ? "degrees" : "m";
        float maxResolution = srs.equals( "EPSG:4326" ) ? (360 / 256) : 500000;
        Bounds bounds = new Bounds( 4500000, 5550000, 4700000, 5700000 );

        map = new OpenLayersMap( olwidget, proj, proj, units, bounds, maxResolution );
        // map.updateSize();

        WMSLayer layer = new WMSLayer( "OSM", "http://ows.terrestris.de/osm/service/", "OSM-WMS" );
        layer.setIsBaseLayer( true );
        map.addLayer( layer );
        //
        map.addControl( new NavigationControl( true ) );
//        map.addControl( new PanZoomBarControl() );
        map.addControl( new LayerSwitcherControl() );
        map.addControl( new MousePositionControl() );
        map.addControl( new ScaleLineControl() );
        map.addControl( new MousePositionControl() );

        // map.addControl( new ScaleControl() );
        // map.addControl( new LoadingPanelControl() );

        // map.setRestrictedExtend( maxExtent );
        map.zoomToExtent( bounds, true );
        //map.zoomTo( 2 );
    }

}
