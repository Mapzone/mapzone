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
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;
import org.eclipse.rap.rwt.client.service.JavaScriptLoader;

import org.polymap.core.runtime.i18n.IMessages;
import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;
import io.mapzone.arena.share.content.ArenaContentBuilder.ArenaContent;
import io.mapzone.arena.share.content.ShareableContentBuilder;
import io.mapzone.arena.share.content.ShareableContentBuilders;
import io.mapzone.arena.share.ui.ShareletPanel;

/**
 * Sharelet for facebook.
 *
 * @author Steffen Stundzig
 */
public class FacebookSharelet
        extends Sharelet {

    private static Log             log  = LogFactory.getLog( FacebookSharelet.class );

    private static final IMessages i18n = Messages.forPrefix( "FacebookSharelet" );


    @Override
    public void init( ShareletSite site ) {
        site.title.set( i18n.get( "title" ) );
        site.description.set( i18n.get( "description" ) );
        site.priority.set( 600 );
        site.image.set( ArenaPlugin.images().image( "resources/icons/facebook48.png" ) );
        super.init( site );
    }

//
//    @Override
//    public void createContents( Composite parent ) {
//        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 0 ).spacing( 15 ).create() );
//        Optional<ShareableContentBuilder> arenaBuilder = ShareableContentBuilders.instance().get( "application/arena", site().context.get() );
//        if (arenaBuilder.isPresent()) {
//            IPanelSection panel = tk().createPanelSection( parent, i18n.get( "map_title" ), SWT.BORDER );
//            panel.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 1 ).spacing( 10 ).create() );
//
//            ArenaContent content = (ArenaContent)arenaBuilder.get().content();
//
//            Button button = tk().createButton( panel.getBody(), i18n.get( "map_button" ), SWT.NONE );
//            // button.setImage( ArenaPlugin.images().svgImage( "facebook.svg",
//            // P4Plugin.HEADER_ICON_CONFIG ) );
//            button.setToolTipText( i18n.get( "map_tooltip" ) );
//            button.addSelectionListener( new SelectionAdapter() {
//
//                @Override
//                public void widgetSelected( SelectionEvent e ) {
//                    shareButtonClicked( content );
//                }
//            } );
//            ColumnDataFactory.on( button ).widthHint( 96 ).heightHint( 48 ).horizAlign( Alignment.RIGHT );
//        }
//    }


    protected void shareButtonClicked( final ArenaContent content ) {
        // UrlLauncher launcher = RWT.getClient().getService(
        // UrlLauncher.class );
        String shareletPanelUrl = ArenaPlugin.instance().config().getProxyUrl() + "/arena#" + ShareletPanel.ID.id();

        JavaScriptLoader loader = RWT.getClient().getService( JavaScriptLoader.class );
        loader.require( "https://connect.facebook.net/en_US/sdk.js" );

        JavaScriptExecutor executor = RWT.getClient().getService( JavaScriptExecutor.class );
        // init
        executor.execute( "FB.init({ appId:'1754931524765083', status:false, cookie:true, xfbml:false, version:'v2.7'});" );
        // open popup
        executor.execute( "FB.ui({ method:'share', mobile_iframe:true, href:'" + content.shareInfo
                + "', redirect_uri:'" + shareletPanelUrl
                + "', hashtag:'#mapzone'}, function(response){console.log(response);});" );
    }


    @Override
    public boolean share() {
        Optional<ShareableContentBuilder> arenaBuilder = ShareableContentBuilders.instance().get( "application/arena", site().context.get() );
        if (arenaBuilder.isPresent()) {
            ArenaContent content = (ArenaContent)arenaBuilder.get().content();
            shareButtonClicked( content );
            return true;
        }
        return super.share();
    }

    @Override
    public List<String> supportedContentTypes() {
        return Lists.newArrayList("application/arena");
    }

    @Override
    public String createContent( Composite parent, String type, ShareableContentBuilder contentBuilder ) {
        throw new RuntimeException( "must not be called, since the the sharing is done in share" );
    }
}
