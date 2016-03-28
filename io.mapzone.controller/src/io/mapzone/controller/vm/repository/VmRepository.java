/* 
 * mapzone.io
 * Copyright (C) 2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.controller.vm.repository;

import static org.polymap.model2.query.Expressions.and;
import static org.polymap.model2.query.Expressions.eq;

import io.mapzone.controller.vm.repository.HostRecord.HostType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.runtime.cache.Cache;
import org.polymap.core.runtime.cache.CacheConfig;

import org.polymap.model2.Entity;
import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.ModelRuntimeException;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.ValueInitializer;
import org.polymap.model2.runtime.locking.CommitLockStrategy;
import org.polymap.model2.runtime.locking.OptimisticLocking;
import org.polymap.model2.runtime.locking.PessimisticLocking;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

/**
 * The registry of all currently active/running Hosts, VMs, processes.
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
                            HostRecord.class,
                            ProjectInstanceRecord.class,
                            ProcessRecord.class })
                .store.set( 
                            // make sure to never loose updates or something
                            // we may skip this later for performance
                            new OptimisticLocking(
                            new RecordStoreAdapter( store ) ) )
                .commitLockStrategy.set( () ->
                            // lock synchronized writes -> concurrent commit is a program error
                            new CommitLockStrategy.FailOnConcurrentCommit() )
                .create();
        
//        instance = new VmRepository( repo.newUnitOfWork() );
        checkInit();
    }
    
    
    protected static void checkInit() {
        try (
            UnitOfWork _uow = repo.newUnitOfWork()
        ){
            if (_uow.query( HostRecord.class ).execute().size() == 0) {
                _uow.createEntity( HostRecord.class, "local", (HostRecord proto) -> {
                    proto.hostType.set( HostType.JCLOUDS );
                    proto.hostId.set( "local" );
                    proto.inetAddress.set( "localhost" );
                    proto.statistics.createValue( HostStatistics.defaults );
                    return HostRecord.defaults.initialize( proto );
                });
                _uow.commit();
            }
        }
    }
    
    
    /**
     *
     */
    public static VmRepository newInstance() {
        return new VmRepository();
    }
    
    
    // instance *******************************************
    
    private UnitOfWork                  uow = repo.newUnitOfWork();

//    /**
//     * Synchronizes modifications of {@link VmRepository} and the real host/process
//     * state. This implements pessimistic locking. A lock must be aquired *before*
//     * accessing the entity to modify. See {@link VmRepository} for detail.
//     * <p/>
//     * XXX This might get a huge bottleneck. We will find a more fine grained
//     * solution later. However, the API will not change to much. The Provision sees a
//     * lock in its context which it aquires for modification. It does not know where
//     * it comes from.
//     */
//    public ReentrantReadWriteLock       lock = new ReentrantReadWriteLock();
    
    /** Global write lock count. Helps to find intercepted read->write upgrade. */
    private volatile int                globalLockCount;
    
    private Cache<ProjectInstanceIdentifier,Optional<ProjectInstanceRecord>> 
                                        instanceCache = CacheConfig.defaults().initSize( 256 ).createCache();
    
    
    public Optional<ProjectInstanceRecord> findInstance( ProjectInstanceIdentifier pid ) {
        return instanceCache.get( pid, key -> {
            ResultSet<ProjectInstanceRecord> rs = uow.query( ProjectInstanceRecord.class )
                    .where( and( 
                            eq( ProjectInstanceRecord.TYPE.organisation, pid.organization() ),
                            eq( ProjectInstanceRecord.TYPE.project, pid.project() ) ) )
                    .execute();
            assert rs.size() < 2;
            return rs.stream().findAny();
        });
    }

    
    public Optional<ProcessRecord> findProcess( ProjectInstanceIdentifier pid ) {
        return findInstance( pid ).map( _instance -> _instance.process.get() );
    }
    
    
    public void removeEntity( Entity entity ) {
        uow.removeEntity( entity );
    }


    public List<HostRecord> allHosts() {
        return uow.query( HostRecord.class ).execute().stream().collect( Collectors.toList() );
    }


    public <T extends Entity> T createEntity( Class<T> entityClass, Object id, ValueInitializer<T>... initializers ) {
        return uow.createEntity( entityClass, id, initializers );
    }


    public void commit() throws ModelRuntimeException {
        log.info( "COMMIT provision: ..." );
        uow.commit();
        close();
    }

    
    public void rollback() throws ModelRuntimeException {
        log.info( "ROLLBACK provision: ..." );
        uow.rollback();
        close();
    }


    public void close() {
        uow.close();
        PessimisticLocking.notifyClosed( uow );
    }

}
