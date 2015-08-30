package io.mapzone.controller.um.repository;

import org.polymap.model2.Association;
import org.polymap.model2.Entity;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class Project
        extends Entity {

    public static Project               TYPE;

    @Queryable
    public Property<String>             name;

    @Queryable
    @Nullable
    public Property<String>             description;

    @Queryable
    @Nullable
    public Property<String>             website;
    
    public Association<Organization>    organization;
    
    public Property<ProjectStoreInfo>   storedAt;

    public Property<Integer>            maxRamMb;
    
}
