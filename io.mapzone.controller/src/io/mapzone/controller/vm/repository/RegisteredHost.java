package io.mapzone.controller.vm.repository;

import io.mapzone.controller.model.Project;
import io.mapzone.controller.vm.runtime.HostRuntime;
import io.mapzone.controller.vm.runtime.LocalHostRuntime;
import io.mapzone.controller.vm.runtime.ProcessRuntime;

import org.polymap.model2.Concerns;
import org.polymap.model2.Entity;
import org.polymap.model2.Property;
import org.polymap.model2.runtime.event.PessimisticLocking;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
@Concerns(PessimisticLocking.class)
public class RegisteredHost
        extends Entity {

    public static RegisteredHost        TYPE;
    
    public enum HostType {
        LOCAL, SSH
    }
    
    
    public Property<HostType>           hostType;
    
    
    public HostRuntime runtime() {
        if (hostType.get().equals( HostType.LOCAL )) {
            return new LocalHostRuntime();
        }
        else {
            throw new RuntimeException( "Unhandled host type: " + hostType.get() );
        }
    }


    public void startProject( Project project ) throws Exception {
        String home = "/servers/polymap4";
        ProcessRuntime processRuntime = runtime().startEclipseProcess( home );
    }
    
}
