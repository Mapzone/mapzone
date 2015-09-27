package io.mapzone.controller.ops;

import java.util.Date;

import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredInstance;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;

/**
 * Starts a {@link RegisteredProcess} for a {@link RegisteredInstance}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class StartProcessOperation
        extends DefaultOperation {

    private static Log log = LogFactory.getLog( StartProcessOperation.class );

    @Mandatory
    public Config2<StartProcessOperation,RegisteredInstance>   instance;

    @Immutable
    public Config2<StartProcessOperation,RegisteredProcess>    process;
    
    
    public StartProcessOperation() {
        super( "Start instance" );
        ConfigurationFactory.inject( this );
    }


    @Override
    public IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        assert !process.isPresent();
        
        // find port
        RegisteredHost host = instance.get().host.get();
        int port = host.runtime.get().findFreePort();
        
        // start instance
        process.set( host.startInstance( instance.get(), (RegisteredProcess proto) -> {
            proto.instance.set( instance.get() );
            proto.port.set( port );
            proto.started.set( new Date() );
            return proto;
        }));
        assert process.get().instance.get() == instance.get();
        assert instance.get().process.get() == process.get();
        
        return Status.OK_STATUS;
    }

}
