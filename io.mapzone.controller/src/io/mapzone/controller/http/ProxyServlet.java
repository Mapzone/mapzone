package io.mapzone.controller.http;

import io.mapzone.controller.http.Provision.Status;
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

    @Override
    protected void service( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        try {
            // provisioning
            VmRepository vmRepo = null;
            ProvisionExecutor executor = new ProvisionExecutor();
            OkToForwardRequest provision = executor.newTargetProvision( OkToForwardRequest.class );
            provision.request.set( req );
            provision.response.set( resp );
            provision.vmRepo.set( vmRepo );
            Status status = executor.execute( provision );
            
            // send request and handle response code / errors
            
            // provisioning
            
            // send response
        }
        catch (Exception e) {
            // XXX log, reset instance(?), send error page
            throw new RuntimeException( e );
        }
    }
    
}
