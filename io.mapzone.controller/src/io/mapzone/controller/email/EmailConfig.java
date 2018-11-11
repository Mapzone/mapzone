/* 
 * mapzone.io
 * Copyright (C) 2018, the @authors. All rights reserved.
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
package io.mapzone.controller.email;

import java.util.Optional;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;

/**
 * General email configuration. 
 *
 * @author Falko Bräutigam
 */
public class EmailConfig
        extends Configurable {

    /**
     * Email configuration for support messages.
     */
    public static EmailConfig forSupport() {
        EmailConfig result = new EmailConfig();
        String KEY_PREFIX = "io.mapzone.arena.supportEmail.";
        Optional.ofNullable( System.getProperty( KEY_PREFIX + "user" ) )
                .ifPresent( v -> result.USER.set( v ) );
        Optional.ofNullable( System.getProperty( KEY_PREFIX + "host" ) )
                .ifPresent( v -> result.HOST.set( v ) );
        result.PWD.set( System.getProperty( KEY_PREFIX + "pwd" ) );
        return result;
    };


    // instance *******************************************

    @Immutable @Mandatory @DefaultString("support@mapzone.io") 
    public Config<String>    USER;

    @Immutable @Mandatory 
    public Config<String>    PWD;

    @Immutable @Mandatory @DefaultString("mail.mapzone.io" )
    public Config<String>    HOST;

}
