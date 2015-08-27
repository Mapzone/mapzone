package io.mapzone.controller.vm.provisions;

import static io.mapzone.controller.provision.Provision.Status.Severity.FAILED_CHECK_AGAIN;
import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.http.OkToForwardRequest;
import io.mapzone.controller.model.Project;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredProcess;

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

    public static final String              NO_HOST = "_no_host_";

    private Context<Project>                project;
    
    private Context<RegisteredHost>         host;

    private Context<RegisteredProcess>      process;

    private Status                          cause;

    
    @Override
    public boolean init( Provision failed, @SuppressWarnings("hiding") Status cause ) {
        this.cause = cause;
        return failed instanceof OkToForwardRequest
                && cause.getCause().equals( OkToForwardRequest.NO_PROCESS );
    }

    
    @Override
    public Status execute() throws Exception {
        // lock others while we change things
        vmRepo.get().lock();

        // find host to use
        List<RegisteredHost> hosts = vmRepo.get().allHosts();
        if (hosts.isEmpty()) {
            return new Status( FAILED_CHECK_AGAIN, NO_HOST );
        }
        if (hosts.size() > 1) {
            throw new RuntimeException( "FIXME find the most suited host to run this project" );
        }
        host.set( hosts.get( 0 ) );

        // start process
        int port = host.get().runtime.get().findFreePort();
        process.set( host.get().startProcess( project.get(), (RegisteredProcess proto) -> {
            proto.organisation.set( project.get().organization.get().name.get() );
            proto.project.set( project.get().name.get() );
            proto.port.set( port );
            return proto;
        }));

        return OK_STATUS;
    }
    
}
