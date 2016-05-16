package io.mapzone.controller.um.repository;

import org.polymap.model2.Association;
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
     * The {@link Organization} this project belongs to. Bidirectional association
     * with {@link Organization#projects}.
     */
    @Nullable
    public Association<Organization>    organization;
    
    /**
     * The servlet path to access this project. For URL "http://localhost:8080/p4"
     * this is "/p4". Starts with "/".
     */
    @DefaultValue( "/p4" )  // XXX
    public Property<String>             servletAlias;
    
    public Property<ProjectLauncher>    launcher;

}
