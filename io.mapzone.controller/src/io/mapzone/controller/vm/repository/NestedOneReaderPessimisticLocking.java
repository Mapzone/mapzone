/* 
 * polymap.org
 * Copyright (C) 2012-2014, Falko Bräutigam. All rights reserved.
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
package io.mapzone.controller.vm.repository;

import org.polymap.model2.Entity;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.locking.OneReaderPessimisticLocking;
import org.polymap.model2.runtime.locking.PessimisticLocking;

/**
 * A {@link PessimisticLocking} that spans locks over a UnitOfWork and all of its
 * nested instances. That is, a reader in UnitOfWork A is not blocked if a nested
 * UnitOfWork or one of its parents already has to lock.
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class NestedOneReaderPessimisticLocking
        extends OneReaderPessimisticLocking {

    @Override
    protected EntityLock newLock( EntityKey key, Entity entity ) {
        return new NestedOneReaderEntityLock();
    }

    /**
     * 
     */
    protected class NestedOneReaderEntityLock
            extends OneReaderEntityLock {

        protected NestedOneReaderEntityLock() {
            this.isAquired = uow -> reader != null ? isCompatible( reader.get(), uow ) : false;
        }

        protected boolean isCompatible( UnitOfWork uow1, UnitOfWork uow2 ) {
            // uow2 parent of uow1 ?
            for (UnitOfWork parent = uow1; parent != null; parent = parent.parent().orElse( null )) {
                if (parent == uow2) {
                    return true;
                }
            }
            // uow1 parent of uow2 ?
            for (UnitOfWork parent = uow2; parent != null; parent = parent.parent().orElse( null )) {
                if (parent == uow1) {
                    return true;
                }
            }
            return false;
        }
        
    }
    
}
