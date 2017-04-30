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

import java.util.List;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.FluentIterable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultInt;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.arena.csw.jaxb.ElementSetXML;
import io.mapzone.arena.csw.jaxb.GetRecordsResponseXML;
import io.mapzone.arena.csw.jaxb.SearchResultsXML;

/**
 * Non-CSW request to get fulltext proposals. Basically this is a "GetRecords"
 * request outputSchema "csw:Proposals", the constraint is send as CqlText.
 * 
 * @author Falko Bräutigam
 */
public class GetProposalsRequest
        extends CswRequest<List<String>> {

    private static final Log log = LogFactory.getLog( GetProposalsRequest.class );
    
    /**
     * Inbound: 
     */
    @Mandatory
    public Config2<GetProposalsRequest,String>  constraint;
    
    /**
     * Inbound: Maximum number of records in result set.
     */
    @Mandatory
    @DefaultInt( 50 )
    public Config2<GetProposalsRequest,Integer> maxRecords;
            

    public GetProposalsRequest() {
        request.set( "GetRecords" );
    }


    @Override
    protected void prepare( IProgressMonitor monitor ) throws Exception {
        out().writeAttribute( "resultType", "results" );
        out().writeAttribute( "maxRecords", maxRecords.get().toString() );
        out().writeAttribute( "startPosition", "1" );
        out().writeAttribute( "outputFormat", "application/xml" );
        out().writeAttribute( "outputSchema", "csw:Proposals" );

        
        writeElement( CSW, "Query", () -> {
            writeElement( CSW, "ElementSetName", () -> out().writeCharacters( ElementSetXML.SUMMARY.value() ) );
            
            writeElement( CSW, "Constraint", () -> {
                out().writeAttribute( "version", "1.1.0" );
                out().writeNamespace( "ogc", OGC );
                writeElement( OGC, "CqlText", () -> out().writeCharacters( constraint.get() ) );
            });
        });
    }
    
    
    @Override
    protected List<String> handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception {
        GetRecordsResponseXML response = readObject( in, GetRecordsResponseXML.class );
        
        SearchResultsXML results = response.searchResults;
        return FluentIterable.from( results.records )
                .transform( r -> r.subject.stream().findAny().get() )
                .toList();
    }

    // test ***********************************************

    /**
     * Test.
     */
    public static void main( String[] args ) throws Exception {
        GetProposalsRequest getProposals = new GetProposalsRequest()
                .constraint.put( "test" )
                .baseUrl.put( "http://localhost:8090/csw" );

        List<String> rs = getProposals.execute( new NullProgressMonitor() );
        System.out.println( "Results:" + rs.size() );
        System.out.println( rs );
    }

}
