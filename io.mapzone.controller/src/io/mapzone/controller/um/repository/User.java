package io.mapzone.controller.um.repository;

import org.polymap.model2.Entity;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.runtime.UnitOfWork;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class User
        extends Entity {

    public static User              TYPE;

    @Queryable
    public Property<String>         name;
    
    public Property<String>         passwordHash;
    
    @Queryable
    public Property<String>         email;
    
    @Queryable
    @Nullable
    public Property<String>         company;
    
    @Queryable
    @Nullable
    public Property<String>         location;
    
    public ManyAssociation<Project> projects;

    
    public UnitOfWork unitOfWork() {
        return context.getUnitOfWork();
    }
    
}
