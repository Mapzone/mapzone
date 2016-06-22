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
package io.mapzone.arena.csw;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.catalog.IMetadata;
import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CswMetadata
        implements IMetadata {

    private static Log log = LogFactory.getLog( CswMetadata.class );
    
    private SummaryRecordType       record;

    
    public CswMetadata( SummaryRecordType record ) {
        assert record != null;
        this.record = record;
    }

    @Override
    public String getIdentifier() {
        return record.getIdentifier().get( 0 ).getValue().getContent().get( 0 );
    }

    @Override
    public String getTitle() {
        return record.getTitle().get( 0 ).getValue().getContent().get( 0 );
    }

    @Override
    public String getDescription() {
        return record.getAbstract().get( 0 ).getContent().get( 0 );
    }

    @Override
    public Set<String> getKeywords() {
        return record.getSubject().stream()
                .map( literal -> literal.getContent().get( 0 ) )
                .collect( Collectors.toSet() );
    }

    /**
     * See {@link SummaryRecordType#getType()}.
     */
    public String getType() {
        return "Service";
        //return record.getType().getContent().get( 0 );
    }

    /**
     * See {@link SummaryRecordType#getFormat()}.
     */
    public String getFormat() {
        //return "WMS";
        return record.getFormat().get( 0 ).getValue().getContent().get( 0 );
    }

    /**
     * See {@link SummaryRecordType#getSpatial()}.
     */
    public Set<String> getSpatial() {
        return record.getSpatial().stream()
                .map( literal -> literal.getContent().get( 0 ) )
                .collect( Collectors.toSet() );
    }
    
    @Override
    public Map<String,String> getConnectionParams() {
//        serviceUrl = getSpatial().stream().filter( o -> o.startsWith( "http" ) )
//        WmsServiceResolver.createParams( url )
        
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
