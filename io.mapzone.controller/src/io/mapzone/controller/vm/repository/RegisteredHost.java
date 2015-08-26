package io.mapzone.controller.vm.repository;

import io.mapzone.controller.model.Project;
import io.mapzone.controller.vm.runtime.HostRuntime;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.model2.Entity;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Property;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
//@Concerns(PessimisticLocking.class)
public class RegisteredHost
        extends Entity {

    public static RegisteredHost        TYPE;
    
    public enum HostType {
        JCLOUDS
    }
    
    // instance *******************************************
    
    public Property<HostType>                   hostType;
    
    /**
     * The address or id that identifies this hosts and allows to access it.
     */
    public Property<String>                     address;
    
    public ManyAssociation<RegisteredProcess>   processes;

    public Lazy<HostRuntime>                    runtime = new LockedLazyInit( () -> HostRuntime.forHost( this ) ); 
    

    /**
     * Starts a new process and associates it with this host.
     *
     * @param initializer
     * @return Newly created entity.
     * @throws Exception 
     */
    public RegisteredProcess startProcess( Project project, ValueInitializer<RegisteredProcess> initializer ) throws Exception {
        // create new entity
        RegisteredProcess result = context.getUnitOfWork()
                .createEntity( RegisteredProcess.class, null, initializer, (RegisteredProcess proto) -> {
                    proto.host.set( this );
                    return proto;
                });
        
        // start process (sets port)
        runtime.get().process( result ).start( project );
        return result;
    }
    
}
