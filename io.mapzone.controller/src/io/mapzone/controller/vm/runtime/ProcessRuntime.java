package io.mapzone.controller.vm.runtime;

import io.mapzone.controller.vm.repository.RegisteredProcess;

import org.polymap.core.runtime.config.Configurable;

/**
 * The runtime interface of an OS process within a {@link HostRuntime}.
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class ProcessRuntime
        extends Configurable {
    
    protected RegisteredProcess     process;
    
    
    public ProcessRuntime( RegisteredProcess rprocess ) {
        this.process = rprocess;
    }
    

    public abstract void start( ) throws Exception;
    
    public abstract void stop() throws Exception;
    
    public abstract boolean isRunning();
    
}
