package io.mapzone.controller.um.repository;

import java.util.EventObject;

import org.polymap.model2.Entity;

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

    @Override
    public Entity getSource() {
        return (Entity)super.getSource();
    }
    
    public <T extends Entity> T getEntity() {
        return (T)super.getSource();
    }
    
}
