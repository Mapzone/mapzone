/* 
 * polymap.org
 * Copyright (C) 2016, the @authors. All rights reserved.
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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.PropertyDescriptor;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.rap.openlayers.base.OlMap;
import org.polymap.rap.openlayers.source.VectorSource;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Sets;

import io.mapzone.arena.Messages;

/**
 * Edge function based on a table which contains a reference/assoziation/column with
 * FeatureId of another feature collection.
 *
 * @author Steffen Stundzig
 */
public class ReferenceTableEdgeFunction
        implements EdgeFunction {

    private static Log log = LogFactory.getLog( ReferenceTableEdgeFunction.class );

    private static final IMessages i18n = Messages.forPrefix( "ReferenceTableEdgeFunction" );

    private FeatureSource selectedSourceFeatureSource;

    private PropertyDescriptor selectedSourcePropertyDescriptor;

    private PropertyDescriptor selectedReferenceProperty;

    private PropertyDescriptor selectedKeyProperty;

    private FeatureSource selectReferenceSource;

    private GraphFunction graphFunction;


    @Override
    public String title() {
        return i18n.get( "title" );
    }


    @Override
    public String description() {
        return i18n.get( "description" );
    }


    @Override
    public int preferredHeight() {
        return 150;
    }


    @Override
    public void init( GraphFunction graphFunction ) {
        this.graphFunction = graphFunction;
    }


    @Override
    public void createContents( MdToolkit tk, Composite parent, VectorSource source, OlMap olMap ) {

        try {
            final FeaturePropertySelectorUI referencePropertiesUI = new FeaturePropertySelectorUI( tk, parent, prop -> {
                this.selectedReferenceProperty = prop;
                checkIfConfigurationComplete();
            } );
            final FeaturePropertySelectorUI keyPropertiesUI = new FeaturePropertySelectorUI( tk, parent, prop -> {
                this.selectedKeyProperty = prop;
                checkIfConfigurationComplete();
            } );
            final FeatureSourceSelectorUI referenceFeaturesUI = new FeatureSourceSelectorUI( tk, parent, fs -> {
                this.selectReferenceSource = fs;
                referencePropertiesUI.setFeatureSource( fs );
                keyPropertiesUI.setFeatureSource( fs );
                checkIfConfigurationComplete();
            } );

            final Label selectReferenceTableLabel = tk.createLabel( parent, i18n.get( "selectReferenceTable" ),
                    SWT.NONE );
            FormDataFactory.on( selectReferenceTableLabel ).top( 2 ).left( 0 );
            FormDataFactory.on( referenceFeaturesUI.control() ).top( selectReferenceTableLabel, 2 ).left( 0 );

            final Label selectReferencePropertyLabel = tk.createLabel( parent, i18n.get( "selectReferenceProperty" ),
                    SWT.NONE );
            FormDataFactory.on( selectReferencePropertyLabel ).top( referenceFeaturesUI.control(), 4 ).left( 4 );
            FormDataFactory.on( referencePropertiesUI.control() ).top( selectReferencePropertyLabel, 2 ).left( 4 );

            final Label selectKeyPropertyLabel = tk.createLabel( parent, i18n.get( "selectKeyProperty" ), SWT.NONE );
            FormDataFactory.on( selectKeyPropertyLabel ).top( referencePropertiesUI.control(), 4 ).left( 4 );
            FormDataFactory.on( keyPropertiesUI.control() ).top( selectKeyPropertyLabel, 2 ).left( 4 );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }


    private void checkIfConfigurationComplete() {
        if (isConfigurationComplete()) {
            graphFunction.edgeFunctionConfigurationDone();
        }
    }


    @Override
    public boolean isConfigurationComplete() {
        return selectReferenceSource != null && selectedReferenceProperty != null && selectedKeyProperty != null;
    }


    @Override
    public Collection<Edge> generateEdges( final MdToolkit tk, final IProgressMonitor monitor,
            final Map<String,Feature> sourceFeatures ) throws Exception {

        final ArrayListMultimap<Object,Feature> edgesByKeyProperty = ArrayListMultimap.create();

        // iterate on features
        FeatureCollection allFeatures = selectReferenceSource.getFeatures();
        FeatureIterator iterator = allFeatures.features();
        int featureCount = 0;
        while (iterator.hasNext()) {
            featureCount++;
            SimpleFeature referenceFeature = (SimpleFeature)iterator.next();
            Object key = referenceFeature.getAttribute( selectedKeyProperty.getName() );
            if (key == null || "".equals( key.toString() )) {
                continue;
            }
            // load the source feature
            Object reference = referenceFeature.getAttribute( selectedReferenceProperty.getName() );
            if (reference != null || !"".equals( reference )) {
                Feature sourceFeature = sourceFeatures.get( key );
                if (sourceFeature != null) {
                    edgesByKeyProperty.put( key, referenceFeature );
                }
                else {
                    // XXX proper error handling
//                    tk.createSnackbar( Appearance.FadeIn, "no source feature with " + key + " found, ignoring" );
                    log.error( "no source feature with " + key + " found, ignoring" );
                }
            }
        }
        Collection<Edge> allEdges = Sets.newHashSet();

        // now iterate on all edgesByKeyProperty and select all with more than one
        // value
        for (Object key : edgesByKeyProperty.keySet()) {
            List<Feature> bundledFeatures = edgesByKeyProperty.get( key );
            if (bundledFeatures != null && bundledFeatures.size() > 1) {
                // step into the features and create an edge for each pair
                int allFeaturesCount = bundledFeatures.size();
                for (int aFeatureCount = 0; aFeatureCount < allFeaturesCount; aFeatureCount++) {
                    Feature featureA = bundledFeatures.get( aFeatureCount );
                    for (int bFeatureCount = aFeatureCount + 1; bFeatureCount < allFeaturesCount; bFeatureCount++) {
                        Feature featureB = bundledFeatures.get( bFeatureCount );
                        allEdges.add( new Edge( key.toString(), featureA, featureB ) );
                    }
                }
            }
        }
        return allEdges;
    }
}
