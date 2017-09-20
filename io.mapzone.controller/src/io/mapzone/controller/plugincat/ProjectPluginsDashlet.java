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
package io.mapzone.controller.plugincat;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import org.polymap.core.runtime.config.ConfigurationFactory;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import org.polymap.model2.runtime.NotNullableException;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.um.repository.Project;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class ProjectPluginsDashlet
        extends DefaultDashlet {

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<Project>          project;

    
    public ProjectPluginsDashlet() {
        ConfigurationFactory.inject( this );
    }


    public void createContents( Composite parent, MdToolkit tk ) {
        PluginCatalogBrowser browser = new PluginCatalogBrowser() {
            @Override 
            public void customizeViewer() {
                viewer.iconProvider.set( new CellLabelProvider() {
                    @Override 
                    public void update( ViewerCell cell ) {
                        PluginCatalogEntry plugin = (PluginCatalogEntry)cell.getElement();
                        SvgImageRegistryHelper images = ControllerPlugin.images();
                        
                        cell.setImage( images.svgImage( "package-variant-closed.svg", SvgImageRegistryHelper.DISABLED24 ) );
                        try {
                            if (project.get().plugins.get().installedPluginIds.contains( plugin.id() )) {
                                cell.setImage( images.svgImage( "package-variant.svg", SvgImageRegistryHelper.ACTION24 ) );
                            }
                        }
                        catch (NotNullableException e) {
                            // just go with the default
                        }
                    }            
                });
            }
        };
        browser.filterRevoked( false );
        browser.filterReleased( true );
        browser.createContents( parent, tk );
    }
    
    
    @Override
    public void createContents( Composite parent ) {
        createContents( parent, (MdToolkit)site().toolkit() );
    }
    
}
