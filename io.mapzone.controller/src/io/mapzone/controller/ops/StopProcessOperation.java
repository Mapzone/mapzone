package io.mapzone.controller.ops;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;

/**
 * Stops a {@link ProcessRecord}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class StopProcessOperation
        extends VmOperation {

    private static Log log = LogFactory.getLog( StopProcessOperation.class );

    @Mandatory
    public Config<ProcessRecord>    process;
    
    
    public StopProcessOperation() {
        super( "Stop instance" );
    }


    @Override
    public IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        ProjectInstanceRecord instance = process.get().instance.get();
        
        // stop OS process
        instance.executeLauncher( launcher -> launcher.stop( instance, monitor ) );
        
        // clear associations
        instance.homePath.get();  // force (pessimistic) lock of instance
        process.get().instance.set( null );
        assert instance.process.get() == null;
        
        // remove entity
        vmUow.get().removeEntity( process.get() );
        return Status.OK_STATUS;
    }

}
