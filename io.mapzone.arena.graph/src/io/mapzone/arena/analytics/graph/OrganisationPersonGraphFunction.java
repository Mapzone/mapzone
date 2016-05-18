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
package io.mapzone.arena.analytics.graph;

import java.util.Map;

import java.io.IOException;

import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.runtime.IProgressMonitor;
import org.polymap.core.data.util.NameImpl;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.p4.P4Plugin;

public class OrganisationPersonGraphFunction
        extends AbstractGraphFunction {

    private FeatureSource           featureSource;

    private static Log              log         = LogFactory.getLog( OrganisationPersonGraphFunction.class );


    @Override
    public String title() {
        return "Q - mapzone recherche 6";
    }


    @Override
    public String description() {
        return "Q - persons and organizations based on 'Mapzone-Recherche-6'";
    }


    @Override
    public void createContents( final MdToolkit tk, final Composite parent, final Graph graph ) {
        super.createContents( tk, parent, graph );

        try {
            featureSource = P4Plugin.localCatalog().localFeaturesStore().getFeatureSource( new NameImpl( "Mapzone-Recherche-06" ) );
            if (featureSource != null) {
                fab.setVisible( true );
                fab.setEnabled( true );
            }
            else {
                tk.createSnackbar( Appearance.FadeIn, "Dataset 'Mapzone-Recherche-06' not found" );
            }
        }
        catch (IOException e1) {
            StatusDispatcher.handleError( "", e1 );
        }

        // final Label selectLabel = tk.createLabel( parent, "using
        // mapzone-recherche-06", SWT.BORDER );
        // FormDataFactory.on( selectLabel ).fill().top( 15 ).left( 1 ).noBottom();
    }


    @Override
    public void generate( MdToolkit tk, IProgressMonitor monitor, Graph graph ) throws Exception {
        tk.createSnackbar( Appearance.FadeIn, "Analysis started - stay tuned" );

        final Map<String,Node> organisations = Maps.newHashMap();
        final Map<String,Node> persons = Maps.newHashMap();
        final Multimap<Node,Node> organisation2Persons = ArrayListMultimap.create();
        final Multimap<Node,Node> person2Organisations = ArrayListMultimap.create();

        // iterate on features
        // create Node for each organisation
        // increase weight for each entry per organisation
        FeatureIterator iterator = featureSource.getFeatures().features();
        int i = 0;
        while (iterator.hasNext() && i < 5000) {
            i++;
            SimpleFeature feature = (SimpleFeature)iterator.next();
            String organisationKey = (String)feature.getAttribute( "Organisation" );
            Node organisationFeature = organisations.get( organisationKey );
            if (organisationFeature == null) {
                organisationFeature = new Node( Node.Type.edge, "o:"
                        + feature.getID(), featureSource, feature, organisationKey, 1 );
                organisations.put( organisationKey, organisationFeature );
                graph.addOrUpdateNode( organisationFeature );
            }
            else {
                // add weight
                int size = organisation2Persons.get( organisationFeature ).size() + 1;
                if (size <= 15) {
                    organisationFeature.increaseWeight();
                }
                graph.addOrUpdateNode( organisationFeature );
            }
            String personKey = (String)feature.getAttribute( "Name" ) + " " + (String)feature.getAttribute( "Vorname" );
            Node personFeature = persons.get( personKey );
            if (personFeature == null) {
                personFeature = new Node( Node.Type.node, "p:"
                        + feature.getID(), featureSource, feature, personKey, 1 );
                persons.put( personKey, personFeature );
                graph.addOrUpdateNode( personFeature );
            }
            else {
                int size = person2Organisations.get( personFeature ).size() + 1;
                if (size <= 15) {
                    personFeature.increaseWeight();
                }
                graph.addOrUpdateNode( personFeature );
            }
            // add also the person to the organisation
            organisation2Persons.put( organisationFeature, personFeature );
            person2Organisations.put( personFeature, organisationFeature );

            graph.addOrUpdateEdge( organisationFeature, personFeature );

            if (i % 100 == 0) {
                log.info( "added " + i );
            }
        }
        tk.createSnackbar( Appearance.FadeIn, organisations.size() + " organisations, " + persons.size()
                + " persons and " + organisation2Persons.size() + " relations analysed" );
        organisations.clear();
        persons.clear();
        organisation2Persons.clear();
        person2Organisations.clear();
        graph.layout();
    }
}
