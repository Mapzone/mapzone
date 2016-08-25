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
package io.mapzone.controller.um.launcher;

import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.model2.Composite;

import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.runtime.HostRuntime;
import io.mapzone.controller.vm.runtime.Script;

/**
 * A launcher executes {@link Script}s on the {@link HostRuntime} of a given project instance
 * in order to maintain (un/install, start/stop) the actual, running instance/process on the host.  
 *
 * @author Falko Bräutigam
 */
public abstract class ProjectLauncher
        extends Composite {

    protected Project project() {
        return context.getEntity();
    }
    
    /**
     * 
     *
     * @param instance
     * @param monitor
     * @throws ExecutionException If a {@link Script} did not execute sucessfully.
     * @throws Exception
     */
    public abstract void install( ProjectInstanceRecord instance, IProgressMonitor monitor ) throws Exception;
    
    public abstract void uninstall( ProjectInstanceRecord instance, IProgressMonitor monitor ) throws Exception;
    
    public abstract void start( ProjectInstanceRecord instance, IProgressMonitor monitor ) throws Exception;
    
    public abstract void stop( ProjectInstanceRecord instance, boolean kill, IProgressMonitor monitor ) throws Exception;

}
