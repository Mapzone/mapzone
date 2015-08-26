package io.mapzone.controller.http;

import io.mapzone.controller.model.ProjectRepository;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.vm.repository.VmRepository;

import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;

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

    protected Context<HttpRequest>          proxyRequest;
    
    protected Context<CloseableHttpResponse> proxyResponse;
    
    public Context<VmRepository>            vmRepo;
    
    public Context<ProjectRepository>       projectRepo;
    
    /**
     * Lock write access to repos and runtime. A lock must be aquired *before
     * accessing* the entity to modify.
     */
    protected Context<Lock>                 lock;
    
}
