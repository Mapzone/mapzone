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

import static java.util.Collections.singletonList;

import java.util.List;
import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.CorePlugin;

import org.polymap.rhei.fulltext.FullQueryProposalDecorator;
import org.polymap.rhei.fulltext.FulltextIndex;
import org.polymap.rhei.fulltext.indexing.FeatureTransformer;
import org.polymap.rhei.fulltext.indexing.LowerCaseTokenFilter;
import org.polymap.rhei.fulltext.model2.FulltextIndexer;
import org.polymap.rhei.fulltext.store.lucene.LuceneFulltextIndex;

import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.locking.CommitLockStrategy;
import org.polymap.model2.runtime.locking.OptimisticLocking;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

import io.mapzone.controller.catalog.model.CatalogEntry;

/**
 * The persistency context of a catalog instance. 
 *
 * @author Falko Bräutigam
 */
public class CatalogRepositoryContext {

    private static final Log log = LogFactory.getLog( CatalogRepositoryContext.class );
    
    private File                    dataDir;

    private LuceneRecordStore       store;

    private EntityRepository        repo;

    private LuceneFulltextIndex     index;
    
//    /** The global read-cache. */
//    private UnitOfWork              uow;

    
    /**
     * Creates the repository instance. 
     */
    public CatalogRepositoryContext() throws IOException {
        log.info( "Initializing repository ..." );
        dataDir = CorePlugin.getDataLocation( CatalogPlugin.instance().getBundle() );
        
        // fulltext
        index = new LuceneFulltextIndex( new File( dataDir, "fulltext" ) );
        index.addTokenFilter( new LowerCaseTokenFilter() );
        List<FeatureTransformer> transformers = singletonList( new CatalogEntryFulltextTransformer() ); 
    
        // store
        File dir = new File( dataDir, "store" );
        dir.mkdirs();
        store = new LuceneRecordStore( dir, false );
        repo = EntityRepository.newConfiguration()
                .entities.set( new Class[] {
                        CatalogEntry.class } )
                .store.set( 
                        new OptimisticLocking(
                        new FulltextIndexer( index, (entity) -> true, transformers,
                        new RecordStoreAdapter( store ) ) ) )
                .commitLockStrategy.set( () -> 
                        new CommitLockStrategy.Serialize() )
                .create();
        
//        uow = repo.newUnitOfWork();
    }

    
    public EntityRepository repo() {
        return repo;
    }

    
    public FulltextIndex index() {
        return new FullQueryProposalDecorator(
               new LowerCaseTokenFilter( index ) );
    }


    /**
     * Creates a new {@link UnitOfWork}. Close after use!
     */
    public UnitOfWork unitOfWork() {
        return repo.newUnitOfWork();
    }
    
    
    public void createTestData() {
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
                uow.createEntity( CatalogEntry.class, null, (CatalogEntry proto) -> {
                    CatalogEntry.defaults.initialize( proto );
                    proto.title.set( "Second entry" );
                    proto.description.set( "mapzone is cool!" );
                    proto.creator.set( "ich" );
                    proto.publisher.set( "mapzone.io" );
                    proto.subject.add( "Test" );
                    return proto;
                });
                uow.commit();
                log.info( "Test data created." );
            }
        }
    }
    
}
