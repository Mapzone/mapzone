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

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import io.mapzone.arena.analytics.graph.Graph;
import io.mapzone.arena.analytics.graph.Messages;
import io.mapzone.arena.analytics.graph.Node;
import com.google.common.collect.Maps;

/**
 * Edge function based on a distinct column with nodes for each column value and
 * edges between this values and the source nodes.
 *
 * @author Steffen Stundzig
 */
public class CompareColumnsWithEdgeNodesFunction
        extends AbstractCompareColumnsEdgeFunction {

    private static final IMessages i18n = Messages.forPrefix( "CompareColumnsWithEdgeNodesFunction" );


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

        final Map<String,Node> edgeNodes = Maps.newHashMap();

        // iterate on features
        int featureCount = 0;
        for (Node sourceNode : sourceNodes.values()) {
            Feature sourceFeature = sourceNode.feature();
            featureCount++;
            Object key = ((SimpleFeature)sourceFeature).getAttribute( selectedCompareProperty.getName() );
            // compare only if a value is set
            if (key != null && !"".equals( key.toString().trim() )) {
                Node edgeNode = edgeNodes.get( key );
                if (edgeNode == null) {
                    edgeNode = new Node( Node.Type.edge, key.toString(), sourceNode.featureSource(), null, key.toString(), 1 );
                    edgeNodes.put( key.toString(), edgeNode );
                    graph.addOrUpdateNode( edgeNode );
                }
                graph.addOrUpdateEdge( edgeNode, sourceNode );
            }
        }
        return featureCount;
    }
}
