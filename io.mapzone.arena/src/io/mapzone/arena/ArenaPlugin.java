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
package io.mapzone.arena;

import java.util.Set;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.osgi.framework.BundleContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.polymap.rhei.batik.app.SvgImageRegistryHelper;

import io.mapzone.arena.csw.catalog.ProjectNodeSynchronizer;
import io.mapzone.arena.jmx.ArenaConfig;
import io.mapzone.arena.jmx.ArenaConfigMBean;
import io.mapzone.arena.share.ShareServletsStarter;
import io.mapzone.arena.tracker.GoogleAnalyticsTracker;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ArenaPlugin 
        extends AbstractUIPlugin {

    private static Log log = LogFactory.getLog( ArenaPlugin.class );

    public static final String ID = "io.mapzone.arena";

    public static final String ALIAS = "/arena";

	private static ArenaPlugin instance;
	

	public static ArenaPlugin instance() {
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

    private GeoServerStarter        geoServerStarter;
    
    private ArenaConfig             config = new ArenaConfig();
    
    private ProjectNodeSynchronizer catalogSynchronizer;

    private GeocodeServletStarter   geocodeService;

    private GoogleAnalyticsTracker  googleAnalyticsTracker;

    private ShareServletsStarter    shareServlets;

    public ArenaConfig config() {
        return config;
    }
    
    public void start( BundleContext context ) throws Exception {
        super.start( context );
        instance = this;
        
        System.setProperty( "http.agent", "mapzone.io (http://mapzone.io/)" );
        System.setProperty( "https.agent", "mapzone.io (http://mapzone.io/)" );

        testMBeanConnection();

        // register GeoServer
        geoServerStarter = new GeoServerStarter( context );
        geoServerStarter.open();
        
        //
        geocodeService = new GeocodeServletStarter( context );
        geocodeService.open();
        
        shareServlets = new ShareServletsStarter( context );
        shareServlets.open();
        //
        catalogSynchronizer = new ProjectNodeSynchronizer();
        
        if (!"off".equals( System.getProperty( "io.mapzone.arena.googleAnalytics", "on" ))) {
            // tracker id for property mapzone.io in analytics account of
            // steffen@mapzone.io
            googleAnalyticsTracker = new GoogleAnalyticsTracker( "UA-80603328-1" );
            googleAnalyticsTracker.start();
        }
    }


    public void stop( BundleContext context ) throws Exception {
        geoServerStarter.close();
        geocodeService.close();
        catalogSynchronizer.close();
        shareServlets.close();
        instance = null;
        super.stop( context );
    }

    
    protected void testMBeanConnection() throws Exception {
        // test connection
        String port = System.getProperty( "com.sun.management.jmxremote.port" );
        if (port != null) {
            String url = "service:jmx:rmi:///jndi/rmi://localhost:" + port + "/jmxrmi";
            JMXServiceURL serviceUrl = new JMXServiceURL( url );
            try (
                JMXConnector jmxConnector = JMXConnectorFactory.connect( serviceUrl, null );        
            ){
                MBeanServerConnection conn = jmxConnector.getMBeanServerConnection();
                Set<ObjectName> beanSet = conn.queryNames( null, null );
                beanSet.forEach( n -> log.debug( "    MBean: " + n ) );

                beanSet = conn.queryNames( ArenaConfigMBean.NAME.get(), null );
                beanSet.forEach( n -> log.debug( "    MBean: " + n ) );

                ArenaConfigMBean arenaConfig = JMX.newMBeanProxy( conn, 
                        ArenaConfigMBean.NAME.get(), ArenaConfigMBean.class );
                arenaConfig.setAppTitle( "Arena" );
            }
        }
        else {
            log.info( "No jmxremote.port specified." );
        }
    }
    
}
