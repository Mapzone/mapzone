package io.mapzone.controller.um.repository;

import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.model2.Association;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
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
    
    public static final String          DEFAULT_SERVLET_ALIAS = "/arena";
    
    public static final ValueInitializer<Project> defaults = (Project proto) -> { 
        return proto; 
    };
    
    /**
     * The {@link Organization} this project belongs to. Bidirectional association
     * with {@link Organization#projects}.
     */
    @Nullable
    public Association<Organization>    organization;
    
    /**
     * The servlet path to access this project. For URL "http://localhost:8080/p4"
     * this is "/p4". Starts with "/".
     */
    @DefaultValue( DEFAULT_SERVLET_ALIAS )
    public Property<String>             servletAlias;
    
    public Property<ProjectLauncher>    launcher;

    /**
     * @see AuthToken
     */
    @Nullable
    @Queryable
    public Property<String>             serviceAuthToken;
    
    
    public Optional<AuthToken> serviceAuthToken() {
        return serviceAuthToken.get() != null 
                ? Optional.of( AuthToken.parse( serviceAuthToken.get() ) )
                : Optional.empty();
    }
    
    
    public AuthToken newServiceAuthToken( IProgressMonitor monitor ) {
        AuthToken authToken = AuthToken.create();
        // XXX encrypt?
        serviceAuthToken.set( authToken.toString() );
        return authToken;
    }
    
}
