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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.action.Action;

import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdActionbar;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.form.batik.BatikFormContainer;

import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.webcat.ui.CatalogEntryForm;

/**
 * Provides a form to edit a {@link PluginCatalogEntry} (aka plugin).
 *
 * @author Falko Bräutigam
 */
public class PluginInfoPanel2
        extends CtrlPanel {

    private static final Log log = LogFactory.getLog( PluginInfoPanel2.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "pluginInfo2" );

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<PluginCatalogEntry>   plugin;

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
        createDangerSection( parent );
    }


    protected void createFormSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, plugin.get().title.get(), SWT.BORDER );
        section.addConstraint( new PriorityConstraint( 10 ), new MinWidthConstraint( 350, 1 ) );

        // submit
        MdActionbar ab = tk().createFloatingActionbar();
        Action submit = ab.addSubmit( a -> { 
            try {
                form.submit( null );
                PluginCatalog.session().commit();

                tk().createSnackbar( Appearance.FadeIn, "Saved" );
                
                UIUtils.sessionDisplay().timerExec( 2500, () -> {
                    getContext().closePanel( site().path() );
                });
            }
            catch (Exception e) {
                StatusDispatcher.handleError( "Changes could not be saved.", e );
            }
        });
        submit.setEnabled( false );
        
        // form
        CatalogEntryForm formPage = new CatalogEntryForm( plugin.get() ) {
            @Override public void fieldChange( FormFieldEvent ev ) {
                submit.setEnabled( form.isDirty() && form.isValid() );
            }
        };
        formPage.creation.put( false );
        formPage.tk.put( tk() );        
        form = new BatikFormContainer( formPage );
        form.createContents( section.getBody() );
    }

    
    protected void createDangerSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "Danger Zone", SWT.BORDER );
        section.getTitleControl().setForeground( UIUtils.getColor( SvgImageRegistryHelper.COLOR_DANGER ) );
        section.addConstraint( new PriorityConstraint( 0 ), new MinWidthConstraint( 350, 1 ) );
        section.getBody().setLayout( ColumnLayoutFactory.defaults().spacing( 5 ).margins( 3 ).create() );

        Button btn = tk().createButton( section.getBody(), "Revoke", SWT.TOGGLE, SWT.FLAT );
        btn.setSelection( plugin.get().isRevoked.get() );
        btn.setToolTipText( "Revoke this plugin.<br/>Remove from catalog and make it unavailable to other users." );
        btn.setLayoutData( ColumnDataFactory.defaults().heightHint( 32 ).create() );
        btn.addSelectionListener( UIUtils.selectionListener( ev -> {
            doRevoke( !plugin.get().isRevoked.get() );

            tk().createSnackbar( Appearance.FadeIn, "Plugin has been <b>" + (plugin.get().isRevoked.get() ? "revoked" : "un-revoked") + "</b>" );
//            UIUtils.sessionDisplay().timerExec( 2000, () -> {
//                getContext().closePanel( site().path() );
//            });
        }));
    }

    
    protected void doRevoke( boolean revoke ) {
        PluginCatalog catalog = PluginCatalog.instance();
        catalog.revokeEntry( PluginCatalog.session(), plugin.get(), revoke );
    }
    
}
