package io.mapzone.controller.vm.repository;

import java.util.Date;

import io.mapzone.controller.vm.runtime.HostRuntime;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.model2.BidiManyAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.Defaults;
import org.polymap.model2.Entity;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Property;
import org.polymap.model2.runtime.UnitOfWork;
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
    
    public static ValueInitializer<RegisteredHost> defaults = (RegisteredHost proto) -> {
        proto.hostType.set( HostType.JCLOUDS );
        proto.hostId.set( "local" );
        proto.inetAddress.set( "localhost" );
        proto.updateStatistics();
        return proto;
    };
    
    public enum HostType {
        JCLOUDS
    }
    
    // instance *******************************************
    
    public Property<HostType>                   hostType;
    
    /**
     * The address or id that identifies this hosts and allows to access it.
     */
    public Property<String>                     hostId;
    
    /**
     * The host's IP or DnS name. This is used to build URIs for the instances.
     */
    public Property<String>                     inetAddress;
    
    @Defaults
    @Concerns( BidiManyAssociationConcern.class )
    public ManyAssociation<RegisteredInstance>  instances;
    
    public Property<HostRuntimeStatistics>      statistics;
    
    public Lazy<HostRuntime>                    runtime = new LockedLazyInit( () -> HostRuntime.forHost( this ) ); 
    
    
    /**
     * Starts a new process and associates it with this host.
     *
     * @param initializer
     * @return Newly created entity.
     * @throws Exception 
     */
    public RegisteredProcess startInstance( RegisteredInstance instance, ValueInitializer<RegisteredProcess> initializer ) throws Exception {
        // XXX activate when process cleaning works?
       // assert instance.process.get() == null;
        
        // create new entity
        UnitOfWork uow = context.getUnitOfWork();
        RegisteredProcess result = uow.createEntity( RegisteredProcess.class, null, initializer, (RegisteredProcess proto) -> {
            proto.instance.set( instance );
            return proto;
        });
        
        // start process (sets port)
        runtime.get().process( result ).start();
        return result;
    }


    public void updateStatistics() {
        statistics.get().lastChecked.set( new Date() );
    }

}
