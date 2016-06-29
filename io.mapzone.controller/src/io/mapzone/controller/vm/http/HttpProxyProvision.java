package io.mapzone.controller.vm.http;

import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.vm.repository.VmRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

/**
 * Provides basic context variables.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class HttpProxyProvision
        implements Provision {

    private static Log log = LogFactory.getLog( HttpProxyProvision.class );

    protected Context<HttpServletRequest>   request;
    
    protected Context<HttpServletResponse>  response;

    protected Context<HttpRequest>          proxyRequest;
    
    protected Context<HttpResponse>         proxyResponse;

    Context<VmRepository>                   vmRepo;
    
    Context<ProjectRepository>              projectRepo;
    

    public VmRepository vmRepo() {
        return vmRepo.get( () -> VmRepository.newInstance() );
    }
    
    public ProjectRepository projectRepo() {
        return projectRepo.get( () -> ProjectRepository.newInstance() );
    }
    
}
