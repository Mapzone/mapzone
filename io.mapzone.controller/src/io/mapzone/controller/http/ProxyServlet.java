package io.mapzone.controller.http;

import static io.mapzone.controller.provision.Provision.Status.Severity.OK;

import java.util.concurrent.locks.ReentrantLock;

import io.mapzone.controller.provision.ProvisionExecutor;
import io.mapzone.controller.provision.Provision.Status;
import io.mapzone.controller.vm.provisions.ProcessRunning;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;

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
    private static final Class[]        forwardRequestProvisions = {OkToForwardRequest.class, ProcessRunning.class};

    /** The provisions to be handled before forwarding the response back to the sender. */
    private static final Class[]        forwardResponseProvisions = {OkToForwardResponse.class, CompressResponseProvision.class};
    

    @Override
    public void init() throws ServletException {
        log.info( "Started." );
    }


    @Override
    protected void service( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        CloseableHttpResponse downResponse = null;
        try {
            // request provisioning
            // XXX job/thread to make in cancelable or timeout?
            ProvisionExecutor executor = new ProvisionExecutor( forwardRequestProvisions );
            OkToForwardRequest forwardRequest = executor.newTargetProvision( OkToForwardRequest.class );
            forwardRequest.request.set( req );
            forwardRequest.response.set( resp );
            forwardRequest.lock.set( new ReentrantLock() );
            Status status = executor.execute( forwardRequest );
            assert status.severityEquals( OK );

            // response provisioning
            executor = new ProvisionExecutor( forwardResponseProvisions );
            OkToForwardResponse forwardResponse = executor.newTargetProvision( OkToForwardResponse.class );
            forwardResponse.request.set( req );
            forwardResponse.response.set( resp );
            forwardResponse.lock.set( new ReentrantLock() );
            status = executor.execute( null );
            assert status.severityEquals( OK );
            
            // send response
        }
        catch (Exception e) {
            // XXX log, reset instance(?), send error page
            throw new RuntimeException( e );
        }
        finally {
            Closer.create().close( downResponse );
        }
    }
    
}
