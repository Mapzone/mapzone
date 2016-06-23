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

import java.util.Iterator;

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
    
    private static final Configuration  CONFIGURATION = new OGCConfiguration();

    public static final Charset         ENCODE_CHARSET = Charset.forName( "UTF-8" );
    
    public static final FilterFactory2  ff = CommonFactoryFinder.getFilterFactory2();

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
    @RequestAttr( "typeNames" )
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
    @RequestAttr( "version" )
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
    @RequestAttr( "resultType" )
    @DefaultString( "results" )
    public Config2<GetRecordsRequest,String>    resultType;
            
    
    public GetRecordsRequest() {
        request.set( "GetRecords" );
    }


    @Override
    protected void prepare( IProgressMonitor monitor ) throws Exception {
        //String url = baseUrl.get() + encodeRequestParams( assembleParams() );
        //GetRecordsResponseType response = read( GetRecordsResponseType.class, url );
        
        writeAttributes( resultType );
        out().writeAttribute( "maxRecords" , "50" );
        out().writeAttribute( "startPosition" , "1" );
        out().writeAttribute( "outputFormat" , "application/xml" );
        //out().writeAttribute( "outputSchema" , "SummaryRecord" );
        
        writeElement( CSW, "Query", () -> {
            //<ElementSetName typeNames="csw:IsoRecord">full</ElementSetName>
            writeElement( CSW, "ElementSetName", () -> {
                writeAttributes( typeName );
                out().writeCharacters( "summary" );
            });
            
            writeElement( CSW, "Constraint", () -> {
                writeAttributes( constraintLangVersion );
                out().writeNamespace( "ogc", OGC );

                writeElement( OGC, "Filter", () -> {
                    // <PropertyIsLike wildCard="%" singleChar="_" escape="\\">
                    writeElement( OGC, "PropertyIsLike", () -> {
                        out().writeAttribute( "wildcard", "*" );
                        writeElement( OGC, "PropertyName", () -> { out().writeCharacters( "AnyText" ); } );
                        writeElement( OGC, "Literal", () -> { out().writeCharacters( "*Landschaftsschutzgebiete*" ); } );
                    });
                });
                
//                out().writeNamespace( "ogc" , "http://www.opengis.net/ogc" );
//                        
//                PropertyIsLike filter = ff.like( ff.property( "AnyText" ), "*Landschaftsschutzgebiete*", "*", "?", "\\" );
//                
//                Encoder encoder = new Encoder( CONFIGURATION );
//                encoder.setIndenting( true );
//                encoder.setEncoding( ENCODE_CHARSET );
//                encoder.setNamespaceAware( false );
//                encoder.setOmitXMLDeclaration( true );
//
//                encoder.encode( filter, org.geotools.filter.v1_1.OGC.Filter, System.out );
//                
//                try {
//                    ContentHandler contentHandler = new ContentHandlerToXMLStreamWriter( out() );
//                    encoder.encode( filter, org.geotools.filter.v1_1.OGC.Filter, contentHandler );
//                }
//                catch (Exception e) {
//                }
                
//                writeElement( Namespaces.XML, "CqlText", () -> {
//                    out().writeCData( constraint.get() );                    
//                });
            });
        });
    }
    
    
    @Override
    protected ResultSet<SummaryRecordType> handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception {
        //IOUtils.copy( in, System.out );
        
        GetRecordsResponseType response = read( in, GetRecordsResponseType.class );
        
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

    // test ***********************************************

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
