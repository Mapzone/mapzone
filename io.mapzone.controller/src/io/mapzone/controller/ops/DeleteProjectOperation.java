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

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.VmRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.i18n.IMessages;

/**
 * Deletes a new {@link Project}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class DeleteProjectOperation
        extends DefaultOperation {

    private static Log log = LogFactory.getLog( DeleteProjectOperation.class );

    public static final IMessages i18n = Messages.forPrefix( "DeleteProjectOperation" );
    
    @Mandatory
    public Config<ProjectRepository>    repo;
    
    @Mandatory
    public Config<Project>              project;
    
    
    public DeleteProjectOperation() {
        super( i18n.get( "title" ) );
        ConfigurationFactory.inject( this );
    }


    @Override
    public IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        VmRepository vmRepo = VmRepository.newInstance();
        try {
            monitor.beginTask( getLabel(), 10 );

            // find instance on host
            ProjectInstanceRecord instance = vmRepo.findInstance( 
                    project.get().organizationOrUser().name.get(),
                    project.get().name.get(),
                    null )
                    .orElseThrow( () -> new RuntimeException( "No project instance found for: " + project ) );
            monitor.worked( 1 );

            // stop process
            ProcessRecord process = instance.process.get();
            if (process != null) {
                StopProcessOperation op = new StopProcessOperation();
                op.process.set( process );
                op.vmRepo.set( vmRepo );
                op.execute( null, null );
            }
            monitor.worked( 1 );
            
            // uninstall filesystem on host
            project.get().launcher.get().uninstall( instance, new SubProgressMonitor( monitor, 7 ) );
            
            // remove instance and association
            HostRecord host = instance.host.get();
            host.instances.remove( instance );
            assert instance.host.get() == null;
            vmRepo.removeEntity( instance );
            
            // remove project
            project.get().organization.set( null );
            project.get().user.set( null );
            repo.get().removeEntity( project.get() );

            // commit
            vmRepo.commit();
            repo.get().commit();
            EventManager.instance().publish( new EntityChangedEvent( project.get() ) );
            monitor.done();
            return Status.OK_STATUS;
        }
        catch (Exception e) {
            log.warn( "", e );
            vmRepo.rollback();
            throw new ExecutionException( i18n.get( "errorMsg", e.getLocalizedMessage() ), e );
        }
    }

}
