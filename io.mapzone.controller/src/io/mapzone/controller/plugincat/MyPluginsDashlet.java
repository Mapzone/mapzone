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

import org.polymap.model2.query.Expressions;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.um.repository.Organization;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.um.repository.User;

/**
 * Uses {@link PluginCatalogBrowser} to display all plugins that the current
 * {@link User} is vendor of.
 *
 * @author Falko Bräutigam
 */
public class MyPluginsDashlet
        extends DefaultDashlet {

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<User>             user;

    

    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( "My developed plugins" );
    }


    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( new FillLayout() );
        
        PluginCatalogBrowser browser = new PluginCatalogBrowser() {
            @Override public void customizeViewer() {
                // icons
                viewer.iconProvider.set( new CellLabelProvider() {
                    @Override public void update( ViewerCell cell ) {
                        PluginCatalogEntry plugin = (PluginCatalogEntry)cell.getElement();
                        cell.setImage( ControllerPlugin.images().svgImage( "package-variant-closed.svg", 
                                plugin.isRevoked.get() ? SvgImageRegistryHelper.DISABLED24 : SvgImageRegistryHelper.NORMAL24 ) );
                    }            
                });
                // label with state
                viewer.firstLineLabelProvider.set( new AdminStateLabelProvider() );
            }
        };

        ProjectUnitOfWork puow = ProjectRepository.session();
        Organization org = puow.findOrganization( user.get().name.get() ).get();
        browser.baseFilter.set( 
                Expressions.eq( PluginCatalogEntry.TYPE.vendorId, (String)org.id() ) );

        browser.openPanel.set( PluginInfoPanel2.ID );
        browser.createContents( parent, (MdToolkit)site().toolkit() );
    }
    
}
