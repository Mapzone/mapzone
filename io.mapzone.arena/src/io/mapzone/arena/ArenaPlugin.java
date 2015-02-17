/*
 * Copyright (C) 2015 Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.osgi.framework.BundleContext;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ArenaPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "io.mapzone.arena"; //$NON-NLS-1$

	private static ArenaPlugin instance;
	

	public static ArenaPlugin instance() {
    	return instance;
    }


    public void start( BundleContext context ) throws Exception {
        super.start( context );
        instance = this;        
    }


    public void stop( BundleContext context ) throws Exception {
        instance = null;
        super.stop( context );
    }

}
