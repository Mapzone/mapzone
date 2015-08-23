package io.mapzone.controller.model;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import java.util.Optional;

import java.io.File;

import org.polymap.core.CorePlugin;

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
            File dir = new File( CorePlugin.getDataLocation( ControllerPlugin.instance() ), "projects" );
            LuceneRecordStore store = new LuceneRecordStore( dir, false );
            repo = EntityRepository.newConfiguration()
                    .entities.set( new Class[] {
                            RegisteredHost.class,
                            RegisteredProcess.class })
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
                    proto.storedAt.createValue( storedAt -> {
                        storedAt.hostId.set( "local" );
                        storedAt.exePath.set( "/home/falko/servers/polymap4/bin/" );
                        storedAt.dataPath.set( "/home/falko/servers/workspace-arena" );
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
    
    
    // instance *******************************************
    
    public Optional<Project> findProject( String organizationName, String projectName ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

}
