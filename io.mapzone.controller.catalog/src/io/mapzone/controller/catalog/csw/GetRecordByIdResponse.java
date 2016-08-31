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

import org.polymap.model2.query.Expressions;
import org.polymap.model2.query.Query;
import org.polymap.model2.query.ResultSet;
import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.controller.catalog.CatalogPlugin;
import io.mapzone.controller.catalog.model.CatalogEntry;
import net.opengis.cat.csw.v_2_0_2.GetRecordByIdType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class GetRecordByIdResponse
        extends CswResponse {

    private static Log log = LogFactory.getLog( GetRecordByIdResponse.class );

    public static final String              REQUEST = "GetRecordById";
    
    
    @Override
    protected void doExecute() throws Exception {
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
            request().<GetRecordByIdType>parsedBody().ifPresent( body -> {
                String identifier = body.getId().get( 0 );
                query.where( Expressions.eq( CatalogEntry.TYPE.identifier, identifier ) );
            });

            ResultSet<CatalogEntry> rs = query.execute();

            // GetRecordsResponse
            out().writeStartElement( "csw", "GetRecordByIdResponse", Namespaces.CSW  );
            out().writeNamespace( "xml", Namespaces.XML );
            out().writeNamespace( "csw", Namespaces.CSW );
            out().writeNamespace( "dc", Namespaces.DC );
            out().writeNamespace( "dct", Namespaces.DCT );

            // records
            for (CatalogEntry entry : rs) {
                new RecordWriter( out() ).process( entry );
            }

            out().writeEndElement();
        }
    }

}
