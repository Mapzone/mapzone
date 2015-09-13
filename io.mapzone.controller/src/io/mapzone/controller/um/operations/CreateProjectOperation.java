package io.mapzone.controller.um.operations;

import io.mapzone.controller.Messages;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectHolder;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.VmRepository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.i18n.IMessages;

/**
 * Creates a new {@link Project}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CreateProjectOperation
        extends AbstractOperation
        implements IUndoableOperation {

    private static Log log = LogFactory.getLog( CreateProjectOperation.class );

    public static final IMessages i18n = Messages.forPrefix( "CreateProjectOperation" );
    
    @Mandatory
    public Config<ProjectRepository>    repo;
    
    @Mandatory
    public Config<Project>              project;
    
    @Mandatory
    public Config<ProjectHolder>        organizationOrUser;
    
    
    public CreateProjectOperation() {
        super( i18n.get( "title" ) );
        ConfigurationFactory.inject( this );
    }


    @Override
    public IStatus execute( IProgressMonitor monitor, IAdaptable info ) throws ExecutionException {
        VmRepository vmRepo = VmRepository.instance();
        try {
            monitor.beginTask( getLabel(), 10 );
            vmRepo.lock();

            // find host to use
            List<RegisteredHost> hosts = vmRepo.allHosts();
            if (hosts.isEmpty()) {
                throw new RuntimeException( "No host!" );
            }
            if (hosts.size() > 1) {
                throw new RuntimeException( "FIXME find the most suited host to run this project" );
            }
            RegisteredHost host = hosts.get( 0 );
            monitor.worked( 1 );
            
            // prepare filesystem and copy runtime there
            host.createProjectInstance( project.get(), organizationOrUser.get(), new SubProgressMonitor( monitor, 9 ) );
            
            vmRepo.commit();
            repo.get().commit();
            return Status.OK_STATUS;
        }
        catch (Exception e) {
            vmRepo.rollback();
            throw new ExecutionException( i18n.get( "errorMsg", e.getLocalizedMessage() ), e );
        }
        finally {
            vmRepo.unlock();
        }
    }

    
    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public IStatus undo( IProgressMonitor monitor, IAdaptable info ) throws ExecutionException {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public IStatus redo( IProgressMonitor monitor, IAdaptable info ) throws ExecutionException {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

}
