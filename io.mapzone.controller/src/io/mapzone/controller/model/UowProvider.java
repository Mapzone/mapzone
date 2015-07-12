package io.mapzone.controller.model;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.rhei.batik.tx.TxProvider;

import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

/**
 * Holds the global {@link EntityRepository} of users and projects.
 *
 * @see TxProvider
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UowProvider
        extends TxProvider<UnitOfWork> {

    private static Log log = LogFactory.getLog( UowProvider.class );
    
    private EntityRepository    repo;

    
    public UowProvider() throws IOException {
        //File workspace;
        LuceneRecordStore store = new LuceneRecordStore();
        repo = EntityRepository.newConfiguration()
                .entities.set( new Class[] {
                        Address.class,
                        User.class, 
                        Organization.class,
                        Project.class,
                        Tag.class })
                .store.set( new RecordStoreAdapter( store ) )
                .create();
        
        initRepo();
    }
    
    protected void initRepo() {
    }
    
    @Override
    protected UnitOfWork newTx( UnitOfWork parent ) {
        return parent != null ? parent.newUnitOfWork() : repo.newUnitOfWork();
    }

    @Override
    protected void commitTx( UnitOfWork uow ) {
        uow.commit();
    }

    @Override
    protected void rollbackTx( UnitOfWork uow ) {
        uow.rollback();
    }

    @Override
    protected void closeTx( UnitOfWork uow ) {
        uow.close();
    }
    
}
