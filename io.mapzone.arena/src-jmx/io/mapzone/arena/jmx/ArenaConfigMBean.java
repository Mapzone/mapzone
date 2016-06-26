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
 * 
 *
 * @author Falko Bräutigam
 */
public interface ArenaConfigMBean {

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

}
