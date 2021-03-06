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

import static org.polymap.core.ui.UIUtils.submon;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.controller.plugincat.PluginCatalogEntry;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.VmRepository;

/**
 * Update the binaries of a {@link Project}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UpdateProjectSoftwareOperation
        extends UmOperation {

    @Mandatory
    public Config<Project>              project;
    
    
    /**
     * Creates a new instance with {@link ProjectRepository#session()} set.
     */
    public UpdateProjectSoftwareOperation() {
        super( "Update" );
        vmUow.set( VmRepository.newUnitOfWork() );
        umUow.set( ProjectRepository.session() );
    }


    @Override
    public IStatus doWithCommit( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        assert project.isPresent();
        assert project.get().belongsTo( umUow.get() );

        List<PluginCatalogEntry> installedPlugins = project.get().plugins.get().installed();
        monitor.beginTask( getLabel(), 1 + 5 + installedPlugins.size() );

        ProjectInstanceRecord instance = vmUow.get().findInstance( new ProjectInstanceIdentifier( project.get() ) )
                .orElseThrow( () -> new RuntimeException( "No project instance found for: " + project.get() ) );
        
        // stop process
        ProcessRecord process = instance.process.get();
        if (process != null) {
            monitor.subTask( "Stop process" );            
            StopProcessOperation op = new StopProcessOperation();
            op.kill.set( true );
            op.process.set( process );
            op.vmUow.set( vmUow.get() );
            op.execute( null, null );
        }
        monitor.worked( 1 );
        
        // install arena
        project.get().launcher.get().install( instance, submon( monitor, 5 ) );

        // install plugins
        for (PluginCatalogEntry plugin : project.get().plugins.get().installed()) {
            InstallPluginOperation op = new InstallPluginOperation();
            op.project.set( project.get() );
            op.plugin.set( plugin );
            op.umUow.set( umUow.get() );
            op.vmUow.set( vmUow.get() );
            op.doWithCommit( submon( monitor, 1 ), null );
        }
        
        return Status.OK_STATUS;
    }

    
    @Override
    protected void onSuccess() {
        vmUow.get().close();
        super.onSuccess();
    }


    @Override
    protected void onError( Throwable e ) {
        vmUow.get().close();
        super.onError( e );
    }
    
}
