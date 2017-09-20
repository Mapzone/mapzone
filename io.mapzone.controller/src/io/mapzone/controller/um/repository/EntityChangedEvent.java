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

import java.util.EventObject;

import org.polymap.model2.Entity;
import org.polymap.model2.runtime.EntityRuntimeContext.EntityStatus;
import org.polymap.model2.runtime.UnitOfWork;

/**
 * An {@link Entity} has been committed or rolled back. 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class EntityChangedEvent
        extends EventObject {

    public EntityStatus     beforeCommitStatus;


    public EntityChangedEvent( Entity source, EntityStatus beforeCommitStatus ) {
        super( source );
        this.beforeCommitStatus = beforeCommitStatus;
        assert beforeCommitStatus != null;
    }

    
    /**
     * The entity that has been committed.
     * <p/>
     * <b>Beware</b> that the entity probably belongs to another {@link UnitOfWork}.
     * Use {@link ProjectRepository#entity(Entity)} in order to get an instance of
     * your repo.
     */
    @Override
    public Entity getSource() {
        return (Entity)super.getSource();
    }
    
    
    /**
     * The entity that has been committed.
     * <p/>
     * <b>Beware</b> that the entity probably belongs to another {@link UnitOfWork}.
     * Use {@link ProjectRepository#entity(Entity)} in order to get an instance of
     * your repo.
     */
    public <T extends Entity> T getEntity() {
        return (T)super.getSource();
    }
    
}
