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

import java.util.Date;

import org.polymap.model2.Computed;
import org.polymap.model2.ComputedBidiManyAssocation;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class User
        extends Named {

    public static User              TYPE;

    public static ValueInitializer<User> defaults = (User proto) -> {
        // FIXME
        proto.name.set( "" );
        proto.email.set( "" );
        proto.passwordHash.set( "" );
        proto.joined.set( new Date() );
        return proto; 
    };
    
        
    public Property<String>         passwordHash;
    
    @Queryable
    @Nullable
    @DefaultValue( "[No fullname set]" )
    public Property<String>         fullname;
    
    @Queryable
    @DefaultValue( "" )
    public Property<String>         company;
    
    @Queryable
    public Property<Date>           joined;
    
    /** 
     * Association with {@link UserRole#user} and the {@link Organization}s.
     */
    @Computed( ComputedBidiManyAssocation.class )
    public ManyAssociation<UserRole> organizations;

    
    /**
     * The {@link Organization} that represents the user itself. It holds the
     * projects of the user that are not shared with anyone else.
     */
    public Organization userOrg() {
        // always the first entry; created by CreateUserOperation
        return organizations.iterator().next().organization.get();
    }
    
}
