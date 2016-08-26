package io.mapzone.controller.ops;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.DefaultBoolean;
import org.polymap.core.runtime.config.Mandatory;

import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.controller.vm.repository.ProcessRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;

/**
 * Stops a {@link ProcessRecord}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class StopProcessOperation
        extends VmOperation {

    private static final Log log = LogFactory.getLog( StopProcessOperation.class );

    /** Inbound: */
    @Mandatory
    public Config<ProcessRecord>    process;
    
    /** Inbound: */
    @Mandatory
    @DefaultBoolean( false )
    public Config<Boolean>          kill;
    
    private ProjectInstanceRecord   origInstance;
    
    
    public StopProcessOperation() {
        super( "Stop instance" );
    }


    @Override
    protected void doWithException( IProgressMonitor monitor, UnitOfWork uow ) throws Exception {
        origInstance = process.get().instance.get();

        ProcessRecord _process = uow.entity( process.get() );
        ProjectInstanceRecord _instance = _process.instance.get();
        
        // stop OS process
        _instance.executeLauncher( launcher -> launcher.stop( _instance, kill.get(), monitor ) );
        
        // clear associations
        _instance.homePath.get();  // force (pessimistic) lock of instance
        _process.instance.set( null );
        assert _instance.process.get() == null;
        
        // remove entity
        uow.removeEntity( _process );
    }


    @Override
    protected void onSuccess( IProgressMonitor monitor, UnitOfWork uow ) throws Exception {
        super.onSuccess( monitor, uow );

        // Entity is actually deleted?
        assert vmUow.get().entity( ProcessRecord.class, process.get().id() ) == null;
        // Association removed?
        assert origInstance.process.get() == null;
    }


    @Override
    protected void onError( IProgressMonitor monitor, Throwable e ) throws Exception {
        super.onError( monitor, e );
    }
    
}
