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

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;
import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.model2.Property;

import io.mapzone.controller.vm.repository.HostRecord;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.runtime.Script;

/**
 * Install an instance from a tgz/zip archive from a given URI.
 *
 * @author Falko Bräutigam
 */
public abstract class ArchiveLauncher
        extends ProjectLauncher {

    private static Log log = LogFactory.getLog( ArchiveLauncher.class );

    public static final String          INSTANCES_BASE_DIR = System.getProperty( "io.mapzone.controller.instancesBaseDir", "/home/mapzone/instances/" );

    /** file:///tmp/p4.[tgz|zip] */
    public Property<String>             installArchiveUri;

    
    public String logPath( ProjectInstanceRecord instance ) {
        return instance.homePath.get() + "/log";
    }
    
    public String dataPath( ProjectInstanceRecord instance ) {
        return instance.homePath.get() + "/data";
    }
    
    public String binPath( ProjectInstanceRecord instance ) {
        return instance.homePath.get() + "/bin";
    }
    
    
    @Override
    public void install( ProjectInstanceRecord instance, IProgressMonitor monitor ) throws Exception {
        monitor.beginTask( "Install instance", 11 );

        // basename
        String basename = Joiner.on( "/" ).skipNulls().join( 
                URLEncoder.encode( project().organization.get().name.get(), "UTF8" ), 
                URLEncoder.encode( project().name.get(), "UTF8" )
                /*, instance.version.get()*/ );

        // allow re-install
        if (!instance.homePath.opt().isPresent()) {
            instance.homePath.set( INSTANCES_BASE_DIR + basename );
        }

        // XXX check free space on disk

        // make directories
        monitor.subTask( "Making directories" );
        HostRecord host = instance.host.get();
        host.runtime.get().execute( new Script()
                .add( "mkdir -p " + instance.homePath.get() )
                .add( "mkdir -p " + logPath( instance ) )
                .add( "rm -rf " + binPath( instance ) )
                .add( "mkdir -p " + binPath( instance ) )
                .add( "mkdir -p " + dataPath( instance ) )
                .blockOnComplete.put( true )
                .exceptionOnFail.put( true ) );
        monitor.worked( 1 );

        // copy archive there
        monitor.subTask( "Copying runtime" );
        URL archiveSource = new URL( installArchiveUri.get() );
        File archiveTarget = new File( instance.homePath.get(), "install.archive" );
        host.runtime.get().file( archiveTarget ).write( archiveSource.openStream() );
        monitor.worked( 5 );

        // unpack
        host.runtime.get().execute( new Script()
                .add( "cd " + binPath( instance ) )
                .add( installArchiveUri.get().endsWith( "tgz" )
                        ? "tar -x -z -f " + archiveTarget
                        : "unzip " + archiveTarget )
                .add( "rm " + archiveTarget.getAbsolutePath() )
                .blockOnComplete.put( true )
                .exceptionOnFail.put( true ) );
        monitor.worked( 5 );

        monitor.done();
    }


    @Override
    public void uninstall( ProjectInstanceRecord instance, IProgressMonitor monitor ) throws Exception {
        monitor.beginTask( "Uninstall instance", 10 );
        HostRecord host = instance.host.get();
        
        // pack/remove
        host.runtime.get().execute( new Script()
                //.add( "tar -c -z --remove-files -f /tmp/mapzone-last-removed.tgz " + instance.homePath.get() )
                .add( "rm -r " + instance.homePath.get() )
                .blockOnComplete.put( true )
                .exceptionOnFail.put( false ) );  // don't fail if dir is removed already
        monitor.done();
    }
    
}
