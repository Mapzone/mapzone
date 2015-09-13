package io.mapzone.controller.um.repository;

import org.polymap.model2.BidiManyAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.ManyAssociation;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class Organization
        extends ProjectHolder {

    public static Organization      TYPE;

    /** 
     * Bidirectional association to {@link User#organizations}.
     */
    @Concerns(BidiManyAssociationConcern.class)
    public ManyAssociation<User>    members;
    
}
