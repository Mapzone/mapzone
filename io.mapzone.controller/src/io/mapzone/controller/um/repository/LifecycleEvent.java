/* 
 * mapzone.io
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
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

import java.util.EventObject;

import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class LifecycleEvent
        extends EventObject {

    public enum Type {
        BEFORE_COMMIT, AFTER_COMMIT, BEFORE_ROLLBACK, AFTER_ROLLBACK, BEFORE_CLOSE
    }
    
    private Type            type;
    
    public LifecycleEvent( ProjectUnitOfWork source, Type type ) {
        super( source );
        this.type = type;
    }

    @Override
    public ProjectUnitOfWork getSource() {
        return (ProjectUnitOfWork)super.getSource();
    }
    
    public Type getType() {
        return type;
    }
    
}
