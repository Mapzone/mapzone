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

import static org.polymap.core.runtime.event.TypeEventFilter.ifType;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.FeatureSource;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.PropertyDescriptor;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import com.google.common.collect.ArrayListMultimap;

import io.mapzone.arena.Messages;

/**
 * Edge function based on a distinct column in the source table.
 *
 * @author Steffen Stundzig
 */
public class CompareColumnEdgeFunction
        extends AbstractEdgeFunction {

    private static final IMessages i18n = Messages.forPrefix( "CompareColumnEdgeFunction" );

    private PropertyDescriptor selectedCompareProperty;

    private GraphFunction graphFunction;

    private Object graphFunctionListener;


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
        return 50;
    }


    @Override
    public void init( GraphFunction graphFunction ) {
        this.graphFunction = graphFunction;
    }


    @Override
    public void createContents( final MdToolkit tk, final Composite parent, final FeatureSource sourceFeatureSource ) {

        try {

            final FeaturePropertySelectorUI comparePropertiesUI = new FeaturePropertySelectorUI( tk, parent, prop -> {
                this.selectedCompareProperty = prop;
                checkIfConfigurationComplete();
            } );
            if (sourceFeatureSource != null) {
                comparePropertiesUI.setFeatureSource( sourceFeatureSource );
            }
            final Label selectComparePropertyLabel = tk.createLabel( parent, i18n.get( "selectCompareProperty" ),
                    SWT.NONE );
            FormDataFactory.on( selectComparePropertyLabel ).top( 2 ).left( 4 );
            FormDataFactory.on( comparePropertiesUI.control() ).top( selectComparePropertyLabel, 2 ).left( 4 );

            // event listener
            EventManager.instance().subscribe( // listener
                    graphFunctionListener = new Object() {

                        @EventHandler(display = true)
                        protected void handleEvent( final GraphFunctionConfigurationChangedEvent ev ) {
                            comparePropertiesUI.setFeatureSource( (FeatureSource)ev.value.get() );
                        }
                    },
                    ifType( GraphFunctionConfigurationChangedEvent.class,
                            ev -> ev.getSource().equals( this.graphFunction )
                                    && ev.propertyName.get().equals( "sourceFeatureSource" )
                                    && ev.value.isPresent() ) );

        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }


    private void checkIfConfigurationComplete() {
        if (isConfigurationComplete()) {
            EventManager.instance().publish( new EdgeFunctionConfigurationDoneEvent( this, Boolean.TRUE ) );
        }
    }


    @Override
    public boolean isConfigurationComplete() {
        return selectedCompareProperty != null;
    }


    @Override
    public Collection<Edge> generateEdges( final MdToolkit tk, final IProgressMonitor monitor,
            final Map<String,Node> sourceFeatures ) throws Exception {

        final ArrayListMultimap<Object,Node> edgesByKeyProperty = ArrayListMultimap.create();

        // iterate on features
        int featureCount = 0;
        for (Node sourceNode : sourceFeatures.values()) {
            Feature sourceFeature = sourceNode.feature();
            featureCount++;
            Object key = ((SimpleFeature)sourceFeature).getAttribute( selectedCompareProperty.getName() );
            // compare only if a value is set
            if (key != null && !"".equals( key.toString().trim() )) {
                edgesByKeyProperty.put( key, sourceNode );
            }
        }
        return transform( edgesByKeyProperty );
    }
}
