package io.mapzone.controller.um.repository;

import org.polymap.model2.Defaults;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class User
        extends Named {

    public static User              TYPE;

    public Property<String>         passwordHash;
    
    @Queryable
    @Nullable
    public Property<String>         fullname;
    
    @Queryable
    @Nullable
    public Property<String>         company;
    
    @Defaults
    public ManyAssociation<Organization> organizations;

    @Defaults
    public ManyAssociation<Project> projects;
    
}
