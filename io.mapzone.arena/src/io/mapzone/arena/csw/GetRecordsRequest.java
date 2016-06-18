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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Mandatory;

import net.opengis.cat.csw.v_2_0_2.GetRecordsResponseType;
import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class GetRecordsRequest
        extends CswRequest<ResultSet<SummaryRecordType>> {

    private static Log log = LogFactory.getLog( GetRecordsRequest.class );
    
    /**
     * Inbound: 
     */
    @Mandatory
    @RequestParam( "typeNames" )
    @DefaultString( "csw:Record" )
    public Config2<GetRecordsRequest,String>    typeName;

    /**
     * Inbound: 
     */
    @Mandatory
    @RequestParam( "ConstraintLanguage" )
    @DefaultString( "CQL_TEXT" )
    public Config2<GetRecordsRequest,String>    constraintLang;
    
    /**
     * Inbound: 
     */
    @Mandatory
    @RequestParam( "constraint_language_version" )
    @DefaultString( "1.1.0" )
    public Config2<GetRecordsRequest,String>    constraintLangVersion;
    
    /**
     * Inbound: 
     */
    @Mandatory
    @RequestParam( "Constraint" )
    public Config2<GetRecordsRequest,String>    constraint;

    /**
     * Inbound: 
     */
    @Mandatory
    @RequestParam( "resultType" )
    @DefaultString( "results" )
    public Config2<GetRecordsRequest,String>    resultType;
            
//    /**
//     * Inbound: 
//     */
//    @Mandatory
//    @RequestParam( "queryString" )
//    public Config2<GetRecordsRequest,String>    queryString;

    
    
    public GetRecordsRequest() {
        super();
        request.set( "GetRecords" );
    }


    @Override
    public ResultSet<SummaryRecordType> execute( IProgressMonitor monitor ) throws Exception {
        String url = baseUrl.get() + encodeRequestParams( assembleParams() );
        log.info( url );
        GetRecordsResponseType response = read( GetRecordsResponseType.class, url );
        System.out.println( response );
        return null;
    }

    
    
    /**
     * Test.
     */
    public static final void main( String[] args ) throws Exception {
        GetRecordsRequest getRecords = new GetRecordsRequest()
                .constraint.put( "AnyText Like '%Mittelsachsen%'" )
                .baseUrl.put( "http://www.geokatalog-mittelsachsen.de/geonetwork2.10/srv/eng/csw" );
        
        getRecords.execute( new NullProgressMonitor() );
    }

}
