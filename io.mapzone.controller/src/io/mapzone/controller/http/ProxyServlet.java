/* 
 * Copyright (C) 2015-2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.controller.http;

import static io.mapzone.controller.provision.Provision.Status.Severity.OK;
import io.mapzone.controller.provision.Provision.Status;
import io.mapzone.controller.provision.ProvisionExecutor;
import io.mapzone.controller.provision.ProvisionExecutor2;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.provisions.MaxProcesses;
import io.mapzone.controller.vm.provisions.ProcessRunning;
import io.mapzone.controller.vm.provisions.ProcessStarted;
import java.util.regex.Pattern;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;

import org.polymap.core.runtime.Closer;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class ProxyServlet
        extends HttpServlet {

    private static Log log = LogFactory.getLog( ProxyServlet.class );
    
    /** The provisions to be handled before {@link ForwardRequest} to the instance. */
    private static final Class[]    forwardRequestProvisions = {ProcessStarted.class, ProcessRunning.class, MaxProcesses.class };

    /** The provisions to be handled before {@link ForwardResponse} back to the sender. */
    private static final Class[]    forwardResponseProvisions = {};

    private static final String     SERVLET_ALIAS = "/projects";

    /** A bit restrictive, just to make sure :) */
    private static final Pattern    NO_URL_CHAR = Pattern.compile( "[^a-zA-Z0-9_-]" );

    
    public static String projectUrl( Project project ) {
        try {
            // assuming that we are in /dashboard servlet
            return Joiner.on( "/" ).join( "..",
                    StringUtils.substringAfter( SERVLET_ALIAS, "/" ),
                    URLEncoder.encode( project.organizationOrUser().name.get(), "UTF8" ),
                    URLEncoder.encode( project.name.get(), "UTF8" ),
                    StringUtils.substringAfter( project.servletAlias.get(), "/" ) );
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }
    }

    
    // instance *******************************************
    
    @Override
    public void init() throws ServletException {
        log.info( "Started." );
    }


    @Override
    protected void service( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        try {
            // request
            // XXX job/thread to make it cancelable or timeout?
            ProvisionExecutor executor = new ProvisionExecutor2( forwardRequestProvisions );
            ForwardRequest forwardRequest = executor.newProvision( ForwardRequest.class );
            forwardRequest.request.set( req );
            forwardRequest.response.set( resp );
            try {
                Status status = executor.execute( forwardRequest );
                assert status.severity( OK ) : "No success forwarding request: ...";

                if (forwardRequest.vmRepo.isPresent()) {
                    forwardRequest.vmRepo.get().commit();
                }
            }
            catch (Throwable e) {
                // error while provisioning or upstream process
                Throwables.propagateIfPossible( e );
            }
            finally {
                forwardRequest.vmRepo.ifPresent( vmRepo -> vmRepo.close() );
            }

            // response
            CloseableHttpResponse proxyResponse = null;
            ProvisionExecutor executor2 = new ProvisionExecutor2( forwardResponseProvisions )
                    .setContextValues( executor.getContextValues() );
            ForwardResponse forwardResponse = executor2.newProvision( ForwardResponse.class );
            try {
                Status status2 = executor2.execute( forwardResponse );
                assert status2.severity( OK );
            }
            catch (Exception e) {
                // error while provisioning or sending response
                // XXX log, reset instance(?), send error page?
                throw new RuntimeException( e );
            }
            finally {
                Closer.create().close( proxyResponse );
            }
        }
        catch (Exception e) {
            // programming error
            Throwables.propagateIfPossible( e );
        }
    }


//    /**
//     * Handle {@link VmRepository} lock for every run a {@link Provision}.
//     */
//    class LockingProvisionExecutor
//            extends ProvisionExecutor2 {
//        
//        public LockingProvisionExecutor( Class<Provision>[] provisions ) {
//            super( provisions );
//        }
//
//        @Override
//        public Status execute( Provision target ) throws Exception {
//            return super.execute( target );
//        }
//
//        @Override
//        protected Status executeProvision( Provision provision ) throws Exception {
//            Timer timer = new Timer();
//            VmRepository vmRepo = ((HttpProxyProvision)provision).vmRepo();
//
//            // FIXME read lock should span execute() *and* init()
//            assert vmRepo.lock.getReadHoldCount() == 0;
//            vmRepo.lock.readLock().lock();
//            
//            try {
//                Status result = super.executeProvision( provision );
//
//                vmRepo.commit();
//                return result;
//            }
//            catch (Exception e) {
//                log.warn( "", e );
//                vmRepo.rollback();
//                throw e;
//            }
//            finally {
//                if (vmRepo.lock.getReadHoldCount() > 0) {
//                    vmRepo.lock.readLock().unlock();
//                }
//                log.info( "Provision: " + provision.getClass().getSimpleName() + " (" + timer.elapsedTime() + "ms)" );
//                
//                assert !vmRepo.lock.writeLock().isHeldByCurrentThread();
//                assert vmRepo.lock.getReadHoldCount() == 0 : "read hold count: " + vmRepo.lock.getReadHoldCount();
//            }
//        }
//
//    }
    
}
