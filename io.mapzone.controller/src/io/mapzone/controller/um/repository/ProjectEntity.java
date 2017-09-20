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

import org.polymap.core.runtime.event.EventManager;

import org.polymap.model2.Entity;
import org.polymap.model2.runtime.EntityRuntimeContext.EntityStatus;
import org.polymap.model2.runtime.Lifecycle;
import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;

/**
 * Common base class of project entities. Throws {@link EntityChangedEvent} on
 * commit.
 *
 * @author Falko Bräutigam
 */
public abstract class ProjectEntity
        extends Entity
        implements Lifecycle {

    private EntityStatus        beforeCommitStatus;


    /**
     * The {@link UnitOfWork} this entity belongs to.
     */
    public UnitOfWork uow() {
        return context.getUnitOfWork();
    }

    
    public boolean belongsTo( UnitOfWork uow ) {
        return uow() == uow 
                || uow instanceof ProjectUnitOfWork && uow() == ((ProjectUnitOfWork)uow).delegate();
    }

    
    @Override
    public void onLifecycleChange( State state ) {
        if (state == State.BEFORE_COMMIT || state == State.BEFORE_ROLLBACK) {
            beforeCommitStatus = status();
        }
        else if (state == State.AFTER_COMMIT || state == State.AFTER_ROLLBACK) {
            EventManager.instance().publish( new EntityChangedEvent( this, beforeCommitStatus ) );
        }
    }

}
