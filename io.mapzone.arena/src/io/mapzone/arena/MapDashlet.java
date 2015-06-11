/* 
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.dashboard.IDashlet;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

import org.polymap.rap.openlayers.base.OlMap;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class MapDashlet
        implements IDashlet {

    private static Log log = LogFactory.getLog( MapDashlet.class );
    
    private DashletSite             site;

    private OlMap                   map;

    
    @Override
    public void init( @SuppressWarnings("hiding") DashletSite site ) {
        this.site = site;
        site.constraints.get().add( new PriorityConstraint( 1 ) );
        site.constraints.get().add( new MinWidthConstraint( 400, 0 ) );
    }


    @Override
    public Composite createContents( Composite parent ) {
//        parent.setLayout( FormLayoutFactory.defaults().create() );
//        
//        olwidget = new OlWidget( parent, SWT.MULTI | SWT.WRAP | SWT.BORDER );
//        olwidget.setLayoutData( FormDataFactory.filled().height( 500 ).create() );
//
//        String srs = "EPSG:31468";// Geometries.srs( getCRS() );
//        Projection proj = new Projection( srs );
//        String units = srs.equals( "EPSG:4326" ) ? "degrees" : "m";
//        float maxResolution = srs.equals( "EPSG:4326" ) ? (360 / 256) : 500000;
//        Bounds bounds = new Bounds( 4500000, 5550000, 4700000, 5700000 );
//
//        map = new OlMap( olwidget, proj, proj, units, bounds, maxResolution );
//        // map.updateSize();
//
//        WMSLayer layer = new WMSLayer( "OSM", "http://ows.terrestris.de/osm/service/", "OSM-WMS" );
//        layer.setIsBaseLayer( true );
//        map.addLayer( layer );
//        //
//        map.addControl( new NavigationControl( true ) );
////        map.addControl( new PanZoomBarControl() );
//        map.addControl( new LayerSwitcherControl() );
//        map.addControl( new MousePositionControl() );
//        map.addControl( new ScaleLineControl() );
//        map.addControl( new MousePositionControl() );
//
//        // map.addControl( new ScaleControl() );
//        // map.addControl( new LoadingPanelControl() );
//
//        // map.setRestrictedExtend( maxExtent );
//        map.zoomToExtent( bounds, true );
//        //map.zoomTo( 2 );
        return parent;
    }
    
}
