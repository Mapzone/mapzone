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

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnDataFactory.Alignment;
import org.polymap.core.ui.ColumnLayoutFactory;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.IPanelSection;

import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;
import io.mapzone.arena.share.OpenLayersContentBuilder.OpenLayersContent;

/**
 * Panel to share e.g. javascript, image or url to a blog.
 *
 * @author Steffen Stundzig
 */
public class EmbedShareletPanel
        extends P4Panel {

    private static Log                   log   = LogFactory.getLog( EmbedShareletPanel.class );

    public static final PanelIdentifier  ID    = PanelIdentifier.parse( "embedSharelet" );

    private static final IMessages       i18n  = Messages.forPrefix( "EmbedShareletPanel" );

    @Mandatory
    @Scope( SharePanel.SCOPE )
    protected Context<SharePanelContext> sharePanelContext;

    private final static int             width = 350;


    @Override
    public void init() {
        site().minWidth.set( width );
        site().preferredWidth.set( 500 );
        site().title.set( i18n.get( "title" ) );
    }


    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 0 ).spacing( 15 ).create() );
        Optional<ShareableContentBuilder> mapzoneBuilder = ShareableContentBuilders.instance().get( "application/mapzone", sharePanelContext.get() );
        if (mapzoneBuilder.isPresent()) {
            IPanelSection panel = tk().createPanelSection( parent, i18n.get( "iframe_title" ), SWT.BORDER );
            panel.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 1 ).spacing( 10 ).create() );

            ColumnDataFactory.on( tk().createLabel( panel.getBody(), i18n.get( "iframe" ), SWT.WRAP ) ).widthHint( width ).heightHint( 40 );

            StringBuffer iframe = new StringBuffer( "<iframe width=\"100%\" height=\"640\" src=\"" );
            iframe.append( ((URL)mapzoneBuilder.get().content()).toExternalForm() );
            iframe.append( "\" frameborder=\"0\" allowfullscreen webkitallowfullscreen mozallowfullscreen oallowfullscreen msallowfullscreen></iframe>" );
            Text text = tk().createText( panel.getBody(), iframe.toString(), SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
            ColumnDataFactory.on( text ).widthHint( width ).heightHint( 80 );
        }
        Optional<ShareableContentBuilder> openLayers = ShareableContentBuilders.instance().get( "application/openlayers", sharePanelContext.get() );
        if (openLayers.isPresent()) {
            IPanelSection panel = tk().createPanelSection( parent, i18n.get( "openlayers_title" ), SWT.BORDER );
            panel.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 1 ).spacing( 10 ).create() );
            OpenLayersContent content = (OpenLayersContent)openLayers.get().content();

            ColumnDataFactory.on( tk().createLabel( panel.getBody(), i18n.get( "openlayers_head" ), SWT.WRAP ) ).widthHint( width ).heightHint( 40 );

            Text head = tk().createText( panel.getBody(), content.cssressource + "\n"
                    + content.jsressource, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
            ColumnDataFactory.on( head ).widthHint( width ).heightHint( 40 );

            ColumnDataFactory.on( tk().createLabel( panel.getBody(), i18n.get( "openlayers_body" ), SWT.WRAP ) ).widthHint( width ).heightHint( 40 );

            Text body = tk().createText( panel.getBody(), content.body, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
            ColumnDataFactory.on( body ).widthHint( width ).heightHint( 20 );

            ColumnDataFactory.on( tk().createLabel( panel.getBody(), i18n.get( "openlayers_complete" ), SWT.WRAP ) ).widthHint( width ).heightHint( 40 );

            Text text = tk().createText( panel.getBody(), content.complete, SWT.BORDER, SWT.WRAP, SWT.READ_ONLY );
            ColumnDataFactory.on( text ).widthHint( width ).heightHint( 180 );

            Button button = tk().createButton( panel.getBody(), "", SWT.NONE );
            button.setImage( ArenaPlugin.images().svgImage( "jsfiddle.svg", P4Plugin.HEADER_ICON_CONFIG) );
            button.setToolTipText( "jsfiddle.net" );
            ColumnDataFactory.on( button ).widthHint( 36 ).heightHint( 36 ).horizAlign( Alignment.RIGHT );
            button.addSelectionListener( new SelectionAdapter() {

                @Override
                public void widgetSelected( SelectionEvent e ) {
                    // UrlLauncher launcher = RWT.getClient().getService(
                    // UrlLauncher.class );
                    // launcher.openURL(
                    // ArenaPlugin.instance().config().getProxyUrl() + "/jsfiddle" );
                    JavaScriptExecutor executor = RWT.getClient().getService( JavaScriptExecutor.class );
                    StringBuffer js = new StringBuffer( "javascript:" );
                    js.append( "var form = document.createElement('form');" );
                    js.append( "form.setAttribute('method', 'POST');" );
                    js.append( "form.setAttribute('target', '_blank');" );
                    js.append( "form.setAttribute('action', 'http://jsfiddle.net/api/post/library/pure/');" );
                    // html
                    js.append( "var hiddenField = document.createElement('input');" );
                    js.append( "hiddenField.setAttribute('name', 'html');" );
                    js.append( "hiddenField.setAttribute('value', \"" ).append( content.body.replaceAll( "\n", "" ).trim() ).append( "\");" );
                    js.append( "form.appendChild(hiddenField);" );
                    // js
                    js.append( "hiddenField = document.createElement('input');" );
                    js.append( "hiddenField.setAttribute('name', 'js');" );
                    js.append( "hiddenField.setAttribute('value', \"" ).append( content.js.replaceAll( "\n", "" ).trim() ).append( "\");" );
                    js.append( "form.appendChild(hiddenField);" );
                    // resources
                    js.append( "hiddenField = document.createElement('input');" );
                    js.append( "hiddenField.setAttribute('name', 'resources');" );
                    js.append( "hiddenField.setAttribute('value', \"" ).append( content.jsressource ).append( "," ).append( content.cssressource ).append( "\");" );
                    js.append( "form.appendChild(hiddenField);" );
                    // wrap
                    js.append( "hiddenField = document.createElement('input');" );
                    js.append( "hiddenField.setAttribute('name', 'wrap');" );
                    js.append( "hiddenField.setAttribute('value', 'd');" );
                    js.append( "form.appendChild(hiddenField);" );
                    // title
                    js.append( "hiddenField = document.createElement('input');" );
                    js.append( "hiddenField.setAttribute('name', 'title');" );
                    js.append( "hiddenField.setAttribute('value', \"" ).append( ArenaPlugin.instance().config().getAppTitle() ).append( "\");" );
                    js.append( "form.appendChild(hiddenField);" );
                    // description
                    js.append( "hiddenField = document.createElement('input');" );
                    js.append( "hiddenField.setAttribute('name', 'description');" );
                    js.append( "hiddenField.setAttribute('value', \"" ).append( "hit Tidy to format the JS code" ).append( "\");" );
                    js.append( "form.appendChild(hiddenField);" );
                    
                    js.append( "document.body.appendChild(form);" );
                    js.append( "form.submit();" );
                    js.append( "document.body.removeChild(form);" );

                    executor.execute( js.toString() );
                }
            } );
        }
    }

}
