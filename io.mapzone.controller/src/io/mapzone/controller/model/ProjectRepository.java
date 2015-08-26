package io.mapzone.controller.model;

import static org.polymap.model2.query.Expressions.and;
import static org.polymap.model2.query.Expressions.eq;
import static org.polymap.model2.query.Expressions.the;
import java.util.Optional;

import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.store.OptimisticLocking;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectRepository {

    private static EntityRepository     repo;
    
    static {
        try {
            //File dir = new File( CorePlugin.getDataLocation( ControllerPlugin.instance() ), "projects" );
            LuceneRecordStore store = new LuceneRecordStore( /*dir, false*/ );
            repo = EntityRepository.newConfiguration()
                    .entities.set( new Class[] {
                            Project.class,
                            Organization.class })
                    .store.set( 
                            // make sure to never loose updates or something
                            new OptimisticLocking(
                            new RecordStoreAdapter( store ) ) )
                    .create();
            
            checkInit();
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
    }
    
    
    public static void checkInit() {
        try (
            UnitOfWork _uow = repo.newUnitOfWork()
        ){
            if (_uow.query( Organization.class ).execute().size() == 0) {
                // Project
                Project project = _uow.createEntity( Project.class, null, (Project proto) -> {
                    proto.name.set( "first" );
                    proto.description.set( "The first registered project at mapzone.io" );
                    proto.maxRamMb.set( 256 );
                    proto.storedAt.createValue( storedAt -> {
                        storedAt.hostId.set( "local" );
                        storedAt.exePath.set( "/home/falko/servers/polymap4/" );
                        storedAt.dataPath.set( "/home/falko/servers/workspace-alkis" );
                        return storedAt;
                    });
                    return proto;
                });
                // Organization
                Organization organization = _uow.createEntity( Organization.class, null, (Organization proto) -> {
                    proto.name.set( "First" );
                    proto.description.set( "The first organization hosted at mapzone.io :)" );
                    proto.website.set( "www.polymap.org" );
                    proto.projects.add( project );
                    return proto;
                });
                project.organization.set( organization );
                _uow.commit();
            }
        }
    }
    

    private static ProjectRepository    instance = new ProjectRepository( repo.newUnitOfWork() );
    
    public static ProjectRepository instance() {
        return instance;
    }

    
    // instance *******************************************
    
    private UnitOfWork              uow;
    
    
    public ProjectRepository( UnitOfWork uow ) {
        this.uow = uow;
    }

    
    public Optional<Project> findProject( String organization, String project ) {
        ResultSet<Project> rs = uow.query( Project.class )
                .where( and( 
                        the( Project.TYPE.organization, eq( Organization.TYPE.name, organization ) ),
                        eq( Project.TYPE.name, project ) ) )
                .execute();
        assert rs.size() <= 1;
        return rs.stream().findAny();
    }

    
    public void rollback() {
        uow.rollback();
    }

    
    public void commit() {
        uow.commit();
    }

}
