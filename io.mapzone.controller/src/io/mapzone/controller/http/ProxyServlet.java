package io.mapzone.controller.http;

import static io.mapzone.controller.provision.Provision.Status.Severity.OK;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.Provision.Status;
import io.mapzone.controller.provision.ProvisionExecutor;
import io.mapzone.controller.provision.ProvisionExecutor2;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.provisions.MaxProcesses;
import io.mapzone.controller.vm.provisions.ProcessRunning;
import io.mapzone.controller.vm.provisions.ProcessStarted;
import io.mapzone.controller.vm.repository.VmRepository;

import java.util.regex.Pattern;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import org.polymap.core.runtime.Closer;
import org.polymap.core.runtime.Timer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProxyServlet
        extends HttpServlet {

    private static Log log = LogFactory.getLog( ProxyServlet.class );
    
    /** The provisions to be handled before forwarding the request to the instance. */
    private static final Class[]    forwardRequestProvisions = {ProcessStarted.class, ProcessRunning.class, MaxProcesses.class };

    /** The provisions to be handled before forwarding the response back to the sender. */
    private static final Class[]    forwardResponseProvisions = {};

    private static final String     SERVLET_ALIAS = "/projects";

    /** A bit restrictive, just to make sure :) */
    private static final Pattern    NO_URL_CHAR = Pattern.compile( "[^a-zA-Z0-9_-]" );

    /**
     * 
     *
     * @param request
     * @return 0: organization/user, 1: project, 2: version
     */
    public static String[] projectName( HttpServletRequest request ) {
        try {
            return StringUtils.split( URLDecoder.decode( request.getPathInfo(), "UTF8" ), "/" );
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }
    }
    
    
    public static String projectUrl( Project project ) {
        try {
            // FIXME
            return Joiner.on( "/" ).join( "http://localhost:8080",
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
        CloseableHttpResponse proxyResponse = null;
        try {
                // request provisioning
                // XXX job/thread to make it cancelable or timeout?
                ProvisionExecutor executor = new LockingProvisionExecutor( forwardRequestProvisions );
                ForwardRequest forwardRequest = executor.newProvision( ForwardRequest.class );
                forwardRequest.request.set( req );
                forwardRequest.response.set( resp );
                forwardRequest.vmRepo.set( VmRepository.instance() );
//                forwardRequest.projectRepo.set( ProjectRepository.newInstance() );
                Status status = executor.execute( forwardRequest );
                assert status.severity( OK );

                // response provisioning
                ProvisionExecutor executor2 = new LockingProvisionExecutor( forwardResponseProvisions )
                        .setContextValues( executor.getContextValues() );
                ForwardResponse forwardResponse = executor2.newProvision( ForwardResponse.class );
                Status status2 = executor2.execute( forwardResponse );
                assert status2.severity( OK );
        }
        catch (Exception e) {
            // XXX log, reset instance(?), send error page?
            throw new RuntimeException( e );
        }
        finally {
            Closer.create().close( proxyResponse );
        }
    }


    /**
     * Handle {@link VmRepository} lock for every run a {@link Provision}.
     */
    class LockingProvisionExecutor
            extends ProvisionExecutor2 {
        
        public LockingProvisionExecutor( Class<Provision>[] provisions ) {
            super( provisions );
        }

        @Override
        public Status execute( Provision target ) throws Exception {
            return super.execute( target );
        }

        @Override
        protected Status executeProvision( Provision provision ) throws Exception {
            Timer timer = new Timer();
            VmRepository vmRepo = ((DefaultProvision)provision).vmRepo.get();

            // FIXME read lock should span execute() *and* init()
            assert vmRepo.lock.getReadHoldCount() == 0;
            vmRepo.lock.readLock().lock();
            
            try {
                Status result = super.executeProvision( provision );

                vmRepo.commit();
                return result;
            }
            catch (Exception e) {
                log.warn( "", e );
                vmRepo.rollback();
                throw e;
            }
            finally {
                vmRepo.lock.readLock().unlock();
                log.info( "Provision: " + provision.getClass().getSimpleName() + " (" + timer.elapsedTime() + "ms)" );
                
                assert !vmRepo.lock.writeLock().isHeldByCurrentThread();
                assert vmRepo.lock.getReadHoldCount() == 0 : "read hold count: " + vmRepo.lock.getReadHoldCount();
            }
        }

    }
    
}
