package io.mapzone.controller.model;

import org.polymap.model2.Entity;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class Tag
        extends Entity {

    @Queryable
    public Property<String>         name;
    
    public Property<String>         description;
    
}
