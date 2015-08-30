package io.mapzone.controller.vm.runtime;

import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import org.polymap.core.runtime.config.Configurable;

/**
 * The runtime interface of an OS process within a {@link HostRuntime}.
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class ProcessRuntime
        extends Configurable {
    
    protected RegisteredProcess     rprocess;
    
    
    public ProcessRuntime( RegisteredProcess rprocess ) {
        this.rprocess = rprocess;
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

    
    public abstract void start( Project project ) throws Exception;
    
    public abstract void stop() throws Exception;
    
    public abstract boolean isRunning();
    
}
