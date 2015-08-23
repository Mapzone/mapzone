package io.mapzone.controller.vm.runtime;

import org.polymap.core.runtime.config.Configurable;

/**
 * Physical or virtual OS instance that host multiple VMs/processes
 * {@link ProcessRuntime}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class HostRuntime
        extends Configurable {

    public abstract ProcessRuntime newEclipseProcess( String projectHome );
    
}
