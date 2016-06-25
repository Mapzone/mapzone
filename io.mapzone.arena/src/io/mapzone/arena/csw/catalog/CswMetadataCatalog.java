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
package io.mapzone.arena.csw.catalog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Iterators;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.catalog.IMetadata;
import org.polymap.core.catalog.IUpdateableMetadata;
import org.polymap.core.catalog.IUpdateableMetadataCatalog;
import org.polymap.core.catalog.MetadataQuery;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.arena.csw.CswRequest;
import io.mapzone.arena.csw.DeleteRecordRequest;
import io.mapzone.arena.csw.GetRecordByIdRequest;
import io.mapzone.arena.csw.GetRecordsRequest;
import io.mapzone.arena.csw.InsertRecordRequest;
import io.mapzone.arena.csw.UpdateRecordRequest;
import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CswMetadataCatalog
        extends Configurable
        implements IUpdateableMetadataCatalog {

    private static Log log = LogFactory.getLog( CswMetadataCatalog.class );

    @Mandatory
    public Config<String>           baseUrl;
    
    
    @Override
    public String getTitle() {
        return "Community resources";
    }

    @Override
    public String getDescription() {
        return "Global catalog of community resources";
    }

    @Override
    public void close() {
    }

    
    @Override
    public MetadataQuery query( String query, IProgressMonitor monitor ) throws Exception {
        return new MetadataQuery() {
            @Override
            public ResultSet execute() throws Exception {
                GetRecordsRequest getRecords = new GetRecordsRequest()
                        .constraint.put( "AnyText Like '%Landschaftsschutzgebiete%'" )
                        .baseUrl.put( baseUrl.get() );

                GetRecordsRequest.ResultSet rs = getRecords.execute( monitor );

                // build ResultSet
                return new ResultSet() {
                    @Override
                    public Iterator<IMetadata> iterator() {
                        Iterator<SummaryRecordType> result = rs.iterator();
                        return Iterators.transform( result, record -> new CswMetadata( record ) );
                    }
                    @Override
                    public int size() {
                        return rs.size();
                    }
                    @Override
                    public void close() {
                        rs.close();
                    }
                    @Override
                    public void finalize() throws Throwable {
                        close();
                    }
                };
            }            
        };
    }

    
    @Override
    public Optional<? extends IMetadata> entry( String identifier, IProgressMonitor monitor ) throws Exception {
        GetRecordByIdRequest request = new GetRecordByIdRequest()
                .identifier.put( identifier )
                .baseUrl.put( baseUrl.get() );
        
        return request.execute( monitor )
                .map( record -> new CswMetadata( record ) );
    }

    
    @Override
    public Updater prepareUpdate() {
        return new Updater() {
            private List<CswRequest>        requests = new ArrayList();
            
            @Override
            public void newEntry( Consumer<IUpdateableMetadata> initializer ) {
                SummaryRecordType record = new SummaryRecordType();
                CswMetadata md = new CswMetadata( record );
                initializer.accept( md );
                requests.add( new InsertRecordRequest( record )
                        .baseUrl.put( baseUrl.get() ) );
            }

            @Override
            public void updateEntry( String identifier, Consumer<IUpdateableMetadata> updater ) {
                SummaryRecordType record = new SummaryRecordType();
                CswMetadata md = new CswMetadata( record );
                md.setIdentifier( identifier );
                updater.accept( md );
                requests.add( new UpdateRecordRequest( record )
                        .baseUrl.put( baseUrl.get() ) );
            }

            @Override
            public void removeEntry( String identifier ) {
                requests.add( new DeleteRecordRequest( identifier )
                        .baseUrl.put( baseUrl.get() ) );
            }

            @Override
            public void commit() throws Exception {
                for (CswRequest request : requests) {
                    request.execute( new NullProgressMonitor() );
                }
            }

            @Override
            public void close() {
            }
        };
    }
    
}
