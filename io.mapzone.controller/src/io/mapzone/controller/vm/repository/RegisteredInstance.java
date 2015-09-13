package io.mapzone.controller.vm.repository;

import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.runtime.InstanceRuntime;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.model2.Association;
import org.polymap.model2.BidiAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.Entity;
import org.polymap.model2.Immutable;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * An installed instance of a {@link Project} consisting of exe/bin path, workspace,
 * log paths. Ready to run and spawn an {@link RegisteredProcess}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
//@Concerns(PessimisticLocking.class)
public class RegisteredInstance
        extends Entity {

    public static RegisteredInstance         TYPE;
    
    /** Organization or user name. */
    @Queryable
    @Immutable
    public Property<String>                 organisation;
    
    @Queryable
    @Immutable
    public Property<String>                 project;
    
    @Nullable
    @Queryable
    @Immutable
    public Property<String>                 version;
    
    @Nullable  // XXX
    @Immutable
    @Concerns( BidiAssociationConcern.class )
    public Association<RegisteredHost>      host;
    
    @Nullable  // XXX
    @Concerns( BidiAssociationConcern.class )
    public Association<RegisteredProcess>   process;
    
    @Immutable
    public Property<String>                 exePath;
    
    @Immutable
    public Property<String>                 homePath;
    
    @Immutable
    public Property<String>                 dataPath;
    
    @Immutable
    public Property<String>                 logPath;

    public Lazy<InstanceRuntime>            runtime = new LockedLazyInit( () -> host.get().runtime.get().instance( this ) );
    
}
