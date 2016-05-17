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
package io.mapzone.arena.refine;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.geotools.data.FeatureSource;
import org.opengis.feature.type.Name;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.p4.P4Plugin;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

/**
 * Draws a combo based view with all known local feature sources.
 *
 * @author Steffen Stundzig
 */
public class FeatureSourceSelectorUI {

    private static Log log = LogFactory.getLog( FeatureSourceSelectorUI.class );

    private final Combo combo;


    public FeatureSourceSelectorUI( final MdToolkit tk, final Composite parent, final Consumer<FeatureSource> onSelect )
            throws IOException {
        this.combo = new Combo( parent, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN );

        final List<Name> featureSourceNames = P4Plugin.localCatalog().localFeaturesStore().getNames();
        // final List<String> featureSources = featureSourceNames.stream().map(
        // name -> name.getLocalPart() ).collect( Collectors.toList() );
        combo.setItems( featureSourceNames.stream().map( name -> name.getLocalPart() ).toArray( String[]::new ) );
        combo.addSelectionListener( new SelectionAdapter() {

            @Override
            public void widgetSelected( SelectionEvent e ) {
                try {
                    Name featureSourceName = featureSourceNames.get( combo.getSelectionIndex() );
                    onSelect.accept(
                            P4Plugin.localCatalog().localFeaturesStore().getFeatureSource( featureSourceName ) );
                }
                catch (IOException e1) {
                    StatusDispatcher.handleError( "", e1 );
                }
            }

        } );
    }


    public Control control() {
        return this.combo;
    }
}
