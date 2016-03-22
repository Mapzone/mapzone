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
package io.mapzone.controller.vm.repository;

import org.polymap.model2.Entity;
import org.polymap.model2.runtime.UnitOfWork;

/**
 * Provides common base methods.
 *
 * @author Falko Bräutigam
 */
public class VmEntity
        extends Entity {

    public UnitOfWork uow() {
        return context.getUnitOfWork();
    }
    
}
