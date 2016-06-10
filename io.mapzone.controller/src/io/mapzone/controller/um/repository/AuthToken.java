/* 
 * polymap.org
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

import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class AuthToken {

    private static Log log = LogFactory.getLog( AuthToken.class );
    
    private static final Lazy<SecureRandom> random = new LockedLazyInit( () -> new SecureRandom() );

    /**
     * Generates and returns a new AuthToken. 
     */
    public static AuthToken create() {
        return new AuthToken();
    }
    
    public static AuthToken parse( String token ) {
        return new AuthToken( token );
    }
    
    // instance *******************************************
    
    private BigInteger          data;
    
    private AuthToken() {
        // http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
        data = new BigInteger( 130, random.get() );
    }

    private AuthToken( String token ) {
        data = new BigInteger( token, 32 );
    }

    public boolean isValid( String token ) {
        return toString().equals( token );
    }
    
    @Override
    public String toString() {
        return data.toString( 32 );
    }

    @Override
    public boolean equals( Object obj ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
