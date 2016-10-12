package io.mapzone.controller.um.repository;

import static org.polymap.model2.query.Expressions.eq;
import io.mapzone.controller.um.launcher.ArenaLauncher;
import io.mapzone.controller.um.launcher.EclipseProjectLauncher;
import io.mapzone.controller.um.launcher.JvmProjectLauncher;
import io.mapzone.controller.um.repository.LifecycleEvent.Type;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.session.SessionContext;
import org.polymap.core.runtime.session.SessionSingleton;

import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.DelegatingUnitOfWork;
import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.locking.CommitLockStrategy;
import org.polymap.model2.runtime.locking.OptimisticLocking;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectRepository {

    private static Log log = LogFactory.getLog( ProjectRepository.class );
    
    private static EntityRepository     repo;
    

    public static void init( File basedir ) throws IOException {
        File dir  = new File( basedir, "um" );
        LuceneRecordStore store = new LuceneRecordStore( dir, false );
        repo = EntityRepository.newConfiguration()
                .entities.set( new Class[] {
                        User.class,
                        LoginCookie.class,
                        Project.class, 
                        JvmProjectLauncher.class,
                        EclipseProjectLauncher.class,
                        ArenaLauncher.class,
                        Organization.class, 
                        UserRole.class })
                .store.set( 
                        // make sure to never loose updates or something
                        new OptimisticLocking(
                        new RecordStoreAdapter( store ) ) )
                .commitLockStrategy.set( () ->
                        new CommitLockStrategy.Serialize() )
                .create();
            
        checkInit();
    }
    
    
    protected static void checkInit() {
        try (
            UnitOfWork _uow = repo.newUnitOfWork()
        ){
            if (_uow.query( Organization.class ).execute().size() == 0) {
                // Organization
                Organization organization = _uow.createEntity( Organization.class, null, (Organization proto) -> {
                    proto.name.set( "First" );
                    proto.description.set( "The first organization hosted at mapzone.io :)" );
                    proto.website.set( "www.mapzone.io" );
                    return proto;
                });
                // Project
                Project project = _uow.createEntity( Project.class, null, (Project proto) -> {
                    Project.defaults.initialize( proto );
                    proto.organization.set( organization );
                    proto.name.set( "first" );
                    proto.description.set( "The first registered project at mapzone.io" );
                    proto.launcher.createValue( ArenaLauncher.defaults );
                    return proto;
                });
                assert project.organization.get() == organization : "Check bidi association.";
                _uow.commit();
            }
        }
    }
    
    
    /**
     * The {@link UnitOfWork} of the current {@link SessionContext}.
     * <p/>
     * Consider creating a {@link ProjectUnitOfWork#newUnitOfWork() nested
     * UnitOfWork}. This helps to prevent your modifications from being
     * committed by another party are leaving half-done, uncommitted changes.
     */
    public static ProjectUnitOfWork session() {
        return SessionHolder.instance( SessionHolder.class ).uow;
    }

    static class SessionHolder
            extends SessionSingleton {
        private ProjectUnitOfWork       uow = new ProjectUnitOfWork( repo.newUnitOfWork() );
    }
    
    /**
     * A newly created {@link UnitOfWork}, independent from any session.
     */
    public static ProjectUnitOfWork newUnitOfWork() {
        return new ProjectUnitOfWork( repo.newUnitOfWork() );
    }

    
    /**
     * The {@link UnitOfWork} of a {@link ProjectRepository}.
     */
    public static class ProjectUnitOfWork
            extends DelegatingUnitOfWork {

        public ProjectUnitOfWork( UnitOfWork delegate ) {
            super( delegate );
        }
        
        
        @Override
        public UnitOfWork delegate() {
            return super.delegate();
        }


        public Optional<Project> findProject( String organization, String project ) {
            Organization org = delegate().query( Organization.class )
                    .where( eq( Organization.TYPE.name, organization ) )
                    .execute().stream().findAny().get();

            return org.projects.stream()
                    .filter( p -> p.name.get().equals( project ) )
                    .findAny();
        }


        public Optional<User> findUser( String username ) {
            assert !StringUtils.isBlank( username );
            ResultSet<User> rs = delegate().query( User.class )
                    .where( eq( User.TYPE.name, username ) )
                    .execute();
            assert rs.size() <= 1 : "Username found: " + rs.size() + " times: " + username;
            return rs.stream().findAny();
        }

        
        public Optional<User> findUserForEmail( String email ) {
            assert !StringUtils.isBlank( email );
            ResultSet<User> rs = delegate().query( User.class )
                    .where( eq( User.TYPE.email, email ) )
                    .execute();
            assert rs.size() <= 1 : "EMail found: " + rs.size() + " times: " + email;
            return rs.stream().findAny();
        }


        public Set<String> groupsOfUser( User user ) {
            return Collections.EMPTY_SET;
        }

        
        public void rollback() {
            EventManager.instance().publish( new LifecycleEvent( this, Type.BEFORE_ROLLBACK ) );
            super.rollback();
            EventManager.instance().publish( new LifecycleEvent( this, Type.AFTER_ROLLBACK ) );
        }


        public void commit() {
            EventManager.instance().publish( new LifecycleEvent( this, Type.BEFORE_COMMIT ) );
            super.commit();
            EventManager.instance().publish( new LifecycleEvent( this, Type.AFTER_COMMIT ) );
        }


        public void close() {
            EventManager.instance().publish( new LifecycleEvent( this, Type.BEFORE_CLOSE ) );
            super.close();
        }
        
        
        /**
         * Registers the given handler for {@link LifecycleEvent}s. 
         * 
         * @see EventManager#subscribe(Object, org.polymap.core.runtime.event.EventFilter...)
         * @param annotated The weakly stored handler.
         * @return this 
         */
        public ProjectUnitOfWork addLifecycleListener( Object annotated ) {
            EventManager.instance().subscribe( annotated, ev -> ev.getSource() == this );
            return this;
        }


        public boolean removeLifecycleListener( Object annotated ) {
            return EventManager.instance().unsubscribe( annotated );    
        }

    }
    
}
