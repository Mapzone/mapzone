package io.mapzone.controller.vm.runtime;

import java.io.File;

import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredHost.HostType;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import org.polymap.core.runtime.config.Configurable;

/**
 * The runtime interface of a physical or virtual OS instance that is able to host
 * multiple VMs/processes {@link ProcessRuntime}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class HostRuntime
        extends Configurable {
    
    
    public static HostRuntime forHost( RegisteredHost rhost ) {
        HostType hostType = rhost.hostType.get();
        if (hostType.equals( HostType.JCLOUDS )) {
            return new JCloudsHostRuntime( rhost );
        }
        else {
            throw new RuntimeException( "Unhandled host type: " + hostType );
        }
    }
    
    
    // instance *******************************************

    protected RegisteredHost            rhost;
    
    
    protected HostRuntime( RegisteredHost rhost ) {
        this.rhost = rhost;
    }


    /**
     * Creates a runtime for process that is to be started or is running already.
     *
     * @param result The {@link RegisteredProcess} to create or connect to.
     * @return Newly created process runtime instance.
     */
    public abstract ProcessRuntime process( RegisteredProcess result );

    
    /**
     * Find the next port that is free to bind on this host. 
     */
    public abstract int findFreePort();
    
    
    /**
     * Returns a handle for a file on the server. 
     *
     * @param f The file(name)
     */
    public abstract HostFile file( File f );
    
}
