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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.FeatureSource;
import org.opengis.feature.type.PropertyDescriptor;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import io.mapzone.arena.analytics.graph.EdgeFunctionConfigurationDoneEvent;
import io.mapzone.arena.analytics.graph.Messages;
import io.mapzone.arena.analytics.graph.ui.FeaturePropertySelectorUI;
import io.mapzone.arena.analytics.graph.ui.FeatureSourceSelectorUI;

/**
 * Edge function based on a table which contains a reference/assoziation/column with
 * FeatureId of another feature collection.
 *
 * @author Steffen Stundzig
 */
public abstract class AbstractReferenceTableEdgeFunction
        extends AbstractEdgeFunction {

    private static Log             log  = LogFactory.getLog( AbstractReferenceTableEdgeFunction.class );

    private static final IMessages i18n = Messages.forPrefix( "ReferenceTableEdgeFunction" );

    protected PropertyDescriptor   selectedReferenceProperty;

    protected PropertyDescriptor   selectedKeyProperty;

    protected FeatureSource        selectedReferenceSource;


    @Override
    public int preferredHeight() {
        return 150;
    }


    @Override
    public void createContents( final MdToolkit tk, final Composite parent, final FeatureSource sourceFeatureSource ) {

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
                this.selectedReferenceSource = fs;
                referencePropertiesUI.setFeatureSource( fs );
                keyPropertiesUI.setFeatureSource( fs );
                checkIfConfigurationComplete();
            } );

            final Label selectReferenceTableLabel = tk.createLabel( parent, i18n.get( "selectReferenceTable" ), SWT.NONE );
            FormDataFactory.on( selectReferenceTableLabel ).top( 2 ).left( 0 );
            FormDataFactory.on( referenceFeaturesUI.control() ).top( selectReferenceTableLabel, 2 ).left( 0 );

            final Label selectReferencePropertyLabel = tk.createLabel( parent, i18n.get( "selectReferenceProperty" ), SWT.NONE );
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
            EventManager.instance().publish( new EdgeFunctionConfigurationDoneEvent( this, Boolean.TRUE ) );
        }
    }


    @Override
    public boolean isConfigurationComplete() {
        return selectedReferenceSource != null && selectedReferenceProperty != null && selectedKeyProperty != null;
    }
}
