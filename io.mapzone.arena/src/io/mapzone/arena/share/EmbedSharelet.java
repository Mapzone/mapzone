/*
 * polymap.org Copyright (C) 2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package io.mapzone.arena.share;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.UrlLauncher;

import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnDataFactory.Alignment;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rap.updownload.download.DownloadService.ContentProvider;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;
import io.mapzone.arena.jmx.ArenaConfig;
import io.mapzone.arena.share.content.ArenaContentBuilder.ArenaContent;
import io.mapzone.arena.share.content.ImagePngContentBuilder.ImagePngContent;
import io.mapzone.arena.share.content.OpenLayersContentBuilder.OpenLayersContent;
import io.mapzone.arena.share.content.ShareableContentBuilder;
import io.mapzone.arena.share.ui.JsFiddleButton;

/**
 * Sharelet to embed e.g. javascript, image or JS in a blog or website.
 *
 * @author Steffen Stundzig
 */
public class EmbedSharelet
        extends Sharelet {

    private static Log             log  = LogFactory.getLog( EmbedSharelet.class );

    private static final IMessages i18n = Messages.forPrefix( "EmbedSharelet" );

    private ContentProvider        provider;


    @Override
    public void init( ShareletSite site ) {
        site.title.set( i18n.get( "title" ) );
        site.description.set( i18n.get( "description" ) );
        site.priority.set( 500 );
        site.image.set( ArenaPlugin.images().svgImage( "embed.svg", SvgImageRegistryHelper.NORMAL48 ) );
        super.init( site );
    }


    @Override
    public String createContent( Composite parent, String type, ShareableContentBuilder contentBuilder ) {
        if ("application/openlayers".equals( type )) {
            return createOpenlayersContent( parent, contentBuilder );
        }
        if ("application/arena".equals( type )) {
            return createArenaContent( parent, contentBuilder );
        }
        if ("image/png".equals( type )) {
            return createImageContent( parent, contentBuilder );
        }
        throw new RuntimeException( "Unsupported type " + type );
    }


    private String createOpenlayersContent( Composite panel, ShareableContentBuilder contentBuilder ) {

        OpenLayersContent content = (OpenLayersContent)contentBuilder.content();

        ColumnDataFactory.on( tk().createLabel( panel, i18n.get( "openlayers_head" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

        Text head = tk().createText( panel, content.cssressource + "\n"
                + content.jsressource, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
        ColumnDataFactory.on( head ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

        ColumnDataFactory.on( tk().createLabel( panel, i18n.get( "openlayers_body" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

        Text body = tk().createText( panel, content.body, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
        ColumnDataFactory.on( body ).widthHint( site().preferredWidth.get() ).heightHint( 20 );

        ColumnDataFactory.on( tk().createLabel( panel, i18n.get( "openlayers_complete" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

        Text text = tk().createText( panel, content.complete, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
        ColumnDataFactory.on( text ).widthHint( site().preferredWidth.get() ).heightHint( 180 );

        JsFiddleButton jsFiddle = new JsFiddleButton( panel, tk(), content.body, content.js, content.jsressource, content.cssressource );
        ColumnDataFactory.on( jsFiddle.control() ).widthHint( 36 ).heightHint( 36 ).horizAlign( Alignment.RIGHT );

        return i18n.get( "openlayers_title" );

    }


    private String createArenaContent( Composite parent, ShareableContentBuilder contentBuilder ) {
        ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "iframe" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

        StringBuffer iframe = new StringBuffer( "<iframe width='100%' height='640' src='" );
        iframe.append( ((ArenaContent)contentBuilder.content()).arena );
        iframe.append( "' frameborder='0' allowfullscreen='allowfullscreen'></iframe>" );
        Text text = tk().createText( parent, iframe.toString(), SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
        ColumnDataFactory.on( text ).widthHint( site().preferredWidth.get() ).heightHint( 80 );

        return i18n.get( "iframe_title" );
    }


    private String createImageContent( Composite parent, ShareableContentBuilder contentBuilder ) {
        ImagePngContent content = (ImagePngContent)contentBuilder.content();

        ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "image" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

        StringBuffer image = new StringBuffer( "<img width='" ).append( content.imgWidth ).append( "' height='" ).append( content.imgHeight ).append( "' src='" );
        image.append( content.imgResource );
        image.append( "' alt='" ).append( ArenaConfig.getAppTitle() ).append( "'/>" );

        Text text = tk().createText( parent, image.toString(), SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
        ColumnDataFactory.on( text ).widthHint( site().preferredWidth.get() ).heightHint( 80 );

        ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "image_preview" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 20 );

        StringBuffer oreview = new StringBuffer( "<img width='" ).append( content.previewWidth ).append( "' height='" ).append( content.previewHeight ).append( "' src='" );
        oreview.append( ((ImagePngContent)contentBuilder.content()).previewResource );
        oreview.append( "'/>" );

        Label l = tk().createLabel( parent, oreview.toString() );
        l.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
        // l.setText( "<i>This</i> <ins>is</ins> <b>markup!</b>" );
        l.addMouseListener( new MouseListener() {

            @Override
            public void mouseUp( MouseEvent e ) {
                UrlLauncher launcher = RWT.getClient().getService( UrlLauncher.class );
                launcher.openURL( ((ImagePngContent)contentBuilder.content()).imgResource );
            }


            @Override
            public void mouseDown( MouseEvent e ) {
            }


            @Override
            public void mouseDoubleClick( MouseEvent e ) {
            }
        } );
        return i18n.get( "image_title" );
    }


    @Override
    public List<String> supportedContentTypes() {
        return Lists.newArrayList( "application/openlayers", "application/arena", "image/png" );
    }
}
