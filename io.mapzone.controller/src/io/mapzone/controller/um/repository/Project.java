package io.mapzone.controller.um.repository;

import org.polymap.model2.Association;
import org.polymap.model2.BidiAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.runtime.ValueInitializer;

import io.mapzone.controller.um.launcher.ProjectLauncher;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class Project
        extends Named {

    public static Project               TYPE;
    
    public static final ValueInitializer<Project> defaults = (Project proto) -> { 
        return proto; 
    };
    
    /**
     * @see #organizationOrUser()
     */
    @Nullable
    @Concerns( BidiAssociationConcern.class )
    public Association<Organization>    organization;
    
    /**
     * @see #organizationOrUser()
     */
    @Nullable
    @Concerns( BidiAssociationConcern.class )
    public Association<User>            user;
    
    /**
     * The servlet path to access this project. For URL "http://localhost:8080/p4"
     * this is "/p4". Starts with "/".
     */
    @DefaultValue( "/p4" )  // XXX
    public Property<String>             servletAlias;
    
    public Property<ProjectLauncher>    launcher;

    
    public Named organizationOrUser() {
        Organization _organization = organization.get();
        User _user = user.get();
        
        assert _organization == null && _user != null || _organization != null && _user == null;
        return _organization != null ? _organization : _user;
        
//        Project self = context.getCompositePart( Project.class );
//
//        // user
//        Optional<User> user = context.getUnitOfWork().query( User.class )
//                .where( Expressions.anyOf( User.TYPE.projects, Expressions.id( self ) ) )
//                .execute().stream().findAny();
//        if (user.isPresent()) {
//            return user.get();
//        }
//        // organization
//        Optional<Organization> org = context.getUnitOfWork().query( Organization.class )
//                .where( Expressions.anyOf( Organization.TYPE.projects, Expressions.id( self ) ) )
//                .execute().stream().findAny();
//        if (org.isPresent()) {
//            return org.get();
//        }
//        else {
//            throw new IllegalStateException( "Both organization and user are null!" );
//        }
    }


//    public Named organizationOrUser() {
//        if (organization.get() != null) {
//            assert user.get() == null;
//            return organization.get();
//        }
//        else if (user.get() != null) {
//            assert organization.get() == null;
//            return user.get();
//        }
//        else {
//            throw new IllegalStateException( "Both organization and user are null!" );
//        }
//    }
//    
//    
//    /**
//     * XXX Checks if both are mutual exclusive.
//     */
//    public static class UserOrOrganizationConcern
//            extends PropertyConcernBase
//            implements AssociationConcern {
//
//        @Override
//        public Entity get() {
//            return ((Association)delegate).get();
//        }
//
//        @Override
//        public void set( Entity value ) {
//            ((Association)delegate).set( value );
//        }
//    }
    
}
