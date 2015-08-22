package io.mapzone.controller.http;

import io.mapzone.controller.http.Provision.Status.Severity;
import io.mapzone.controller.model.Project;
import io.mapzone.controller.vm.repository.RegisteredProcess;
import io.mapzone.controller.vm.runtime.ProcessRuntime;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class OkToForwardRequest
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( OkToForwardRequest.class );

    private static final String             NO_PROCESS = "_no_process_";

    private Context<Project>                project;
    
    private Context<ProcessRuntime>         processRuntime;
    
    
    @Override
    public boolean init( Provision failed ) {
        return failed == null;
    }


    @Override
    public Status execute() throws Exception {
        String[] path = StringUtils.split( request.get().getPathInfo(), "/" );
        String projectName = path[2];
        String organizationName = path[1];
        
        Optional<RegisteredProcess> process = vmRepo.get().findProcess( organizationName, projectName, null );
        if (process.isPresent()) {
            processRuntime.set( process.get().runtime() );
            return OK_STATUS;
        }
        else {
            project.set( projectRepo.__()
                    .findProject( organizationName, projectName )
                    .orElseThrow( () -> new RuntimeException( "No such project: " + organizationName + "/" + projectName ) ) ); 
            return new Status( Severity.FAILED_CHECK_AGAIN, NO_PROCESS );
        }
    }
 
}
