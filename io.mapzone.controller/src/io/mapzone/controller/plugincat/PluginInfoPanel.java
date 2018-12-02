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

import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;

import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.text.FormTextBuilder;
import org.polymap.core.runtime.text.TextBuilder.Element;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.contribution.ContributionManager;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;
import org.polymap.rhei.form.batik.BatikFormContainer;

import io.mapzone.controller.ops.InstallPluginOperation;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.webcat.ui.CatalogEntryForm;

/**
 * Information about a {@link PluginCatalogEntry} (aka plugin) and install/update status
 * regarding a (possibly) given {@link Project}. Does not allow to edit plugin fields.
 *
 * @author Falko Bräutigam
 */
public class PluginInfoPanel
        extends CtrlPanel {

    private static final Log log = LogFactory.getLog( PluginInfoPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "pluginInfo" );

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<PluginCatalogEntry>   entry;

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<Project>              project;
    
    private BatikFormContainer              form;
    
    
    @Override
    public void init() {
        super.init();
        site().title.set( "Plugin" );
        site().setSize( SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH2, SIDE_PANEL_WIDTH2 );
    }        

    
    @Override
    public void createContents( Composite parent ) {
        createFormSection( parent );
        createInstallSection( parent );
        ContributionManager.instance().contributeTo( parent, this, ID.id() );
        
        UIThreadExecutor.async( () -> site().layout( true ) );
    }


    protected void createFormSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, entry.get().title.get(), IPanelSection.EXPANDABLE );
        section.addConstraint( new PriorityConstraint( 10 ), new MinWidthConstraint( 350, 1 ) );

        new CatalogEntryForm( entry.get() )
                .creation.put( false )
                .tk.put( tk() )
                .createFlowText( section.getBody() );
    }

    
    protected void createInstallSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "Install", SWT.BORDER, IPanelSection.EXPANDABLE );
        section.addConstraint( new PriorityConstraint( 9 ), new MinWidthConstraint( 350, 1 ) );
        section.setExpanded( false );
        
        section.getBody().setLayout( ColumnLayoutFactory.defaults().spacing( 5 ).margins( 3 ).create() );

        // outside project scope
        FormTextBuilder text = FormTextBuilder.forHtml();
        text.useForNull( "[not set]" );
        if (!project.isPresent()) {
            text.build( Element.NOOP, "Choose a project from your dashboard and check the Plugins section in order to install the plugin in one of your projects." );
            tk().createFlowText( section.getBody(), text.toString() )
                    .setLayoutData( ColumnDataFactory.defaults().widthHint( 300 ).create() );
            return;
        }
        
        // TODO check admin/permission
        
        // already installed
        String btnText = null;
        if (project.get().plugins.get().installedPluginIds.contains( entry.get().id() )) {
            btnText = "Update";
            text.build( Element.NOOP, "This plugin is already installed in this project. "
                    + "Click on the button below to update to the latest version." );
        }
        else if (entry.get().isFree()) {
            btnText = "Install";
            text.build( Element.NOOP, "This plugin is available free of charge. "
                    + "Click on the button below to install it into your project." );
        }
        else {
            btnText = "Purchase and Install";
            text.build( Element.P, "This plugin is available under a **commercial** license." );
            text.form( null, f -> {
                f.formField( "Monthly fee", entry.get().fee.get() );
            });
        }
        tk().createFlowText( section.getBody(), text.toString() )
                .setLayoutData( ColumnDataFactory.defaults().widthHint( 300 ).create() );

        
//        // organizations
//        Map<String,Organization> orgs = user.get().organizations.stream()
//                .map( role -> role.organization.get() )
//                .collect( Collectors.toMap( org -> org.name.get(), org -> org ) );
//        Combo combo = new Combo( section.getBody(), SWT.BORDER|SWT.READ_ONLY );
//        combo.setItems( orgs.keySet().toArray( new String[orgs.size()] ) );
//        combo.select( 0 );
        
        // purchase
        Button btn = tk().createButton( section.getBody(), btnText, SWT.PUSH );
        //btn.setBackground( UIUtils.getColor( SvgImageRegistryHelper.COLOR_OK ) );
        //btn.setImage( ControllerPlugin.images().svgImage( "truck-delivery.svg", SvgImageRegistryHelper.WHITE24 ) );
        btn.setLayoutData( ColumnDataFactory.defaults().heightHint( 32 ).create() );
        btn.addSelectionListener( new SelectionAdapter() {
            @Override public void widgetSelected( SelectionEvent ev ) {
                doInstall( jev -> {
                    UIThreadExecutor.async( () -> {
                        btn.setEnabled( false );                        
                    });
                });
            }
        });
        
        // uninstall
        Button btn2 = tk().createButton( section.getBody(), "Uninstall", SWT.PUSH );
        btn2.setLayoutData( ColumnDataFactory.defaults().heightHint( 32 ).create() );
        btn2.setEnabled( project.get().plugins.get().installedPluginIds.contains( entry.get().id() ) );
        btn2.addSelectionListener( new SelectionAdapter() {
            @Override public void widgetSelected( SelectionEvent ev ) {
                doUninstall();
            }
        });
    }

    
    protected void doInstall( Consumer<IJobChangeEvent> doneHandler ) {
        InstallPluginOperation op = new InstallPluginOperation();
        op.project.set( project.get() );
        op.plugin.set( entry.get() );
        
        OperationSupport.instance().execute2( op, true, false, doneHandler );
        
    }


    protected void doUninstall() {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
