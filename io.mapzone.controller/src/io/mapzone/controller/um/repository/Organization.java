/* 
 * mapzone.io
 * Copyright (C) 2015-2016, the @authors. All rights reserved.
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
package io.mapzone.controller.um.repository;

import org.polymap.model2.Computed;
import org.polymap.model2.ComputedBidiManyAssocation;
import org.polymap.model2.Defaults;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * An Organization has a name and is associated with one or many {@link Project}s. It
 * represents a group of {@link #members} who are authorized to access the projects.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class Organization
        extends Named {

    public static Organization          TYPE;

    public static final ValueInitializer<Organization> defaults = (Organization proto) -> { 
        return proto; 
    };
    
    /** 
     * Association with {@link UserRole#organization} and the {@link User}s.
     */
    @Computed( ComputedBidiManyAssocation.class )
    public ManyAssociation<UserRole>    members;
    
    @Defaults
    @Computed( ComputedBidiManyAssocation.class )
    public ManyAssociation<Project>     projects;

}
