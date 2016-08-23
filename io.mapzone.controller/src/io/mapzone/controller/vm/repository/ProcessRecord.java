package io.mapzone.controller.vm.repository;

import java.util.Date;

import org.polymap.model2.Association;
import org.polymap.model2.BidiAssociationConcern;
import org.polymap.model2.Concerns;
import org.polymap.model2.Defaults;
import org.polymap.model2.Immutable;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;

/**
 * Represents a started/running process of a {@link ProjectInstanceRecord}.
 *
 * @author Falko Bräutigam
 */
public class ProcessRecord
        extends VmEntity {

    public static ProcessRecord         TYPE;

    @Nullable  // XXX
//    @Immutable
    @Concerns( BidiAssociationConcern.class )
    public Association<ProjectInstanceRecord>  instance;
    
    @Nullable
    @Immutable
    @Concerns( NestedOneReaderPessimisticLocking.class )
    public Property<Integer>            pid;
    
    @Immutable
    @Concerns( NestedOneReaderPessimisticLocking.class )
    public Property<Integer>            port;
    
    @Immutable
    @Concerns( NestedOneReaderPessimisticLocking.class )
    public Property<Integer>            jmxPort;
    
    @Defaults
    @Concerns( NestedOneReaderPessimisticLocking.class )
    public Property<Date>               started;
    
}
