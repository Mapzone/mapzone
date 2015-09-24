package io.mapzone.controller.um.repository;

import static org.polymap.model2.query.Expressions.eq;
import static org.polymap.model2.query.Expressions.or;
import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.um.repository.LifecycleEvent.Type;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.CorePlugin;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.runtime.session.SessionContext;
import org.polymap.core.runtime.session.SessionSingleton;

import org.polymap.model2.Entity;
import org.polymap.model2.query.Query;
import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.ValueInitializer;
import org.polymap.model2.store.recordstore.RecordStoreAdapter;
import org.polymap.recordstore.lucene.LuceneRecordStore;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectRepository
        extends SessionSingleton {

    private static Log log = LogFactory.getLog( ProjectRepository.class );
    
    static {
        try {
            File dir = new File( CorePlugin.getDataLocation( ControllerPlugin.instance() ), "um" );
            LuceneRecordStore store = new LuceneRecordStore( dir, false );
            repo = EntityRepository.newConfiguration()
                    .entities.set( new Class[] {
                            User.class,
                            LoginCookie.class,
                            Project.class,
                            Organization.class })
                    .store.set( 
                            // make sure to never loose updates or something
                            //new OptimisticLocking(
                            new RecordStoreAdapter( store ) )
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
                assert project.organization.get() == organization : "Check bidi association.";
                _uow.commit();
            }
        }
    }
    
    private static EntityRepository     repo;
    

    /**
     * The instance of the current {@link SessionContext}. This is the <b>read</b>
     * cache for all entities used by the UI.
     * <p/>
     * Do <b>not</b> use this for <b>modifications</b> that might be canceled or
     * otherwise may left pending changes! Create a {@link #newNested()} instance for
     * that. This helps to prevent your modifications from being committed by another
     * party are leaving half-done, uncommitted changes. Commiting a nested instance
     * commits also the parent, hence making changes persistent, in one atomic
     * action. If that fails the <b>parent</b> is rolled back.
     */
    public static ProjectRepository instance() {
        return instance( ProjectRepository.class );
    }

    /**
     *
     */
    public static ProjectRepository newInstance() {
        return new ProjectRepository();
    }

    
    // instance *******************************************
    
    private UnitOfWork              uow;
    
    
    protected ProjectRepository() {
        this.uow = repo.newUnitOfWork();
    }
    
    
    protected ProjectRepository( UnitOfWork uow ) {
        this.uow = uow;
    }

    
    public Optional<Project> findProject( String organizationOrUser, String project ) {
        ProjectHolder projects = uow.query( User.class ).where( eq( User.TYPE.name, organizationOrUser ) ).execute().stream().findAny().get();
        if (projects == null) {
            projects = uow.query( Organization.class ).where( eq( Organization.TYPE.name, organizationOrUser ) ).execute().stream().findAny().get();
        }
        return projects.projects.stream().filter( p -> p.name.get().equals( project ) ).findAny();
        
//        ResultSet<Project> rs = uow.query( Project.class )
//                .where( and( 
//                        Expressions.or(
//                                the( Project.TYPE.user, eq( User.TYPE.name, organizationOrUser ) ),
//                                the( Project.TYPE.organization, eq( Organization.TYPE.name, organizationOrUser ) ) ),
//                        eq( Project.TYPE.name, project ) ) )
//                .execute();
//        assert rs.size() <= 1;
//        return rs.stream().findAny();
    }

    
    public Optional<User> findUser( String usernameOrEmail ) {
        ResultSet<User> rs = uow.query( User.class )
                .where( or( 
                        eq( User.TYPE.name, usernameOrEmail ),
                        eq( User.TYPE.email, usernameOrEmail ) ) )
                .execute();
        assert rs.size() <= 1;
        return rs.stream().findAny();
    }


    public Set<String> groupsOfUser( User user ) {
        return Collections.EMPTY_SET;
    }

    
    protected <T extends Entity> Query<T> query( Class<T> entityClass ) {
        return uow.query( entityClass );
    }


    public <T extends Entity> T createEntity( Class<T> entityClass, Object id, ValueInitializer<T>... initializers ) {
        return uow.createEntity( entityClass, id, initializers );
    }


    public void removeEntity( Entity entity ) {
        uow.removeEntity( entity );
    }

    
    public void rollback() {
        EventManager.instance().publish( new LifecycleEvent( this, Type.BEFORE_ROLLBACK ) );
        uow.rollback();
        EventManager.instance().publish( new LifecycleEvent( this, Type.AFTER_ROLLBACK ) );
    }

    
    public void commit() {
        EventManager.instance().publish( new LifecycleEvent( this, Type.BEFORE_COMMIT ) );
        uow.commit();
        EventManager.instance().publish( new LifecycleEvent( this, Type.AFTER_COMMIT ) );
    }

    
    public void close() {
        EventManager.instance().publish( new LifecycleEvent( this, Type.BEFORE_CLOSE ) );
        uow.close();
    }

    
    public ProjectRepository newNested() {
        ProjectRepository parent = this;
        return new ProjectRepository( uow.newUnitOfWork() ) {
            @Override
            public void commit() {
                synchronized (parent) {
                    try {
                        super.commit();
                        parent.commit();
                    }
                    catch (Exception e) {
                        log.info( "Commit nested ProjectRepository failed.", e );
                        parent.rollback();
                    }
                }
            }            
        };
    }

    
    public <T extends Entity> T entity( T entity ) {
        return uow.entity( entity );
    }

    
    /**
     * Registers the given handler for {@link LifecycleEvent}s. 
     * 
     * @see EventManager#subscribe(Object, org.polymap.core.runtime.event.EventFilter...)
     * @param annotated The weakly stored handler.
     * @return this 
     */
    public ProjectRepository addLifecycleListener( Object annotated ) {
        EventManager.instance().subscribe( annotated, ev -> ev.getSource() == this );
        return this;
    }
    
    
    public boolean removeLifecycleListener( Object annotated ) {
        return EventManager.instance().unsubscribe( annotated );    
    }
    
}
