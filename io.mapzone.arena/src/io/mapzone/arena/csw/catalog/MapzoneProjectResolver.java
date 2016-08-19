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
package io.mapzone.arena.csw.catalog;

import java.util.Map;
import java.util.Set;

import java.io.IOException;
import java.net.MalformedURLException;

import org.geotools.data.ows.Layer;
import org.geotools.data.wms.WebMapServer;
import org.geotools.ows.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Sets;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.catalog.IMetadata;
import org.polymap.core.catalog.resolve.DefaultResourceInfo;
import org.polymap.core.catalog.resolve.DefaultServiceInfo;
import org.polymap.core.catalog.resolve.IMetadataResourceResolver;
import org.polymap.core.catalog.resolve.IResolvableInfo;
import org.polymap.core.catalog.resolve.IResourceInfo;
import org.polymap.core.catalog.resolve.IServiceInfo;
import org.polymap.core.runtime.StreamIterable;
import org.polymap.core.security.SecurityContext;

import io.mapzone.arena.jmx.ArenaConfigMBean;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class MapzoneProjectResolver
        implements IMetadataResourceResolver {

    private static final Log log = LogFactory.getLog( MapzoneProjectResolver.class );

    public static final String      CONNECTION_TYPE = "Mapzone Project Service";

    
    @Override
    public boolean canResolve( IMetadata md ) {
        Map<String,String> params = md.getConnectionParams();
        return CONNECTION_TYPE.equals( params.get( CONNECTION_PARAM_TYPE ) )
                && params.containsKey( CONNECTION_PARAM_URL );
    }
    
    
    @Override
    public IResolvableInfo resolve( IMetadata md, IProgressMonitor monitor ) throws Exception {
        return MapzoneProjectServiceInfo.of( md );
    }

    
    @Override
    public Map<String,String> createParams( Object service ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    
    /**
     * 
     */
    public static class MapzoneProjectServiceInfo
            extends DefaultServiceInfo {

        public static MapzoneProjectServiceInfo of( IMetadata md ) 
                throws ServiceException, MalformedURLException, IOException {
            String url = md.getConnectionParams().get( IMetadataResourceResolver.CONNECTION_PARAM_URL );
            String user = SecurityContext.instance().getUser().getName();
            WebMapServer wms = new MapzoneWebMapServer( url, ArenaConfigMBean.REQUEST_PARAM_USER, user );
            return new MapzoneProjectServiceInfo( md, wms );
        }

        private WebMapServer wms;

        protected MapzoneProjectServiceInfo( IMetadata metadata, WebMapServer wms ) {
            super( metadata, wms.getInfo() );
            this.wms = wms;
        }

        @Override
        public <T> T createService( IProgressMonitor monitor ) throws Exception {
            return (T)wms;
        }

        @Override
        public Iterable<IResourceInfo> getResources( IProgressMonitor monitor ) {
            return StreamIterable.of( wms.getCapabilities().getLayerList().stream()
                    .map( layer -> new MapzoneProjectResourceInfo( MapzoneProjectServiceInfo.this, wms, layer ) ) );
        }
    }


    /**
     * 
     */
    public static class MapzoneProjectResourceInfo
            extends DefaultResourceInfo {
        
        private WebMapServer            wms;
        
        private Layer                   layer;

        public MapzoneProjectResourceInfo( IServiceInfo serviceInfo, WebMapServer wms, Layer layer ) {
            super( serviceInfo, wms.getInfo( layer ) );
            this.wms = wms;
            this.layer = layer;
        }

        @Override
        public Set<String> getKeywords() {
            return Sets.newHashSet( layer.getKeywords() );
        }

        @Override
        public String getDescription() {
            return "[Cascade] " + layer.getTitle() + ": " + super.getDescription() + "\n\n";
        }
        
    }
    
}
