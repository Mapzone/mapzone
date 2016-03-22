/* 
 * mapzone.io
 * Copyright (C) 2013, Falko Bräutigam. All rights reserved.
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
package io.mapzone.controller.um.xauth;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class PasswordEncryptor {

    public static PasswordEncryptor instance() {
        return new CrackStationEncryptor();
    }
    
    
    // API ************************************************
    
    public abstract String encryptPassword( String password );
    
    public abstract boolean checkPassword( String inputPassword, String encryptedPassword );

    public abstract String createPassword( int length );
    
}
