package io.mapzone.controller.test;

import static org.jclouds.compute.options.RunScriptOptions.Builder.wrapInInitScript;
import static org.jclouds.scriptbuilder.domain.Statements.exec;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jclouds.ContextBuilder;
import org.jclouds.byon.BYONApiMetadata;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.ExecResponse;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.scriptbuilder.ScriptBuilder;
import org.jclouds.scriptbuilder.domain.Statements;
import org.jclouds.ssh.SshClient;
import org.jclouds.ssh.jsch.config.JschSshClientModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ListenableFuture;

import org.polymap.core.runtime.Timer;

/**
 * 
 */
public class JCloudsByonTest {

   private static ComputeServiceContext context;

   @BeforeClass
   public static void setup() throws FileNotFoundException, IOException {
       Properties contextProperties = new Properties();

        StringBuilder nodes = new StringBuilder();
        nodes.append( "nodes:\n" );
        nodes.append( "    - id: local\n" );
        nodes.append( "      name: my local machine\n" );
        nodes.append( "      hostname: localhost\n" );
        nodes.append( "      os_arch: " ).append( System.getProperty( "os.arch" ) ).append( "\n" );
        nodes.append( "      os_family: " ).append( OsFamily.LINUX ).append( "\n" );
        nodes.append( "      os_description: " ).append( System.getProperty( "os.name" ) ).append( "\n" );
        nodes.append( "      os_version: " ).append( System.getProperty( "os.version" ) ).append( "\n" );
        nodes.append( "      group: " ).append( "ssh" ).append( "\n" );
        nodes.append( "      tags:\n" );
        nodes.append( "          - local\n" );
        nodes.append( "      username: " ).append( System.getProperty( "user.name" ) ).append( "\n" );
        nodes.append( "      credential_url: file://" ).append( System.getProperty( "user.home" ) ).append( "/.ssh/id_rsa" ).append( "\n" );

        contextProperties.setProperty( "byon.nodes", nodes.toString() );
        context = ContextBuilder.newBuilder( new BYONApiMetadata() )
                .overrides( contextProperties )
                .modules( ImmutableSet.of( new JschSshClientModule(), new SLF4JLoggingModule() ) )
                .build( ComputeServiceContext.class );
   }

   
   @Test
   public void testCanRunCommandAsCurrentUser() throws Exception {
       Map<? extends NodeMetadata, ExecResponse> responses = context
               .getComputeService()
               .runScriptOnNodesMatching( 
                       Predicates.alwaysTrue(), 
                       exec( "id" ), 
                       wrapInInitScript( false ).runAsRoot( false ) );

       for (Entry<? extends NodeMetadata, ExecResponse> response : responses.entrySet()) {
           System.out.println( "RESPONSE: " + response.getValue().getOutput() );
           assert response.getValue().getOutput().trim().contains( System.getProperty( "user.name" ) ) : response
           .getKey() + ": " + response.getValue();
       }
   }

   
   @Test
   public void testScript() throws Exception {
       String script = new ScriptBuilder()
               .addStatement( Statements.exec( "cd /tmp" ) )
               .addStatement( Statements.exec( "nohup setsid ls -asl > /tmp/first.log &" ) )
               .addStatement( Statements.exec( "echo $! > /tmp/first.pid" ) )
               .render( org.jclouds.scriptbuilder.domain.OsFamily.UNIX );
       
       System.out.println( "COMMAND: " + script );
       
       ListenableFuture<ExecResponse> response = context
               .getComputeService()
               .submitScriptOnNode( 
                       "local", 
                       script, 
                       wrapInInitScript( false ).runAsRoot( false ) );

       System.out.println( "RESPONSE: " + response.get().getOutput() );
       
       //
       // get PID and make sure that process is running
       Timer timer = new Timer();
       NodeMetadata node = context.getComputeService().getNodeMetadata( "local" );
       SshClient ssh = context.utils().sshForNode().apply( node );
       try {
           ssh.connect();
           List<String> lines = IOUtils.readLines( ssh.get( "/tmp/first.pid" ).openStream() );
           System.out.println( "READ: " + lines + " (" + timer.elapsedTime() + "ms)" );
       } 
       finally {
           if (ssh != null) {
               ssh.disconnect();
           }
       }
   }
   
   
   @AfterClass
   public static void close() throws FileNotFoundException, IOException {
      if (context != null)
         context.close();
   }
   
}