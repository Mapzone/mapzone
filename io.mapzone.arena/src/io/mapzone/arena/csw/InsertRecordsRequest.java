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

import static io.mapzone.arena.csw.Namespaces.CSW;

import java.util.UUID;

import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import io.mapzone.arena.csw.catalog.CswMetadata;
import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;
import net.opengis.cat.csw.v_2_0_2.TransactionResponseType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class InsertRecordsRequest
        extends CswRequest<TransactionResponseType> {

    private final static Log log = LogFactory.getLog( InsertRecordsRequest.class );
    
    private SummaryRecordType           record;
    

    protected InsertRecordsRequest( SummaryRecordType record ) {
        this.record = record;
        this.request.set( "Transaction" );
    }

    
    @Override
    protected void prepare( IProgressMonitor monitor ) throws Exception {
        writeElement( CSW, "Insert", () -> {
            writeObject( JAXBF.createSummaryRecord( record ) );
        });    
    }
    
        
    @Override
    protected TransactionResponseType handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception {
        return null;
    }

    
    /**
     * Test.
     */
    public static final void main( String[] args ) throws Exception {
        SummaryRecordType record = new SummaryRecordType();
        CswMetadata md = new CswMetadata( record );
        md.setIdentifier( UUID.randomUUID().toString() );
        md.setTitle( "Test" );
        md.setDescription( "HashCode: " + md.hashCode() );
        
        InsertRecordsRequest request = new InsertRecordsRequest( record )
                .baseUrl.put( "http://localhost:8090/csw" );
        request.execute( new NullProgressMonitor() );
    }

}
