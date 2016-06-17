/* 
 * polymap.org
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
package io.mapzone.controller.catalog;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.CorePlugin;

import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.locking.OptimisticLocking;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

import io.mapzone.controller.catalog.model.CatalogEntry;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CatalogRepositoryConfig {

    private static final Log log = LogFactory.getLog( CatalogRepositoryConfig.class );


    /**
     * Creates the repository instance. 
     */
    public static EntityRepository initRepo() throws IOException {
        log.info( "Initializing repository ..." );
        File dir = new File( CorePlugin.getDataLocation( CatalogPlugin.instance().getBundle() ), "catalog" );
        dir.mkdirs();
        LuceneRecordStore store = new LuceneRecordStore( dir, false );
        return EntityRepository.newConfiguration()
                .entities.set( new Class[] {
                        CatalogEntry.class } )
                .store.set( 
                        new OptimisticLocking( 
                        new RecordStoreAdapter( store ) ) )
                .create();
    }

    
    public static void createTestData( EntityRepository repo ) {
        try (
            UnitOfWork uow = repo.newUnitOfWork();
        ){
            if (uow.query( CatalogEntry.class ).maxResults( 1 ).execute().size() == 0) {
                uow.createEntity( CatalogEntry.class, null, (CatalogEntry proto) -> {
                    CatalogEntry.defaults.initialize( proto );
                    proto.title.set( "Testentry" );
                    proto.description.set( "Test entry." );
                    proto.creator.set( "ich" );
                    proto.publisher.set( "mapzone.io" );
                    proto.subject.add( "Test" );
                    proto.subject.add( "mapzone" );
                    return proto;
                });
                uow.commit();
                log.info( "Test data created." );
            }
        }
    }
    
}
