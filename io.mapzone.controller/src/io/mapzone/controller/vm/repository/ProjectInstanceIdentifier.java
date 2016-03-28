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
package io.mapzone.controller.vm.repository;

import static org.apache.commons.lang3.StringUtils.split;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

import io.mapzone.controller.um.repository.Project;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class ProjectInstanceIdentifier {
    
    private String          organization;
    
    private String          project;
    
    private String          version;

    
    /**
     * 
     * @param request
     */
    public ProjectInstanceIdentifier( HttpServletRequest request ) {
        try {
            String[] parts = split( URLDecoder.decode( request.getPathInfo(), "UTF8" ), "/" );
            this.project = parts[1];
            this.organization = parts[0];
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }
    }
    
    public ProjectInstanceIdentifier( Project project ) {
        this.project = project.name.get();
        this.organization = project.organizationOrUser().name.get();
    }
    
    public String organization() {
        return organization;
    }
    
    public String project() {
        return project;
    }

    @Override
    public String toString() {
        return Joiner.on( "/").join( organization, project );
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((organization == null) ? 0 : organization.hashCode());
        result = 31 * result + ((project == null) ? 0 : project.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if (obj instanceof ProjectInstanceIdentifier) {
            ProjectInstanceIdentifier rhs = (ProjectInstanceIdentifier)obj;
            return Objects.equal( organization, rhs.organization ) 
                    && Objects.equal( project, rhs.project );
        }
        return false;
    }
    
}
