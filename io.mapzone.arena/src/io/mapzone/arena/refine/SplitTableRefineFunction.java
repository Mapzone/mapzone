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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Composite;
import org.polymap.core.project.IMap;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import io.mapzone.arena.Messages;

/**
 * SplitTable function with UI and logic.
 * 
 *
 * @author Steffen Stundzig
 */
public class SplitTableRefineFunction implements RefineFunction {

    private static Log log = LogFactory.getLog( SplitTableRefineFunction.class );

    private static final IMessages i18n = Messages.forPrefix( "SplitTableRefineFunction" );

    @Override
    public String title() {
        return i18n.get("title");
    }

    @Override
    public String description() {
        return i18n.get("description");
    }

    @Override
    public void createContents( final MdToolkit tk, final Composite parent ) {
    }

    @Override
    public void init( Context<IMap> map ) {
    }
}
