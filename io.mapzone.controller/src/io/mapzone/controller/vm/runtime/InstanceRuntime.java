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
    

//    @Immutable
//    @Mandatory
//    public Config2<ProcessRuntime,String>       executablePath;
//    
//    @Immutable
//    @Mandatory
//    public Config2<ProcessRuntime,String>       homePath;
//    
//    @Immutable
//    @Mandatory
//    @Check(value=NumberRangeValidator.class, args={"1024","65535"})
//    public Config2<ProcessRuntime,Integer>      port;
//    
//    @Immutable
//    @Mandatory
//    public Config2<ProcessRuntime,String>       logPath;
//
//    @Immutable
//    @Defaults
//    public Config2<ProcessRuntime,List<String>> args;
//
//    @Immutable
//    @Defaults
//    public Config2<ProcessRuntime,List<String>> vmargs;

    
    /**
     * Initializes properties of the target {@link #instance}. Prepares filesystem on the host.
     *
     * @param project
     * @param monitor 
     * @throws Exception
     */
    public abstract void prepareInstall( Project project, IProgressMonitor monitor ) throws Exception;
    
}
