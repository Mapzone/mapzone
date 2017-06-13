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
package io.mapzone.controller.api;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IPath;

import org.polymap.core.security.SecurityContext;
import org.polymap.core.security.UserPrincipal;

import org.polymap.service.fs.spi.DefaultContentFolder;
import org.polymap.service.fs.spi.DefaultContentProvider;
import org.polymap.service.fs.spi.IContentFolder;
import org.polymap.service.fs.spi.IContentNode;
import org.polymap.service.fs.spi.IContentProvider;

import io.mapzone.controller.um.repository.Organization;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class ApiContentProvider
        extends DefaultContentProvider
        implements IContentProvider {

    private static final Log log = LogFactory.getLog( ApiContentProvider.class );

    public static final String      PROJECTS_FOLDER_NAME = "projects";


    @Override
    public List<? extends IContentNode> getChildren( IPath path ) {
        // ProjectsFolder
        if (path.segmentCount() == 0) {
            return Collections.singletonList( new ProjectsFolder( path ) );
        }
        IContentFolder parent = getSite().getFolder( path );

        // OrganizationFolder
        if (parent.getClass().equals( ProjectsFolder.class )) {
            SecurityContext sc = SecurityContext.instance();
            UserPrincipal sessionUser = (UserPrincipal)sc.getUser();

            ProjectUnitOfWork uow = ProjectRepository.session();
            Organization org = uow.findOrganization( sessionUser.getName() ).get();
            return Collections.singletonList( new OrganizationFolder( org, path ) );
        }
        
        // ProjectFolder
        else if (parent.getClass().equals( OrganizationFolder.class )) {
            return ((OrganizationFolder)parent).getSource().projects.stream()
                    .map( project -> new ProjectFolder( project, path ) )
                    .collect( Collectors.toList() );
        }
        return null;
    }

    
    /**
     * 
     */
    public class ProjectsFolder
            extends DefaultContentFolder {

        public ProjectsFolder( IPath parentPath ) {
            super( PROJECTS_FOLDER_NAME, parentPath, ApiContentProvider.this, null );
        }
    }

    
    /**
     * 
     */
    public class OrganizationFolder
            extends DefaultContentFolder {

        public OrganizationFolder( Organization org, IPath parentPath ) {
            super( org.name.get(), parentPath, ApiContentProvider.this, org );
        }

        @Override
        public Organization getSource() {
            return (Organization)super.getSource();
        }
    }

    
    /**
     * 
     */
    public class ProjectFolder
            extends DefaultContentFolder {

        public ProjectFolder( Project project, IPath parentPath ) {
            super( project.name.get(), parentPath, ApiContentProvider.this, project );
        }

        @Override
        public Project getSource() {
            return (Project)super.getSource();
        }
    }

}
