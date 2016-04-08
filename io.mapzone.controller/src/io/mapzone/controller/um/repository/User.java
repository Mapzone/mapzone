package io.mapzone.controller.um.repository;

import java.util.Date;

import org.polymap.model2.BidiManyAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.Defaults;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.runtime.ValueInitializer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class User
        extends ProjectHolder {

    public static User              TYPE;

    public static ValueInitializer<User> defaults = (User proto) -> {
        // FIXME
        proto.name.set( "" );
        proto.email.set( "" );
        proto.passwordHash.set( "" );
        proto.joined.set( new Date() );
        return proto; };
    
        
    public Property<String>         passwordHash;
    
    @Queryable
    @Nullable
    @DefaultValue( "[No fullname yet]" )
    public Property<String>         fullname;
    
    @Queryable
    @DefaultValue( "" )
    public Property<String>         company;
    
    @Queryable
    public Property<Date>           joined;
    
    /** 
     * Bidirectional association to {@link Organization#members}.
     */
    @Defaults
    @Concerns( BidiManyAssociationConcern.class )
    public ManyAssociation<Organization> organizations;

}
