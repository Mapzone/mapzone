package io.mapzone.controller.vm.runtime;

import java.util.List;

import org.polymap.core.runtime.config.Check;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.config.Defaults;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.config.NumberRangeValidator;

/**
 * Represents an OS process within an {@link HostRuntime}.
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class ProcessRuntime
        extends Configurable {
    
    @Immutable
    @Mandatory
    @Check(value=NumberRangeValidator.class, args={"1024","65535"})
    public Config<Integer>          port;
    
    @Immutable
    @Mandatory
    public Config<String>           logPath;

    @Immutable
    @Defaults
    public Config<List<String>>     args;

    @Immutable
    @Defaults
    public Config<List<String>>     vmargs;

    
    public abstract void start();
    
    public abstract void stop();
    
}
