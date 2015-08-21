package io.mapzone.controller.provisions;

import io.mapzone.controller.http.Context;
import io.mapzone.controller.http.OkToForwardRequest;
import io.mapzone.controller.http.Provision;
import io.mapzone.controller.model.Project;
import io.mapzone.controller.vm.repository.VmRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProcessRunning
        implements Provision {

    private static Log log = LogFactory.getLog( ProcessRunning.class );

    protected Context<VmRepository>         vmRepo;
    
    private Context<Project>                project;

    
    @Override
    public boolean init( Provision failed ) {
        return failed instanceof OkToForwardRequest;
    }

    
    @Override
    public Status execute() throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
