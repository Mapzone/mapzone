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

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnDataFactory.Alignment;
import org.polymap.core.ui.ColumnLayoutFactory;

import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.toolkit.IPanelSection;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;
import io.mapzone.arena.share.content.ShareableContentBuilder;
import io.mapzone.arena.share.content.ShareableContentBuilders;
import io.mapzone.arena.share.content.ArenaContentBuilder.ArenaContent;
import io.mapzone.arena.share.content.OpenLayersContentBuilder.OpenLayersContent;
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


    @Override
    public void init( ShareletSite site ) {
        site.title.set( i18n.get( "title" ) );
        site.description.set( i18n.get( "description" ) );
        site.image.set( ArenaPlugin.images().svgImage( "embed.svg", SvgImageRegistryHelper.NORMAL48 ) );
        super.init( site );
    }


    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 0 ).spacing( 15 ).create() );
        Optional<ShareableContentBuilder> arenaBuilder = ShareableContentBuilders.instance().get( "application/arena", site().context.get() );
        if (arenaBuilder.isPresent()) {
            IPanelSection panel = tk().createPanelSection( parent, i18n.get( "iframe_title" ), SWT.BORDER );
            panel.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 1 ).spacing( 10 ).create() );

            ColumnDataFactory.on( tk().createLabel( panel.getBody(), i18n.get( "iframe" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

            StringBuffer iframe = new StringBuffer( "<iframe width='100%' height='640' src='" );
            iframe.append( ((ArenaContent)arenaBuilder.get().content()).arena );
            iframe.append( "' frameborder='0' allowfullscreen='allowfullscreen'></iframe>" );
            Text text = tk().createText( panel.getBody(), iframe.toString(), SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
            ColumnDataFactory.on( text ).widthHint( site().preferredWidth.get() ).heightHint( 80 );
        }
        Optional<ShareableContentBuilder> openLayers = ShareableContentBuilders.instance().get( "application/openlayers", site().context.get() );
        if (openLayers.isPresent()) {
            IPanelSection panel = tk().createPanelSection( parent, i18n.get( "openlayers_title" ), SWT.BORDER );
            panel.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 1 ).spacing( 10 ).create() );
            OpenLayersContent content = (OpenLayersContent)openLayers.get().content();

            ColumnDataFactory.on( tk().createLabel( panel.getBody(), i18n.get( "openlayers_head" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

            Text head = tk().createText( panel.getBody(), content.cssressource + "\n"
                    + content.jsressource, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
            ColumnDataFactory.on( head ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

            ColumnDataFactory.on( tk().createLabel( panel.getBody(), i18n.get( "openlayers_body" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

            Text body = tk().createText( panel.getBody(), content.body, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
            ColumnDataFactory.on( body ).widthHint( site().preferredWidth.get() ).heightHint( 20 );

            ColumnDataFactory.on( tk().createLabel( panel.getBody(), i18n.get( "openlayers_complete" ), SWT.WRAP ) ).widthHint( site().preferredWidth.get() ).heightHint( 40 );

            Text text = tk().createText( panel.getBody(), content.complete, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
            ColumnDataFactory.on( text ).widthHint( site().preferredWidth.get() ).heightHint( 180 );

            JsFiddleButton jsFiddle = new JsFiddleButton( panel.getBody(), tk(), content.body, content.js, content.jsressource, content.cssressource );
            ColumnDataFactory.on( jsFiddle.control() ).widthHint( 36 ).heightHint( 36 ).horizAlign( Alignment.RIGHT );
        }
    }

}
