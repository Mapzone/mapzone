/* Copyright (C) 2018 Falko Bräutigam. All rights reserved. */
package io.mapzone.controller.vm.http;

import static com.google.common.base.Throwables.propagate;
import static com.google.common.base.Throwables.propagateIfPossible;
import static io.mapzone.controller.provision.Provision.Status.Severity.OK;
import static org.apache.commons.lang3.StringUtils.substringAfter;

import java.util.regex.Pattern;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import com.google.common.base.Joiner;

import org.polymap.core.runtime.Timer;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.provision.Provision.Status;
import io.mapzone.controller.provision.ProvisionExecutor;
import io.mapzone.controller.provision.ProvisionExecutor2;
import io.mapzone.controller.provision.ProvisionRuntimeException;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.provisions.MaxStartedProcesses;
import io.mapzone.controller.vm.provisions.ProjectURICache;
import io.mapzone.controller.vm.provisions.ReStartProcess;

/**
 * The proxy servlet takes request targeting a project instance and forwards it via
 * provision framework.
 *
 * @author Falko Bräutigam
 */
public class ProxyServlet
        extends HttpServlet {

    private static final Log log = LogFactory.getLog( ProxyServlet.class );
    
    /** The provisions to be handled before {@link ForwardRequest} to the instance. */
    private static final Class[]    FORWARD_REQUEST_PROVISIONS = {
            LoginProvision.class,
            ServiceAuthProvision.class,
            ProjectURICache.class, 
            MaxStartedProcesses.class,
            ReStartProcess.class };

    /** The provisions to be handled before {@link ForwardResponse} back to the sender. */
    private static final Class[]    FORWARD_RESPONSE_PROVISIONS = {};

    public static final String      SERVLET_ALIAS = "/projects";

    /** A bit restrictive, just to make sure :) */
    private static final Pattern    NO_URL_CHAR = Pattern.compile( "[^a-zA-Z0-9_-]" );

    
    /**
     * 
     */
    public static String relativeClientUrl( Project project ) {
        try {
            // assuming that we are in /dashboard servlet
            return Joiner.on( "/" ).join( "..",
                    substringAfter( SERVLET_ALIAS, "/" ),
                    URLEncoder.encode( project.organization.get().name.get(), "UTF8" ),
                    URLEncoder.encode( project.name.get(), "UTF8" ),
                    substringAfter( project.servletAlias.get(), "/" ) );
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }
    }

    
    /**
     * 
     */
    public static String projectUrlBase( Project project ) {
        try {
            String localHttpPort = ControllerPlugin.instance().httpPort();
            String baseUrl = "mapzone".equals( System.getProperty( "user.name" ) )
                    ? "http://mapzone.io" 
                    : "http://localhost:" + localHttpPort;
            
            return Joiner.on( "" ).join(
                    baseUrl, ProxyServlet.SERVLET_ALIAS, "/",
                    URLEncoder.encode( project.organization.get().name.get(), "UTF8" ), "/",
                    URLEncoder.encode( project.name.get(), "UTF8" ) );
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }
    }

    
    // instance *******************************************
    
    @Override
    public void init() throws ServletException {
        log.info( "Initialized." );
    }


    @Override
    protected void service( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        try {
            Timer timer = new Timer();
            
            // request
            // XXX job/thread to make it cancelable or timeout?
            ProvisionExecutor executor = new ProvisionExecutor2( FORWARD_REQUEST_PROVISIONS );
            ForwardRequest forwardRequest = executor.newProvision( ForwardRequest.class );
            forwardRequest.request.set( req );
            forwardRequest.response.set( resp );

            // commit on sent or discard
            forwardRequest.onRequestSend.set( (HttpRequest request) -> {
                // see InterceptableHttpClientConnectionFactory
                forwardRequest.vmUow.ifPresent( uow -> uow.commit() );
                log.debug( "Provisioning: " + timer.elapsedTime() + "ms (" + Thread.currentThread().getName() + ")" );
            });
            try {
                Status status = executor.execute( forwardRequest );
                if (!status.severity( OK )) {
                    // no successful provisioning found 
                    throw new HttpProvisionRuntimeException( 503, "Sorry. Project is not available currently." );
                }
            }
            catch (HttpProvisionRuntimeException e) {
                ProvisionErrorResponse.send( resp, e.statusCode, e.message );
                return;
            }
            catch (ProvisionRuntimeException e) {
                return; // login redirect
            }
            catch (Throwable e) {
                log.error( "Error while provisioning or upstream process. (" + Thread.currentThread().getName() + ")", e );
                ProvisionErrorResponse.send( resp, 503, "Sorry. Project is currently not available." );
                return;
            }
            finally {
                forwardRequest.vmUow.ifPresent( uow -> uow.close() );
                log.debug( "   UOW closed. (" + Thread.currentThread().getName() + ")" );
            }

            // response
            ProvisionExecutor executor2 = new ProvisionExecutor2( FORWARD_RESPONSE_PROVISIONS )
                    .setContextValues( executor.getContextValues() );
            ForwardResponse forwardResponse = executor2.newProvision( ForwardResponse.class );
            try {
                Status status2 = executor2.execute( forwardResponse );
                assert status2.severity( OK );
            }
            catch (Exception e) {
                // FIXME
                log.error( "!!! Exception while response processing, proxy response/connection might not have been closed properly !!!" );
                
                // error while provisioning or sending response
                // XXX log, reset instance(?), send error page?
                throw new RuntimeException( e );
            }
        }
        catch (Exception e) {
            // programming error
            propagateIfPossible( e, ServletException.class );
            propagateIfPossible( e, IOException.class );
            throw propagate( e );
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
