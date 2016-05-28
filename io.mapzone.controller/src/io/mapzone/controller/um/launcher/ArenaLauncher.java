/* 
 * mapzone.io
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
package io.mapzone.controller.um.launcher;

import java.net.MalformedURLException;
import java.net.URLEncoder;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.model2.runtime.ValueInitializer;

import io.mapzone.arena.jmx.ArenaConfigMBean;
import io.mapzone.controller.http.ProxyServlet;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class ArenaLauncher
        extends EclipseProjectLauncher {

    private static Log log = LogFactory.getLog( ArenaLauncher.class );

    @SuppressWarnings( "hiding" )
    public static final ValueInitializer<ArenaLauncher> defaults = new ValueInitializer<ArenaLauncher>() {
        @Override
        public ArenaLauncher initialize( ArenaLauncher proto ) throws Exception {
            EclipseProjectLauncher.defaults.initialize( proto );
            String uri = System.getProperty( "io.mapzone.controller.installArchiveUri", "file:///home/falko/servers/arena.tgz" );
            proto.installArchiveUri.set( uri );
            return proto;
        }
    };
    

    // instance *******************************************
    
    @Override
    public void start( ProjectInstanceRecord instance, IProgressMonitor monitor ) throws Exception {
        super.start( instance, monitor );
        
        configureInstance( instance ) ;    
    }
    
    
    protected void configureInstance( ProjectInstanceRecord instance ) throws MalformedURLException {
        // config via JMX
        String url = "service:jmx:rmi:///jndi/rmi://" + instance.host.get().inetAddress.get() + 
                ":" + instance.process.get().jmxPort.get() + "/jmxrmi";
        JMXServiceURL serviceUrl = new JMXServiceURL( url );
        try (
            JMXConnector connector = JMXConnectorFactory.connect( serviceUrl, null );        
        ){
            MBeanServerConnection conn = connector.getMBeanServerConnection();
            
            log.info( url );
            while (conn.queryNames( ArenaConfigMBean.NAME.get(), null ).isEmpty()) {
                log.info( "No such MBean: " + ArenaConfigMBean.NAME.get() );
                Thread.sleep( 100 );
            }
            
            // title
            ArenaConfigMBean arenaConfig = JMX.newMBeanProxy( conn, ArenaConfigMBean.NAME.get(), ArenaConfigMBean.class );
            arenaConfig.setAppTitle( instance.project.get() );

            // baseUrl
            String baseUrl = Joiner.on( "" ).join(
                    System.getProperty( "io.mapzone.controller.baseUrl", "http://localhost:8080" ),
                    ProxyServlet.SERVLET_ALIAS, "/",
                    URLEncoder.encode( instance.organisation.get(), "UTF8" ), "/",
                    URLEncoder.encode( instance.project.get(), "UTF8" ) );
            arenaConfig.setProxyUrl( baseUrl );
            
            log.info( "Instance process configured." );
        }
        catch (Exception e) {
            log.info( "", e );
        }        
    }
    
}
