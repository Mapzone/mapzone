package io.mapzone.controller.http;

import static io.mapzone.controller.provision.Provision.Status.Severity.OK;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.Provision.Status;
import io.mapzone.controller.provision.ProvisionExecutor;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.vm.provisions.ProcessRunning;
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

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProxyServlet
        extends HttpServlet {

    private static Log log = LogFactory.getLog( ProxyServlet.class );
    
    /** The provisions to be handled before forwarding the request to the instance. */
    private static final Class[]    forwardRequestProvisions = {OkToForwardRequest.class, ProcessRunning.class};

    /** The provisions to be handled before forwarding the response back to the sender. */
    private static final Class[]    forwardResponseProvisions = {OkToForwardResponse.class};

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
            // XXX job/thread to make in cancelable or timeout?
            ProvisionExecutor executor = new LockingProvisionExecutor( forwardRequestProvisions );
            OkToForwardRequest forwardRequest = executor.newTargetProvision( OkToForwardRequest.class );
            forwardRequest.request.set( req );
            forwardRequest.response.set( resp );
            Status status = executor.execute( forwardRequest );
            assert status.severityEquals( OK );

            // response provisioning
            executor = new LockingProvisionExecutor( forwardResponseProvisions )
                    .setContextValues( executor.getContextValues() );
            OkToForwardResponse forwardResponse = executor.newTargetProvision( OkToForwardResponse.class );
            status = executor.execute( forwardResponse );
            assert status.severityEquals( OK );
        }
        catch (Exception e) {
            // XXX log, reset instance(?), send error page
            throw new RuntimeException( e );
        }
        finally {
            Closer.create().close( proxyResponse );
        }
    }

    
    /**
     * 
     */
    class LockingProvisionExecutor
            extends ProvisionExecutor {
        
        public LockingProvisionExecutor( Class<? extends Provision>[] provisions ) {
            super( provisions );
        }

        @Override
        protected Status executeProvision( Provision provision ) throws Exception {
            VmRepository vmRepo = VmRepository.instance();
            // the projectRepo does/must not change during provisioning
            ProjectRepository projectRepo = ProjectRepository.newInstance();
            try {
                ((DefaultProvision)provision).vmRepo.set( vmRepo );
                ((DefaultProvision)provision).projectRepo.set( projectRepo );
       
                Status result = super.executeProvision( provision );

                if (vmRepo.isLocked()) {
                    log.info( "COMMIT provision: " + provision.getClass().getSimpleName() );
                }
                vmRepo.commit();
                return result;
            }
            catch (Exception e) {
                if (vmRepo.isLocked()) {
                    log.info( "ROLLBACK provision: " + provision.getClass().getSimpleName() );
                }
                vmRepo.rollback();
                throw e;
            }
        }

    }
    
}
