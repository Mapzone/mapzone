package io.mapzone.controller.vm.runtime;

import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.RegisteredHost;
import io.mapzone.controller.vm.repository.RegisteredInstance;
import io.mapzone.controller.vm.repository.RegisteredProcess;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.ExecResponse;
import org.jclouds.compute.options.RunScriptOptions.Builder;
import org.jclouds.io.payloads.ByteArrayPayload;
import org.jclouds.scriptbuilder.ScriptBuilder;
import org.jclouds.scriptbuilder.domain.Statements;
import org.jclouds.ssh.SshClient;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.ListenableFuture;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.runtime.Timer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class JCloudsHostRuntime
        extends HostRuntime {

    private static Log log = LogFactory.getLog( JCloudsHostRuntime.class );

    public static final Pattern     NO_FILENAME_CHAR = Pattern.compile( "[^a-zA-Z0-9_-]" );

    public static final String      INSTANCES_BASE_DIR = "/tmp/mapzone/";
    
    // FIXME super hack to find "free" port
    private static AtomicInteger    portCount = new AtomicInteger( 32768 );
    
    
    // instance *******************************************
    
    public JCloudsHostRuntime( RegisteredHost rhost ) {
        super( rhost );
    }


    @Override
    public ProcessRuntime process( RegisteredProcess target ) {
        return new JCloudsProcessRuntime( target );
    }

    
    @Override
    public InstanceRuntime instance( RegisteredInstance target ) {
        return new JCloudsInstanceRuntime( target );
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
    protected class JCloudsInstanceRuntime
            extends InstanceRuntime {

        public JCloudsInstanceRuntime( RegisteredInstance instance ) {
            super( instance );
        }

        @Override
        public void install( Project project, IProgressMonitor monitor ) throws Exception {
            monitor.beginTask( "Preparing instance", 11 );
            
            // find free space on disk
            String basename = Joiner.on( "/" ).skipNulls().join( 
                    URLEncoder.encode( project.organizationOrUser().name.get(), "UTF8" ), 
                    URLEncoder.encode( project.name.get(), "UTF8" )
                    /*, instance.version.get()*/ );

            instance.homePath.set( INSTANCES_BASE_DIR + basename );
            instance.dataPath.set( INSTANCES_BASE_DIR + basename + "/data" );
            instance.exePath.set( INSTANCES_BASE_DIR + basename + "/bin" );
            instance.logPath.set( INSTANCES_BASE_DIR + basename + "/log" );
            
            // make directories
            monitor.subTask( "Making directories" );
            String script = new ScriptBuilder()
                    .addStatement( Statements.exec( "mkdir -p " + instance.homePath.get() ) )
                    .addStatement( Statements.exec( "mkdir " + instance.logPath.get() ) )
                    .addStatement( Statements.exec( "mkdir " + instance.exePath.get() ) )
                    .addStatement( Statements.exec( "mkdir " + instance.dataPath.get() ) )
                    .render( org.jclouds.scriptbuilder.domain.OsFamily.UNIX );
    
            log.info( "SCRIPT: " + script );
    
            ComputeService cs = JCloudsRuntime.instance.get().computeService();
            ExecResponse response = cs.runScriptOnNode( 
                    rhost.hostId.get(), 
                    Statements.exec( script ), 
                    Builder.blockOnComplete( true ).wrapInInitScript( false ).runAsRoot( false ) );

            // XXX check response
            log.info( "RESPONSE: " + response.getError() );
            log.info( "RESPONSE: " + response.getExitStatus() );
            log.info( "RESPONSE: " + response.getOutput() );
            monitor.worked( 1 );
            
            // copy bin there
            monitor.subTask( "Copying runtime" );
            URL exeTgzSource = new URL( "file:///tmp/p4.tgz" );
            File exeTgz = new File( instance.homePath.get(), "p4.tgz" );
            try (
                InputStream in = exeTgzSource.openStream();
                OutputStream out = file( exeTgz ).outputStream();
            ){
                IOUtils.copy( in, out );
            }
            monitor.worked( 5 );
            
            // unpack
            response = cs.runScriptOnNode( 
                    rhost.hostId.get(), 
                    Statements.exec( "tar -x -z -C " + instance.homePath.get() + " -f " + exeTgz ), 
                    Builder.blockOnComplete( true ).wrapInInitScript( false ).runAsRoot( false ) );
            
            // XXX check response
            log.info( "RESPONSE: " + response.getError() );
            log.info( "RESPONSE: " + response.getExitStatus() );
            log.info( "RESPONSE: " + response.getOutput() );
            monitor.worked( 5 );
            
            monitor.done();
        }

        
        @Override
        public void uninstall( IProgressMonitor monitor ) throws Exception {
            ComputeService cs = JCloudsRuntime.instance.get().computeService();

            log.info( "home: " + instance.homePath.get() );
//            assert instance.homePath.get().startsWith( INSTANCES_BASE_DIR );
            ExecResponse response = cs.runScriptOnNode( 
                    rhost.hostId.get(),
                    Statements.exec( "tar -c -z --remove-files -f /tmp/mapzone-last-removed.tgz " + instance.homePath.get() ),
                    Builder.blockOnComplete( true ).wrapInInitScript( false ).runAsRoot( false ) );

            // XXX check response
            log.info( "RESPONSE: " + response.getError() );
            log.info( "RESPONSE: " + response.getExitStatus() );
            log.info( "RESPONSE: " + response.getOutput() );
            monitor.worked( 1 );
        }
    }
    
    
    /**
     * 
     */
    protected class JCloudsProcessRuntime
            extends ProcessRuntime {

        public JCloudsProcessRuntime( RegisteredProcess process ) {
            super( process );
            assert process.instance.get() != null;
        }

        
        @Override
        public void start() throws Exception {
            ComputeService cs = JCloudsRuntime.instance.get().computeService();

            RegisteredInstance instance = process.instance.get();
            String logFile = instance.logPath.get() + "/console.log"; 
            String pidFile = instance.homePath.get() + "/pid";
            String exePath = instance.exePath.get();
            String dataPath = instance.dataPath.get();

            String command = Joiner.on( " " ).join( 
                  "nohup",
                  "setsid",
                      exePath + "/eclipse", 
                      "-vm /home/falko/bin/jdk1.8/bin/java",
                      "-console -consolelog -registryMultiLanguage",
                      "-data", dataPath,
                      "-vmargs -server -XX:+TieredCompilation -ea -Xmx256m -XX:+UseG1GC -XX:SoftRefLRUPolicyMSPerMB=1000",
                      "-Dorg.osgi.service.http.port=" + process.port.get(),
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

            // FIXME check
            log.info( "RESPONSE: " + response.get().getError() );
            log.info( "RESPONSE: " + response.get().getExitStatus() );
            log.info( "RESPONSE: " + response.get().getOutput() );

            // XXX better poll HTTP?
            log.info( "SLEEP: 1s (allow instance to start up)" );
            Thread.sleep( 3000 );
            
            // fail on exception
            log.info( "PID: " + file( new File( pidFile ) ).content() );
        }

        
        @Override
        public void stop() {
            ComputeService cs = JCloudsRuntime.instance.get().computeService();

            RegisteredInstance instance = process.instance.get();
            String pidFile = instance.homePath.get() + "/pid";
            
            ExecResponse response = cs.runScriptOnNode( 
                    rhost.hostId.get(), 
                    Statements.newStatementList(
                            Statements.exec( "kill -- -`cat " + pidFile + "`" ),
                            Statements.exec( "rm " + pidFile ) ),
                    Builder.blockOnComplete( true ).wrapInInitScript( false ).runAsRoot( false ) );

            // FIXME check
            log.info( "RESPONSE: " + response.getError() );
            log.info( "RESPONSE: " + response.getExitStatus() );
            log.info( "RESPONSE: " + response.getOutput() );
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
            // XXX make it stream
            return new ByteArrayOutputStream() {
                @Override
                public void close() throws IOException {
                    SshClient ssh = JCloudsRuntime.instance.get().sshForNode( rhost.hostId.get() );
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
