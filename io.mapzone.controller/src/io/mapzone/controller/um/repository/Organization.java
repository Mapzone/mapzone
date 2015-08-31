package io.mapzone.controller.um.repository;

import org.polymap.model2.ManyAssociation;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class Organization
        extends Named {

    public static Organization      TYPE;

    public ManyAssociation<User>    members;
    
    public ManyAssociation<Project> projects;     
    
}
