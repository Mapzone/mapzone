package io.mapzone.controller.um.repository;

import org.polymap.model2.BidiManyAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.Defaults;
import org.polymap.model2.ManyAssociation;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class ProjectHolder
        extends Named {

    @Defaults
    @Concerns( BidiManyAssociationConcern.class )
    public ManyAssociation<Project>     projects;
    
}
