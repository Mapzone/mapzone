package io.mapzone.controller.http;

import io.mapzone.controller.model.Project;
import io.mapzone.controller.provision.Context;
import io.mapzone.controller.provision.Provision;
import io.mapzone.controller.provision.Provision.Status.Severity;
import io.mapzone.controller.vm.repository.RegisteredProcess;
import io.mapzone.controller.vm.runtime.ProcessRuntime;

import java.util.Collections;
import java.util.Optional;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class OkToForwardRequest
        extends DefaultProvision {

    private static Log log = LogFactory.getLog( OkToForwardRequest.class );

    public static final String              NO_PROCESS = "_no_process_";
    public static final String              BAD_RESPONSE = "_bad_response_";

    public static CloseableHttpClient       httpclient = HttpClients.createDefault();

    private Context<Project>                project;
    
    private Context<ProcessRuntime>         processRuntime;
    
    
    @Override
    public boolean init( Provision failed , Status cause  ) {
        return failed == null;
    }


    @Override
    public Status execute() throws Exception {
        String[] path = StringUtils.split( request.get().getPathInfo(), "/" );
        String projectName = path[1];
        String organizationName = path[0];
        
        // find process
        Optional<RegisteredProcess> process = vmRepo.get().findProcess( organizationName, projectName, null );
        
        // ok -> forward request
        if (process.isPresent()) {
            try {
                forwardRequest( process.get() );
                return OK_STATUS;
            }
            catch (IOException e) {
                log.info( "Forwarding request failed.", e );
                return new Status( Severity.FAILED_CHECK_AGAIN, BAD_RESPONSE );                
            }
        }
        // no process -> fail
        else {
            project.set( projectRepo.get()
                    .findProject( organizationName, projectName )
                    .orElseThrow( () -> new RuntimeException( "No such project: " + organizationName + "/" + projectName ) ) ); 
            return new Status( Severity.FAILED_CHECK_AGAIN, NO_PROCESS );
        }
    }
 
    
    protected void forwardRequest( RegisteredProcess process ) throws ClientProtocolException, IOException, Exception {
        HttpServletRequest r = request.get();
        String method = r.getMethod();
        
        URI downUri = new URIBuilder().setScheme( "http")
                .setHost( process.host.get().address.get() )
                .setPort( process.port.get() )
                .setQuery( request.get().getQueryString() )
                .build();
        
        // GET
        if (method.equals( HttpGet.METHOD_NAME )) {
            downRequest.set( new HttpGet( downUri ) );
        }
        // POST
        else if (method.equals( HttpPost.METHOD_NAME )) {
            downRequest.set( new HttpPost( downUri ) );
        }
        else {
            throw new RuntimeException( "Unhandled request method: " + r.getMethod() );
        }
        copyRequest( r, downRequest.get() );
        
        downResponse.set( httpclient.execute( downRequest.get() ) );
        log.info( "    response: " + downResponse.get().getStatusLine() );
    }
    
    
    protected void copyRequest( HttpServletRequest from, HttpRequestBase to ) {
        for (String header : Collections.list( from.getHeaderNames() )) {
            String value = from.getHeader( header );
            log.info( "    header: " + header + " = " + value );
            to.setHeader( header, value );
        }
//        Cookie[] cookies = from.getCookies();
//        if (cookies != null) {
//        }
    }
    
}
