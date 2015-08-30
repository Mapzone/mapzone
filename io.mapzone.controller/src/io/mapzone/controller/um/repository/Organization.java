package io.mapzone.controller.um.repository;

import org.polymap.model2.Entity;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class Organization
        extends Entity {

    public static Organization      TYPE;

    @Queryable
    public Property<String>         name;
    
    @Queryable
    @Nullable
    public Property<String>         description;

    @Queryable
    @Nullable
    public Property<String>         website;

    public ManyAssociation<User>    members;
    
    public ManyAssociation<Project> projects;     
    
}
