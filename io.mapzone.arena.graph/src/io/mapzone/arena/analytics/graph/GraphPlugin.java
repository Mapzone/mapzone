/* 
 * polymap.org
 * Copyright (C) 2015-2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.arena.analytics.graph;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.polymap.rhei.batik.app.SvgImageRegistryHelper;

import org.osgi.framework.BundleContext;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class GraphPlugin 
        extends AbstractUIPlugin {

	public static final String ID = "io.mapzone.arena.analytics.graph"; //$NON-NLS-1$

	private static GraphPlugin instance;
	

	public static GraphPlugin instance() {
    	return instance;
    }

    /**
     * Shortcut for <code>instance().images</code>.
     */
    public static SvgImageRegistryHelper images() {
        return instance().images;
    }

	// instance *******************************************
	
    public SvgImageRegistryHelper   images = new SvgImageRegistryHelper( this );

    public void start( BundleContext context ) throws Exception {
        super.start( context );
        instance = this;        
    }


    public void stop( BundleContext context ) throws Exception {
        instance = null;
        super.stop( context );
    }

}
