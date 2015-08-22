package io.mapzone.controller.vm.runtime;

/**
 * Physical or virtual OS instance that host multiple VMs/processes
 * {@link ProcessRuntime}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public interface HostRuntime {

    ProcessRuntime startEclipseProcess( String home );
    
}
