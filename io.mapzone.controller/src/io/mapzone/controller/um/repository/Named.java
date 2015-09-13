package io.mapzone.controller.um.repository;

import org.polymap.model2.Defaults;
import org.polymap.model2.Entity;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class Named
        extends Entity {

    @Defaults
    @Queryable
    public Property<String>             name;

    @Defaults
    @Queryable
    public Property<String>             description;

    @Defaults
    @Queryable
    public Property<String>             website;
    
    @Defaults
    @Queryable
    public Property<String>             email;
    
    @Defaults
    @Queryable
    public Property<String>             location;
    
}
