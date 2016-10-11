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
package io.mapzone.arena.share;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.ui.forms.widgets.ColumnLayout;

import io.mapzone.arena.share.content.ShareableContentProvider;

/**
 * Provides a section of a {@link Sharelet}.
 * 
 * @author Steffen Stundzig
 * @author Falko Bräutigam
 */
public interface ShareletSectionProvider {

    public String title();

    /**
     * <p/>
     * The given parent has a default {@link ColumnLayout} set. This might be chnaged
     * if necessary.
     *
     * @param parent The parent Composite with default {@link ColumnLayout} set.
     * @param contentBuilder
     */
    public void createContents( Composite parent, ShareableContentProvider... contentBuilder );

    public String[] supportedTypes();
    
}
