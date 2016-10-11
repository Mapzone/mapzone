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
package io.mapzone.arena.share.app;

import java.util.List;

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
import io.mapzone.arena.share.DefaultSectionProvider;
import io.mapzone.arena.share.Sharelet;
import io.mapzone.arena.share.ShareletSectionProvider;
import io.mapzone.arena.share.ShareletSite;
import io.mapzone.arena.share.content.ArenaContentProvider.ArenaContent;
import io.mapzone.arena.share.content.ImagePngContentProvider.ImagePngContent;
import io.mapzone.arena.share.content.OpenLayersContentProvider.OpenLayersContent;
import io.mapzone.arena.share.content.ShareableContentProvider;
import io.mapzone.arena.share.ui.JsFiddleButton;

/**
 * Sharelet to embed e.g. javascript, image or JS in a blog or website.
 *
 * @author Steffen Stundzig
 * @author Falko Bräutigam
 */
public class EmbedSharelet
        extends Sharelet {

    private static final IMessages i18n = Messages.forPrefix( "EmbedSharelet" );

    private ContentProvider        provider;


    @Override
    public void init( ShareletSite site ) {
        site.title.set( i18n.get( "title" ) );
        site.description.set( i18n.get( "description" ) );
        site.priority.set( 210 );
        site.image.set( ArenaPlugin.images().svgImage( "embed.svg", SvgImageRegistryHelper.NORMAL48 ) );
        super.init( site );
    }


    /**
     * 
     */
    protected class JavascriptSectionProvider
            extends DefaultSectionProvider {

        @Override
        public String title() {
            return i18n.get( "openlayers_title" );
        }

        @Override
        public String[] supportedTypes() {
            return new String[] { "application/openlayers" };
        }

        @Override
        public void createContents( Composite parent, ShareableContentProvider... contentBuilders ) {
            OpenLayersContent content = (OpenLayersContent)contentBuilders[0].get();

            adaptLayout( tk().createLabel( parent, i18n.get( "openlayers_head" ), SWT.WRAP ) )
                    .setEnabled( false );

            createField( tk(), parent, "&lt;html&gt;&lt;head&gt;", field -> {
                ColumnDataFactory.on( tk().createText( field, content.cssressource + "\n" + content.jsressource, 
                        SWT.BORDER, SWT.WRAP, SWT.READ_ONLY ) )
                        .widthHint( width() ).heightHint( 35 );                
            });

            createField( tk(), parent, i18n.get( "openlayers_body" ), field -> {
                adaptLayout( tk().createText( field, content.body, SWT.BORDER, SWT.READ_ONLY ) );
            });

            createField( tk(), parent, i18n.get( "openlayers_complete" ), field -> {
                ColumnDataFactory.on( tk().createText( field, content.complete, 
                        SWT.BORDER, SWT.WRAP, SWT.READ_ONLY ) )
                        .widthHint( width() ).heightHint( 180 );
            });

            JsFiddleButton jsFiddle = new JsFiddleButton( parent, tk(), content.body, content.js, content.jsressource, content.cssressource );
            ColumnDataFactory.on( jsFiddle.control() ).heightHint( 36 ).horizAlign( Alignment.CENTER );
        }
    }


    private ShareletSectionProvider iframe() {
        return new ShareletSectionProvider() {

            @Override
            public String title() {
                return i18n.get( "iframe_title" );
            }


            @Override
            public String[] supportedTypes() {
                return new String[] { "application/arena" };
            }


            @Override
            public void createContents( Composite parent, ShareableContentProvider... contentBuilders ) {
                ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "iframe" ), SWT.WRAP ) ).widthHint( preferredWidth( parent ) );//.heightHint( 60 );

                StringBuffer iframe = new StringBuffer( "<iframe width='100%' height='640' src='" );
                iframe.append( ((ArenaContent)contentBuilders[0].get()).arena );
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
            public String[] supportedTypes() {
                return new String[] { "image/png" };
            }

            @Override
            public void createContents( final Composite parent, final ShareableContentProvider... contentBuilders ) {
                ImagePngContent content = (ImagePngContent)contentBuilders[0].get();
                int width = preferredWidth( parent );

                ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "image" ), SWT.WRAP ) ).widthHint( width );//.heightHint( 40 );

                StringBuffer image = new StringBuffer( "<img width='" ).append( content.imgWidth ).append( "' height='" ).append( content.imgHeight ).append( "' src='" );
                image.append( content.imgResource );
                image.append( "' alt='" ).append( ArenaConfig.getAppTitle() ).append( "'/>" );

                Text text = tk().createText( parent, image.toString(), SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
                ColumnDataFactory.on( text ).widthHint( width ).heightHint( 120 );

                ColumnDataFactory.on( tk().createLabel( parent, i18n.get( "image_preview" ), SWT.WRAP ) ).widthHint( width );//.heightHint( 20 );

                StringBuffer oreview = new StringBuffer( "<img width='" ).append( content.previewWidth ).append( "' height='" ).append( content.previewHeight ).append( "' src='" );
                oreview.append( content.previewResource );
                oreview.append( "'/>" );

                Label l = tk().createLabel( parent, oreview.toString(), SWT.BORDER );
                ColumnDataFactory.on( l ).widthHint( Math.min( width, content.previewWidth ) ).heightHint( content.previewHeight );
                l.addMouseListener( new MouseListener() {

                    @Override
                    public void mouseUp( MouseEvent e ) {
                        UrlLauncher launcher = RWT.getClient().getService( UrlLauncher.class );
                        launcher.openURL( ((ImagePngContent)contentBuilders[0].get()).imgResource );
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
        return Lists.newArrayList( new JavascriptSectionProvider(), iframe(), screenshot() );
    }

}
