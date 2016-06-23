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

import java.util.Iterator;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Iterators;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.catalog.IMetadata;
import org.polymap.core.catalog.IMetadataCatalog;
import org.polymap.core.catalog.MetadataQuery;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.arena.csw.GetRecordsRequest;
import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CswMetadataCatalog
        extends Configurable
        implements IMetadataCatalog {

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
    public Optional<? extends IMetadata> entry( String identifier, IProgressMonitor monitor ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
