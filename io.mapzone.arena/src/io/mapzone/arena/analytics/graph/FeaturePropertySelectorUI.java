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
import java.util.TreeMap;
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
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.feature.type.PropertyDescriptor;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Draws a combo based view with all properties, except the geometry descriptor, of a
 * specified feature source.
 *
 * @author Steffen Stundzig
 */
public class FeaturePropertySelectorUI {

    private static Log log = LogFactory.getLog( FeaturePropertySelectorUI.class );

    private FeatureSource featureSource;

    private final Combo combo;

    private final Consumer<PropertyDescriptor> onSelect;


    public FeaturePropertySelectorUI( MdToolkit tk, Composite parent, final Consumer<PropertyDescriptor> onSelect ) {
        this.combo = new Combo( parent, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN );
        this.onSelect = onSelect;
    }


    public void setFeatureSource( final FeatureSource featureSource ) {
        this.featureSource = featureSource;
        fill();
    }


    public Control control() {
        return combo;
    }


    private void fill() {
        if (combo != null && featureSource != null) {
            combo.clearSelection();

            final Collection<PropertyDescriptor> schemaDescriptors = featureSource.getSchema().getDescriptors();
            final GeometryDescriptor geometryDescriptor = featureSource.getSchema().getGeometryDescriptor();
            final TreeMap<String,PropertyDescriptor> properties = Maps.newTreeMap();
            for (PropertyDescriptor descriptor : schemaDescriptors) {
                if (geometryDescriptor == null || !geometryDescriptor.equals( descriptor )) {
                    properties.put( descriptor.getName().getLocalPart(), descriptor );
                }
            }
            final List<String> columns = Lists.newArrayList();
            columns.addAll( properties.keySet() );
            combo.setItems( columns.toArray( new String[properties.size()] ) );

            combo.addSelectionListener( new SelectionAdapter() {

                @Override
                public void widgetSelected( SelectionEvent e ) {
                    onSelect.accept( properties.get( columns.get( combo.getSelectionIndex() ) ) );
                }
            } );
            combo.getParent().layout();
        }
    }

}
