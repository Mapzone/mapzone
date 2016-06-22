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

import java.util.Iterator;
import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Mandatory;

import net.opengis.cat.csw.v_2_0_2.AbstractRecordType;
import net.opengis.cat.csw.v_2_0_2.GetRecordsResponseType;
import net.opengis.cat.csw.v_2_0_2.SearchResultsType;
import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class GetRecordsRequest
        extends CswRequest<GetRecordsRequest.ResultSet<SummaryRecordType>> {

    private static final Log log = LogFactory.getLog( GetRecordsRequest.class );
    
    /**
     * 
     */
    public static interface ResultSet<T>
            extends Iterable<T>, AutoCloseable {

        public abstract int size();

        @Override
        public void close();
    }
    
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
            
    
    public GetRecordsRequest() {
        super();
        request.set( "GetRecords" );
    }


    @Override
    public ResultSet<SummaryRecordType> execute( IProgressMonitor monitor ) throws Exception {
        String url = baseUrl.get() + encodeRequestParams( assembleParams() );
        GetRecordsResponseType response = read( GetRecordsResponseType.class, url );
        
        return new ResultSet<SummaryRecordType>() {
            SearchResultsType results = response.getSearchResults();
            Iterator<JAXBElement<? extends AbstractRecordType>> it = results.getAbstractRecord().iterator();

            @Override
            public Iterator<SummaryRecordType> iterator() {
                return new Iterator<SummaryRecordType>() {
                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }
                    @Override
                    public SummaryRecordType next() {
                        return (SummaryRecordType)it.next().getValue();
                    }
                };
            }

            @Override
            public int size() {
                return results.getNumberOfRecordsReturned().intValue();
            }

            @Override
            public void close() {
            }
        };
    }

    
    
    /**
     * Test.
     */
    public static final void main( String[] args ) throws Exception {
        GetRecordsRequest getRecords = new GetRecordsRequest()
                .constraint.put( "AnyText Like '%Landschaftsschutzgebiete%'" )
                .baseUrl.put( "http://www.geokatalog-mittelsachsen.de/geonetwork2.10/srv/eng/csw" );

        ResultSet<SummaryRecordType> rs = getRecords.execute( new NullProgressMonitor() );
        System.out.println( "Results:" + rs.size() );
        for (SummaryRecordType record : rs) {
            System.out.println( "-----------------------------------");
            System.out.println( record.getIdentifier().get( 0 ).getValue().getContent() );
            System.out.println( record.getTitle().get( 0 ).getValue().getContent() );
            System.out.println( record.getSubject().get( 0 ).getContent() );
            System.out.println( record.getAbstract().get( 0 ).getContent() );
        }
    }

}
