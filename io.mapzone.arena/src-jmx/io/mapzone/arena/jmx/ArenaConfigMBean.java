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

import javax.management.ObjectName;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.PlainLazyInit;

/**
 * ...
 *
 * @author Falko Bräutigam
 */
public interface ArenaConfigMBean {

    /**
     * URL param that specifies the mapzone user of service (request). Checked by
     * io.mapzone.controller.vm.http.ServiceAuthProvision. Set for example by
     * {@link MapzoneProjectResolver}
     */
    public static final String              REQUEST_PARAM_USER = "_user$!._";

    /**
     * The magic string used in metadata records to signal a connection param.
     * These params are used by io.mapzone.arena.csw.catalog.MapzoneProjectResolver
     * to create a WMS/WFS service.
     */
    public static final String              CONNECTION_PARAM_NAME = "Mapzone connection parameter"; 

    /**
     * The magic string used in metadata records to signal a connection param.
     * These params are used by io.mapzone.arena.csw.catalog.MapzoneProjectResolver
     * to create a WMS/WFS service.
     */
    public static final String              MAPZONE_SERVICE_TYPE = "Mapzone project service"; 

    /**
     * The servlet alias used by {@link GeoServerStarter GeoServer} to provide OWS
     * services.
     */
    public static final String              GEOSERVER_ALIAS = "/ows";

    public static final Lazy<ObjectName>    NAME = new PlainLazyInit( () -> {
        try {
            return ObjectName.getInstance( "io.mapzone.arena:type=ArenaConfig" );
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
    });
    
    
    // interface ******************************************
    
    public void setAppTitle( String title );
    
    /**
     * The base URL under which we are available to the internet. This delegates to
     * {@link GeoServerPlugin#baseUrl} which is initialized to
     * <code>http://localhost:port/</code>.
     */
    public String getProxyUrl();
    
    /**
     * See {@link #getProxyUrl()}.
     */
    public void setProxyUrl( String proxyUrl );

    public void setServiceAuthToken( String authToken );

    
    /**
     * The base URL of the catalog server (CSW) that this app instance uses to
     * synchronize metadata of project and layers with.
     *
     * @see ProjectNodeSynchronizer
     */
    public String getCatalogServerUrl();

    /**
     * See {@link #getCatalogServerUrl()}.
     */
    void setCatalogServerUrl( String url );

    /**
     * The identifier of the project in the global metadata catalog.
     *
     * @see ProjectNodeSynchronizer
     */
    public String getProjectCatalogId();

    public void setProjectCatalogId( String projectCatalogId );

}
