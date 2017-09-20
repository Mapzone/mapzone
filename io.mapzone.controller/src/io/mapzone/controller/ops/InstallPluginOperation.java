/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
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

import org.polymap.core.runtime.SubMonitor;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.controller.plugincat.PluginCatalogEntry;
import io.mapzone.controller.plugincat.PluginInstaller;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.VmRepository;

/**
 * 
 * 
 * @see PluginInstaller
 * @author Falko Bräutigam
 */
public class InstallPluginOperation
        extends UmOperation {

    @Mandatory
    public Config<Project>              project;

    @Mandatory
    public Config<PluginCatalogEntry>   plugin;


    public InstallPluginOperation() {
        super( "Install Plugin" );
        this.umUow.set( ProjectRepository.session() );
        this.vmUow.set( VmRepository.newUnitOfWork() );
    }


    @Override
    protected IStatus doWithCommit( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        assert project.get().belongsTo( umUow.get() );
        monitor.beginTask( getLabel() + " " + plugin.get().id(), 2 );

        // stop process
        ProjectInstanceRecord instance = vmUow.get().findInstance( new ProjectInstanceIdentifier( project.get() ) )
                .orElseThrow( () -> new RuntimeException( "No project instance found for: " + project.get() ) );
        
        ProcessRecord process = instance.process.get();
        if (process != null) {
            StopProcessOperation op = new StopProcessOperation();
            op.kill.set( true );
            op.process.set( process );
            op.vmUow.set( vmUow.get() );
            op.execute( SubMonitor.on( monitor, 1 ), null );
        }

        // install plugin
        project.get().plugins.get().install( instance, plugin.get(), monitor );
        monitor.done();
        return Status.OK_STATUS;
    }
    
}
