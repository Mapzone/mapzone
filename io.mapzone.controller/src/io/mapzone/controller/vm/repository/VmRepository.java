package io.mapzone.controller.vm.repository;

import static org.polymap.model2.query.Expressions.and;
import static org.polymap.model2.query.Expressions.eq;
import io.mapzone.controller.vm.repository.RegisteredHost.HostType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.model2.Entity;
import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.CommitLockStrategy;
import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.ModelRuntimeException;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.ValueInitializer;
import org.polymap.model2.store.OptimisticLocking;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

/**
 * The registry of all currently active/running VMs.
 * <p/>
 * There is one global {@link #instance()}. In order to synchronize concurrent
 * access it exposes the {@link #lock()} method. 
 * <p/>
 * XXX {@link Entity} is not for concurrent modifications. Is this a problem? 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class VmRepository {

    private static Log log = LogFactory.getLog( VmRepository.class );
    
    private static EntityRepository     repo;
    
    public static void init( File basedir ) throws IOException {
        File dir  = new File( basedir, "vm" );
        LuceneRecordStore store = new LuceneRecordStore( dir, false );
        repo = EntityRepository.newConfiguration()
                .entities.set( new Class[] {
                            RegisteredHost.class,
                            RegisteredInstance.class,
                            RegisteredProcess.class })
                .store.set( 
                            // make sure to never loose updates or something
                            // we may skip this later for performance
                            new OptimisticLocking(
                            new RecordStoreAdapter( store ) ) )
                .commitLockStrategy.set( () ->
                            // lock synchronized writes -> concurrent commit is a program error
                            new CommitLockStrategy.FailOnConcurrentCommit() )
                .create();            
        instance = new VmRepository( repo.newUnitOfWork() );
        checkInit();
    }
    
    
    protected static void checkInit() {
        try (
            UnitOfWork _uow = repo.newUnitOfWork()
        ){
            if (_uow.query( RegisteredHost.class ).execute().size() == 0) {
                _uow.createEntity( RegisteredHost.class, "local", (RegisteredHost proto) -> {
                    proto.hostType.set( HostType.JCLOUDS );
                    proto.hostId.set( "local" );
                    proto.inetAddress.set( "localhost" );
                    proto.statistics.createValue( HostRuntimeStatistics.defaults );
                    return RegisteredHost.defaults.initialize( proto );
                });
                _uow.commit();
            }
        }
    }
    
    
    private static VmRepository         instance;
    
    public static VmRepository instance() {
        return instance;
    }
    
    
    // instance *******************************************
    
    private UnitOfWork                  uow;

    /**
     * Synchronizes modifications of {@link VmRepository} and the real host/process
     * state. This implements pessimistic locking. A lock must be aquired *before*
     * accessing the entity to modify. See {@link VmRepository} for detail.
     * <p/>
     * XXX This might get a huge bottleneck. We will find a more fine grained
     * solution later. However, the API will not change to much. The Provision sees a
     * lock in its context which it aquires for modification. It does not know where
     * it comes from.
     */
    public ReentrantReadWriteLock       lock = new ReentrantReadWriteLock();
    
    /** Global write lock count. Helps to find intercepted read->write upgrade. */
    private volatile int                globalLockCount;

    
    public VmRepository( UnitOfWork uow ) {
        this.uow = uow;
    }

    
    /**
     * Aquires a write lock for the current thread. This possibly blocks execution until
     * it is possible to aquire the lock. A lock must be aquired *before* accessing
     * the entity to modify.
     */
    public void lock() {
        int readLocKCount = globalLockCount;
        if (lock.getReadHoldCount() > 0) {
            lock.readLock().unlock();
        }
        assert !lock.isWriteLockedByCurrentThread();
        lock.writeLock().lock();
        globalLockCount++;

        // XXX does this actually work? Doug?
        if (globalLockCount > readLocKCount+1) {
            // FIXME
            throw new RuntimeException( "FIXME: Atomically upgrading lock failed." );
        }
    }
    
    
    protected void unlock() {
        if (lock.isWriteLockedByCurrentThread()) {
            lock.readLock().lock();
            lock.writeLock().unlock();
        }
    }

    
    public boolean isLockeByCurrentThread() {
        return lock.isWriteLockedByCurrentThread();
    }
    
    
    public Optional<RegisteredInstance> findInstance( String organisation, String project, String version ) {
        ResultSet<RegisteredInstance> rs = uow.query( RegisteredInstance.class )
                .where( and( 
                        eq( RegisteredInstance.TYPE.organisation, organisation ),
                        eq( RegisteredInstance.TYPE.project, project ) ) )
                .execute();
        assert rs.size() < 2;
        return rs.stream().findAny();
    }

    
    public Optional<RegisteredProcess> findProcess( String organisation, String project, String version ) {
        return findInstance( organisation, project, version ).map( _instance -> _instance.process.get() );
    }

    
    
    public void removeEntity( Entity entity ) {
        uow.removeEntity( entity );
    }


    public List<RegisteredHost> allHosts() {
        return uow.query( RegisteredHost.class ).execute().stream().collect( Collectors.toList() );
    }


    public <T extends Entity> T createEntity( Class<T> entityClass, Object id, ValueInitializer<T>... initializers ) {
        return uow.createEntity( entityClass, id, initializers );
    }


    public void commit() throws ModelRuntimeException {
        if (isLockeByCurrentThread()) {
            log.info( "COMMIT provision: ..." );
            uow.commit();
            unlock();
        }
    }

    public void rollback() throws ModelRuntimeException {
        if (isLockeByCurrentThread()) {
            log.info( "ROLLBACK provision: ..." );
            uow.rollback();
            unlock();
        }
    }

//    public void close() {
//        uow.close();
//    }

}
