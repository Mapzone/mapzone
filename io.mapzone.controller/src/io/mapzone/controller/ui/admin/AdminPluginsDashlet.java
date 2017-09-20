/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
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
package io.mapzone.controller.ui.admin;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.plugincat.PluginCatalogBrowser;
import io.mapzone.controller.plugincat.PluginCatalogEntry;
import io.mapzone.controller.plugincat.PluginInfoPanel;
import io.mapzone.controller.um.repository.User;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class AdminPluginsDashlet
        extends DefaultDashlet {

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<User>             user;

    

    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( "Plugins" );
    }


    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( new FillLayout() );
        
        PluginCatalogBrowser browser = new PluginCatalogBrowser() {
            @Override 
            public void customizeViewer() {
                // icons
                viewer.iconProvider.set( new CellLabelProvider() {
                    @Override public void update( ViewerCell cell ) {
                        PluginCatalogEntry plugin = (PluginCatalogEntry)cell.getElement();
                        if (plugin.isReleased.get() && !plugin.isRevoked.get()) {
                            cell.setImage( ControllerPlugin.images().svgImage( "package-variant-closed.svg", SvgImageRegistryHelper.ACTION24 ) );
                        }
                        else {
                            cell.setImage( ControllerPlugin.images().svgImage( "package-variant-closed.svg", SvgImageRegistryHelper.DISABLED24 ) );
                        }
                    }    
                });
                // label with state
                viewer.firstLineLabelProvider.set( new AdminStateLabelProvider() );
            }
        };
        browser.openPanel.set( PluginInfoPanel.ID );
        browser.createContents( parent, (MdToolkit)site().toolkit() );
    }
    
}
