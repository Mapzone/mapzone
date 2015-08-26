package io.mapzone.controller.vm.repository;

import io.mapzone.controller.vm.runtime.ProcessRuntime;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.model2.Association;
import org.polymap.model2.Entity;
import org.polymap.model2.Immutable;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
//@Concerns(PessimisticLocking.class)
public class RegisteredProcess
        extends Entity {

    public static RegisteredProcess         TYPE;
    
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
    
    @Nullable
    @Immutable
    public Property<Integer>                pid;
    
    @Immutable
    public Property<Integer>                port;
    
    @Immutable
    public Association<RegisteredHost>      host;
    
//    @Immutable
//    public Property<String>                 executablePath;
//    
//    @Immutable
//    public Property<String>                 homePath;
//    
//    @Immutable
//    public Property<String>                 dataPath;
    
    @Immutable
    public Property<String>                 logPath;

    public Lazy<ProcessRuntime>             runtime = new LockedLazyInit( () -> host.get().runtime.get().process( this ) );
    
}
