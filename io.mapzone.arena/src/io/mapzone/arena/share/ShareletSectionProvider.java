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

import io.mapzone.arena.share.content.ShareableContentProvider;

/**
 * If a sharelet contains different sections, than for each section a new provider
 * should be implemented.
 * 
 *
 * @author Steffen Stundzig
 */
public interface ShareletSectionProvider {

    String title();


    void createContents( Composite parent, ShareableContentProvider contentBuilder );


    String supportedType();
}
