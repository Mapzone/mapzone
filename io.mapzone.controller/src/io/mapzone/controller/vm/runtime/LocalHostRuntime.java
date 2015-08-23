package io.mapzone.controller.vm.runtime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class LocalHostRuntime
        extends HostRuntime {

    private static Log log = LogFactory.getLog( LocalHostRuntime.class );

    @Override
    public ProcessRuntime newEclipseProcess( String projectHome ) {
        return new LocalProcessRuntime( this, projectHome );
    }

    
    /**
     * 
     */
    public static class LocalProcessRuntime
            extends ProcessRuntime {

        public LocalProcessRuntime( LocalHostRuntime hostRuntime, String projectHome ) {
            
        }

        @Override
        public void start() {
            // XXX Auto-generated method stub
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public void stop() {
            // XXX Auto-generated method stub
            throw new RuntimeException( "not yet implemented." );
        }
        
    }
    
}
