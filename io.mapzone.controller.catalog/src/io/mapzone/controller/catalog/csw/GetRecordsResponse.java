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
package io.mapzone.controller.catalog.csw;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.model2.query.Query;
import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.controller.catalog.CatalogPlugin;
import io.mapzone.controller.catalog.CatalogRepositoryContext;
import io.mapzone.controller.catalog.model.CatalogEntry;
import net.opengis.cat.csw.v_2_0_2.GetRecordsType;
import net.opengis.cat.csw.v_2_0_2.QueryType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class GetRecordsResponse
        extends CswResponse {

    private static Log log = LogFactory.getLog( GetRecordsResponse.class );

    public static final String              REQUEST = "GetRecords";
    
    
    @Override
    protected void doExecute() throws Exception {
        CatalogRepositoryContext catalog = CatalogPlugin.instance().catalog();
        try (
            UnitOfWork uow = CatalogPlugin.instance().catalog().unitOfWork();
        ){
            // query
            Query<CatalogEntry> query = uow.query( CatalogEntry.class );

            // GET
            if (!request().parameters().isEmpty()) {
                throw new UnsupportedOperationException( "GetRecords: GET requests are not supported." );
            }
            // POST
            GetRecordsType body = request().<GetRecordsType>parsedBody().orElse( null );
            if (body != null) {
                if (body.getMaxRecords() != null ) {
                    query.maxResults( body.getMaxRecords().intValue() );
                }
                if (body.getStartPosition() != null ) {
                    query.firstResult( body.getStartPosition().intValue() );
                }
                //            if (!"application/xml".equalsIgnoreCase( body.getOutputFormat() )) {
                //                throw new UnsupportedOperationException( "OutputFormat: supported format: application/xml" );
                //            }

                QueryType queryBody = (QueryType)body.getAbstractQuery().getValue();
                query.where( new FilterParser( queryBody.getConstraint(), catalog.index() ).parse() );
            }

            ResultSet<CatalogEntry> rs = query.execute();

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
            out().writeAttribute( "recordSchema", Namespaces.CSW );  // ?
            out().writeAttribute( "elementSet", "full" );  // ?

            // records
            for (CatalogEntry entry : rs) {
                new SummaryRecordWriter( out() ).process( entry );
            }

            out().writeEndElement();
            out().writeEndElement();
        }
    }

}
