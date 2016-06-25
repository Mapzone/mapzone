/* 
 * polymap.org
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
package io.mapzone.arena.csw.test;

import java.util.UUID;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Sets;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.catalog.IMetadata;
import org.polymap.core.catalog.IUpdateableMetadataCatalog.Updater;
import org.polymap.core.catalog.MetadataQuery.ResultSet;
import org.polymap.core.data.wms.catalog.WmsServiceResolver;

import io.mapzone.arena.csw.catalog.CswMetadataCatalog;

/**
 * 
 * 
 * @author Falko Bräutigam
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CswCatalogTest {

    private static Log log = LogFactory.getLog( CswCatalogTest.class );

    protected static CswMetadataCatalog     catalog;
    
    protected static String                 identifier;

    protected static IProgressMonitor       monitor = new NullProgressMonitor();
    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        catalog = new CswMetadataCatalog();
        catalog.baseUrl.set( "http://localhost:8090/csw" );
    }

    // instance *******************************************
    
    @Test
    public void _10_createEntry() throws Exception {
        try (
            Updater updater = catalog.prepareUpdate();
        ){
            updater.newEntry( md -> {
                identifier = UUID.randomUUID().toString();
                md.setIdentifier( identifier );
                md.setTitle( "First" );
                md.setDescription( "Südstr" );
                md.setKeywords( Sets.newHashSet( "Test", "Ulli" ) );
                md.setConnectionParams( WmsServiceResolver.createParams( "http://google.de" ) );
            });
            updater.commit();
        }
    }

    
    @Test
    public void _20_getEntryById() throws Exception {
        IMetadata md = catalog.entry( identifier, monitor ).get();
        Assert.assertEquals( "First", md.getTitle() );
        Assert.assertEquals( "Südstr", md.getDescription() );
        Assert.assertTrue( md.getModified() != null );
        Assert.assertTrue( md.getKeywords().contains( "Test" ) );
        Assert.assertTrue( md.getKeywords().contains( "Ulli" ) );
        
        md.getConnectionParams().entrySet().forEach( p -> log.info( p.getKey() + " = " + p.getValue() ) );
        
        Assert.assertTrue( "", new WmsServiceResolver().canResolve( md ) );
    }
    
    
    @Test
    public void _25_update() throws Exception {
        try (
            Updater updater = catalog.prepareUpdate();
        ){
            updater.updateEntry( identifier, md -> {
                md.setTitle( "First" );
                md.setDescription( "Südstr. 6" );
                md.setKeywords( Sets.newHashSet( "Test", "Ulli", "Falko" ) );                
            });
            updater.commit();
        }
        IMetadata entry = catalog.entry( identifier, monitor ).get();
        Assert.assertEquals( "Südstr. 6", entry.getDescription() );
        Assert.assertTrue( entry.getKeywords().contains( "Ulli" ) );
        Assert.assertTrue( entry.getKeywords().contains( "Falko" ) );
    }
    
    
    @Test
    public void _30_query() throws Exception {
        ResultSet rs = catalog.query( "", monitor ).execute();
        
        Assert.assertTrue( rs.size() > 0 );
        rs.stream().forEach( entry -> log.info( "entry: " + entry ) );
    }
    
    
    @Test
    public void _40_remove() throws Exception {
        try (
            Updater updater = catalog.prepareUpdate();
        ){
            updater.removeEntry( identifier );
            updater.commit();
        }
        Assert.assertFalse( catalog.entry( identifier, monitor ).isPresent() );
        identifier = null;
    }
    
}
