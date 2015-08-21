package io.mapzone.controller.http;

import io.mapzone.controller.model.Project;
import io.mapzone.controller.model.ProjectRepository;
import io.mapzone.controller.vm.repository.RegisteredProcess;
import io.mapzone.controller.vm.repository.VmRepository;
import io.mapzone.controller.vm.runtime.ProcessRuntime;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class OkToForwardRequest
        implements Provision {

    private static Log log = LogFactory.getLog( OkToForwardRequest.class );

    protected Context<HttpServletRequest>   request;
    
    protected Context<HttpServletResponse>  response;
    
    protected Context<VmRepository>         vmRepo;
    
    protected Context<ProjectRepository>    projectRepo;
    
    private Context<Project>                project;
    
    private Context<ProcessRuntime>         processRuntime;
    
    
    @Override
    public Status execute() throws Exception {
        String[] path = StringUtils.split( request.get().getPathInfo(), "/" );
        String projectName = path[2];
        String organizationName = path[1];
        
        Optional<RegisteredProcess> process = vmRepo.get().findProcess( organizationName, projectName, null );
        if (process.isPresent()) {
            processRuntime.set( process.get().runtime() );
            return Status.OK;
        }
        else {
            project.set( projectRepo.__()
                    .findProject( organizationName, projectName )
                    .orElseThrow( () -> new RuntimeException( "No such project: " + organizationName + "/" + projectName ) ) ); 
            return Status.FAILED_CHECK_AGAIN;
        }
    }


    @Override
    public boolean init( Provision failed ) {
        throw new RuntimeException( "must not be called" );
    }
 
}
