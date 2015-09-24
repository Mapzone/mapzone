package io.mapzone.controller.vm.repository;

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


//    /**
//     * Creates a new project instance for the given {@link Project}. Creates a new
//     * {@link RegisteredInstance}, initializes properties, prepares filesystem on the
//     * host.
//     *
//     * @param project
//     * @param projectHolder 
//     * @param monitor 
//     * @return Newly created entity.
//     * @throws Exception 
//     */
//    public RegisteredInstance createInstance( Project project, ProjectHolder projectHolder, IProgressMonitor monitor ) 
//            throws Exception {
//        // create new entity
//        UnitOfWork uow = context.getUnitOfWork();
//        RegisteredInstance result = uow.createEntity( RegisteredInstance.class, null, (RegisteredInstance proto) -> {
//            proto.organisation.set( projectHolder.name.get() );
//            proto.project.set( project.name.get() );
//            return proto;
//        });
//        instances.add( result );
//        assert result.host.get() == this;
//
//        // prepare the instance on the host
//        runtime.get().instance( result ).prepareInstall( project, monitor );
//        return result;
//    }

}
