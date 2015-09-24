package io.mapzone.controller.um.repository;

import java.util.EventObject;

import org.polymap.model2.Entity;
import org.polymap.model2.runtime.UnitOfWork;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class EntityChangedEvent
        extends EventObject {

    public EntityChangedEvent( Entity source ) {
        super( source );
    }

    
    /**
     * The entity that has been changed.
     * <p/>
     * <b>Beware</b> that the entity probably belongs to another {@link UnitOfWork}.
     * Use {@link ProjectRepository#entity(Entity)} in order to get an instance of
     * your repo.
     */
    @Override
    public Entity getSource() {
        return (Entity)super.getSource();
    }
    
    
    /**
     * The entity that has been changed.
     * <p/>
     * <b>Beware</b> that the entity probably belongs to another {@link UnitOfWork}.
     * Use {@link ProjectRepository#entity(Entity)} in order to get an instance of
     * your repo.
     */
    public <T extends Entity> T getEntity() {
        return (T)super.getSource();
    }
    
}
