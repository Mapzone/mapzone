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
package io.mapzone.controller.vm.runtime;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.config.DefaultInt;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Immutable;

/**
 * The result of {@link HostRuntime#execute(Script)}. 
 *
 * @author Falko Bräutigam
 */
public class ScriptExecutionResult
        extends Configurable {

    @Immutable
    @DefaultInt( 0 )
    public Config<Integer>  exitStatus;
    
    @Immutable
    @DefaultString( "" )
    public Config<String>   error;
    
    @Immutable
    @DefaultString( "" )
    public Config<String>   output;
    
}
