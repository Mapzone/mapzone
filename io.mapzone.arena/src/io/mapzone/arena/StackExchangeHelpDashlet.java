/* 
 * polymap.org
 * Copyright (C) 2016, the @authors. All rights reserved.
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
package io.mapzone.arena;

import static org.polymap.core.ui.FormDataFactory.on;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.FormLayoutFactory;

import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.help.HelpDashlet;
import org.polymap.p4.P4Panel;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class StackExchangeHelpDashlet
        extends HelpDashlet {

    public static final IMessages i18n = Messages.forPrefix( "StackExchangeHelpDashlet" );

    
    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site().title.set( i18n.get( "title" ) );
        site().setExpanded( false );
    }


    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( FormLayoutFactory.defaults().margins( 8 ).spacing( 8 ).create() );
        
        //int fontSize = MdAppDesign.font( MdAppDesign.FontStyle.Body2 ).getFontData()[0].getHeight();
        Control msg = on( site().toolkit().createLabel( parent, i18n.get( "welcome" ), SWT.WRAP ) )
                .fill().height( 210 ).noBottom().control();
        msg.setEnabled( false );
        
//        Button btn = site().toolkit().createButton( parent, "Ask question...", SWT.FLAT );
//        on( btn ).fill().top( msg );
//        btn.addSelectionListener( new SelectionAdapter() {
//            @Override
//            public void widgetSelected( SelectionEvent e ) {
//                BatikApplication.instance().getContext().openPanel( site().panelSite().getPath(), StackExchangePanel.ID );
//            }
//        });
    }

    
    /**
     * 
     */
    public static class StackExchangePanel
            extends P4Panel {

        public static final PanelIdentifier ID = PanelIdentifier.parse( "StackExchange" );

        @Override
        public void createContents( Composite parent ) {
            site().title.set( "StackExchange" );
            site().setSize( 650, 650, Integer.MAX_VALUE );
            parent.setLayout( new FillLayout() );
            
            Browser browser = new Browser( parent, SWT.NONE );
            browser.setUrl( "http://gis.stackexchange.com/questions/ask" );
        }
        
    }
    
}
