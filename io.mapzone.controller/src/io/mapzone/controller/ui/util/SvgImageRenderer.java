/* 
 * polymap.org
 * Copyright (C) 2014-2015, Falko Bräutigam. All rights reserved.
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
package io.mapzone.controller.ui.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.mapzone.controller.ControllerPlugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Widget;

import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.toolkit.DefaultToolkit;
import org.polymap.rhei.batik.toolkit.IMarkdownNode;
import org.polymap.rhei.batik.toolkit.IMarkdownRenderer;
import org.polymap.rhei.batik.toolkit.MarkdownRenderOutput;

import org.polymap.rap.updownload.download.DownloadService;

/**
 * SVG images from {@link ControllerPlugin} in markdown text.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class SvgImageRenderer
        implements IMarkdownRenderer, DisposeListener {

    private static Log log = LogFactory.getLog( SvgImageRenderer.class );
    
    public static final Pattern                 urlPattern = Pattern.compile( "#([^@]+)[@]?([a-zA-Z0-9_-]*)" );
            
    private DownloadService.ContentProvider     provider;
    
    
    @Override
    public boolean render( DefaultToolkit toolkit, IMarkdownNode node, MarkdownRenderOutput out, Widget widget ) {
        Matcher urlMatcher = urlPattern.matcher( node.url() );
        if (node.type() == IMarkdownNode.Type.ExpImage && urlMatcher.matches()) {
            log.debug( "url=" + node.url() + ", text=" + node.text() );

            String svgName = urlMatcher.group( 1 );
            String configName = urlMatcher.group( 2 ).length() > 0 ? urlMatcher.group( 2 ) : SvgImageRegistryHelper.NORMAL24; 
                    
            Optional<Image> image = ControllerPlugin.images().svgImageOpt( svgName, configName );
            if (!image.isPresent()) {
                log.warn( "File not found: " + svgName);
                return false;
            }
            
            ImageLoader loader = new ImageLoader();
            loader.data = new ImageData[] { image.get().getImageData() };
            ByteArrayOutputStream encoded = new ByteArrayOutputStream();
            loader.save( encoded, SWT.IMAGE_PNG );
            
            // download handler
            assert provider == null; 
            provider = new DownloadService.ContentProvider() {
                @Override
                public InputStream getInputStream() throws Exception {
                    return new ByteArrayInputStream( encoded.toByteArray() );
                }
                @Override
                public String getFilename() {
                    return svgName;
                }
                @Override
                public String getContentType() {
                    return "image/png";
                }
                @Override
                public boolean done( boolean success ) {
                    return false;
                }
            };
            
            // prevent this from being GCed as long as the widget exists
            widget.addDisposeListener( this );

            String url = DownloadService.registerContent( provider );
            out.setUrl( url );
            out.setText( node.text() );
            return true;
        }
        return false;
    }
    
    @Override
    public void widgetDisposed( DisposeEvent ev ) {
        //DownloadService.unregisterContent( provider );
    }    

}
