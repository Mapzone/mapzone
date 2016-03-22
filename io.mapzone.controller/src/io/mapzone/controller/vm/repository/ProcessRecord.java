package io.mapzone.controller.vm.repository;

import java.util.Date;

import org.polymap.model2.Association;
import org.polymap.model2.BidiAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.Defaults;
import org.polymap.model2.Entity;
import org.polymap.model2.Immutable;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;

/**
 * Represents a started/running process of a {@link ProjectInstanceRecord}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
//@Concerns(PessimisticLocking.class)
public class ProcessRecord
        extends Entity {

    public static ProcessRecord         TYPE;

    @Nullable  // XXX
//    @Immutable
    @Concerns( BidiAssociationConcern.class )
    public Association<ProjectInstanceRecord>  instance;
    
    @Nullable
    @Immutable
    public Property<Integer>            pid;
    
    @Immutable
    public Property<Integer>            port;
    
    @Defaults
    public Property<Date>               started;
    
    //public Lazy<ProcessRuntime>         runtime = new LockedLazyInit( () -> instance.get().host.get().runtime.get().process( this ) );
    
}
