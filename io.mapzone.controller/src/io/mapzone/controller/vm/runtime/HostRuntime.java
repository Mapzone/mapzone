package io.mapzone.controller.vm.runtime;

import java.util.List;

import java.io.File;

import com.google.common.util.concurrent.ListenableFuture;

import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.HostRecord.HostType;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;

/**
 * The runtime interface of a physical or virtual OS instance that is able to host
 * multiple {@link ProcessRuntime VMs/processes}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class HostRuntime
        extends Configurable {
    
    public static HostRuntime forHost( HostRecord host ) {
        HostType hostType = host.hostType.get();
        if (hostType.equals( HostType.JCLOUDS )) {
            return new JCloudsHostRuntime( host );
        }
        else {
            throw new RuntimeException( "Unhandled host type: " + hostType );
        }
    }
    
    
    // instance *******************************************

    protected HostRecord                host;
    
    
    protected HostRuntime( HostRecord host ) {
        this.host = host;
    }

    
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
    

    public abstract List<HostFile> listFiles( File f );
    
    
    /**
     * Executes the given script on this host. Options are given via {@link Config}
     * properties on the script.
     */
    public abstract ScriptExecutionResult execute( Script script );
    

    /**
     * Executes the given script in the background typically with nohup. Options are
     * given via {@link Config} properties on the script.
     */
    public abstract ListenableFuture<ScriptExecutionResult> nohupExecute( Script script );

}
