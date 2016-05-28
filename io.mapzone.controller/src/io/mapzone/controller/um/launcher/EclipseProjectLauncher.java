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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.model2.DefaultValue;
import org.polymap.model2.Property;
import org.polymap.model2.runtime.ValueInitializer;

import io.mapzone.controller.vm.repository.ProjectInstanceRecord;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class EclipseProjectLauncher
        extends JvmProjectLauncher { 

    private static Log log = LogFactory.getLog( EclipseProjectLauncher.class );

    public static final ValueInitializer<EclipseProjectLauncher> defaults = new ValueInitializer<EclipseProjectLauncher>() {
        @Override
        public EclipseProjectLauncher initialize( EclipseProjectLauncher proto ) throws Exception {
            proto.mainClass.set( "org.eclipse.equinox.launcher.Main" );
            // XXX find correct jar from installed files
            // XXX /bin folder is defined in binPath()
            proto.jar.set( "./bin/plugins/org.eclipse.equinox.launcher_1.0.201.R35x_v20090715.jar" );
            return proto;
        }
    };
    
    
    @AppParam( "registryMultiLanguage" )
    @DefaultValue( "true" )
    public Property<Boolean>        multiLanguage;

    @AppParam( "console" )
    @DefaultValue( "true" )
    public Property<Boolean>        console;

    @AppParam( "consolelog" )
    @DefaultValue( "true" )
    public Property<Boolean>        consolelog;

    @AppParam( "os" )
    @DefaultValue( "linux" )
    public Property<String>         os;

    @AppParam( "ws" )
    @DefaultValue( "gtk" )
    public Property<String>         ws;

    @AppParam( "arch" )
    @DefaultValue( "x86" )
    public Property<String>         arch;

    @AppParam( "data" )
    @DefaultValue( "./data" )
    public Property<String>         data;

    @JvmParam( "Dcom.sun.management.jmxremote" )
    @DefaultValue( "true" )
    public Property<String>         jmxRemote;

    @JvmParam( "Dcom.sun.management.jmxremote.authenticate" )
    @DefaultValue( "false" )
    public Property<String>         jmxAuth;

    @JvmParam( "Dcom.sun.management.jmxremote.ssl" )
    @DefaultValue( "false" )
    public Property<String>         jmxSsl;

    
    @Override
    public void install( ProjectInstanceRecord instance, IProgressMonitor monitor ) throws Exception {
        super.install( instance, monitor );
        mainClass.set( "org.eclipse.equinox.launcher.Main" );
        // XXX find correct jar from installed files
        jar.set( binPath( instance ) + "/plugins/org.eclipse.equinox.launcher_1.0.201.R35x_v20090715.jar" );
    }


    @Override
    protected void addJvmParams( StringBuilder commandLine, ProjectInstanceRecord instance ) {
        super.addJvmParams( commandLine, instance );
        commandLine.append( " -Dorg.osgi.service.http.port=" ).append( instance.process.get().port.get() );
        commandLine.append( " -Dcom.sun.management.jmxremote.port=" ).append( instance.process.get().jmxPort.get() );
    }
    
}
