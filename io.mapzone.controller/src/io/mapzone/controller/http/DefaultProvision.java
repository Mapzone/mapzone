package io.mapzone.controller.http;

import io.mapzone.controller.model.ProjectRepository;
import io.mapzone.controller.vm.repository.VmRepository;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides basic context variables.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class DefaultProvision
        implements Provision {

    private static Log log = LogFactory.getLog( DefaultProvision.class );

    protected Context<HttpServletRequest>   request;
    
    protected Context<HttpServletResponse>  response;
    
    protected Context<VmRepository>         vmRepo;
    
    protected Context<ProjectRepository>    projectRepo;
    
    /**
     * Lock write access to repos and runtime. A lock must be aquired *before
     * accessing* the entity to modify.
     */
    protected Context<Lock>                 lock;
    
}
