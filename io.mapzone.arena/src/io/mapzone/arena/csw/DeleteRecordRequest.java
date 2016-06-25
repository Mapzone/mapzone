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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Strings;

import org.eclipse.core.runtime.IProgressMonitor;
import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Mandatory;

import net.opengis.cat.csw.v_2_0_2.TransactionResponseType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class DeleteRecordRequest
        extends CswRequest<TransactionResponseType> {

    private final static Log log = LogFactory.getLog( DeleteRecordRequest.class );
    
    /**
     * Inbound: 
     */
    @Mandatory
    @RequestParam( "constraint_language_version" )
    @RequestAttr( "version" )
    @DefaultString( "1.1.0" )
    public Config2<GetRecordsRequest,String>    constraintLangVersion;
    
    private String                              identifier;
    

    public DeleteRecordRequest( String identifier ) {
        assert !Strings.isNullOrEmpty( identifier ); 
        this.identifier = identifier;
        this.request.set( "Transaction" );
    }

    
    @Override
    protected void prepare( IProgressMonitor monitor ) throws Exception {
        writeElement( CSW, "Delete", () -> {
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
    protected TransactionResponseType handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception {
        // FIXME
        return null;
    }
    
}
