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
import org.polymap.model2.runtime.DelegatingUnitOfWork;
import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.ModelRuntimeException;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.locking.CommitLockStrategy;
import org.polymap.model2.runtime.locking.OptimisticLocking;
import org.polymap.model2.runtime.locking.PessimisticLocking;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

/**
 * The registry of all currently active/running Hosts, VMs, processes.
 * <p/>
 * There is one global {@link #instance()}. Concurrent access is synchronized on
 * {@link Entity} level using {@link NestedOneReaderPessimisticLocking}. 
 * <p/>
 * XXX {@link Entity} is not for concurrent modifications. Is this a problem? 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class VmRepository {

    private static final Log log = LogFactory.getLog( VmRepository.class );
    
    private static EntityRepository     repo;
    
    
    public static void init( File basedir ) throws IOException {
        File dir  = new File( basedir, "vm" );
        LuceneRecordStore store = LuceneRecordStore.newConfiguration()
                .indexDir.put( dir )
                .clean.put( false )
                .executor.put( null )
                .create();
        
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
                            new CommitLockStrategy.Serialize() )
                .create();
        
//        instance = new VmRepository( repo.newUnitOfWork() );
        checkInit();
    }
    
    
    protected static void checkInit() {
        try (
            UnitOfWork _uow = repo.newUnitOfWork()
        ){
            if (_uow.query( HostRecord.class ).execute().size() == 0) {
                _uow.createEntity( HostRecord.class, "localhost", (HostRecord proto) -> {
                    proto.hostType.set( HostType.JCLOUDS );
                    proto.hostId.set( "localhost" );
                    proto.inetAddress.set( "localhost" );
                    proto.statistics.createValue( HostStatistics.defaults );
                    return HostRecord.defaults.initialize( proto );
                });
                _uow.commit();
            }
        }
    }
    
    
    /**
     * Make sure to {@link VmRepository#commit()} or {@link VmRepository#rollback()}!
     */
    public static VmUnitOfWork newUnitOfWork() {
        return new VmUnitOfWork( repo.newUnitOfWork() );
    }
    

    /**
     * The {@link UnitOfWork} of a {@link VmRepository}. 
     *
     * @author Falko Bräutigam
     */
    public static class VmUnitOfWork
            extends DelegatingUnitOfWork {

        /** Global write lock count. Helps to find intercepted read->write upgrade. */
        private volatile int    globalLockCount;

        private Cache<ProjectInstanceIdentifier,Optional<ProjectInstanceRecord>> 
                                instanceCache = CacheConfig.defaults().initSize( 256 ).createCache();
     
        public VmUnitOfWork( UnitOfWork delegate ) {
            super( delegate );
        }
        

        public Optional<ProjectInstanceRecord> findInstance( ProjectInstanceIdentifier pid ) {
            return instanceCache.get( pid, key -> {
                ResultSet<ProjectInstanceRecord> rs = delegate().query( ProjectInstanceRecord.class )
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


        public List<HostRecord> allHosts() {
            return delegate().query( HostRecord.class ).execute().stream().collect( Collectors.toList() );
        }


        public void commit() throws ModelRuntimeException {
            log.debug( "COMMIT provision: ..." );
            super.commit();
            close();
        }


        public void rollback() throws ModelRuntimeException {
            log.info( "ROLLBACK provision: ..." );
            super.rollback();
            close();
        }


        public void close() {
            try {
                super.close();
            }
            finally {
                PessimisticLocking.notifyClosed( delegate() );
            }
        }


        @Override
        protected void finalize() throws Throwable {
            if (isOpen()) {
                log.warn( "FINALIZE: VmUnitOfWork is still open!" );
                close();
            }
        }
        
    }
    
}
