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
import io.mapzone.arena.share.content.ArenaContentProvider.ArenaContent;
import io.mapzone.arena.share.content.ImagePngContentProvider.ImagePngContent;
import io.mapzone.arena.share.content.OpenLayersContentProvider.OpenLayersContent;
import io.mapzone.arena.share.content.ShareableContentProvider;
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


    private ShareletSectionProvider javascript() {
        return new ShareletSectionProvider() {

            @Override
            public String title() {
                return i18n.get( "openlayers_title" );
            }


            @Override
            public String supportedType() {
                return "application/openlayers";
            }


            @Override
            public void createContents( Composite parent, ShareableContentProvider contentBuilder ) {
                OpenLayersContent content = (OpenLayersContent)contentBuilder.get();

                ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "openlayers_head" ), SWT.WRAP ) ).widthHint( preferredWidth( parent ) ).heightHint( 60 );

                Text head = tk().createText( parent, content.cssressource + "\n"
                        + content.jsressource, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
                ColumnDataFactory.on( head ).widthHint( preferredWidth( parent ) ).heightHint( 40 );

                ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "openlayers_body" ), SWT.WRAP ) ).widthHint( preferredWidth( parent ) ).heightHint( 40 );

                Text body = tk().createText( parent, content.body, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
                ColumnDataFactory.on( body ).widthHint( preferredWidth( parent ) ).heightHint( 20 );

                ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "openlayers_complete" ), SWT.WRAP ) ).widthHint( preferredWidth( parent ) ).heightHint( 60 );

                Text text = tk().createText( parent, content.complete, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
                ColumnDataFactory.on( text ).widthHint( preferredWidth( parent ) ).heightHint( 180 );

                JsFiddleButton jsFiddle = new JsFiddleButton( parent, tk(), content.body, content.js, content.jsressource, content.cssressource );
                ColumnDataFactory.on( jsFiddle.control() ).widthHint( 36 ).heightHint( 36 ).horizAlign( Alignment.RIGHT );
            }
        };
    }


    private ShareletSectionProvider iframe() {
        return new ShareletSectionProvider() {

            @Override
            public String title() {
                return i18n.get( "iframe_title" );
            }


            @Override
            public String supportedType() {
                return "application/arena";
            }


            @Override
            public void createContents( Composite parent, ShareableContentProvider contentBuilder ) {
                ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "iframe" ), SWT.WRAP ) ).widthHint( preferredWidth( parent ) ).heightHint( 60 );

                StringBuffer iframe = new StringBuffer( "<iframe width='100%' height='640' src='" );
                iframe.append( ((ArenaContent)contentBuilder.get()).arena );
                iframe.append( "' frameborder='0' allowfullscreen='allowfullscreen'></iframe>" );
                Text text = tk().createText( parent, iframe.toString(), SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
                ColumnDataFactory.on( text ).widthHint( preferredWidth( parent ) ).heightHint( 80 );
            }

        };
    }


    private int preferredWidth( Composite parent ) {
        return Math.min( parent.getDisplay().getClientArea().width, site().preferredWidth.get() ) - 50;
    }


    private ShareletSectionProvider screenshot() {
        return new ShareletSectionProvider() {

            @Override
            public String title() {
                return i18n.get( "image_title" );
            }


            @Override
            public String supportedType() {
                return "image/png";
            }


            @Override
            public void createContents( Composite parent, ShareableContentProvider contentBuilder ) {
                ImagePngContent content = (ImagePngContent)contentBuilder.get();
                int width = preferredWidth( parent );

                ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "image" ), SWT.WRAP ) ).widthHint( width ).heightHint( 40 );

                StringBuffer image = new StringBuffer( "<img width='" ).append( content.imgWidth ).append( "' height='" ).append( content.imgHeight ).append( "' src='" );
                image.append( content.imgResource );
                image.append( "' alt='" ).append( ArenaConfig.getAppTitle() ).append( "'/>" );

                Text text = tk().createText( parent, image.toString(), SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
                ColumnDataFactory.on( text ).widthHint( width ).heightHint( 120 );

                ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "image_preview" ), SWT.WRAP ) ).widthHint( width ).heightHint( 20 );

                StringBuffer oreview = new StringBuffer( "<img width='" ).append( content.previewWidth ).append( "' height='" ).append( content.previewHeight ).append( "' src='" );
                oreview.append( content.previewResource );
                oreview.append( "'/>" );

                Label l = tk().createLabel( parent, oreview.toString(), SWT.BORDER );
                l.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
                // l.setText( "<i>This</i> <ins>is</ins> <b>markup!</b>" );
                ColumnDataFactory.on( l ).widthHint( Math.min( width, content.previewWidth ) ).heightHint( content.previewHeight );
                l.addMouseListener( new MouseListener() {

                    @Override
                    public void mouseUp( MouseEvent e ) {
                        UrlLauncher launcher = RWT.getClient().getService( UrlLauncher.class );
                        launcher.openURL( ((ImagePngContent)contentBuilder.get()).imgResource );
                    }


                    @Override
                    public void mouseDown( MouseEvent e ) {
                    }


                    @Override
                    public void mouseDoubleClick( MouseEvent e ) {
                    }
                } );
            }
        };
    }


    public List<ShareletSectionProvider> sections() {
        return Lists.newArrayList( javascript(), iframe(), screenshot() );
    }

}
