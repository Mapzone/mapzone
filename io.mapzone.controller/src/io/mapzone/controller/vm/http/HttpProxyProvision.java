/* Copyright (C) 2018 Falko Bräutigam. All rights reserved. */
package io.mapzone.controller.vm.http;

import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.vm.repository.VmRepository;
import io.mapzone.controller.vm.repository.VmRepository.VmUnitOfWork;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

/**
 * Provides basic context variables.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class HttpProxyProvision
        implements Provision {

    protected Context<HttpServletRequest>   request;
    
    protected Context<HttpServletResponse>  response;

    protected Context<HttpRequest>          proxyRequest;
    
    protected Context<HttpResponse>         proxyResponse;

    Context<VmUnitOfWork>                   vmUow;
    
    Context<ProjectUnitOfWork>              projectUow;
    

    public VmUnitOfWork vmUow() {
        return vmUow.get( () -> VmRepository.newUnitOfWork() );
    }
    
    public ProjectUnitOfWork projectUow() {
        return projectUow.get( () -> ProjectRepository.newUnitOfWork() );
    }
    
}
