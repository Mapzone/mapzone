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
package io.mapzone.arena.share.app;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.polymap.core.runtime.i18n.IMessages;
import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;
import io.mapzone.arena.share.DefaultSectionProvider;
import io.mapzone.arena.share.Sharelet;
import io.mapzone.arena.share.ShareletSectionProvider;
import io.mapzone.arena.share.ShareletSite;
import io.mapzone.arena.share.content.ShareableContentProvider;
import io.mapzone.arena.share.content.WmsUrlProvider;
import io.mapzone.arena.share.content.WmsUrlProvider.WmsUrlContent;

/**
 * Promotes GeoServer's OWS WMS/WFS service endpoints. 
 *
 * @author Falko Bräutigam
 */
public class GeoServerSharelet
        extends Sharelet {

    private static Log log = LogFactory.getLog( GeoServerSharelet.class );

    private static final IMessages i18n = Messages.forPrefix( "GeoServerSharelet" );

    
    @Override
    public void init( ShareletSite site ) {
        super.init( site );
        site.title.set( i18n.get( "title" ) );
        site.description.set( i18n.get( "description" ) );
        site.image.set( ArenaPlugin.images().svgImage( "lan-connect.svg", ICON_CONFIG ) );
    }


    @Override
    public List<ShareletSectionProvider> sections() {
        return Lists.newArrayList( new GetCapabilitiesSection() );
    }
    

    /**
     * 
     */
    protected class GetCapabilitiesSection
            extends DefaultSectionProvider
            implements ShareletSectionProvider {

        @Override
        public String title() {
            return i18n.get( "capa_title" );
        }

        @Override
        public void createContents( Composite parent, ShareableContentProvider... contentBuilders ) {
            Label description = tk().createLabel( parent, i18n.get( "capa_description" ), SWT.WRAP );
            adaptLayout( description ).setEnabled( false );
          
            // WMS
            WmsUrlContent content = (WmsUrlContent)contentBuilders[0].get();
            String url = content.url.toExternalForm() + "?";  // authToken is in URL
            String wms = url + "SERVICE=WMS&REQUEST=GetCapabilities";
            createField( tk(), parent, "Web Map Service (WMS)", field -> {
                adaptLayout( tk().createText( field, wms, SWT.READ_ONLY ) );
                adaptLayout( tk().createLabel( field, "<a href=\"" + wms + "\" target=\"_blank\">Test...</a>" ) );
            });

            // WFS
            String wfs = url + "SERVICE=WFS&REQUEST=GetCapabilities";
            createField( tk(), parent, "Web Feature Service (WFS)", field -> {
                adaptLayout( tk().createText( field, wfs, SWT.READ_ONLY ) );
                adaptLayout( tk().createLabel( field, "<a href=\"" + wfs + "\" target=\"_blank\">Test...</a>" ) );
            });
        }

        @Override
        public String[] supportedTypes() {
            // FIXME
            return new String[] {WmsUrlProvider.MIMETYPE};
        }
        
    }
    
}
