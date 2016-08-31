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

import java.util.Optional;

import java.io.InputStream;

import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.v1_1.OGCConfiguration;
import org.geotools.xml.Configuration;
import org.opengis.filter.FilterFactory2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.arena.csw.jaxb.AbstractRecordXML;
import io.mapzone.arena.csw.jaxb.GetRecordByIdResponseXML;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class GetRecordByIdRequest<T extends AbstractRecordXML>
        extends CswRequest<Optional<T>> {

    private static final Log log = LogFactory.getLog( GetRecordByIdRequest.class );
    
    private static final Configuration  CONFIGURATION = new OGCConfiguration();

    public static final FilterFactory2  ff = CommonFactoryFinder.getFilterFactory2();

    /**
     * Inbound: The identifier to search for.
     */
    @Mandatory
    @RequestElement( prefix=CSW, value="Id" )
    public Config2<GetRecordByIdRequest<T>,String> identifier;

    
    public GetRecordByIdRequest() {
        request.set( "GetRecordById" );
    }


    @Override
    protected void prepare( IProgressMonitor monitor ) throws Exception {
        writeElement( identifier, () -> {} );
    }
    
    
    @Override
    protected Optional<T> handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception {
        GetRecordByIdResponseXML response = readObject( in, GetRecordByIdResponseXML.class );
        if (response.records.size() > 1) {
            assert response.records.size() <= 1;
            log.warn( "Multiple records for id: " + identifier.get() );
        }
        return (Optional<T>)response.records.stream().findAny();
    }

    
//    // test ***********************************************
//
//    /**
//     * Test.
//     */
//    public static final void main( String[] args ) throws Exception {
////        GetRecordsRequest getRecords = new GetRecordsRequest()
////              .constraint.put( "AnyText Like '%Landschaftsschutzgebiete%'" )
////              .constraint.put( "*Landschaftsschutzgebiete*" )
////                .baseUrl.put( "http://www.geokatalog-mittelsachsen.de/geonetwork2.10/srv/eng/csw" );
//
//        GetRecordById getRecords = new GetRecordById()
//                .identifier.put( "e2d17537-1cf3-4406-9980-572e41c027d2" )
//                .baseUrl.put( "http://localhost:8090/csw" );
//
//        SummaryRecordType record = getRecords.execute( new NullProgressMonitor() );
//        System.out.println( "-----------------------------------");
//        System.out.println( record.getIdentifier().get( 0 ).getValue().getContent() );
//        System.out.println( record.getTitle().get( 0 ).getValue().getContent() );
//    }

}
