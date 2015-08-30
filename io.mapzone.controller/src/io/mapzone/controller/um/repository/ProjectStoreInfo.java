package io.mapzone.controller.um.repository;

import org.polymap.model2.Composite;
import org.polymap.model2.Property;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectStoreInfo
        extends Composite {

    public Property<String>             hostId;
    
    /**
     * The filesystem path where to find the workspace.
     */
    public Property<String>             dataPath;
    
    /**
     * The filesystem path where to find the eclipse executables.
     */
    public Property<String>             exePath;
    
}
