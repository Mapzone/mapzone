/* 
 * mapzone.io
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
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

import io.mapzone.controller.um.repository.Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import org.eclipse.rap.rwt.RWT;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectLabelProvider
        extends CellLabelProvider {

    private static Log log = LogFactory.getLog( ProjectLabelProvider.class );

    public enum Type {
        Name, Description
    }

    private Type            type;

    
    public ProjectLabelProvider( Type type ) {
        this.type = type;
    }


    @Override
    public void update( ViewerCell cell ) {
        Project project = (Project)cell.getElement();
        switch (type) {
            case Name: {
                cell.setText( project.organization.get().name.get() + "/ " + project.name.get() );
                break;
            }
            case Description: {
                DateFormat df = SimpleDateFormat.getDateInstance( SimpleDateFormat.MEDIUM, RWT.getLocale() );
                cell.setText( Joiner.on( " - " ).join( 
                        project.description.get(), 
                        "Last modified: " + project.modified.opt().map( v -> df.format( v ) ).orElse( "???" ) ) );
                break;
            }
            default: throw new RuntimeException( "Unhandled Type: " + type );
        }
    }
    
}
