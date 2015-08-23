package io.mapzone.controller.vm.provisions;

import static io.mapzone.controller.provision.Provision.Status.Severity.FAILED_CHECK_AGAIN;
import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.http.OkToForwardRequest;
import io.mapzone.controller.model.Project;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredProcess;
import io.mapzone.controller.vm.runtime.ProcessRuntime;

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
    
    private Context<RegisteredHost>         rhost;

    
    @Override
    public boolean init( Provision failed , Status cause  ) {
        return failed instanceof OkToForwardRequest;
    }

    
    @Override
    public Status execute() throws Exception {
        lock.get().lock();
        
        List<RegisteredHost> hosts = vmRepo.get().allHosts();
        if (hosts.isEmpty()) {
            return new Status( FAILED_CHECK_AGAIN, NO_HOST );
        }
        if (hosts.size() > 1) {
            throw new RuntimeException( "FIXME find the most suited host to run this project" );
        }
        
        // define host to use
        rhost.set( hosts.get( 0 ) );

        // start process
        String projectHome = project.get().storedAt.get().path.get();
        ProcessRuntime process = rhost.get().runtime().newEclipseProcess( projectHome );

        // update repo
        vmRepo.get().createEntity( RegisteredProcess.class, null, (RegisteredProcess proto) -> {
            proto.host.set( rhost.get() );
            proto.organisation.set( project.get().organization.get().name.get() );
            proto.project.set( project.get().name.get() );
            return proto;
        });
        
        return OK_STATUS;
    }
    
}
