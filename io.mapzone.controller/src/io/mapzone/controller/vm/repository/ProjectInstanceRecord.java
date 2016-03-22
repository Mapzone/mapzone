package io.mapzone.controller.vm.repository;

import io.mapzone.controller.um.launcher.ProjectLauncher;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;

import org.polymap.core.runtime.collect.Consumer;

import org.polymap.model2.Association;
import org.polymap.model2.BidiAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.Immutable;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * Represents an installed instance of a {@link Project} consisting of exe/bin path,
 * workspace, log paths. Ready to run and spawn a {@link ProcessRecord}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
//@Concerns( PessimisticLocking.class )
public class ProjectInstanceRecord
        extends VmEntity {

    public static ProjectInstanceRecord     TYPE;
    
    /** Organization or user name. */
    @Queryable
    @Immutable
    public Property<String>                 organisation;
    
    /** The name of the project. */
    @Queryable
    @Immutable
    public Property<String>                 project;
    
    @Nullable
    @Queryable
    @Immutable
    public Property<String>                 version;
    
    @Nullable  // XXX
    //FIXME @Immutable  
    @Concerns( BidiAssociationConcern.class )
    public Association<HostRecord>          host;
    
    @Nullable  // XXX
    @Concerns( BidiAssociationConcern.class )
    public Association<ProcessRecord>       process;
    
    @Immutable
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
