package io.mapzone.controller.vm.provisions;

import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.http.OkToForwardRequest;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredInstance;
import io.mapzone.controller.vm.repository.RegisteredProcess;

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

    private Context<RegisteredInstance>     instance;

    private Context<RegisteredProcess>      process;

    private Status                          cause;

    
    @Override
    public boolean init( Provision failed, @SuppressWarnings("hiding") Status cause ) {
        this.cause = cause;
        return failed instanceof OkToForwardRequest
                /*&& cause.getCause().equals( OkToForwardRequest.NO_PROCESS )*/;
    }

    
    @Override
    public Status execute() throws Exception {
        // lock others while we change things
        vmRepo.get().lock();

//        // find host to use
//        List<RegisteredHost> hosts = vmRepo.get().allHosts();
//        if (hosts.isEmpty()) {
//            return new Status( FAILED_CHECK_AGAIN, NO_HOST );
//        }
//        if (hosts.size() > 1) {
//            throw new RuntimeException( "FIXME find the most suited host to run this project" );
//        }
//        host.set( hosts.get( 0 ) );

        // find instance on host
        instance.set( vmRepo.get().findInstance( 
                project.get().organizationOrUser().name.get(),
                project.get().name.get(),
                null )
                .orElseThrow( () -> new RuntimeException( "No project instance found for: " + project ) ) );
        
        // XXX check this again if process cleaning is in place
       // assert instance.get().process.get() == null;
        RegisteredProcess currentProcess = instance.get().process.get();
        if (currentProcess != null) {
            log.warn( "Registered process found. Deleting without checking OS process!" );
            instance.get().process.set( null );
            vmRepo.get().removeProcess( currentProcess );
        }
        
        host.set( instance.get().host.get() );
        assert host.get() != null;
        
        // start process
        int port = host.get().runtime.get().findFreePort();
        process.set( host.get().startInstance( instance.get(), (RegisteredProcess proto) -> {
            proto.instance.set( instance.get() );
            proto.port.set( port );
            return proto;
        }));
        assert process.get().instance.get() == instance.get();
        assert instance.get().process.get() == process.get();

        return OK_STATUS;
    }
    
}
