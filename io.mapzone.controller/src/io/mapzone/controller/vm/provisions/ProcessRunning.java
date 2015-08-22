package io.mapzone.controller.vm.provisions;

import static io.mapzone.controller.http.Provision.Status.Severity.FAILED_CHECK_AGAIN;
import io.mapzone.controller.http.Context;
import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.http.OkToForwardRequest;
import io.mapzone.controller.http.Provision;
import io.mapzone.controller.model.Project;
import io.mapzone.controller.vm.repository.RegisteredHost;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProcessRunning
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( ProcessRunning.class );

    private static final String             NO_HOST = "_no_host_";

    private Context<Project>                project;
    
    private Context<RegisteredHost>         host;

    
    @Override
    public boolean init( Provision failed ) {
        return failed instanceof OkToForwardRequest;
    }

    
    @Override
    public Status execute() throws Exception {
        lock.__().lock();
        
        List<RegisteredHost> hosts = vmRepo.__().allHosts();
        if (hosts.isEmpty()) {
            return new Status( FAILED_CHECK_AGAIN, NO_HOST );
        }
        if (hosts.size() > 1) {
            throw new RuntimeException( "FIXME find the most suited host to run this project" );
        }
        
        host.set( hosts.get( 0 ) );
        host.__().startProject( project.__() );
    }
    
}
