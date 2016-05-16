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
package io.mapzone.controller.um.repository;

import org.polymap.model2.Association;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * The many-to-many association between {@link Organization}s and its {@link User}s.
 *
 * @author Falko Bräutigam
 */
public class UserRole
        extends ProjectEntity {

    public static UserRole              TYPE;

    public static ValueInitializer<UserRole> defaults( User user, Organization org) {
        return (UserRole proto) -> {
            proto.user.set( user );
            proto.organization.set( org );
            return proto; 
        };
    }

    // instance *******************************************
    
    public Association<User>            user;
    
    public Association<Organization>    organization;
    
}
