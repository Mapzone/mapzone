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
package io.mapzone.arena.csw.catalog;

import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.catalog.IUpdateableMetadata;

import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;
import net.opengis.cat.csw.v_2_0_2.dc.elements.ObjectFactory;
import net.opengis.cat.csw.v_2_0_2.dc.elements.SimpleLiteral;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CswMetadata
        implements IUpdateableMetadata {

    private static Log log = LogFactory.getLog( CswMetadata.class );
    
    public static final ObjectFactory   ELMS = new ObjectFactory();
    
    private SummaryRecordType           record;

    
    public CswMetadata( SummaryRecordType record ) {
        assert record != null;
        this.record = record;
    }

    protected Optional<String> get( SimpleLiteral literal ) {
        List<String> content = literal.getContent();
        return Optional.ofNullable( !content.isEmpty() ? literal.getContent().get( 0 ) : null );
    }
    
    protected Optional<String> first( List<JAXBElement<SimpleLiteral>> elms ) {
        return !elms.isEmpty() ? get( elms.get( 0 ).getValue() ) : Optional.empty();
    }
    
    protected Optional<String> first2( List<SimpleLiteral> elms ) {
        return !elms.isEmpty() ? get( elms.get( 0 ) ) : Optional.empty();
    }
    
    protected SimpleLiteral literal( String value ) {
        SimpleLiteral literal = ELMS.createSimpleLiteral();
        literal.getContent().add( value );
        return literal;        
    }
    
    
    @Override
    public String getIdentifier() {
        return first( record.getIdentifier() ).get(); 
//        return record.getIdentifier().get( 0 ).getValue().getContent().get( 0 );
    }

    @Override
    public IUpdateableMetadata setIdentifier( String identifier ) {
        JAXBElement<SimpleLiteral> elm = ELMS.createIdentifier( literal( identifier ) );
        record.setIdentifier( singletonList( elm ) );
        return this;
    }

    @Override
    public String getTitle() {
        return first( record.getTitle() ).get();
    }

    @Override
    public IUpdateableMetadata setTitle( String title ) {
        JAXBElement<SimpleLiteral> elm = ELMS.createTitle( literal( title ) );
        record.setTitle( singletonList( elm ) );
        return this;
    }

    @Override
    public String getDescription() {
        return first2( record.getAbstract() ).orElse( null );
    }

    @Override
    public IUpdateableMetadata setDescription( String description ) {
        record.setAbstract( singletonList( literal( description ) ) );
        return this;
    }

    @Override
    public Set<String> getKeywords() {
        return record.getSubject().stream()
                .map( literal -> get( literal ).get() )
                .collect( Collectors.toSet() );
    }

    @Override
    public IUpdateableMetadata setKeywords( Set<String> keywords ) {
        List<SimpleLiteral> subjects = keywords.stream().map( kw -> literal( kw ) ).collect( Collectors.toList() );
        record.setSubject( subjects );
        return this;
    }

    /**
     * See {@link SummaryRecordType#getType()}.
     */
    public String getType() {
        return "Service";
        //return record.getType().getContent().get( 0 );
    }

    /**
     * See {@link SummaryRecordType#getFormat()}.
     */
    public String getFormat() {
        //return "WMS";
        return record.getFormat().get( 0 ).getValue().getContent().get( 0 );
    }

    /**
     * See {@link SummaryRecordType#getSpatial()}.
     */
    public Set<String> getSpatial() {
        return record.getSpatial().stream()
                .map( literal -> literal.getContent().get( 0 ) )
                .collect( Collectors.toSet() );
    }
    
    @Override
    public Map<String,String> getConnectionParams() {
//        serviceUrl = getSpatial().stream().filter( o -> o.startsWith( "http" ) )
//        WmsServiceResolver.createParams( url )
        
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public IUpdateableMetadata setConnectionParams( Map<String,String> params ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
