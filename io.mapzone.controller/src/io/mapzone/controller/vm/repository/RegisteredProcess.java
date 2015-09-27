package io.mapzone.controller.vm.repository;

import java.util.Date;

import io.mapzone.controller.vm.runtime.ProcessRuntime;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.model2.Association;
import org.polymap.model2.BidiAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.Defaults;
import org.polymap.model2.Entity;
import org.polymap.model2.Immutable;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
//@Concerns(PessimisticLocking.class)
public class RegisteredProcess
        extends Entity {

    public static RegisteredProcess         TYPE;

    @Nullable  // XXX
//    @Immutable
    @Concerns( BidiAssociationConcern.class )
    public Association<RegisteredInstance>  instance;
    
    @Nullable
    @Immutable
    public Property<Integer>                pid;
    
    @Immutable
    public Property<Integer>                port;
    
    @Defaults
    public Property<Date>                   started;
    
    public Lazy<ProcessRuntime>             runtime = new LockedLazyInit( () -> instance.get().host.get().runtime.get().process( this ) );
    
}
