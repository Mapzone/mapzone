/* 
 * polymap.org
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.arena.analytics.graph;

import org.eclipse.rap.rwt.RWT;

import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.runtime.i18n.MessagesImpl;

/**
 * The messages of the <code>io.mapzone.arena.graph</code> plugin.
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 * @author Steffen Stundzig 
 */

public class Messages {

    private static final String BUNDLE_NAME = Messages.class.getPackage().getName() + ".messages"; //$NON-NLS-1$

    private static final MessagesImpl   instance = new MessagesImpl( BUNDLE_NAME, Messages.class.getClassLoader() );

    public static IMessages forPrefix( String prefix ) {
        return instance.forPrefix( prefix );
    }

    // instance *******************************************
    
    private Messages() {
        // prevent instantiation
    }

    public static String get( String key, Object... args ) {
        return instance.get( key, args );
    }

    public static String get2( Object caller, String key, Object... args ) {
        return instance.get( caller, key, args );
    }
    
    public static Messages get() {
        Class clazz = Messages.class;
        return (Messages)RWT.NLS.getISO8859_1Encoded( BUNDLE_NAME, clazz );
    }

}
