/*
 * polymap.org Copyright (C) 2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package io.mapzone.arena.analytics.graph.edgefunctions;

import java.util.Collection;
import java.util.Map;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ArrayListMultimap;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.runtime.i18n.IMessages;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import io.mapzone.arena.analytics.graph.Edge;
import io.mapzone.arena.analytics.graph.Graph;
import io.mapzone.arena.analytics.graph.Messages;
import io.mapzone.arena.analytics.graph.Node;

/**
 * Edge function based on a table which contains a reference/assoziation/column with
 * FeatureId of another feature collection.
 *
 * @author Steffen Stundzig
 */
public class ReferenceTableWithDirectEdgeFunction
        extends AbstractReferenceTableEdgeFunction {

    private static Log             log  = LogFactory.getLog( ReferenceTableWithDirectEdgeFunction.class );

    private static final IMessages i18n = Messages.forPrefix( "ReferenceTableWithDirectEdgeFunction" );


    @Override
    public String title() {
        return i18n.get( "title" );
    }


    @Override
    public String description() {
        return i18n.get( "description" );
    }


    @Override
    public int generateEdges( final MdToolkit tk, final IProgressMonitor monitor,
            final Map<String,Node> sourceNodes, final Graph graph ) throws Exception {

        final ArrayListMultimap<Object,Node> edgesByKeyProperty = ArrayListMultimap.create();

        // iterate on features
        FeatureCollection allFeatures = selectedReferenceSource.getFeatures();
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
            if (reference != null && !"".equals( reference )) {
                Node sourceNode = sourceNodes.get( reference );
                if (sourceNode != null) {
                    edgesByKeyProperty.put( key, sourceNode );
                }
                else {
                    // XXX proper error handling
                    // tk.createSnackbar( Appearance.FadeIn, "no source feature with
                    // " + key + " found, ignoring" );
                    // fid--1da18301_154b9540fce_-70ea
                    // fid--1da18301_154b9540fce_-70ea
                    log.error( "no source feature with " + key + " found, ignoring" );
                }
            }
        }
        Collection<Edge> edges = transform( edgesByKeyProperty );
        for (Edge edge : edges) {
            graph.addOrUpdateEdge( edge );
        }
        return edges.size();
    }
}
