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
package io.mapzone.controller.catalog;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CatalogPlugin
        implements BundleActivator {

    private static Log log = LogFactory.getLog( CatalogPlugin.class );

    public static final String      ID = "io.mapzone.controller.catalog";
    
    private static CatalogPlugin    instance;
    
    public static CatalogPlugin instance() {
        return instance;
    }

    
    // instance *******************************************
    
    private BundleContext               context;
    
    private CatalogRepositoryContext    catalog;
    

    @Override
    public void start( @SuppressWarnings( "hiding" ) BundleContext context ) throws Exception {
        this.context = context;
        instance = this;
        
        catalog = new CatalogRepositoryContext();
        catalog.createTestData();
    }


    @Override
    public void stop( @SuppressWarnings( "hiding" ) BundleContext context ) throws Exception {
        this.context = null;
        instance = null;
    }

    
    /**
     * The one and only catalog instance of the plugin.
     */
    public CatalogRepositoryContext catalog() {
        assert catalog != null;
        return catalog;
    }


    public Bundle getBundle() {
        return context.getBundle();
    }
    
}
