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
package io.mapzone.arena.share;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnLayoutFactory;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.p4.P4Panel;

/**
 * Helpers for implementing a {@link ShareletSectionProvider}. 
 *
 * @author Falko Bräutigam
 */
public abstract class DefaultSectionProvider
        implements ShareletSectionProvider {

    public LabeledField createField( MdToolkit tk, Composite parent, String text, Consumer<Composite> contents ) {
        return createField( tk, parent, text, SWT.NONE, contents );
    }

    
    public <T extends Control> T adaptLayout( T control ) {
        control.setLayoutData( ColumnDataFactory.defaults().widthHint( width() ).create() );
        return control;
    }
    
    
    public int width() {
        // XXX
        return P4Panel.SIDE_PANEL_WIDTH-50;        
    }
    
    
    public LabeledField createField( MdToolkit tk, Composite parent, String text, int style, Consumer<Composite> contents ) {
        LabeledField field = new LabeledField( parent, style );
        tk.adapt( field );
        
        tk.createLabel( field, "<b>" + text + "</b>" );
        
        if (contents != null) {
            contents.accept( field );
        }
        return field;
    }
    
    /**
     * 
     */
    protected class LabeledField
            extends Composite {

        public LabeledField( Composite parent, int style ) {
            super( parent, style );
            adaptLayout( this );
            setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( 1 ).create() );
        }
        
    }
    
}
