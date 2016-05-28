/* 
 * mapzone.io
 * Copyright (C) 2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.controller.um.launcher;

import java.util.Collection;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.message.BasicHttpRequest;

import com.google.common.base.Joiner;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.model2.CollectionProperty;
import org.polymap.model2.Concerns;
import org.polymap.model2.DefaultValue;
import org.polymap.model2.Nullable;
import org.polymap.model2.Property;
import org.polymap.model2.PropertyConcernAdapter;
import org.polymap.model2.runtime.PropertyInfo;

import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.runtime.Script;

/**
 *
 *
 * @author Falko Bräutigam
 */
public class JvmProjectLauncher
        extends ArchiveLauncher {

    private static final Log log = LogFactory.getLog( JvmProjectLauncher.class );
    
    /** System.getProperty( "io.mapzone.controller.javaCommand" ) */
    public static class SystemPropertyDefault
            extends PropertyConcernAdapter<String> {
        @Override
        public String get() {
            return System.getProperty( "io.mapzone.controller.javaCommand", super.get() );
        }
    }
    
    @DefaultValue( "java" )
    @Concerns( SystemPropertyDefault.class )
    public Property<String>             javaCommand;

    public Property<String>             mainClass;

    @Nullable
    public Property<String>             jar;

    @JvmParam( "classpath" )
    @DefaultValue( "" )
    public CollectionProperty<String>   classpath;

    @JvmParam( "Xmx" )
    @DefaultValue( "256" )
    public Property<Integer>            xmxMB;

    /** Use -server VM, or not. */
    @JvmParam( "server" )
    @DefaultValue( "true" )
    public Property<Boolean>            server;

    /** Enable assertions */
    @JvmParam( "ea" )
    @DefaultValue( "true" )
    public Property<Boolean>            ea;

    @JvmParam( "XX:+TieredCompilation" )
    @DefaultValue( "true" )
    public Property<Boolean>            tieredCompilation;

    @JvmParam( "XX:+UseG1GC" )
    @DefaultValue( "true" )
    public Property<Boolean>            g1gc;

    
    /** Denotes an JVM parameter and its name. */
    @Documented
    @Retention( RetentionPolicy.RUNTIME )
    @Target( { ElementType.FIELD } )
    public @interface JvmParam {
        public String value();
    }

    /** Denotes an application parameter and its name. */
    @Documented
    @Retention( RetentionPolicy.RUNTIME )
    @Target( { ElementType.FIELD } )
    public @interface AppParam {
        public String value();
    }

    
    // instance *******************************************
    
    public String pidFile( ProjectInstanceRecord instance ) {
        return instance.homePath.get() + "/pid";
    }
    
    public String logFile( ProjectInstanceRecord instance ) {
        return logPath( instance ) + "/console.log";
    }
    

    @Override
    public void start( ProjectInstanceRecord instance, IProgressMonitor monitor ) throws Exception {
        String logFile = logFile( instance ); 
        String pidFile = pidFile( instance );
        
        // commandLine
        StringBuilder commandLine = new StringBuilder( 512 );
        commandLine/*.append( "nohup setsid" )*/.append( javaCommand.get() );

        addJvmParams( commandLine, instance );
        addJarParam( commandLine, instance );
        addMainClassParam( commandLine, instance );
        addAppParams( commandLine, instance );
        
        // redirect output
        commandLine.append( " &>" ).append( logFile ).append( " &" );

        HostRecord host = instance.host.get();
        host.runtime.get().nohupExecute( new Script()
                .add( "cd " + instance.homePath.get() )
                .add( commandLine.toString() )
                .add( "echo $! > " + pidFile  )
                .blockOnComplete.put( true )
                .exceptionOnFail.put( true ) );

        pollHttp( instance );
        
        // fail on exception
        log.info( "PID: " + host.runtime.get().file( new File( pidFile ) ).content() );
    }

    
    protected void pollHttp( ProjectInstanceRecord instance ) 
            throws ClientProtocolException, IOException {
        log.info( "SLEEP 1s: allow instance to start up..." );
        try { Thread.sleep( 1000 ); } catch (InterruptedException e) {}
        
        try (
            @SuppressWarnings("deprecation")
            CloseableHttpClient httpClient = new SystemDefaultHttpClient();
        ){
            // XXX /arena
            BasicHttpRequest request = new BasicHttpRequest( "GET", "/arena" );
            HttpHost host = new HttpHost( instance.host.get().inetAddress.get(), instance.process.get().port.get() );
            
            // max 40x250ms => 10s
            for (int i=0; i<80; i++) {
                try (
                    CloseableHttpResponse response = httpClient.execute( host, request );
                ){
                    log.info( request.getRequestLine().getUri() + " -> " + response.getStatusLine().getStatusCode() );
                    if (response.getStatusLine().getStatusCode() == 200) {
                        return;
                    }
                }
                catch (IOException e) {
                    log.info( "    response: " + e );
                }
                try { Thread.sleep( 250 ); } catch (InterruptedException e) {}
            }
        }
    }
    
    
    protected void addMainClassParam( StringBuilder commandLine, ProjectInstanceRecord instance ) {
        commandLine.append( " " ).append( mainClass.get() );
    }


    protected void addJarParam( StringBuilder commandLine, ProjectInstanceRecord instance ) {
        if (jar.get() != null) {
            commandLine.append( " -jar " ).append( jar.get() );
        }
    }

    
    protected void addJvmParams( StringBuilder commandLine, ProjectInstanceRecord instance ) {
        Collection<PropertyInfo> props = info().getProperties();
        for (PropertyInfo info : props) {
            JvmParam a = (JvmParam)info.getAnnotation( JvmParam.class );
            if (a != null) {
                // classpath
                if (a.value().equals( "classpath" )) {
                    String entries = Joiner.on( ":" ).join( (CollectionProperty<String>)info.get( this ) );
                    if (!entries.isEmpty()) {
                        commandLine.append( " -" ).append( a.value() ).append( " " ).append( entries );
                    }
                }
                // Integer: -Xmx, -Xms, -Xss
                else if (info.getType().equals( Integer.class )) {
                    String value = ((Property)info.get( this )).get().toString();
                    commandLine.append( " -" ).append( a.value() ).append( value ).append( "m" );
                }
                // String: -(D)name=value
                else if (info.getType().equals( String.class )) {
                    String value = (String)((Property)info.get( this )).get();
                    commandLine.append( " -" ).append( a.value() ).append( "=" ).append( value );
                }
                // Boolean: -name
                else if (info.getType().equals( Boolean.class )) {
                    Boolean enabled = (Boolean)((Property)info.get( this )).get();
                    if (enabled) {
                        commandLine.append( " -" ).append( a.value() );
                    }
                }
                else {
                    throw new RuntimeException( "Unhandled property: " + info );
                }
            }
        }
    }

    
    protected void addAppParams( StringBuilder commandLine, ProjectInstanceRecord instance ) {
        Collection<PropertyInfo> props = info().getProperties();
        for (PropertyInfo info : props) {
            AppParam a = (AppParam)info.getAnnotation( AppParam.class );
            if (a != null) {
                // Boolean: -name
                if (info.getType().equals( Boolean.class )) {
                    commandLine.append( " -" ).append( a.value() );
                }
                // else: -name value
                else {
                    String value = (String)((Property)info.get( this )).get();
                    commandLine.append( " -" ).append( a.value() ).append( " " ).append( value );
                }
            }
        }
    }

    
    @Override
    public void stop( ProjectInstanceRecord instance, IProgressMonitor monitor ) throws Exception {
        String pidFile = pidFile( instance );
        
        HostRecord host = instance.host.get();
        host.runtime.get().execute( new Script()
                .add( "kill -- `cat " + pidFile + "`" )
                .add( "rm " + pidFile )
                .blockOnComplete.put( true )
                .exceptionOnFail.put( false ) );
    }

}
