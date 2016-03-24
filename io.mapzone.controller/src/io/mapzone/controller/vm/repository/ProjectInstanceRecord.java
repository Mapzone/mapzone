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

import org.polymap.core.runtime.collect.Consumer;

import org.polymap.model2.Association;
import org.polymap.model2.BidiAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.Immutable;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.runtime.locking.OneReaderPessimisticLocking;

import io.mapzone.controller.um.launcher.ProjectLauncher;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;

/**
 * Represents an installed instance of a {@link Project} consisting of exe/bin path,
 * workspace, log paths. Ready to run and spawn a {@link ProcessRecord}.
 *
 * @author Falko Bräutigam
 */
public class ProjectInstanceRecord
        extends VmEntity {

    public static ProjectInstanceRecord     TYPE;
    
    /** Organization or user name. */
    @Queryable
    @Immutable
    @Concerns( OneReaderPessimisticLocking.class )
    public Property<String>                 organisation;
    
    /** The name of the project. */
    @Queryable
    @Immutable
    @Concerns( OneReaderPessimisticLocking.class )
    public Property<String>                 project;
    
    @Nullable
    @Queryable
    @Immutable
    @Concerns( OneReaderPessimisticLocking.class )
    public Property<String>                 version;
    
    @Nullable  // XXX
    //FIXME @Immutable  
    @Concerns( BidiAssociationConcern.class )
    public Association<HostRecord>          host;
    
    @Nullable  // XXX
    @Concerns( BidiAssociationConcern.class )
    public Association<ProcessRecord>       process;
    
    @Immutable
    @Concerns( OneReaderPessimisticLocking.class )
    public Property<String>                 homePath;

    /**
     * 
     *
     * @param task
     * @throws IllegalStateException If project is not found.
     */
    public void executeLauncher( Consumer<ProjectLauncher,Exception> task ) throws Exception {
        try (
            ProjectRepository pr = ProjectRepository.newInstance();
        ){
            Project p = pr.findProject( organisation.get(), project.get() )
                    .orElseThrow( () -> new IllegalStateException( "No project for instance: " + organisation.get() + "/" + project.get() ) );
            task.accept( p.launcher.get() );
        }
    }
    
}
