/* 
 * polymap.org
 * Copyright (C) 2016-2017, the @authors. All rights reserved.
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
package io.mapzone.controller.catalog.csw;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ImmutableList;

import io.mapzone.controller.catalog.CatalogPlugin;
import io.mapzone.controller.catalog.CatalogRepositoryContext;
import net.opengis.cat.csw.v_2_0_2.ElementSetType;
import net.opengis.cat.csw.v_2_0_2.GetRecordsType;
import net.opengis.cat.csw.v_2_0_2.QueryType;

/**
 * Non-CSW response for fulltext query proposals.
 *
 * @author Falko Bräutigam
 */
public class GetProposalsResponse
        extends CswResponse {

    private static final Log log = LogFactory.getLog( GetProposalsResponse.class );

    public static final String              REQUEST = "GetProposals";
    
    
    @Override
    protected void doExecute() throws Exception {
        CatalogRepositoryContext catalog = CatalogPlugin.instance().catalog();

        ElementSetType elementSet = ElementSetType.SUMMARY;
        int maxResults = Integer.MAX_VALUE;
        String query = null;

        // GET
        if (!request().parameters().isEmpty()) {
            throw new UnsupportedOperationException( "GetRecords: GET requests are not supported." );
        }
        // POST
        GetRecordsType body = request().<GetRecordsType>parsedBody().orElse( null );
        if (body != null) {
            if (body.getMaxRecords() != null ) {
                maxResults = body.getMaxRecords().intValue();
            }
            if (body.getStartPosition() != null && body.getStartPosition().intValue() != 1) {
                throw new UnsupportedOperationException( "startPos is not supported for GetProposals: " + body.getStartPosition() );
            }
            if (!"csw:Proposals".equals( body.getOutputSchema() )) {
                throw new UnsupportedOperationException( "Only supported schema: " + body.getOutputSchema() );
            }

            QueryType queryBody = (QueryType)body.getAbstractQuery().getValue();
            query = queryBody.getConstraint().getCqlText();
            if (query == null) {
                throw new IllegalStateException( "CQL text is mandatory for GetProposals." );                    
            }

//            if (!ElementSetType.SUMMARY.value().equals( queryBody.getElementSetName() )) {
//                throw new UnsupportedOperationException( "elementSet is always SUMMARY for GetProposals: " + queryBody.getElementSetName() );
//            }
        }

        List<String> rs = ImmutableList.copyOf( catalog.index().propose( query, maxResults, null ) );

        // GetRecordsResponse
        out().writeStartElement( "csw", "GetRecordsResponse", Namespaces.CSW  );
        out().writeNamespace( "xml", Namespaces.XML );
        out().writeNamespace( "csw", Namespaces.CSW );
        out().writeNamespace( "dc", Namespaces.DC );
        out().writeNamespace( "dct", Namespaces.DCT );

        // SearchResults
        out().writeStartElement( "csw", "SearchResults", Namespaces.CSW );
        String resultSize = Integer.toString( rs.size() );
        out().writeAttribute( "numberOfRecordsMatched", resultSize );
        out().writeAttribute( "numberOfRecordsReturned", resultSize );
        out().writeAttribute( "nextRecord", "0" );
        out().writeAttribute( "recordSchema", Namespaces.CSW );
        out().writeAttribute( "elementSet", elementSet.value() );

        // records
        for (String proposal : rs) {
            out().writeStartElement( Namespaces.CSW, "SummaryRecord" );
          
            out().writeStartElement( Namespaces.DCT, "abstract" );
            out().writeCharacters( proposal );
            out().writeEndElement();        
            
            out().writeStartElement( Namespaces.DC, "subject" );
            out().writeCharacters( proposal );
            out().writeEndElement();        
            
            out().writeEndElement();
        }

        out().writeEndElement();
        out().writeEndElement();
    }

}
