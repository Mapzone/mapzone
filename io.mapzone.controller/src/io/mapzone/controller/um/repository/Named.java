package io.mapzone.controller.um.repository;

import org.polymap.model2.Defaults;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * Provides common metadata properties.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class Named
        extends ProjectEntity {

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
