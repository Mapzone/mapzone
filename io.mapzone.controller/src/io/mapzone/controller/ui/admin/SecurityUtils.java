/* 
 * polymap.org
 * Copyright (C) 2017, Polymap GmbH. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.controller.ui.admin;

import org.polymap.core.security.SecurityContext;

/**
 * Provides basic security checks against the {@link SecurityContext}.
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public final class SecurityUtils {

    public static final String      ADMIN_USER = "falko";
    public static final String      ADMIN_GROUP = "admins";
    
    
//    public static boolean isAdmin( Principal principal ) {
//        return principal.getName().equals( ADMIN_USER )
//                || principal.getName().equals( ADMIN_GROUP );
//    }
//
//
//    public static boolean isAdmin( Collection<Principal> principals ) {
//        for (Principal principal : principals) {
//            if (isAdmin( principal )) {
//                return true;
//            }
//        }
//        return false;
//    }

    
    public static boolean isAdmin() {
        SecurityContext sc = SecurityContext.instance();
        return sc.isLoggedIn() && ADMIN_USER.equals( sc.getUser().getName() );
    }

    
//    public static boolean isInGroup( Set<Principal> principals, String group ) {
//        for (Principal principal : principals) {
//            if (principal.getName().equals( group ) || isAdmin( principal )) {
//                return true;
//            }
//        }
//        return false;
//    }
//    
//    
//    public static boolean isUserInGroup( String group ) {
//        return isInGroup( SecurityContext.instance().getPrincipals(), group );
//    }
    
}
