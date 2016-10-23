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
package io.mapzone.arena.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.service.geoserver.GeoServerPlugin;

import org.polymap.p4.P4AppDesign;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class ArenaConfig
        implements ArenaConfigMBean {

    private static final Log log = LogFactory.getLog( ArenaConfig.class );
    
    private static ArenaConfig      instance;
    
    /**
     * The global instance of this JVM.
     */
    public static ArenaConfig instance() {
        return instance;
    }
    
    // instance *******************************************
    
    private String                  authToken;
    
    private String                  catalogServerUrl = "http://localhost:8090/csw";

    private String                  projectCatalogId;

    /**
     * The ArenaPlugin must be started automatically at level 5 in order to make this
     * available right after startup.
     */
    public ArenaConfig() {
        assert instance == null;
        instance = this;
        try {
            MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
            mbeanServer.registerMBean( this, ArenaConfigMBean.NAME.get() );
            log.info( "MBean registered." );
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }        
    }
    
    @Override
    public void setAppTitle( String title ) {
        log.info( "setAppTitle(): " + title );
        P4AppDesign.appTitle = title;
        GeoServerPlugin.instance().baseName.set( title );
    }
    
    public static String getAppTitle() {
        return P4AppDesign.appTitle;
    }

    @Override
    public void setProxyUrl( String proxyUrl ) {
        log.info( "setProxyUrl(): " +  proxyUrl );
        assert authToken != null;
        GeoServerPlugin.instance().baseUrl.set( proxyUrl + GEOSERVER_ALIAS + "/" + authToken );
    }

    @Override
    public String getProxyUrl() {
        return GeoServerPlugin.instance().baseUrl.get();
    }

    @Override
    public void setServiceAuthToken( String authToken ) {
        log.info( "setServiceAuthToken(): " +  authToken );
        this.authToken = authToken;
    }

    public String getServiceAuthToken() {
        return authToken;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Defaults for testing: <code>http://localhost:8090/csw</code>
     */
    @Override
    public String getCatalogServerUrl() {
        return catalogServerUrl;
    }
    
    @Override
    public void setCatalogServerUrl( String url ) {
        log.info( "setCatalogServerUrl(): " +  url );
        this.catalogServerUrl = url;
    }

    @Override
    public String getProjectCatalogId() {
        return projectCatalogId;
        
    }

    @Override
    public void setProjectCatalogId( String projectCatalogId ) {
        log.info( "setProjectCatalogId(): " + projectCatalogId );
        this.projectCatalogId = projectCatalogId;
    }
    
}
