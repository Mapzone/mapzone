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

import org.eclipse.swt.widgets.Composite;
import org.polymap.core.project.IMap;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

/**
 * Base interface for all functions including UI and logic, that could be used in the RefinePanel
 *
 * @author Steffen Stundzig
 */
public interface RefineFunction {
    
    String title();
    
    String description();
    
    void createContents(final MdToolkit tk, final Composite parent);

    void init( Context<IMap> map );
}
