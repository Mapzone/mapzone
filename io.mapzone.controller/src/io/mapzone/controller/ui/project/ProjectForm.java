/* 
 * mapzone.io
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
package io.mapzone.controller.ui.project;

import static java.util.stream.Collectors.toMap;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnLayoutFactory;

import org.polymap.rhei.field.DateValidator;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.NotEmptyValidator;
import org.polymap.rhei.field.PicklistFormField;
import org.polymap.rhei.field.PlainValuePropertyAdapter;
import org.polymap.rhei.field.StringFormField;
import org.polymap.rhei.field.TextFormField;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.FieldBuilder;
import org.polymap.rhei.form.IFormPageSite;

import io.mapzone.controller.ui.util.PropertyAdapter;
import io.mapzone.controller.um.repository.Named;
import io.mapzone.controller.um.repository.Organization;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.um.repository.User;

/**
 * {@link Project} settings. 
 *
 * @author Falko Bräutigam
 */
public abstract class ProjectForm
        extends DefaultFormPage 
        implements IFormFieldListener {
    
    private static final int            TEXT_HEIGHT = 70;

    /**
     * True specifies that the form is used for creation of a new Project in a
     * {@link CreateProjectPanel}
     */
    @Mandatory
    public Config2<ProjectForm,Boolean> creation;
    
    private ProjectUnitOfWork           uow;
    
    private Project                     project;
    
    private User                        user;

    
    
    protected ProjectForm( ProjectUnitOfWork uow, Project project, User user ) {
        this.uow = uow;
        this.project = project;
        this.user = user;
        ConfigurationFactory.inject( this );
    }


    @Override
    public void createFormContents( IFormPageSite site ) {
        super.createFormContents( site );
        
        Composite body = site.getPageBody();
        body.setLayout( ColumnLayoutFactory.defaults().spacing( 3 ).create() );
        
        // organization
        Map<String,Organization> orgs = user.organizations.stream()
                .map( role -> role.organization.get() )
                .collect( toMap( org -> org.name.get(), org -> org ) );
        Named defaultOwner = project.organization.isPresent() 
                ? project.organization.get() 
                : orgs.values().stream().findAny().get(); 
        site.newFormField( new PlainValuePropertyAdapter( "organizationOrUser", defaultOwner ) )
                .label.put( "Owner" )
                .field.put( new PicklistFormField( orgs ) )
                .tooltip.put( "The owner of this project" )
                .fieldEnabled.put( creation.get() )
                .create();
        
        // name
        FieldBuilder nameField = site.newFormField( new PropertyAdapter( project.name ) );
        nameField.fieldEnabled.put( creation.get() );
        if (creation.get()) {
            nameField.validator.put( new NotEmptyValidator<String,String>() {
                @Override
                public String validate( String fieldValue ) {
                    String result = super.validate( fieldValue );
                    if (result == null) {
                        if (project.organization.isPresent()) {
                            // this is a tricky check; because project.organization is set this checks
                            // also the newly created project (which has name==null until submit)
                            if (uow.findProject( project.organization.get().name.get(), (String)fieldValue ).isPresent()) { 
                                result = "Project name is already taken";
                            }
                        }
                        else {
                            result = "Choose an organization first";
                        }
                    };
                    return result;
                }
            });
        }
        nameField.create();
        
        // description
        site.newFormField( new PropertyAdapter( project.description ) )
                .field.put( new TextFormField() )
                .create().setLayoutData( ColumnDataFactory.defaults().heightHint( TEXT_HEIGHT ).create() );

        // keywords
        site.newFormField( new PropertyAdapter( project.keywords ) )
                .label.put( "Keywords" )
                .validator.put( new KeywordsValidator() )
                .field.put( new StringFormField() )
                .create(); //.setLayoutData( ColumnDataFactory.defaults().heightHint( TEXT_HEIGHT ).create() );
        
        // creator
        site.newFormField( new PropertyAdapter( project.creator ) )
                .field.put( new TextFormField() )
                .tooltip.put( "An entity primarily responsible for making the resource." )
                .create().setLayoutData( ColumnDataFactory.defaults().heightHint( TEXT_HEIGHT ).create() );
        
        // rights
        site.newFormField( new PropertyAdapter( project.rights ) )
                .field.put( new TextFormField() )
                .tooltip.put( "Information about rights held in and over the resource. Typically, rights\n"
                        + "information includes a statement about various property rights associated with\n"
                        + "the resource, including intellectual property rights." )
                .create().setLayoutData( ColumnDataFactory.defaults().heightHint( TEXT_HEIGHT ).create() );
        
        // publisher
        site.newFormField( new PropertyAdapter( project.publisher ) )
                .field.put( new TextFormField() )
                .tooltip.put( "An entity responsible for making the resource available." )
                .create().setLayoutData( ColumnDataFactory.defaults().heightHint( TEXT_HEIGHT ).create() );
        
        // accessRights
        site.newFormField( new PropertyAdapter( project.accessRights ) )
                .fieldEnabled.put( false )
                .label.put( "Access rights" )
                .field.put( new TextFormField() )
                .tooltip.put( "Information about who can access the resource or an indication of its security\n"
                        + "status. Access Rights may include information regarding access or restrictions\n"
                        + "based on privacy, security, or other policies." )
                .create().setLayoutData( ColumnDataFactory.defaults().heightHint( TEXT_HEIGHT ).create() );
        
        // created
        site.newFormField( new PropertyAdapter( project.created ) )
                .fieldEnabled.put( false )
                .field.put( new StringFormField() )
                .validator.put( new DateValidator() )
                .create();
        
        // modified
        site.newFormField( new PropertyAdapter( project.modified ) )
                .fieldEnabled.put( false )
                .field.put( new StringFormField() )
                .validator.put( new DateValidator() )
                .create();
        
        site.addFieldListener( this );
    }

    
    @Override
    public void fieldChange( FormFieldEvent ev ) {
        if (ev.getEventCode() == VALUE_CHANGE) {
            if (ev.getFieldName().equals( "organizationOrUser" )) {
                ev.getNewModelValue().ifPresent( v -> project.organization.set( (Organization)v ) );
               // op.organization.set( (Organization)ev.getNewModelValue().orElse( null ) );
            }
            updateEnabled();
        }
    }

    
    protected abstract void updateEnabled();

}
