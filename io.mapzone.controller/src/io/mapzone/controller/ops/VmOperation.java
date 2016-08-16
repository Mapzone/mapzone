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

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.controller.vm.repository.VmRepository.VmUnitOfWork;

/**
 * A {@link VmOperation} is typically an operation executed during provision or to
 * support an {@link UmOperation}. {@link #vmUow} is not commited.
 *
 * @author Falko Bräutigam
 */
public abstract class VmOperation
        extends DefaultOperation {

    @Mandatory
    @Immutable
    public Config<VmUnitOfWork>     vmUow;


    public VmOperation( String label ) {
        super( label );
        ConfigurationFactory.inject( this );
    }

}
