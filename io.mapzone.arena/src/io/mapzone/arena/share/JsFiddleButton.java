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

import java.util.StringJoiner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.p4.P4Plugin;

import io.mapzone.arena.ArenaPlugin;

/**
 * A simple button which opens a browser at jsfiddle.net.
 * 
 *
 * @author Steffen Stundzig
 */
public class JsFiddleButton {

    private Button button;


    public JsFiddleButton( Composite parent, MdToolkit tk, String htmlCode, String jsCode, String... resources ) {
        button = tk.createButton( parent, "", SWT.NONE );
        button.setImage( ArenaPlugin.images().svgImage( "jsfiddle.svg", P4Plugin.HEADER_ICON_CONFIG ) );
        button.setToolTipText( "jsfiddle.net" );
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
                js.append( "hiddenField.setAttribute('value', \"" ).append( htmlCode.replaceAll( "\n", "" ).trim() ).append( "\");" );
                js.append( "form.appendChild(hiddenField);" );
                // js
                js.append( "hiddenField = document.createElement('input');" );
                js.append( "hiddenField.setAttribute('name', 'js');" );
                js.append( "hiddenField.setAttribute('value', \"" ).append( jsCode.replaceAll( "\n", "" ).trim() ).append( "\");" );
                js.append( "form.appendChild(hiddenField);" );
                // resources
                js.append( "hiddenField = document.createElement('input');" );
                js.append( "hiddenField.setAttribute('name', 'resources');" );
                StringJoiner joiner = new StringJoiner( "," );
                for (String resource : resources) {
                    joiner.add( resource );
                }
                js.append( "hiddenField.setAttribute('value', \"" ).append( joiner.toString() ).append( "\");" );
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


    public Control control() {
        return button;
    }
}
