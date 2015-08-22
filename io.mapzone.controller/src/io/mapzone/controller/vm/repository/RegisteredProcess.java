package io.mapzone.controller.vm.repository;

import io.mapzone.controller.vm.runtime.ProcessRuntime;

import org.polymap.model2.Association;
import org.polymap.model2.Concerns;
import org.polymap.model2.Entity;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.runtime.event.PessimisticLocking;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
@Concerns(PessimisticLocking.class)
public class RegisteredProcess
        extends Entity {

    public static RegisteredProcess         TYPE;
    
    @Queryable
    public Property<String>                 organisation;
    
    @Queryable
    public Property<String>                 project;
    
    @Nullable
    @Queryable
    public Property<String>                 version;
    
    public Property<Integer>                port;
    
    public Association<RegisteredHost>      host;
    
    public Property<String>                 fsPath;
    
    
    public ProcessRuntime runtime() {
        
    }
    
}
