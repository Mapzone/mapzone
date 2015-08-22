package io.mapzone.controller.http;

import static io.mapzone.controller.http.Provision.Status.Severity.OK;
import io.mapzone.controller.http.Provision.Status;
import io.mapzone.controller.vm.provisions.ProcessRunning;
import io.mapzone.controller.vm.repository.VmRepository;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    protected void service( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        try {
            // provisioning
            VmRepository vmRepo = null;
            ProvisionExecutor executor = new ProvisionExecutor( forwardRequestProvisions );
            OkToForwardRequest okRequest = executor.newTargetProvision( OkToForwardRequest.class );
            okRequest.request.set( req );
            okRequest.response.set( resp );
            okRequest.vmRepo.set( vmRepo );
            Status status = executor.execute( null );
            assert status.severityEquals( OK );

            // send request and handle response code / errors
            
            // provisioning
            executor = new ProvisionExecutor( forwardResponseProvisions );
            OkToForwardResponse okResponse = executor.newTargetProvision( OkToForwardResponse.class );
            okResponse.request.set( req );
            okResponse.response.set( resp );
            okResponse.vmRepo.set( vmRepo );
            status = executor.execute( null );
            assert status.severityEquals( OK );
            
            // send response
        }
        catch (Exception e) {
            // XXX log, reset instance(?), send error page
            throw new RuntimeException( e );
        }
    }
    
}
