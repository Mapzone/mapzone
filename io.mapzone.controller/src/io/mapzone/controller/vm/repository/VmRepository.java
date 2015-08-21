package io.mapzone.controller.vm.repository;

import static org.polymap.model2.query.Expressions.and;
import static org.polymap.model2.query.Expressions.eq;

import java.util.Optional;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.model2.Entity;
import org.polymap.model2.query.Query;
import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.ConcurrentEntityModificationException;
import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.ModelRuntimeException;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.ValueInitializer;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

/**
 * The registry of all currently active/running VMs. 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class VmRepository {

    private static Log log = LogFactory.getLog( VmRepository.class );
    
    private static EntityRepository     repo;
    
    static {
        try {
            LuceneRecordStore store = new LuceneRecordStore();
            repo = EntityRepository.newConfiguration()
                    .entities.set( new Class[] {
                            RegisteredHost.class,
                            RegisteredProcess.class })
                    .store.set( 
                            new RecordStoreAdapter( store ) )
                    .create();
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
    }
    
    
    // instance *******************************************
    
    private UnitOfWork                  uow;

    
    public Optional<RegisteredProcess> findProcess( String organisation, String project, String version ) {
        ResultSet<RegisteredProcess> rs = uow.query( RegisteredProcess.class )
                .where( and( 
                        eq( RegisteredProcess.TYPE.organisation, organisation ),
                        eq( RegisteredProcess.TYPE.project, project ) ) )
                .execute();
        assert rs.size() < 2;
        return rs.stream().findAny();
    }

    
    public <T extends Entity> T entity( Class<T> entityClass, Object id ) {
        return uow.entity( entityClass, id );
    }

    public <T extends Entity> T entity( T entity ) {
        return uow.entity( entity );
    }

    public <T extends Entity> T createEntity( Class<T> entityClass, Object id, ValueInitializer<T>... initializers ) {
        return uow.createEntity( entityClass, id, initializers );
    }

    public void prepare() throws IOException, ConcurrentEntityModificationException {
        uow.prepare();
    }

    public void commit() throws ModelRuntimeException {
        uow.commit();
    }

    public void rollback() throws ModelRuntimeException {
        uow.rollback();
    }

    public void close() {
        uow.close();
    }

    public <T extends Entity> Query<T> query( Class<T> entityClass ) {
        return uow.query( entityClass );
    }
    
    
}
