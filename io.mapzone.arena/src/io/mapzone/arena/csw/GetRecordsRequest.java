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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.polymap.core.catalog.IMetadata;
import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.arena.csw.catalog.CswMetadataDCMI;
import io.mapzone.arena.csw.jaxb.AbstractRecordXML;
import io.mapzone.arena.csw.jaxb.ElementSetXML;
import io.mapzone.arena.csw.jaxb.GetRecordsResponseXML;
import io.mapzone.arena.csw.jaxb.RecordXML;
import io.mapzone.arena.csw.jaxb.SearchResultsXML;

/**
 * 
 * @param <T> The record type to be returned by this request specified by
 *        {@link #elementSet}.
 * @author Falko Bräutigam
 */
public class GetRecordsRequest<T extends AbstractRecordXML>
        extends CswRequest<GetRecordsRequest.ResultSet<T>> {

    private static final Log log = LogFactory.getLog( GetRecordsRequest.class );
    
    /**
     * 
     */
    public static interface ResultSet<T>
            extends Iterable<T>, AutoCloseable {

        public abstract int size();

        @Override
        public void close();
        
        public default Stream<T> stream() {
            return StreamSupport.stream( spliterator(), false );
        }
    }
    
    /**
     * Inbound: 
     */
    @Mandatory
    @RequestParam( "ConstraintLanguage" )
    @DefaultString( "CQL_TEXT" )
    public Config2<GetRecordsRequest<T>,String> constraintLang;
    
    /**
     * Inbound: 
     */
    @Mandatory
    @RequestParam( "constraint_language_version" )
    @RequestAttr( "version" )
    @DefaultString( "1.1.0" )
    public Config2<GetRecordsRequest<T>,String> constraintLangVersion;
    
    /**
     * Inbound: 
     * <p/>
     * Wildcard: '*'
     */
    @Mandatory
    @RequestParam( "Constraint" )
    public Config2<GetRecordsRequest<T>,String> constraint;

    /**
     * Inbound: defaults to {@link ElementSetType#SUMMARY}
     */
    @Mandatory
    public Config2<GetRecordsRequest<T>,ElementSetXML> elementSet;
            
    
    public GetRecordsRequest() {
        request.set( "GetRecords" );
        elementSet.set( ElementSetXML.SUMMARY );
    }


    @Override
    protected void prepare( IProgressMonitor monitor ) throws Exception {
        //String url = baseUrl.get() + encodeRequestParams( assembleParams() );
        //GetRecordsResponseType response = read( GetRecordsResponseType.class, url );
        
        out().writeAttribute( "resultType" , "results" );
        out().writeAttribute( "maxRecords" , "50" );
        out().writeAttribute( "startPosition" , "1" );
        out().writeAttribute( "outputFormat" , "application/xml" );
        out().writeAttribute( "outputSchema" , "csw:Record" );

        
        writeElement( CSW, "Query", () -> {
            //<ElementSetName typeNames="csw:IsoRecord">full</ElementSetName>
            writeElement( CSW, "ElementSetName", () -> {
                //writeAttributes( typeName );
                out().writeCharacters( elementSet.get().value() );
            });
            
            writeElement( CSW, "Constraint", () -> {
                writeAttributes( constraintLangVersion );
                out().writeNamespace( "ogc", OGC );

                writeElement( OGC, "Filter", () -> {
                    // <PropertyIsLike wildCard="%" singleChar="_" escape="\\">
                    writeElement( OGC, "PropertyIsLike", () -> {
                        out().writeAttribute( "wildCard", "*" );
                        writeElement( OGC, "PropertyName", () -> { out().writeCharacters( "AnyText" ); } );
                        writeElement( OGC, "Literal", () -> { out().writeCharacters( constraint.get() ); } );
                    });
                });
            });
        });
    }
    
    
    @Override
    protected ResultSet<T> handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception {
        GetRecordsResponseXML response = readObject( in, GetRecordsResponseXML.class );
        
        return new ResultSet<T>() {
            SearchResultsXML results = response.searchResults;
            Iterator<? extends AbstractRecordXML> it = results.records.iterator();

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }
                    @Override
                    public T next() {
                        return (T)it.next();
                    }
                };
            }

            @Override
            public int size() {
                return results.numberOfRecordsReturned.intValue();
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
//        GetRecordsRequest<RecordXML> getRecords = new GetRecordsRequest<RecordXML>()
//                .elementSet.put( ElementSetType.FULL )
//                //.constraint.put( "AnyText Like '%Landschaftsschutzgebiete%'" )
//                .constraint.put( "*Landschaftsschutzgebiete*" )
//                .baseUrl.put( "http://www.geokatalog-mittelsachsen.de/geonetwork2.10/srv/eng/csw" );

        GetRecordsRequest<RecordXML> getRecords = new GetRecordsRequest<RecordXML>()
                .elementSet.put( ElementSetXML.FULL )
                .constraint.put( "ahnung" )
                .baseUrl.put( "http://localhost:8090/csw" );

        ResultSet<RecordXML> rs = getRecords.execute( new NullProgressMonitor() );
        System.out.println( "Results:" + rs.size() );
        rs.stream().forEach( record -> printFull( record ) );
    }

    
    protected static void printFull( RecordXML record ) {
        System.out.println( "-----------------------------------");
        System.out.println( record.modified );
        System.out.println( record.subject );
        System.out.println( record.rights );
        System.out.println( record.identifier );
        System.out.println( record.URI );
        
        CswMetadataDCMI md = new CswMetadataDCMI( record );
        System.out.println( md.getIdentifier() );
        System.out.println( md.getTitle() );
        System.out.println( md.getDescription().orElse( null ) );
        System.out.println( md.getKeywords() );
        System.out.println( md.getFormats() );
        System.out.println( md.getType().get() );
        System.out.println( md.getModified().orElse( null ) );
        System.out.println( md.getLanguages() );
        System.out.println( md.getDescription( IMetadata.Field.Creator ).orElse( null ) );
        System.out.println( md.getDescription( IMetadata.Field.Rights ).orElse( null ) );
        System.out.println( md.getDescription( IMetadata.Field.Publisher ).orElse( null ) );
        System.out.println( md.getDescription( IMetadata.Field.AccessRights ).orElse( null ) );
        System.out.println( md.getConnectionParams() );
    }

    
//    protected static void printSummary( SummaryRecordType record ) {
//        System.out.println( "-----------------------------------");
//        System.out.println( record.getIdentifier().get( 0 ).getValue().getContent() );
//        System.out.println( record.getTitle().get( 0 ).getValue().getContent() );
//        //System.out.println( record.getSubject().get( 0 ).getContent() );
//        //System.out.println( record.getAbstract().get( 0 ).getContent() );
//    }

}
