package io.mapzone.controller.vm.runtime;

import org.eclipse.core.runtime.IProgressMonitor;

import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.RegisteredInstance;

import org.polymap.core.runtime.config.Configurable;

/**
 * The runtime interface of an OS process within a {@link HostRuntime}.
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class InstanceRuntime
        extends Configurable {
    
    protected RegisteredInstance    instance;
    
    
    public InstanceRuntime( RegisteredInstance instance ) {
        this.instance = instance;
    }
    

    /**
     * Prepares filesystem on the host.
     *
     * @param project
     * @param monitor 
     * @throws Exception
     */
    public abstract void install( Project project, IProgressMonitor monitor ) throws Exception;
    
    
    /**
     * Deletes filesystem on the host.
     */
    public abstract void uninstall( IProgressMonitor monitor ) throws Exception;
    
}
