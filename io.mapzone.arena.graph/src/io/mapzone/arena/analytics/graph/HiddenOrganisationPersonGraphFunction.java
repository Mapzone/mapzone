///* 
// * polymap.org
// * Copyright (C) 2016, the @authors. All rights reserved.
// *
// * This is free software; you can redistribute it and/or modify it
// * under the terms of the GNU Lesser General Public License as
// * published by the Free Software Foundation; either version 3.0 of
// * the License, or (at your option) any later version.
// *
// * This software is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// * Lesser General Public License for more details.
// */
//package io.mapzone.arena.analytics.graph;
//
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.eclipse.core.runtime.IAdaptable;
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.Status;
//import org.eclipse.rap.rwt.service.ServerPushSession;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Label;
//import org.geotools.data.FeatureSource;
//import org.geotools.feature.FeatureCollection;
//import org.geotools.feature.FeatureIterator;
//import org.opengis.feature.simple.SimpleFeature;
//import org.polymap.core.data.util.NameImpl;
//import org.polymap.core.operation.DefaultOperation;
//import org.polymap.core.operation.OperationSupport;
//import org.polymap.core.runtime.UIThreadExecutor;
//import org.polymap.core.ui.FormDataFactory;
//import org.polymap.core.ui.StatusDispatcher;
//import org.polymap.p4.P4Plugin;
//import org.polymap.rap.openlayers.style.Base;
//import org.polymap.rap.openlayers.style.StrokeStyle;
//import org.polymap.rap.openlayers.style.Style;
//import org.polymap.rap.openlayers.style.StyleFunction;
//import org.polymap.rap.openlayers.types.Color;
//import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
//import org.polymap.rhei.batik.toolkit.md.MdToolkit;
//
//import com.google.common.collect.ArrayListMultimap;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Multimap;
//
//public class HiddenOrganisationPersonGraphFunction
//        implements GraphFunction {
//
//    private final ServerPushSession pushSession = new ServerPushSession();
//
//    private static Log log = LogFactory.getLog( HiddenOrganisationPersonGraphFunction.class );
//
//    private final Style edgeStyle = new Style().stroke
//            .put( new StrokeStyle().color.put( new Color( "black" ) ).width.put( 1f ) ).zIndex.put( 0f );
//
//
//    @Override
//    public String title() {
//        return "Q - raw mapzone recherche, hidden organizations";
//    }
//
//
//    @Override
//    public String description() {
//        return "Q - raw mapzone recherche as persons and organizations with hidden organizations nodes based on 'Mapzone-Recherche-6'";
//    }
//
//
//    @Override
//    public void createContents( final MdToolkit tk, final Composite parent, final Graph graph ) {
//
//        final Button fab = tk.createFab();
//        fab.setEnabled( true );
//        fab.setVisible( true );
//        fab.addSelectionListener( new SelectionAdapter() {
//
//            @Override
//            public void widgetSelected( SelectionEvent e ) {
//                DefaultOperation op = new DefaultOperation( "Analyse Mapzone-Recherche-06" ) {
//
//                    @Override
//                    public IStatus doExecute( final IProgressMonitor monitor, final IAdaptable info ) throws Exception {
//                        try {
//                            final FeatureSource fs = P4Plugin.localCatalog().localFeaturesStore()
//                                    .getFeatureSource( new NameImpl( "Mapzone-Recherche-06" ) );
//                            if (fs != null) {
//                                UIThreadExecutor.async( () -> pushSession.start(),
//                                        error -> StatusDispatcher.handleError( "", error ) );
//                                analyse( tk, fs.getFeatures(), graph, monitor );
//                                return Status.OK_STATUS;
//                            }
//                            else {
//                                tk.createSnackbar( Appearance.FadeIn, "Dataset 'Mapzone-Recherche-06' not found" );
//                                return Status.CANCEL_STATUS;
//                            }
//                        }
//                        finally {
//                            UIThreadExecutor.async( () -> pushSession.stop(),
//                                    error -> StatusDispatcher.handleError( "", error ) );
//                        }
//                    }
//                };
//                // execute
//                OperationSupport.instance().execute2( op, true, false );
//                fab.setVisible( false );
//            }
//        } );
//
//        final Label selectLabel = tk.createLabel( parent, "using mapzone-recherche-06 directly", SWT.BORDER );
//        FormDataFactory.on( selectLabel ).fill().top( 15 ).left( 1 ).noBottom();
//        // FormDataFactory.on( combo ).top( selectLabel, 3 ).left( 1 );
//    }
//
//
//    public void analyse( final MdToolkit tk, final FeatureCollection featureCollection, final Graph graph,
//            final IProgressMonitor monitor ) throws Exception {
//
//        tk.createSnackbar( Appearance.FadeIn, "Analysis started - stay tuned" );
//
//        final Map<String,Node> organisations = Maps.newHashMap();
//        final Map<String,Node> persons = Maps.newHashMap();
//        final Multimap<Node,Node> organisation2Persons = ArrayListMultimap.create();
//        final Multimap<Node,Node> person2Organisations = ArrayListMultimap.create();
//
//        // iterate on features
//        // create Node for each organisation
//        // increase weight for each entry per organisation
//        FeatureIterator iterator = featureCollection.features();
//        int i = 0;
//        while (iterator.hasNext() && i < 5000) {
//            i++;
//            SimpleFeature feature = (SimpleFeature)iterator.next();
//            String organisationKey = (String)feature.getAttribute( "Organisation" );
//            Node organisationFeature = organisations.get( organisationKey );
//            if (organisationFeature == null) {
//                organisationFeature = new Node( "o:" + feature.getID(), feature, organisationKey, 1 );
//                organisations.put( organisationKey, organisationFeature );
//                graph.addOrUpdateNode( organisationFeature );
//            }
//            else {
//                // add weight
//                int size = organisation2Persons.get( organisationFeature ).size() + 1;
//                if (size <= 15) {
//                    organisationFeature.increaseWeight();
//                }
//                graph.addOrUpdateNode( organisationFeature );
//            }
//            String personKey = (String)feature.getAttribute( "Name" ) + " " + (String)feature.getAttribute( "Vorname" );
//            Node personFeature = persons.get( personKey );
//            if (personFeature == null) {
//                personFeature = new Node( "p:" + feature.getID(), feature, personKey, 1 );
//                persons.put( personKey, personFeature );
//                graph.addOrUpdateNode( personFeature );
//            }
//            else {
//                int size = person2Organisations.get( personFeature ).size() + 1;
//                if (size <= 15) {
//                    personFeature.increaseWeight();
//                }
//                graph.addOrUpdateNode( personFeature );
//            }
//            // add also the person to the organisation
//            organisation2Persons.put( organisationFeature, personFeature );
//            person2Organisations.put( personFeature, organisationFeature );
//
//            graph.addOrUpdateEdge( organisationFeature, personFeature );
//
//            if (i % 100 == 0) {
//                log.info( "added " + i );
//            }
//        }
//        tk.createSnackbar( Appearance.FadeIn, organisations.size() + " organisations, " + persons.size()
//                + " persons and " + organisation2Persons.size() + " relations analysed" );
//        organisations.clear();
//        persons.clear();
//        organisation2Persons.clear();
//        person2Organisations.clear();
//        graph.layout();
//
//        UIThreadExecutor.async( () -> pushSession.stop(), error -> StatusDispatcher.handleError( "", error ) );
//    }
//
//
//    private Base edgeStyle() {
//        return edgeStyle;
//    }
//
//    private StyleFunction organizationStyle;
//
//
//    private StyleFunction organizationStyle() {
//        if (organizationStyle == null) {
//            organizationStyle = new StyleFunction( organization() );
//        }
//        return organizationStyle;
//    }
//
//
//    private Base personStyle( int weight ) {
//        return new StyleFunction( person( weight ) );
//    }
//
//    private final static String PERSONCOLOR = "red";
//
//
//    private String person( int weight ) {
//        StringBuffer sb = new StringBuffer();
//        sb.append( "return [new ol.style.Style({" );
//        sb.append( "  zIndex: 1," );
//        sb.append( "  image: new ol.style.Circle({" );
//        sb.append( "      radius: '" ).append( weight ).append( "'," );
//        sb.append( "    stroke: new ol.style.Stroke({" );
//        sb.append( "      color: '" ).append( PERSONCOLOR ).append( "'" );
//        sb.append( "    })," );
//        sb.append( "    fill: new ol.style.Fill({" );
//        sb.append( "      color: '" ).append( PERSONCOLOR ).append( "'" );
//        sb.append( "    })" );
//        sb.append( "  })," );
//        sb.append( "  text: new ol.style.Text({" );
//        sb.append( "    text: this.get('name')," );
//        sb.append( "    fill: new ol.style.Fill({" );
//        sb.append( "      color: 'black'" );
//        sb.append( "    })" );
//        sb.append( "  })" );
//        sb.append( "})];" );
//        return sb.toString();
//    }
//
//
//    private String organization() {
//        StringBuffer sb = new StringBuffer();
//        sb.append( "return [new ol.style.Style({" );
//        sb.append( "  zIndex: 1," );
//        sb.append( "  image: new ol.style.Circle({" );
//        sb.append( "    radius: 0.1," );
//        sb.append( "    fill: new ol.style.Fill({" );
//        sb.append( "      color: 'black'" );
//        sb.append( "    })" );
//        sb.append( "  })" );
//        sb.append( "})];" );
//        return sb.toString();
//    }
//
//}
