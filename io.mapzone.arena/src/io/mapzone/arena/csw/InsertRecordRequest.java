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

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import io.mapzone.arena.csw.catalog.CswMetadataDCMI;
import io.mapzone.arena.csw.jaxb.RecordXML;
import io.mapzone.arena.csw.jaxb.TransactionResponseXML;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class InsertRecordRequest
        extends CswRequest<TransactionResponseXML> {

    private final static Log log = LogFactory.getLog( InsertRecordRequest.class );
    
    private RecordXML               record;
    

    public InsertRecordRequest( RecordXML record ) {
        this.record = record;
        this.request.set( "Transaction" );
    }

    
    @Override
    protected void prepare( IProgressMonitor monitor ) throws Exception {
        writeElement( CSW, "Insert", () -> {
            QName name = new QName( Namespaces.CSW, "Record" );
            writeObject( new JAXBElement( name, RecordXML.class, null, record ) );
        });
    }
    
        
    @Override
    protected TransactionResponseXML handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception {
        return readObject( in, TransactionResponseXML.class );
    }

    
    /**
     * Test.
     */
    public static final void main( String[] args ) throws Exception {
        RecordXML record = new RecordXML();
        CswMetadataDCMI md = new CswMetadataDCMI( record );
        md.setIdentifier( UUID.randomUUID().toString() );
        md.setTitle( "Test" );
        md.setDescription( "HashCode: " + md.hashCode() );
        
        InsertRecordRequest request = new InsertRecordRequest( record )
                .baseUrl.put( "http://localhost:8090/csw" );
        request.execute( new NullProgressMonitor() );
    }

}
