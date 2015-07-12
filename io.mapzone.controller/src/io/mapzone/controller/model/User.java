package io.mapzone.controller.model;

import org.polymap.model2.Entity;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class User
        extends Entity {

    @Queryable
    public Property<String>         name;
    
    public Property<String>         passwordHash;
    
    @Queryable
    public Property<String>         email;
    
    @Queryable
    public Property<String>         company;
    
    @Queryable
    public Property<String>         location;
    
    public ManyAssociation<Project> projects;     
    
}
