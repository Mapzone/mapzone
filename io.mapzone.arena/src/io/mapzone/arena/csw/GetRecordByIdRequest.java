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

import java.util.List;
import java.util.Optional;

import java.io.InputStream;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBElement;

import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.v1_1.OGCConfiguration;
import org.geotools.xml.Configuration;
import org.opengis.filter.FilterFactory2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.Mandatory;

import net.opengis.cat.csw.v_2_0_2.AbstractRecordType;
import net.opengis.cat.csw.v_2_0_2.GetRecordByIdResponseType;
import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class GetRecordByIdRequest
        extends CswRequest<Optional<SummaryRecordType>> {

    private static final Log log = LogFactory.getLog( GetRecordByIdRequest.class );
    
    private static final Configuration  CONFIGURATION = new OGCConfiguration();

    public static final Charset         ENCODE_CHARSET = Charset.forName( "UTF-8" );
    
    public static final FilterFactory2  ff = CommonFactoryFinder.getFilterFactory2();

    /**
     * Inbound: The identifier to search for.
     */
    @Mandatory
    @RequestElement( prefix=CSW, value="Id" )
    public Config2<GetRecordByIdRequest,String>    identifier;

//    /**
//     * Inbound: 
//     */
//    @Mandatory
//    @RequestParam( "typeNames" )
//    @RequestAttr( "typeNames" )
//    @DefaultString( "csw:Record" )
//    public Config2<GetRecordById,String>    typeName;
//
//    /**
//     * Inbound: 
//     */
//    @Mandatory
//    @RequestParam( "resultType" )
//    @RequestAttr( "resultType" )
//    @DefaultString( "results" )
//    public Config2<GetRecordById,String>    resultType;
            
    
    public GetRecordByIdRequest() {
        request.set( "GetRecordById" );
    }


    @Override
    protected void prepare( IProgressMonitor monitor ) throws Exception {
        writeElement( identifier, () -> {} );
    }
    
    
    @Override
    protected Optional<SummaryRecordType> handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception {
        GetRecordByIdResponseType response = readObject( in, GetRecordByIdResponseType.class );
        List<JAXBElement<? extends AbstractRecordType>> result = response.getAbstractRecord();
        return !result.isEmpty()
                ? Optional.of( (SummaryRecordType)result.get( 0 ).getValue() )
                : Optional.empty();        
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
