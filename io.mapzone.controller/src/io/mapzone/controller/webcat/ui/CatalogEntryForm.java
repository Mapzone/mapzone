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
package io.mapzone.controller.webcat.ui;

import org.apache.commons.lang3.StringUtils;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.text.FormTextBuilder;
import org.polymap.core.runtime.text.TextBuilder.Element;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnLayoutFactory;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.LabelFormField;
import org.polymap.rhei.field.NotEmptyValidator;
import org.polymap.rhei.field.PlainValuePropertyAdapter;
import org.polymap.rhei.field.TextFormField;
import org.polymap.rhei.field.VerticalFieldLayout;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;

import io.mapzone.controller.ui.project.CreateProjectPanel;
import io.mapzone.controller.ui.util.PropertyAdapter;
import io.mapzone.controller.webcat.model.CatalogEntry;

/**
 * {@link CatalogEntry} settings displayed as
 * {@link #createFormContents(IFormPageSite) form} or
 * {@link #createFlowText(Composite) flowtext}.
 *
 * @author Falko Bräutigam
 */
public class CatalogEntryForm
        extends DefaultFormPage 
        implements IFormFieldListener {
    
    private static final int                TEXT_HEIGHT = 150;

    /**
     * True specifies that the form is used for creation of a new Project in a
     * {@link CreateProjectPanel}
     */
    @Mandatory
    public Config2<CatalogEntryForm,Boolean> creation;
    
    @Mandatory
    public Config2<CatalogEntryForm,MdToolkit> tk;

    private CatalogEntry                    entry;
    
    
    public CatalogEntryForm( CatalogEntry entry ) {
        this.entry = entry;
        ConfigurationFactory.inject( this );
    }

    
    public Label createFlowText( Composite parent ) {
        FormTextBuilder text = FormTextBuilder.forHtml();
        text.useForNull( "" );
        text.build( Element.P, entry.description.get() ); //+ "</br></br>" );
        text.form( "Info", f -> {
            f.formField( "Plugin ID", entry.id() );
            f.formField( "Created", entry.created.get(), "date,long" );
            f.formField( "Updated", entry.updated.get(), "date,long" );
            f.formField( "Project URL", formatUrl( entry.projectUrl.get() ) );
            f.formField( "License", entry.license.get() );
        });
        
        text.build( Element.H2, "Vendor" );
        text.form( null, f -> {
            f.formField( "Vendor", entry.vendor.get() );
            f.formField( "Vendor URL", formatUrl( entry.vendorUrl.get() ) );
        });
        
        return tk.get().createFlowText( parent, text.toString() );
    }

    
    protected String formatUrl( String s ) {
        if (StringUtils.isBlank( s )) {
            return s;
        }
        else if (s.startsWith( "https://" )) {
            return "<a target=\"blank\" href=\"" + s + "\">" + s.substring( 8 ) + "</a>";
        }
        else if (s.startsWith( "http://" )) {
            return "<a target=\"blank\" href=\"" + s + "\">" + s.substring( 7 ) + "</a>";
        }
        else if (s.contains( "." )) {
            return "<a target=\"blank\" href=\"http://" + s + "\">" + s + "</a>";
        }
        else {
            return s;
        }
    }
    
    
    @Override
    public void createFormContents( IFormPageSite site ) {
        super.createFormContents( site );
        
        Composite body = site.getPageBody();
        body.setLayout( ColumnLayoutFactory.defaults().spacing( 8 ).create() );
        site.setDefaultFieldLayout( VerticalFieldLayout.INSTANCE );
        
        // id
        site.newFormField( new PlainValuePropertyAdapter( "id", entry.id() ) )
                .label.put( "Identifier" )
                .field.put( new LabelFormField() )
                .create();
        
        // title
        site.newFormField( new PropertyAdapter( entry.title ) )
                .validator.put( new NotEmptyValidator() )
                .create();
        
        // description
        site.newFormField( new PropertyAdapter( entry.description ) )
                .field.put( new TextFormField() )
                .validator.put( new NotEmptyValidator() )
                .create().setLayoutData( ColumnDataFactory.defaults().heightHint( TEXT_HEIGHT ).create() );

        // projectUrl
        site.newFormField( new PropertyAdapter( entry.projectUrl ) )
                .label.put( "Project URL" )
                .tooltip.put( "A link to the development website" )
                .create();
        
        // vendor
        site.newFormField( new PropertyAdapter( entry.vendor ) )
                .tooltip.put( "The name of the vendor" )
                .create();

        // vendorUrl
        site.newFormField( new PropertyAdapter( entry.vendorUrl ) )
                .label.put( "Vendor URL" )
                .tooltip.put( "A link to the website of the vendor" )
                .create();
        
//        // revoke
//        site.newFormField( new PropertyAdapter( entry.isRevoked ) )
//                .label.put( "Revoked" )
//                .tooltip.put( "Revoke this plugin. This removes the plugin from global catalog. Other users can no longer install this plugin." )
//                .create();
        
        site.addFieldListener( this );
    }

    
    @Override
    public void fieldChange( FormFieldEvent ev ) {
//        if (ev.getEventCode() == VALUE_CHANGE) {
//            if (ev.getFieldName().equals( "organizationOrUser" )) {
//                ev.getNewModelValue().ifPresent( v -> project.organization.set( (Organization)v ) );
//               // op.organization.set( (Organization)ev.getNewModelValue().orElse( null ) );
//            }
//            updateEnabled();
//        }
    }

    
    protected void updateEnabled() {
    }

}
