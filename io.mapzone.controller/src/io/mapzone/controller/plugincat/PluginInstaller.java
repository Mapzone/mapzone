/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
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
package io.mapzone.controller.plugincat;

import static org.polymap.model2.query.Expressions.eq;

import java.util.List;
import java.util.stream.Collectors;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.model2.CollectionProperty;
import org.polymap.model2.Composite;
import org.polymap.model2.Defaults;
import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.UnitOfWork;
import org.polymap.model2.runtime.ValueInitializer;

import io.mapzone.controller.um.launcher.ArchiveLauncher;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.runtime.HostFile;
import io.mapzone.controller.vm.runtime.HostRuntime;
import io.mapzone.controller.vm.runtime.Script;

/**
 * Data about installed plugins of a {@link Project} and methods to retrieve and
 * manipulate.
 *
 * @author Falko Bräutigam
 */
public class PluginInstaller
        extends Composite {

    private static final Log log = LogFactory.getLog( PluginInstaller.class );

    public static final ValueInitializer<PluginInstaller> defaults = (PluginInstaller proto) -> { 
        return proto; 
    };

    // instance *******************************************
    
    @Defaults
    protected CollectionProperty<String>    installedPluginIds;
    

    protected Project project() {
        return context.getEntity();
    }
    
    
    /**
     * The list of plugins that are available for immediate install in this
     * {@link #project}. Free plugins and commercial that are purchased
     * already.(installed in an other project).
     */
    public List<PluginCatalogEntry> available() {
        UnitOfWork uow = PluginCatalog.session();
        ResultSet<PluginCatalogEntry> freePlugins = uow.query( PluginCatalogEntry.class )
                .where( eq( PluginCatalogEntry.TYPE.fee, 0f ) )
                .execute();
        // FIXME add non-free but already installed in another project
        return freePlugins.stream().collect( Collectors.toList() );
    }
    
    
    /**
     * The list of plugins that are currently installed in this {@link #project}. 
     */
    public List<PluginCatalogEntry> installed() {
        UnitOfWork uow = PluginCatalog.session();
        return installedPluginIds.stream()
                .map( id -> uow.entity( PluginCatalogEntry.class, id ) )
                .collect( Collectors.toList() );
    }
    
 
    /**
     * Install or update the given plugin.
     */
    public void install( ProjectInstanceRecord instance, PluginCatalogEntry plugin, IProgressMonitor monitor ) throws IOException {
        monitor.beginTask( "Install plugin", 10 );

        HostRuntime hostRuntime = instance.host.get().runtime.get();
        File pluginsPath = new File( ArchiveLauncher.binPath( instance ), "/dropins" );

        hostRuntime.execute( new Script()
                .add( "mkdir -p " + pluginsPath.getAbsolutePath() )
                .blockOnComplete.put( true )
                .exceptionOnFail.put( true ) );
        
        // delete currently installed
        monitor.subTask( "Check current version" );
        if (installedPluginIds.contains( plugin.id() )) {
            for (HostFile f : hostRuntime.listFiles( pluginsPath )) {
                if (f.name().startsWith( plugin.id() + "_" )) {
                    f.delete();
                }
            }
        }
        else {
            installedPluginIds.add( plugin.id() );
        }
        monitor.worked( 5 );  // XXX real progress
        
        // copy plugin.jar
        monitor.subTask( "Copy new version" );
        File[] items = PluginCatalog.instance().entryItems( plugin ).listFiles();
        assert items.length == 1;
        File target = new File( pluginsPath, items[0].getName() );
        try (
            InputStream in = new BufferedInputStream( new FileInputStream( items[0] ) ); 
        ){
            hostRuntime.file( target ).write( in );
            monitor.worked( 5 );  // XXX real progress
        }
        monitor.done();
    }
}
