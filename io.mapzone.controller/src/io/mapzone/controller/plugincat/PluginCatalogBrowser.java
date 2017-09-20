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

import static org.polymap.model2.query.Expressions.and;
import static org.polymap.model2.query.Expressions.eq;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import org.polymap.core.runtime.config.Config;

import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.IAppContext;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Scope;

import org.polymap.model2.query.Expressions;
import org.polymap.model2.query.grammar.BooleanExpression;

import io.mapzone.controller.webcat.model.CatalogEntry;
import io.mapzone.controller.webcat.ui.CatalogBrowser;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class PluginCatalogBrowser
        extends CatalogBrowser {

    /**
     * The panel to open when an entry is clicked ({@link #doOpen(CatalogEntry)}).
     * Defaults to {@link PluginInfoPanel#ID}.
     */
    public Config<PanelIdentifier>          openPanel;
    
    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<PluginCatalogEntry>   selected;
    
    private PanelPath                       topPanel;
    

    public PluginCatalogBrowser() {
        IAppContext appContext = BatikApplication.instance().getContext();
        topPanel = appContext.topPanel();
    }

    
    public PluginCatalogBrowser filterRevoked( boolean revoked ) {
        PluginCatalog.session();  // force repo to init TYPE member
        BooleanExpression newFilter = and( 
                baseFilter.get(),
                Expressions.eq( PluginCatalogEntry.TYPE.isRevoked, revoked ) );
        baseFilter.set( newFilter );
        return this;
    }

    
    public PluginCatalogBrowser filterReleased( boolean released ) {
        BooleanExpression newFilter = and( 
                baseFilter.get(), 
                eq( PluginCatalogEntry.TYPE.isReleased, released ) );
        baseFilter.set( newFilter );
        return this;
    }

    
    @Override
    protected void doSearch( String query ) throws Exception {
        viewer.setInput( PluginCatalog.instance().query( PluginCatalog.session(), baseFilter.get(), query ) );
    }


    @Override
    protected void doOpen( CatalogEntry elm ) {
        selected.set( (PluginCatalogEntry)elm );
        IAppContext appContext = BatikApplication.instance().getContext();
        appContext.openPanel( topPanel, openPanel.orElse( PluginInfoPanel.ID ) );                        
    }


    /**
     * Plugin name and released/revoke state. 
     */
    public static class AdminStateLabelProvider
            extends CellLabelProvider {
        
        @Override 
        public void update( ViewerCell cell ) {
            PluginCatalogEntry plugin = (PluginCatalogEntry)cell.getElement();
            String state = plugin.isReleased.get() ? "released" : "not released";
            if (plugin.isRevoked.get()) {
                state = "revoked";
            }
            cell.setText( plugin.title.get() + " - (" + state + ")" );
        }
    }
    
}
