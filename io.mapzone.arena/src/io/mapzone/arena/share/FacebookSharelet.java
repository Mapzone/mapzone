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
import java.util.StringJoiner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;
import org.eclipse.rap.rwt.client.service.JavaScriptLoader;

import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnDataFactory.Alignment;
import org.polymap.core.ui.ColumnLayoutFactory;

import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.toolkit.IPanelSection;

import org.polymap.rap.updownload.download.DownloadService;
import org.polymap.rap.updownload.download.DownloadService.ContentProvider;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.GeoServerStarter;
import io.mapzone.arena.Messages;
import io.mapzone.arena.jmx.ArenaConfig;
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
        //
        site.image.set( ArenaPlugin.images().svgImage( "facebook.svg", SvgImageRegistryHelper.NORMAL48 ) );
        super.init( site );
    }


    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 0 ).spacing( 15 ).create() );
        Optional<ShareableContentBuilder> arenaBuilder = ShareableContentBuilders.instance().get( "application/arena", site().context.get() );
        if (arenaBuilder.isPresent()) {
            IPanelSection panel = tk().createPanelSection( parent, i18n.get( "map_title" ), SWT.BORDER );
            panel.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 1 ).spacing( 10 ).create() );

            ArenaContent content = (ArenaContent)arenaBuilder.get().content();

            Button button = tk().createButton( panel.getBody(), i18n.get( "map_button" ), SWT.NONE );
            // button.setImage( ArenaPlugin.images().svgImage( "facebook.svg",
            // P4Plugin.HEADER_ICON_CONFIG ) );
            button.setToolTipText( i18n.get( "map_tooltip" ) );
            button.addSelectionListener( new SelectionAdapter() {

                @Override
                public void widgetSelected( SelectionEvent e ) {
                    shareButtonClicked( content );
                }
            } );
            ColumnDataFactory.on( button ).widthHint( 96 ).heightHint( 48 ).horizAlign( Alignment.RIGHT );
        }
    }


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
        String download = DownloadService.registerContent( new FBContentProvider(content) );
        executor.execute( "FB.ui({ method:'share', mobile_iframe:true, href:'" + ArenaPlugin.instance().config().getProxyUrl() + ArenaPlugin.ALIAS + download
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
    

    
    public class FBContentProvider
            implements ContentProvider {

        private ArenaContent content;


        public FBContentProvider( final ArenaContent content ) {
            this.content = content;
        }


        @Override
        public String getFilename() {
            return "shareinfo.html";
        }


        @Override
        public String getContentType() {
            return "text/html;charset=utf-8";
        }


        @SuppressWarnings( "deprecation" )
        @Override
        public InputStream getInputStream() throws Exception {
            final String projectName = ArenaConfig.getAppTitle();
            // FIXME add the project description here
            final String description = ArenaConfig.getAppTitle();
            final String arenaUrl = ArenaPlugin.instance().config().getProxyUrl() + ArenaPlugin.ALIAS;
            final StringBuilder imageUrl = new StringBuilder( ArenaPlugin.instance().config().getProxyUrl() );
            imageUrl.append( GeoServerStarter.ALIAS );
            imageUrl.append( "?SERVICE=WMS&VERSION=1.3.0&REQUEST=GetMap&FORMAT=image%2Fpng&CRS=EPSG%3A3857&STYLES=&WIDTH=1200&HEIGHT=630" );
            imageUrl.append( content.shareInfo );

            StringJoiner html = new StringJoiner("\n");
            html.add( "<html>" );
            html.add( " <head>" );
            html.add( "  <title>mapzone - " + projectName + "</title>" );
            html.add( "  <meta name='author' content='mapzone' />" );
            html.add( "  <meta name='description' content='" + description + "' />" );
            html.add( "  <meta name='keywords' content='location, geo, web, osm, map, maps, styling, wms, csv, xls, georeference, geofence, geocode' />" );
            html.add( "  <meta name='robots' content='index,follow' />" );
            html.add( "  <meta name='audience' content='all' />" );
            // html.add( " <meta name='revisit-after' content='5 days' />" );
            // facebook/opengraph
            html.add( "  <meta property='og:locality' content='Leipzig'/>" );
            html.add( "  <meta property='og:country-name' content='Germany'/>" );
            html.add( "  <meta property='og:latitude' content='51.32794'/>" );
            html.add( "  <meta property='og:longitude' content='12.33126'/>" );
            html.add( "  <meta property='og:image:url' content='" + imageUrl.toString() + "' />" );
            // html.add( " <meta property='og:image:type' content='image/png' />" );
            html.add( "  <meta property='og:image:width' content='1200' />" );
            html.add( "  <meta property='og:image:height' content='630' />" );
            html.add( "  <meta property='og:type' content='article' />" );
            html.add( "  <meta property='og:site_name' content='mapzone - " + projectName + "' />" );
            // wird grad nicht von Facebook unterstützt
            // html.add( " <meta property='fb:app_id' content='1754931524765083'
            // />" );
            // html.add( " <meta property='fb:admins' content='739545402735248'
            // />" );
            html.add( "  <meta property='article:publisher' content='https://www.facebook.com/mapzoneio-1401853630109662' />" );
            html.add( "  <meta property='article:author' content='https://www.facebook.com/stundzig' />" );

            html.add( "  <meta property='og:url' content='" + arenaUrl + "' />" );

            // perform a redirect
            html.add( "  <script type='text/javascript'>window.setTimeout(function(){ window.location.href = '" + arenaUrl + "'; },10000);</script>" );
            html.add( " </head>" );
            html.add( " <body>" );
            // html.add( " <iframe src='" + arenaUrl
            // + "' width='100%' height='520' frameborder='0'
            // allowfullscreen='allowfullscreen'></iframe>" );
            html.add( " </body>" );
            html.add( "<head>" );

            return new ByteArrayInputStream(html.toString().getBytes(StandardCharsets.UTF_8.name()));
        }


        @Override
        public boolean done( boolean success ) {
            return false;
        }
    }

}
