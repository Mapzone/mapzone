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
package io.mapzone.controller.ops;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Mandatory;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.um.xauth.PasswordEncryptor;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class UpdateUserOperation
        extends UmOperation {

    /** Inbound: the {@link User} to be updated. */
    @Mandatory
    public Config<User>         user;
    

    /** Inbound: optional new password. */
    public Config<String>       newPassword;
 
    
    /**
     * Creates a new instance with {@link ProjectRepository#session()} set.
     */
    public UpdateUserOperation( String username ) {
        super( "Update user" );
        umUow.set( ProjectRepository.session() );
        
        user.set( umUow.get().findUser( username )
                .orElseThrow( () -> new RuntimeException( "No such user: " + username ) ) );

    }

    
    @Override
    protected IStatus doWithCommit( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        monitor.beginTask( getLabel(), 3 );

        // password hash
        newPassword.ifPresent( password -> {
            PasswordEncryptor encryptor = PasswordEncryptor.instance();
            String hash = encryptor.encryptPassword( password );
            user.get().passwordHash.set( hash );
        });
        return Status.OK_STATUS;
    }

}
