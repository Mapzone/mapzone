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

import static org.polymap.core.runtime.event.TypeEventFilter.ifType;

import org.geotools.data.FeatureSource;
import org.opengis.feature.type.PropertyDescriptor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import io.mapzone.arena.analytics.graph.EdgeFunctionConfigurationDoneEvent;
import io.mapzone.arena.analytics.graph.GraphFunction;
import io.mapzone.arena.analytics.graph.GraphFunctionConfigurationChangedEvent;
import io.mapzone.arena.analytics.graph.Messages;
import io.mapzone.arena.analytics.graph.ui.FeaturePropertySelectorUI;

/**
 * Abstract compare columns edge function with the configuration part.
 *
 * @author Steffen Stundzig
 */
public abstract class AbstractCompareColumnsEdgeFunction
        extends AbstractEdgeFunction {

    private static final IMessages i18n = Messages.forPrefix( "CompareColumnsEdgeFunction" );

    protected PropertyDescriptor   selectedCompareProperty;

    private GraphFunction          graphFunction;

    private Object                 graphFunctionListener;


    @Override
    public int preferredHeight() {
        return 50;
    }


    @SuppressWarnings( "hiding" )
    @Override
    public void init( final GraphFunction graphFunction ) {
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
            final Label selectComparePropertyLabel = tk.createLabel( parent, i18n.get( "selectCompareProperty" ), SWT.NONE );
            FormDataFactory.on( selectComparePropertyLabel ).top( 2 ).left( 4 );
            FormDataFactory.on( comparePropertiesUI.control() ).top( selectComparePropertyLabel, 2 ).left( 4 );

            // event listener
            EventManager.instance().subscribe( // listener
                    graphFunctionListener = new Object() {

                        @EventHandler( display = true )
                        protected void handleEvent( final GraphFunctionConfigurationChangedEvent ev ) {
                            comparePropertiesUI.setFeatureSource( (FeatureSource)ev.value.get() );
                        }
                    }, ifType( GraphFunctionConfigurationChangedEvent.class, ev -> ev.getSource().equals( this.graphFunction )
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
}
