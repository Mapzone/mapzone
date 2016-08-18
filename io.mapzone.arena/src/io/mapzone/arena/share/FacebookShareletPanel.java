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

import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.toolkit.IPanelSection;

import org.polymap.p4.P4Plugin;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;
import io.mapzone.arena.share.ArenaContentBuilder.ArenaContent;

/**
 * Panel to share on facebook.
 *
 * @author Steffen Stundzig
 */
public class FacebookShareletPanel
        extends ShareletPanel {

    private static Log                  log  = LogFactory.getLog( FacebookShareletPanel.class );

    public static final PanelIdentifier ID   = PanelIdentifier.parse( "facebookSharelet" );

    private static final IMessages      i18n = Messages.forPrefix( "FacebookShareletPanel" );


    @Override
    protected String title() {
        return i18n.get( "title" );
    }


    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 0 ).spacing( 15 ).create() );
        Optional<ShareableContentBuilder> arenaBuilder = ShareableContentBuilders.instance().get( "application/arena", sharePanelContext.get() );
        if (arenaBuilder.isPresent()) {
            IPanelSection panel = tk().createPanelSection( parent, i18n.get( "map_title" ), SWT.BORDER );
            panel.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 1 ).spacing( 10 ).create() );

            ColumnDataFactory.on( tk().createLabel( panel.getBody(), i18n.get( "iframe" ), SWT.WRAP ) ).widthHint( width ).heightHint( 40 );
            //
            // Browser browser = new Browser( parent, SWT.BORDER );
            // browser.setUrl(
            // "https://www.facebook.com/dialog/share?app_id=145634995501895&mobile_iframe=true&href=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2F&redirect_uri=https%3A%2F%2Fdevelopers.facebook.com%2Ftools%2Fexplorer"
            // );
            // ColumnDataFactory.on( browser ).widthHint( width ).heightHint( width
            // );
            ArenaContent content = (ArenaContent)arenaBuilder.get().content();

            Button button = tk().createButton( panel.getBody(), "", SWT.NONE );
            button.setImage( ArenaPlugin.images().svgImage( "jsfiddle.svg", P4Plugin.HEADER_ICON_CONFIG ) );
            button.setToolTipText( "jsfiddle.net" );
            button.addSelectionListener( new SelectionAdapter() {

                @Override
                public void widgetSelected( SelectionEvent e ) {
                    // UrlLauncher launcher = RWT.getClient().getService(
                    // UrlLauncher.class );
                    String shareletPanelUrl = ArenaPlugin.instance().config().getProxyUrl() + "/arena#" + ID.id();

                    JavaScriptLoader loader = RWT.getClient().getService( JavaScriptLoader.class );
                    loader.require( "https://connect.facebook.net/en_US/sdk.js" );

                    JavaScriptExecutor executor = RWT.getClient().getService( JavaScriptExecutor.class );
                    // init
                    executor.execute( "FB.init({ appId:'1754931524765083', status:false, cookie:true, xfbml:false, version:'v2.0'});" );
                    // open popup
                    executor.execute( "FB.ui({ method:'share', mobile_iframe:true, href:'" + content.shareInfo
                            + "', redirect_uri:'" + shareletPanelUrl
                            + "'hashtag:'#mapzone'}, function(response){console.log(response);});" );
                }
            } );
            ColumnDataFactory.on( button ).widthHint( 96 ).heightHint( 48 ).horizAlign( Alignment.RIGHT );
        }
    }

}
