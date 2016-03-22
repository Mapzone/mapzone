package io.mapzone.controller.vm.repository;

import java.util.Date;

import io.mapzone.controller.vm.runtime.HostRuntime;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.model2.BidiManyAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.Defaults;
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
public class HostRecord
        extends Entity {

    public static HostRecord        TYPE;
    
    public static ValueInitializer<HostRecord> defaults = (HostRecord proto) -> {
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
     * The host's IP or DNS name. This is used to build URIs for the instances.
     */
    public Property<String>                     inetAddress;
    
    /**
     * The next free port on the host.
     */
    @DefaultValue( "32768" )
    public Property<Integer>                    portCount;
    
    @Defaults
    @Concerns( BidiManyAssociationConcern.class )
    public ManyAssociation<ProjectInstanceRecord>  instances;
    
    public Property<HostStatistics>             statistics;
    
    public Lazy<HostRuntime>                    runtime = new LockedLazyInit( () -> HostRuntime.forHost( this ) ); 
    
    
//    /**
//     * Starts a new process and associates it with this host.
//     *
//     * @param initializer
//     * @return Newly created entity.
//     * @throws Exception 
//     */
//    public ProcessRecord startInstance( ProjectInstanceRecord instance, ValueInitializer<ProcessRecord> initializer ) throws Exception {
//        // XXX activate when process cleaning works?
//       // assert instance.process.get() == null;
//        
//        // create new entity
//        UnitOfWork uow = context.getUnitOfWork();
//        ProcessRecord result = uow.createEntity( ProcessRecord.class, null, initializer, (ProcessRecord proto) -> {
//            proto.instance.set( instance );
//            return proto;
//        });
//        
//        // start process (sets port)
//        runtime.get().process( result ).start();
//        return result;
//    }


    public void updateStatistics() {
        statistics.get().lastChecked.set( new Date() );
    }

}
