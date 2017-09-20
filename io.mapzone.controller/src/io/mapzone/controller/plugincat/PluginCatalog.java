/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
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
package io.mapzone.controller.plugincat;

import static java.util.Collections.singletonList;
import static org.polymap.core.runtime.event.TypeEventFilter.ifType;

import java.util.List;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.CorePlugin;
import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.event.Event.Scope;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.session.SessionContext;
import org.polymap.core.runtime.session.SessionSingleton;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.fulltext.FulltextIndex;
import org.polymap.rhei.fulltext.indexing.FeatureTransformer;
import org.polymap.rhei.fulltext.indexing.LowerCaseTokenFilter;
import org.polymap.rhei.fulltext.model2.FulltextIndexer;
import org.polymap.rhei.fulltext.store.lucene.LuceneFulltextIndex;

import org.polymap.model2.Entity;
import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.EntityRuntimeContext.EntityStatus;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.locking.CommitLockStrategy;
import org.polymap.model2.runtime.locking.OptimisticLocking;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.webcat.CatalogBase;
import io.mapzone.controller.webcat.model.CatalogEntry;
import io.mapzone.controller.webcat.model.CatalogEntryFulltextTransformer;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class PluginCatalog
        extends CatalogBase
        implements AutoCloseable {

    private static final Log log = LogFactory.getLog( PluginCatalog.class );
    
    // XXX evictable?
    private static Lazy<PluginCatalog> instance = new LockedLazyInit();
    
    /**
     * The global instance of the {@link PluginCatalog}.
     */
    public static PluginCatalog instance() {
        return instance.get( () -> {
            try {
                return new PluginCatalog();
            } catch (Exception e) {
                throw new RuntimeException( e );
            }
        });
    }
    
    /**
     * 
     */
    protected static class SessionHolder
            extends SessionSingleton {

        UnitOfWork          uow = PluginCatalog.instance().newUnitOfWork();
        
        protected SessionHolder() {
            EventManager.instance().subscribe( this, ifType( EntityChangedEvent.class,
                    ev -> ev.getEntity() instanceof PluginCatalogEntry ) );
        }

        /**
         * Reload entities when modified via WebDAV API (from another session).
         */
        @EventHandler( scope=Scope.JVM )
        protected void onEntityCommit( EntityChangedEvent ev ) {
            PluginCatalogEntry entity = ev.getEntity();
            
            if (!entity.belongsTo( uow )) {
                log.info( "RELOAD: " + ev.getEntity().id() );
                if (ev.beforeCommitStatus == EntityStatus.CREATED) {
                    // entity has not been seen by the local uow
                }
                else if (ev.beforeCommitStatus == EntityStatus.REMOVED
                        || ev.beforeCommitStatus == EntityStatus.MODIFIED) {
                    Entity local = uow.entity( ev.getEntity() );
                    
                    if (local.status() == EntityStatus.MODIFIED 
                            || local.status() == EntityStatus.REMOVED) {
                        UIThreadExecutor.async( () -> {
                            StatusDispatcher.handleError( "Plugin data have been modified by second party. Overwrite?", null );
                        });
                    }
                    
                    if (ev.beforeCommitStatus == EntityStatus.REMOVED) {
                       // XXX uow.removeEntity( local );
                    }
                    else {
                        uow.reload( local );
                    }
                }
                else {
                    throw new RuntimeException( "Unhandled entity status: " + ev.beforeCommitStatus );
                }
            }
        }
        
        @Override
        protected void finalize() throws Throwable {
            EventManager.instance().unsubscribe( this );
            if (uow != null) {
                log.info( "Closing session UnitOfWork..." );
                uow.close();
            }
        }
    }

    /**
     * The {@link UnitOfWork} of the current {@link SessionContext}.
     */
    public static UnitOfWork session() {
        return SessionHolder.instance( SessionHolder.class ).uow;
    }

    // instance *******************************************
    
    private LuceneFulltextIndex     index;
    
    private EntityRepository        repo;

    private File                    fileRepo;


    protected PluginCatalog() throws IOException {
        log.info( "Initializing repository ..." );
        File dataDir = new File( CorePlugin.getDataLocation( ControllerPlugin.instance().getBundle() ), "plugins" );
        dataDir.mkdir();
        
        // fileRepo
        fileRepo = new File( dataDir, "files" );
        fileRepo.mkdir();
        
        // fulltext
        index = new LuceneFulltextIndex( new File( dataDir, "fulltext" ) );
        index.addTokenFilter( new LowerCaseTokenFilter() );
        List<FeatureTransformer> transformers = singletonList( new CatalogEntryFulltextTransformer() ); 
    
        // store
        File dir  = new File( dataDir, "store" );
        LuceneRecordStore store = LuceneRecordStore.newConfiguration()
                .indexDir.put( dir )
                .clean.put( false )
                .executor.put( null )
                .create();

        repo = EntityRepository.newConfiguration()
                .entities.set( new Class[] {
                        PluginCatalogEntry.class } )
                .store.set( 
                        new OptimisticLocking(
                        new FulltextIndexer( index, (entity) -> true, transformers,
                        new RecordStoreAdapter( store ) ) ) )
                .commitLockStrategy.set( () -> 
                        new CommitLockStrategy.Serialize() )
                .create();
    }
    
    @Override
    public void close() throws Exception {
        index.close();
        repo.close();
    }
    
    @Override
    protected void finalize() throws Throwable {
        close();
    }

    @Override
    public EntityRepository repo() {
        return repo;
    }

    @Override
    public FulltextIndex index() {
        return index;
    }

    @Override
    public File fileRepo() {
        return fileRepo;
    }

    @Override
    public Class<? extends CatalogEntry> entryClass() {
        return PluginCatalogEntry.class;
    }

    File file( String category, String filename ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

}
