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
package io.mapzone.controller.ui;

import org.polymap.rhei.batik.BatikPlugin;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.help.HelpPanel;

import io.mapzone.controller.ControllerPlugin;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CtrlHelpPanel
        extends HelpPanel {

    public static final PanelIdentifier ID = PanelIdentifier.parse( "help" );

    @Override
    public boolean beforeInit() {
        if (super.beforeInit() 
                && !(parentPanel().get() instanceof StartPanel)) {
            site().icon.set( BatikPlugin.images().svgImage( "help-circle-outline.svg", ControllerPlugin.HEADER_ICON_CONFIG ) );
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        super.init();
        site().setSize( CtrlPanel.SIDE_PANEL_WIDTH, CtrlPanel.SIDE_PANEL_WIDTH2, CtrlPanel.SIDE_PANEL_WIDTH2 );
    }

    
}
