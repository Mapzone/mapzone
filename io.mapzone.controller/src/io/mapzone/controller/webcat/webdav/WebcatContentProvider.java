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
package io.mapzone.controller.webcat.webdav;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;

import org.eclipse.core.runtime.IPath;

import org.polymap.core.security.SecurityContext;

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
import org.polymap.service.fs.spi.IContentWriteable;
import org.polymap.service.fs.spi.IMakeFolder;
import org.polymap.service.fs.spi.NotAuthorizedException;
import org.polymap.service.fs.spi.Range;

import org.polymap.model2.query.Expressions;
import org.polymap.model2.query.grammar.BooleanExpression;
import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.controller.um.repository.Organization;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.webcat.CatalogBase;
import io.mapzone.controller.webcat.model.CatalogEntry;
import io.milton.http.FileItem;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class WebcatContentProvider
        extends DefaultContentProvider
        implements IContentProvider {

    private static final Log log = LogFactory.getLog( WebcatContentProvider.class );

    protected UnitOfWork        uow;

    private AllFolder           allFolder;

    private MyFolder            myFolder;

    private CatalogEntry        template;
    
    
    protected abstract String baseFolderName();

    protected abstract CatalogBase catalog();
    

    public WebcatContentProvider() {
        assert uow == null;
        uow = catalog().newUnitOfWork();
        template = Expressions.template( catalog().entryClass(), catalog().repo() );
    }

    @Override
    public void dispose() {
        uow.close();
        super.dispose();
    }

    @Override
    public List<? extends IContentNode> getChildren( IPath path ) {
        try {
            // root
            if (path.segmentCount() == 0) {
                return Collections.singletonList( new BaseFolder( path ) );
            }

            // BaseFolder
            IContentFolder parent = getSite().getFolder( path );
            if (parent instanceof BaseFolder) {
                return Lists.newArrayList(
                        //new InboxFolder( path )
                        allFolder = new AllFolder( path ),
                        myFolder = new MyFolder( path ) );
            }
            // AllFolder
            else if (parent instanceof AllFolder) {
                BooleanExpression filter = Expressions.eq( template.isReleased, true );
                return entryFolders( filter, null, path );
            }
            // MyFolder
            else if (parent instanceof MyFolder) {
                String username = SecurityContext.instance().getUser().getName();
                ProjectUnitOfWork puow = ProjectRepository.session();
                Organization org = puow.findOrganization( username ).get();

                BooleanExpression filter = Expressions.eq( template.vendorId, (String)org.id() );
                return entryFolders( filter, null, path );
            }
            // EntryFolder
            else if (parent instanceof EntryFolder) {
                EntryFolder folder = (EntryFolder)parent;
                List<IContentNode> result = Lists.newArrayList( new EntryContentsFile( folder ) );
                for (File item : catalog().entryItems( folder.getSource() ).listFiles()) {
                    result.add( new EntryItemFile( item, folder ) );
                }
                return result;
            }
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
    }

    
    protected List<EntryFolder> entryFolders( BooleanExpression filter, String luceneQuery, IPath parentPath ) throws Exception {
        return catalog().query( uow, filter, luceneQuery ).stream()
                .map( entry -> new EntryFolder( entry, parentPath ) )
                .collect( Collectors.toList() );
    }
    
    
    protected IContentFolder createNewEntry( String id ) {
        CatalogEntry newEntry = catalog().createEntry( uow, id );
        
        if (allFolder != null) {
            getSite().invalidateFolder( allFolder );
        }
        if (myFolder != null) {
            getSite().invalidateFolder( myFolder );
        }
        return new EntryFolder( newEntry, myFolder.getPath() );
    }


    protected void deleteEntry( CatalogEntry entry ) {
        catalog().deleteEntry( uow, entry );
        
        if (allFolder != null) {
            getSite().invalidateFolder( allFolder );
        }
        if (myFolder != null) {
            getSite().invalidateFolder( myFolder );
        }
    }

    
    /**
     * A catalog entry folder.
     */
    public class EntryFolder
            extends DefaultContentFolder
            implements IContentPutable, IContentDeletable {
        
        public EntryFolder( CatalogEntry entry, IPath parentPath ) {
            super( entry.id().toString(), parentPath, WebcatContentProvider.this, entry );
        }

        @Override
        public CatalogEntry getSource() {
            return (CatalogEntry)super.getSource();
        }

        @Override
        public IContentFile createNew( String newName, InputStream in, Long length, String contentType )
                throws IOException, NotAuthorizedException, BadRequestException {
            // this is not contents.json
            File items = catalog().entryItems( getSource() );
            File item = new File( items, newName );
            if (item.exists()) {
                throw new RuntimeException( "Item already exists: " + newName );
            }
            FileUtils.copyInputStreamToFile( in, item );
                
            getSite().invalidateFolder( this );
            return new EntryItemFile( item, EntryFolder.this );
        }

        @Override
        public void delete() throws BadRequestException, NotAuthorizedException {
            deleteEntry( getSource() );
        }
    }


    /**
     * A catalog entry *.jar or any other kind of item file.
     */
    public class EntryItemFile
            extends DefaultContentNode
            implements IContentFile, IContentWriteable, IContentDeletable {
        
        private File        item;
        
        public EntryItemFile( File item, EntryFolder parent ) {
            super( item.getName(), parent.getPath(), WebcatContentProvider.this, parent.getSource() );
            this.item = item;
        }

        @Override
        public CatalogEntry getSource() {
            return (CatalogEntry)super.getSource();
        }

        @Override
        public void sendContent( OutputStream out, Range range, Map<String,String> params, String contentType )
                throws IOException, BadRequestException {
            FileUtils.copyFile( item, out );
        }

        @Override
        public void replaceContent( InputStream in, Long length )
                throws IOException, BadRequestException, NotAuthorizedException {
            FileUtils.copyInputStreamToFile( in, item );
        }

        @Override
        public String processForm( Map<String,String> params, Map<String,FileItem> files )
                throws IOException, BadRequestException, NotAuthorizedException {
            // XXX Auto-generated method stub
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public Date getModifiedDate() {
            return new Date( item.lastModified() );
        }

        @Override
        public Long getMaxAgeSeconds() {
            return null; //Long.valueOf( 1 );
        }

        @Override
        public String getContentType( String accepts ) {
            try {
                return Files.probeContentType( item.toPath() );
            }
            catch (IOException e) {
                return "application/octet-stream";
            }
        }

        @Override
        public Long getContentLength() {
            return item.length();
        }

        @Override
        public void delete() throws BadRequestException, NotAuthorizedException {
            if (!item.delete()) {
                throw new RuntimeException( "Unable to delete item: " + item.getName() );
            }
        }
    }


    /**
     * A catalog entry contents.json file.
     */
    public class EntryContentsFile
            extends DefaultContentNode
            implements IContentFile, IContentWriteable, IContentDeletable {
        
        public EntryContentsFile( EntryFolder parent ) {
            super( "contents.json", parent.getPath(), WebcatContentProvider.this, parent.getSource() );
        }

        @Override
        public CatalogEntry getSource() {
            return (CatalogEntry)super.getSource();
        }

        @Override
        public void delete() throws BadRequestException, NotAuthorizedException {
            // called if PlugInFolder is deleted
        }

        @Override
        public void sendContent( OutputStream out, Range range, Map<String,String> params, String contentType )
                throws IOException, BadRequestException {
            try {
                JSONObject json = Entity2JsonTransformer.transform( getSource() );
                IOUtils.write( json.toString( 4 ).getBytes( "UTF-8" ), out );
            }
            catch (Exception e) {
                throw new RuntimeException( e );
            }
        }

        @Override
        public void replaceContent( InputStream in, Long length )
                throws IOException, BadRequestException, NotAuthorizedException {
            try {
                String source = IOUtils.toString( in, "UTF-8" );
                JSONObject json = new JSONObject( source );
                Json2EntityTransformer.transform( json, getSource() );
                uow.commit();
            }
            catch (Exception e) {
                uow.rollback();
                throw new RuntimeException( e );
            } 
        }

        @Override
        public String processForm( Map<String,String> params, Map<String,FileItem> files )
                throws IOException, BadRequestException, NotAuthorizedException {
            // XXX Auto-generated method stub
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public Date getModifiedDate() {
            return getSource().updated.get();
        }

        @Override
        public Long getMaxAgeSeconds() {
            return null; //Long.valueOf( 1 );
        }

        @Override
        public String getContentType( String accepts ) {
            return "application/json";
        }

        @Override
        public Long getContentLength() {
            return Long.valueOf( -1 );
        }
    }


    /**
     * The root of the catalog hierarchy.
     */
    public class BaseFolder
            extends DefaultContentFolder {

        public BaseFolder( IPath parentPath ) {
            super( baseFolderName(), parentPath, WebcatContentProvider.this, null );
        }
    }


    /**
     * All entries of the catalog.
     */
    public class AllFolder
            extends DefaultContentFolder {

        public AllFolder( IPath parentPath ) {
            super( "all", parentPath, WebcatContentProvider.this, null );
        }
    }


    /**
     * 
     */
    public class MyFolder
            extends DefaultContentFolder
            implements IMakeFolder {

        public MyFolder( IPath parentPath ) {
            super( "my", parentPath, WebcatContentProvider.this, null );
        }

        @Override
        public IContentFolder createFolder( String newName ) {
            return createNewEntry( newName );
        }
    }

}
