package io.mapzone.controller.ops;

import io.mapzone.controller.vm.repository.RegisteredInstance;
import io.mapzone.controller.vm.repository.RegisteredProcess;
import io.mapzone.controller.vm.repository.VmRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Mandatory;

/**
 * Stops a {@link RegisteredProcess}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class StopProcessOperation
        extends DefaultOperation {

    private static Log log = LogFactory.getLog( StopProcessOperation.class );

    @Mandatory
    public Config2<StopProcessOperation,VmRepository>       vmRepo;

    @Mandatory
    public Config2<StopProcessOperation,RegisteredProcess>  process;
    
    
    public StopProcessOperation() {
        super( "Stop instance" );
        ConfigurationFactory.inject( this );
    }


    @Override
    public IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        // stop OS process
        process.get().runtime.get().stop();
        
        // clear associations
        RegisteredInstance instance = process.get().instance.get();
        process.get().instance.set( null );
        assert instance.process.get() == null;
        
        // remove entity
        vmRepo.get().removeEntity( process.get() );
        return Status.OK_STATUS;
    }

}
