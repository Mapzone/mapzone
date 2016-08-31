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
import static io.mapzone.arena.csw.Namespaces.OGC;

import java.io.InputStream;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.arena.csw.jaxb.RecordXML;
import io.mapzone.arena.csw.jaxb.TransactionResponseXML;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class UpdateRecordRequest
        extends CswRequest<TransactionResponseXML> {

    private final static Log log = LogFactory.getLog( UpdateRecordRequest.class );
    
    /**
     * Inbound: 
     */
    @Mandatory
    @RequestParam( "constraint_language_version" )
    @RequestAttr( "version" )
    @DefaultString( "1.1.0" )
    public Config2<GetRecordsRequest,String>    constraintLangVersion;
    
    private RecordXML                           record;
    
    private String                              identifier;
    

    public UpdateRecordRequest( RecordXML record ) {
        this.record = record;
        this.identifier = record.identifier;
        this.request.set( "Transaction" );
    }

    
    @Override
    protected void prepare( IProgressMonitor monitor ) throws Exception {
        writeElement( CSW, "Update", () -> {
            // record
            QName name = new QName( Namespaces.CSW, "Record" );
            writeObject( new JAXBElement( name, RecordXML.class, null, record ) );
            
            // constraint
            writeElement( CSW, "Constraint", () -> {
                writeAttributes( constraintLangVersion );
                out().writeNamespace( "ogc", OGC );
                writeElement( OGC, "Filter", () -> {
                    writeElement( OGC, "PropertyIsEqualTo", () -> {
                        writeElement( OGC, "PropertyName", () -> { out().writeCharacters( "Identifier" ); } );
                        writeElement( OGC, "Literal", () -> { out().writeCharacters( identifier ); } );
                    });
                });
            });            
        });
    }
    
        
    @Override
    protected TransactionResponseXML handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception {
        return readObject( in, TransactionResponseXML.class );
    }

}
