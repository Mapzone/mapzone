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

import java.lang.management.ManagementFactory;

import javax.management.JMX;
import javax.management.MBeanServer;
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

import org.polymap.service.geoserver.jmx.GeoServerConfig;
import org.polymap.service.geoserver.jmx.GeoServerConfigMBean;

import io.mapzone.arena.jmx.ArenaConfig;
import io.mapzone.arena.jmx.ArenaConfigMBean;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ArenaPlugin 
        extends AbstractUIPlugin {

    private static Log log = LogFactory.getLog( ArenaPlugin.class );

    public static final String ID = "io.mapzone.arena";

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

    /**
     * Start JMX as soon as possible.
     */
    static {
        try {
            MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
            mbeanServer.registerMBean( new GeoServerConfig(), GeoServerConfigMBean.NAME.get() );
            mbeanServer.registerMBean( new ArenaConfig(), ArenaConfigMBean.NAME.get() );
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
    }
    
	// instance *******************************************
	
    public SvgImageRegistryHelper   images = new SvgImageRegistryHelper( this );

    
    public void start( BundleContext context ) throws Exception {
        super.start( context );
        instance = this;
        
        // test connection
        String url = "service:jmx:rmi:///jndi/rmi://localhost:1617/jmxrmi";
        JMXServiceURL serviceUrl = new JMXServiceURL( url );
        try (
            JMXConnector jmxConnector = JMXConnectorFactory.connect( serviceUrl, null );        
        ){
            MBeanServerConnection conn = jmxConnector.getMBeanServerConnection();
            Set<ObjectName> beanSet = conn.queryNames( null, null );
            beanSet.forEach( n -> log.info( "    MBean: " + n ) );
            
            GeoServerConfigMBean mbean = JMX.newMBeanProxy( conn, GeoServerConfigMBean.NAME.get(), GeoServerConfigMBean.class );
            log.info( "Services: proxyUrl=" + mbean.getProxyUrl() );
            
            ArenaConfigMBean arenaConfig = JMX.newMBeanProxy( conn, 
                    ArenaConfigMBean.NAME.get(), ArenaConfigMBean.class );
            arenaConfig.setAppTitle( "Arena" );
        } 
    }


    public void stop( BundleContext context ) throws Exception {
        instance = null;
        super.stop( context );
    }
    
}
