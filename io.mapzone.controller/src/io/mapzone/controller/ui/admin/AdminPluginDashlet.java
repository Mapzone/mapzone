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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.contribution.IContributionSite;
import org.polymap.rhei.batik.contribution.IPanelContribution;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import io.mapzone.controller.plugincat.PluginCatalog;
import io.mapzone.controller.plugincat.PluginCatalogEntry;
import io.mapzone.controller.plugincat.PluginInfoPanel;

/**
 * Adds an un/release admin section to {@link PluginInfoPanel}.
 *
 * @author Falko Bräutigam
 */
public class AdminPluginDashlet
        extends DefaultDashlet
        implements IPanelContribution {

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<PluginCatalogEntry>   plugin;
    
    /**
     * Contribution provider. 
     */
    @Override
    public void fillPanel( IContributionSite site, Composite parent ) {
        if (site.tagsContain( PluginInfoPanel.ID.id() ) && SecurityUtils.isAdmin()) {
            IPanelSection section = site.toolkit().createPanelSection( parent, "Administration", SWT.BORDER );
            section.addConstraint( new PriorityConstraint( 9 ), new MinWidthConstraint( 350, 1 ) );
            
            AdminPluginDashlet dashlet = new AdminPluginDashlet();
            BatikApplication.instance().getContext().propagate( dashlet );
            dashlet.createContents( section.getBody(), site.toolkit() );
        }
    }

    
    // instance *******************************************
    
    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( "Admin" );
    }


    @Override
    public void createContents( Composite parent ) {
        createContents( parent, (MdToolkit)site().toolkit() );
    }
    

    protected void createContents( Composite parent, MdToolkit tk ) {
        parent.setLayout( new FillLayout() );
        
        boolean released = plugin.get().isReleased.get();
        
        Button btn = tk.createButton( parent, "Release", SWT.TOGGLE );
        btn.setToolTipText( "Toggle release state" );
        btn.setSelection( released );
        btn.addSelectionListener( new SelectionAdapter() {
            @Override public void widgetSelected( SelectionEvent ev ) {
                doRelease( !released );
                tk.createSnackbar( Appearance.FadeIn, "Plugin has been <b>" + (plugin.get().isReleased.get() ? "released" : "unreleased") + "</b>" );
            }
        });
    }
    
    
    protected void doRelease( boolean release ) {
        PluginCatalog catalog = PluginCatalog.instance();
        catalog.releaseEntry( PluginCatalog.session(), plugin.get(), release );
    }
    
}
