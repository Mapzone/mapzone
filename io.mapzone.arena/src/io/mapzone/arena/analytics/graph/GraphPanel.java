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
package io.mapzone.arena.analytics.graph;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.geotools.data.FeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.filter.Filter;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.polymap.core.data.util.Geometries;
import org.polymap.core.data.util.NameImpl;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.core.project.ILayer;
import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.PlainLazyInit;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;
import org.polymap.rap.openlayers.format.GeoJSONFormat;
import org.polymap.rap.openlayers.layer.VectorLayer;
import org.polymap.rap.openlayers.source.VectorSource;
import org.polymap.rhei.batik.PanelIdentifier;

import io.mapzone.arena.ArenaPlugin;

/**
 * Proof-of-concept for generated geometries and graph displayed in an OL map.
 *
 * @author Steffen Stundzig
 */
public class GraphPanel
        extends P4Panel {

    private final ServerPushSession pushSession = new ServerPushSession();

    private static Log                                  log = LogFactory.getLog( GraphPanel.class );

    public static final PanelIdentifier                 ID  = PanelIdentifier.parse( "graph" );

    public static final Lazy<CoordinateReferenceSystem> CRS = new PlainLazyInit( () -> {
                                                                try {
                                                                    return Geometries.crs( "EPSG:3857" );
                                                                }
                                                                catch (Exception e) {
                                                                    throw new RuntimeException( e );
                                                                }
                                                            } );

    // instance *******************************************

    // private ILayer layer;

    // /** {@link EncodedImageProducer} pipeline of {@link #layer}. */
    // private Pipeline pipeline;

    private MapViewer<VectorLayer>                      mapViewer;

    // private String servletAlias;

    // private Composite parent;

    // /** Names of the attributes that we support for chart. */
    // private List<String> availableAttributes;
    //
    // private List<MappingFunction> mappingFunctions = new ArrayList();

    private Composite                                   mapContainer;

    private VectorSource                                source;


    @Override
    public boolean wantsToBeShown() {
        if (site().path().size() == 2) {
            site().icon.set( ArenaPlugin.images().svgImage( "chart-bar.svg", P4Plugin.HEADER_ICON_CONFIG ) );
            site().tooltip.set( "Network Analysis" );
            site().title.set( "" );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        pushSession.start();
        site().title.set( "Network Analysis" );
    }


    @Override
    public void dispose() {
        pushSession.stop();
        super.dispose();
    }


    @Override
    public void createContents( @SuppressWarnings("hiding") Composite parent ) {
        try {
            FeatureSource fs = null;
            Filter filter = null;
            if (!featureSelection.isPresent()) {
                // TODO combobox with all available layers
                P4Plugin.localCatalog().localFeaturesStore().getNames()
                        .forEach( name -> System.out.println( "local:" + name ) );
                fs = P4Plugin.localCatalog().localFeaturesStore()
                        .getFeatureSource( new NameImpl( "Mapzone-Recherche-06" ) );
            }
            else {
                // this.layer = featureSelection.get().layer();
                fs = featureSelection.get().waitForFs().get();
                filter = featureSelection.get().filter();
            }
            // this.parent = parent;
            parent.setLayout( FormLayoutFactory.defaults().spacing( 5 ).margins( 0, 5 ).create() );

            // SimpleFeatureType schema = (SimpleFeatureType)fs.getSchema();
            // availableAttributes = new ArrayList();
            // for (AttributeDescriptor prop : schema.getAttributeDescriptors()) {
            // if (Number.class.isAssignableFrom( prop.getType().getBinding() )
            // || String.class.isAssignableFrom( prop.getType().getBinding() )) {
            // availableAttributes.add( prop.getLocalName() );
            // }
            // }
            // if (availableAttributes.isEmpty()) {
            // tk().createFlowText( parent, "No number attributes in the active
            // layer."
            // );
            // return;
            // }
            // // combo
            // ComboViewer combo = new ComboViewer( parent );
            // combo.setContentProvider( new ArrayContentProvider() );
            // combo.setInput( availableAttributes.toArray() );
            // combo.addSelectionChangedListener( ev -> {
            // NumberMappingFunction mappingFunction = new NumberMappingFunction();
            // mappingFunction.init( SelectionAdapter.on( ev.getSelection() ).first(
            // String.class ).get(), fs );
            // mappingFunctions.add( mappingFunction );
            //
            // });
            // //combo.setSelection( new StructuredSelection(
            // availableAttributes.get( 0
            // ) ) );
            //
            // mapContainer
            mapContainer = tk().createComposite( parent, SWT.BORDER );
            mapContainer.setLayout( new FillLayout() );
            //
            // // layout
            // FormDataFactory.on( combo.getCombo() ).fill().noBottom().control();
            FormDataFactory.on( mapContainer ).fill();// .top( combo.getCombo() );
            if (mapViewer != null) {
                mapViewer.dispose();
            }
            createMapViewer();

            OrganisationPersonGraphFunction omf = new OrganisationPersonGraphFunction();
            omf.init( source, mapViewer );
            omf.addFeatures( fs.getFeatures( filter ) );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }


    protected void createMapViewer() {
        mapViewer = new MapViewer<VectorLayer>( mapContainer );
        source = new VectorSource().format.put( new GeoJSONFormat() );
        GraphLayerProvider graphLayerProvider = new GraphLayerProvider( source );
        mapViewer.layerProvider.set( graphLayerProvider );
        mapViewer.contentProvider.set( new ArrayContentProvider() );
        mapViewer.maxExtent.set( new ReferencedEnvelope( -10000, -10000, 10000, 10000, CRS.get() ) );
        // mapViewer.addMapControl( new MousePositionControl() );
        // mapViewer.addMapControl( new ScaleLineControl() );
        mapViewer.setInput( new ILayer[] { null } );
        mapContainer.layout();
    }
}
