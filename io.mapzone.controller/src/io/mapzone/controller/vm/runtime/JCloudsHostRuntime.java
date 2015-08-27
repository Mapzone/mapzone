package io.mapzone.controller.vm.runtime;

import io.mapzone.controller.model.Project;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.ExecResponse;
import org.jclouds.compute.options.RunScriptOptions.Builder;
import org.jclouds.scriptbuilder.ScriptBuilder;
import org.jclouds.scriptbuilder.domain.Statements;
import org.jclouds.ssh.SshClient;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.ListenableFuture;

import org.polymap.core.runtime.Timer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class JCloudsHostRuntime
        extends HostRuntime {

    private static Log log = LogFactory.getLog( JCloudsHostRuntime.class );

    public static final Pattern     NO_FILENAME_CHAR = Pattern.compile( "[^a-zA-Z_-]" );
    
    // FIXME super hack to find "free" port
    private static AtomicInteger    portCount = new AtomicInteger( 32768 );
    
    
    // instance *******************************************
    
    public JCloudsHostRuntime( RegisteredHost rhost ) {
        super( rhost );
    }


    @Override
    public ProcessRuntime process( RegisteredProcess template ) {
        return new JCloudsProcessRuntime( template );
    }

    
    @Override
    public int findFreePort() {
        return portCount.getAndIncrement();
    }


    @Override
    public HostFile file( File f ) {
        return new JCloudsHostFile( f );
    }


    /**
     * 
     */
    protected class JCloudsProcessRuntime
            extends ProcessRuntime {

        public JCloudsProcessRuntime( RegisteredProcess template ) {
            super( template );            
        }

        
        @Override
        public void start( Project project ) throws Exception {
            ComputeService cs = JCloudsRuntime.instance.get().computeService();

            String baseFilename = Joiner.on( "_" ).skipNulls().join( 
                    rprocess.organisation.get(), rprocess.project.get(), rprocess.version.get() );
            baseFilename = NO_FILENAME_CHAR.matcher( baseFilename ).replaceAll( "" );            
            String logFile = "/tmp/" + baseFilename + ".log";
            String pidFile = "/tmp/" + baseFilename + ".pid";
            rprocess.logPath.set( logFile );

            String command = Joiner.on( " " ).join( 
                  "nohup",
                  "setsid",
                      project.storedAt.get().exePath.get() + "/eclipse", 
                      "-vm /home/falko/bin/jdk1.8/bin/java",
                      "-console -consolelog -registryMultiLanguage",
                      "-data", project.storedAt.get().dataPath.get(),
                      "-vmargs -server -XX:+TieredCompilation -ea -Xmx256m -XX:+UseG1GC -XX:SoftRefLRUPolicyMSPerMB=1000",
                      "-Dorg.osgi.service.http.port=" + rprocess.port.get(),
                      "-Dorg.eclipse.equinox.http.jetty.log.stderr.threshold=info",
                      ">", logFile, "2>", logFile, "&" );
          
            String script = new ScriptBuilder()
                    .addStatement( Statements.exec( "cd /tmp" ) )
                    .addStatement( Statements.exec( command ) )
                    .addStatement( Statements.exec( "echo $! > " + pidFile  ) )
                    .render( org.jclouds.scriptbuilder.domain.OsFamily.UNIX );
            
            log.info( "SCRIPT: " + script );
            
            ListenableFuture<ExecResponse> response = cs.submitScriptOnNode( 
                    rhost.hostId.get(), 
                    Statements.exec( script ), 
                    Builder.blockOnComplete( true ).wrapInInitScript( false ).runAsRoot( false ) );

            log.info( "RESPONSE: " + response.get().getError() );
            log.info( "RESPONSE: " + response.get().getExitStatus() );
            log.info( "RESPONSE: " + response.get().getOutput() );

            // XXX better poll HTTP?
            log.info( "SLEEP: 1s (allow instance to start up)" );
            Thread.sleep( 1000 );
            
            // fail on exception
            log.info( "PID: " + file( new File( pidFile ) ).content() );
        }

        
        @Override
        public void stop() {
            // XXX Auto-generated method stub
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public boolean isRunning() {
            // XXX Auto-generated method stub
            throw new RuntimeException( "not yet implemented." );
        }
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
            SshClient ssh = JCloudsRuntime.instance.get().sshForNode( rhost.hostId.get() );
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
            // XXX Auto-generated method stub
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public boolean exists() {
            // XXX Auto-generated method stub
            throw new RuntimeException( "not yet implemented." );
        }
        
    }
    
}
