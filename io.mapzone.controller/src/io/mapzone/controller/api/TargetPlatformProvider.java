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
package io.mapzone.controller.api;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Throwables;

import org.eclipse.core.runtime.IPath;
import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.LockedLazyInit;

import org.polymap.service.fs.spi.BadRequestException;
import org.polymap.service.fs.spi.DefaultContentFolder;
import org.polymap.service.fs.spi.DefaultContentNode;
import org.polymap.service.fs.spi.DefaultContentProvider;
import org.polymap.service.fs.spi.IContentDeletable;
import org.polymap.service.fs.spi.IContentFile;
import org.polymap.service.fs.spi.IContentFolder;
import org.polymap.service.fs.spi.IContentNode;
import org.polymap.service.fs.spi.IContentProvider;
import org.polymap.service.fs.spi.IContentPutable;
import org.polymap.service.fs.spi.NotAuthorizedException;
import org.polymap.service.fs.spi.Range;

import io.mapzone.controller.api.ApiContentProvider.ProjectFolder;
import io.mapzone.controller.ops.StopProcessOperation;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.repository.ProjectInstanceIdentifier;
import io.mapzone.controller.vm.repository.ProjectInstanceRecord;
import io.mapzone.controller.vm.repository.VmRepository;
import io.mapzone.controller.vm.repository.VmRepository.VmUnitOfWork;
import io.mapzone.controller.vm.runtime.HostFile;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class TargetPlatformProvider
        extends DefaultContentProvider
        implements IContentProvider {

    private static final Log log = LogFactory.getLog( TargetPlatformProvider.class );

    @Override
    public List<? extends IContentNode> getChildren( IPath path ) {
        IContentFolder parent = getSite().getFolder( path );

        // PluginsFolder
        if (parent instanceof ProjectFolder) {
            return singletonList( new PluginsFolder( path, ((ProjectFolder)parent).getSource() ) );
        }
        
        // PluginFile
        else if (parent instanceof PluginsFolder) {
            try (
                VmUnitOfWork vmuow = VmRepository.newUnitOfWork()
            ){
                Project project = ((PluginsFolder)parent).getSource();
                ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( project );
                ProjectInstanceRecord instance = vmuow.findInstance( pid ).get();
                
                File pluginDir = new File( instance.homePath.get(), "/bin/plugins" );
                List<HostFile> files = instance.host.get().runtime.get().listFiles( pluginDir );
                return files.stream()
                        .map( hostFile -> new PluginFile( hostFile.name(), path, project ) )
                        .collect( toList() );
            }
        }
        return null;
    }

    
    /**
     * 
     */
    public class PluginsFolder
            extends DefaultContentFolder
            implements IContentPutable {
    
        public PluginsFolder( IPath parentPath, Project project ) {
            super( "plugins", parentPath, TargetPlatformProvider.this, project );
        }
        
        @Override
        public Project getSource() {
            return (Project)super.getSource();
        }

        @Override
        public IContentFile createNew( String newName, InputStream in, Long length, String contentType )
                throws IOException, NotAuthorizedException, BadRequestException {
            try (
                VmUnitOfWork vmuow = VmRepository.newUnitOfWork()
            ){
                ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( getSource() );
                ProjectInstanceRecord instance = vmuow.findInstance( pid ).get();

                File pluginDir = new File( instance.homePath.get(), "/bin/plugins" );
                HostFile hostFile = instance.host.get().runtime.get().file( new File( pluginDir, newName ) );
                boolean isNewFile = true;  //hostFile.exists();
                hostFile.write( in );

                // stop/restart process
                if (instance.process.isPresent()) {
                    StopProcessOperation op = new StopProcessOperation();
                    op.process.set( instance.process.get() );
                    op.vmUow.set( vmuow );
                    op.execute( null, null );
                }
                
                if (isNewFile) {
                    getSite().invalidateFolder( this );
                }
                return new PluginFile( newName, getPath(), getSource() );
            }
            catch (Exception e) {
                throw Throwables.propagate( e );
            }
        }
    }


    /**
     * 
     */
    public class PluginFile
            extends DefaultContentNode
            implements IContentFile, IContentDeletable {

        private Lazy<byte[]>        content;
        
        
        public PluginFile( String name, IPath parentPath, Project project ) {
            super( name, parentPath, TargetPlatformProvider.this, project );
            
            content = new LockedLazyInit( () -> {
                try (
                    VmUnitOfWork vmuow = VmRepository.newUnitOfWork()
                ){
                    ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( project );
                    ProjectInstanceRecord instance = vmuow.findInstance( pid ).get();

                    File pluginDir = new File( instance.homePath.get(), "/bin/plugins" );
                    HostFile hostFile = instance.host.get().runtime.get().file( new File( pluginDir, getName() ) );
                    return hostFile.content();
                }
                catch (IOException e) {
                    throw new RuntimeException( e );
                }
            });
        }

        @Override
        public Project getSource() {
            return (Project)super.getSource();
        }

        @Override
        public Date getModifiedDate() {
            // milton-client throws warning if null
            return new Date( 0 ); 
        }

        @Override
        public Long getMaxAgeSeconds() {
            return null;
        }

        @Override
        public void sendContent( OutputStream out, Range range, Map<String,String> params, String contentType )
                throws IOException, BadRequestException {
            assert range == null;
            IOUtils.copy( new ByteArrayInputStream( content.get() ), out );
        }

        @Override
        public String getContentType( String accepts ) {
            return "application/octet-stream";
        }

        @Override
        public Long getContentLength() {
            // avoid fetching content for folder listing
            return null; //Long.valueOf( content.get().length );
        }

        @Override
        public void delete() throws BadRequestException, NotAuthorizedException {
            try (
                VmUnitOfWork vmuow = VmRepository.newUnitOfWork()
            ){
                ProjectInstanceIdentifier pid = new ProjectInstanceIdentifier( getSource() );
                ProjectInstanceRecord instance = vmuow.findInstance( pid ).get();

                File pluginDir = new File( instance.homePath.get(), "/bin/plugins" );
                HostFile hostFile = instance.host.get().runtime.get().file( new File( pluginDir, getName() ) );
                hostFile.delete();
                getSite().invalidateFolder( getSite().getFolder( getParentPath() ) );
                
                // stop/restart process
                if (instance.process.isPresent()) {
                    StopProcessOperation op = new StopProcessOperation();
                    op.process.set( instance.process.get() );
                    op.vmUow.set( vmuow );
                    op.execute( null, null );
                }
            }
            catch (Exception e) {
                throw Throwables.propagate( e );
            }
        }
    }

}
