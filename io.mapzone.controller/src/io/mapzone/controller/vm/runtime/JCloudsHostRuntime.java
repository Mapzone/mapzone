package io.mapzone.controller.vm.runtime;

import io.mapzone.controller.vm.repository.HostRecord;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.ExecResponse;
import org.jclouds.compute.options.RunScriptOptions;
import org.jclouds.io.payloads.ByteArrayPayload;
import org.jclouds.ssh.SshClient;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.util.concurrent.ListenableFuture;

import org.polymap.core.runtime.Timer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class JCloudsHostRuntime
        extends HostRuntime {

    static Log log = LogFactory.getLog( JCloudsHostRuntime.class );

    public static final Pattern     NO_FILENAME_CHAR = Pattern.compile( "[^a-zA-Z0-9_-]" );

    public static final String      INSTANCES_BASE_DIR = "/tmp/mapzone/";
    
    
    // instance *******************************************
    
    public JCloudsHostRuntime( HostRecord host ) {
        super( host );
    }


    @Override
    public ScriptExecutionResult execute( Script script ) {
        log.info( "SCRIPT: " + script );
        ComputeService cs = JCloudsRuntime.instance.get().computeService();
        ExecResponse response = cs.runScriptOnNode( host.hostId.get(), script.render(), options( script ) );

        if (script.exceptionOnFail.get() && response.getExitStatus() != 0) {
            throw new RuntimeException( response.getError() + " - status:" + response.getExitStatus() + " - " + response.getOutput());
        }
        return executionResult( response );
    }


    @Override
    public ListenableFuture<ScriptExecutionResult> nohupExecute( Script script ) {
        log.info( "SCRIPT: " + script );
        ComputeService cs = JCloudsRuntime.instance.get().computeService();
        
        ListenableFuture<ExecResponse> response = cs.submitScriptOnNode( host.hostId.get(), 
                script.render(), options( script ) );

        return new ListenableFuture<ScriptExecutionResult>() {
            ListenableFuture<ExecResponse> delegate = response;
            @Override
            public ScriptExecutionResult get() throws InterruptedException, ExecutionException {
                return executionResult( delegate.get() );
            }
            @Override
            public ScriptExecutionResult get( long timeout, TimeUnit unit )
                    throws InterruptedException, ExecutionException, TimeoutException {
                return executionResult( delegate.get( timeout, unit ) );
            }
            @Override
            public void addListener( Runnable listener, Executor executor ) {
                delegate.addListener( listener, executor );
            }
            @Override
            public boolean cancel( boolean mayInterruptIfRunning ) {
                return delegate.cancel( mayInterruptIfRunning );
            }
            @Override
            public boolean isCancelled() {
                return delegate.isCancelled();
            }
            @Override
            public boolean isDone() {
                return delegate.isDone();
            }            
        };
    }


    protected RunScriptOptions options( Script script ) {
        return RunScriptOptions.Builder
                .blockOnComplete( script.blockOnComplete.get() )
                .wrapInInitScript( false )
                .runAsRoot( false );
    }


    protected ScriptExecutionResult executionResult( ExecResponse response ) {
        log.info( "RESPONSE: " + response.getError() );
        log.info( "RESPONSE: " + response.getExitStatus() );
        log.info( "RESPONSE: " + response.getOutput() );

        ScriptExecutionResult result = new ScriptExecutionResult();
        result.error.set( response.getError() );
        result.exitStatus.set( response.getExitStatus() );
        result.output.set( response.getOutput() );
        return result;
    }


    @Override
    public int findFreePort() {
        // XXX assuming that anybody else has locked VmRepository
        Integer port = host.portCount.get();
        host.portCount.set( port == 65535 ? 32768 : port + 1 );
        return port;
    }


    @Override
    public HostFile file( File f ) {
        return new JCloudsHostFile( f );
    }


    /**
     * <p/>
     * XXX Makes new connection for each and every request!
     */
    protected class JCloudsHostFile
            extends HostFile {
        
        private File            f;

        public JCloudsHostFile( File f ) {
            this.f = f;
        }

        @Override
        public String content() throws IOException {
            return content( "UTF8" );
        }
        
        @Override
        public String content( String charset ) throws IOException {
            SshClient ssh = JCloudsRuntime.instance.get().sshForNode( host.hostId.get() );
            Timer timer = new Timer();
            InputStream in = null;
            try {
                ssh.connect();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                in = ssh.get( f.getAbsolutePath() ).openStream();
                IOUtils.copy( in, out );
                System.out.println( "READ: " + out.size() + "bytes (" + timer.elapsedTime() + "ms)" );
                
                return out.toString( charset );
            } 
            finally {
                if (in != null) { in.close(); }
                if (ssh != null) { ssh.disconnect(); }
            }
        }
        
        @Override
        public InputStream inputStream() {
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public OutputStream outputStream() {
            // XXX make it stream
            return new ByteArrayOutputStream() {
                @Override
                public void close() throws IOException {
                    SshClient ssh = JCloudsRuntime.instance.get().sshForNode( host.hostId.get() );
                    try {
                        ssh.connect();
                        ssh.put( f.getAbsolutePath(), new ByteArrayPayload( toByteArray() ) );
                    } 
                    finally {
                        if (ssh != null) { ssh.disconnect(); }
                    }
                }                
            };            
        }

        @Override
        public boolean exists() {
            // XXX Auto-generated method stub
            throw new RuntimeException( "not yet implemented." );
        }
        
    }
    
}
