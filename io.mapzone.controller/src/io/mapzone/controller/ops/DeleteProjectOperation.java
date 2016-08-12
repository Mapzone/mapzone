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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.i18n.IMessages;

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.VmRepository;

/**
 * Deletes a {@link Project}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class DeleteProjectOperation
        extends UmOperation {

    private static Log log = LogFactory.getLog( DeleteProjectOperation.class );

    public static final IMessages i18n = Messages.forPrefix( "DeleteProjectOperation" );
    
    @Mandatory
    public Config<Project>              project;
    
    
    /**
     * Creates a new instance wirh {@link ProjectRepository#session()} set.
     */
    public DeleteProjectOperation() {
        super( i18n.get( "title" ) );
        vmUow.set( VmRepository.newUnitOfWork() );
        umUow.set( ProjectRepository.session() );
    }


    @Override
    public IStatus doWithCommit( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        assert project.isPresent();
        assert project.get().belongsTo( umUow.get() );

        monitor.beginTask( getLabel(), 10 );

        // find instance on host
        ProjectInstanceRecord instance = vmUow.get().findInstance( new ProjectInstanceIdentifier( project.get() ) )
                .orElseThrow( () -> new RuntimeException( "No project instance found for: " + project.get() ) );
        monitor.worked( 1 );

        // stop process
        ProcessRecord process = instance.process.get();
        if (process != null) {
            StopProcessOperation op = new StopProcessOperation();
            op.process.set( process );
            op.vmUow.set( vmUow.get() );
            op.execute( null, null );
        }
        monitor.worked( 1 );

        // uninstall filesystem on host
        project.get().launcher.get().uninstall( instance, new SubProgressMonitor( monitor, 7 ) );

        // remove instance and association
        Object instanceId = instance.id();
        vmUow.get().removeEntity( instance );
        HostRecord host = instance.host.get();
        assert host.instances.stream().allMatch( i -> !i.id().equals( instanceId ) );

        // remove project
        umUow.get().removeEntity( project.get() );

        monitor.done();
        return Status.OK_STATUS;
    }


    @Override
    protected void onSuccess() {
        EventManager.instance().publish( new EntityChangedEvent( project.get() ) );
        vmUow.get().close();
    }


    @Override
    protected void onError( Throwable e ) {
        vmUow.get().close();
    }
    
}
