package io.mapzone.controller.um.repository;

import org.polymap.model2.Association;
import org.polymap.model2.AssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.Entity;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.PropertyConcernBase;
import org.polymap.model2.runtime.config.DefaultInt;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class Project
        extends Named {

    public static Project               TYPE;

    @Nullable
    @Concerns( UserOrOrganizationConcern.class )
    public Association<Organization>    organization;
    
    @Nullable
    @Concerns( UserOrOrganizationConcern.class )
    public Association<User>            user;
    
    public Property<ProjectStoreInfo>   storedAt;

    @DefaultInt(256)
    public Property<Integer>            maxRamMb;
    
    
    /**
     * XXX Checks if both are mutual exclusive.
     */
    public static class UserOrOrganizationConcern
            extends PropertyConcernBase
            implements AssociationConcern {

        @Override
        public Entity get() {
            return ((Association)delegate).get();
        }

        @Override
        public void set( Entity value ) {
            ((Association)delegate).set( value );
        }
    }
    
}
