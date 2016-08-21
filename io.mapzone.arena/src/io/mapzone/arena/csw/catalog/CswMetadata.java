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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import java.text.ParseException;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.polymap.core.catalog.IUpdateableMetadata;

import io.mapzone.arena.csw.CswRequest;
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
    public Optional<String> getDescription() {
        return first2( record.getAbstract() );
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

    @Override
    public Optional<Date> getModified() {
        Optional<String> result = first2( record.getModified() );
        if (result.isPresent()) {
            try {
                return Optional.of( CswRequest.DF.parse( result.get() ) );
            }
            catch (ParseException e) {
                throw new RuntimeException( e );
            }
        }
        return null;
    }
    
    /**
     * See {@link SummaryRecordType#getType()}.
     */
    @Override
    public Optional<String> getType() {
        return Optional.of( "Service" );
        //return record.getType().getContent().get( 0 );
    }

    @Override
    public IUpdateableMetadata setType( String type ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    /**
     * See {@link SummaryRecordType#getFormat()}.
     */
    @Override
    public Set<String> getFormats() {
        return new HashSet( record.getFormat().get( 0 ).getValue().getContent() );
    }

    
    @Override
    public IUpdateableMetadata setFormats( Set<String> formats ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public Set<String> getLanguages() {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public IUpdateableMetadata setLanguages( Set<String> langs ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public Optional<Date> getCreated() {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public Date[] getAvailable() {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public Optional<String> getDescription( Field field ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public IUpdateableMetadata setDescription( Field field, String description ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
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
        return record.getSpatial().stream()
                .map( literal -> decodeParam( literal.getContent().get( 0 ) ) )
                .collect( Collectors.toMap( p -> p.getLeft(), p -> p.getRight() ) );
    }

    @Override
    public IUpdateableMetadata setConnectionParams( Map<String,String> params ) {
        record.setSpatial( params.entrySet().stream()
                .map( entry -> literal( encodeParam( entry ) ) )
                .collect( Collectors.toList() ) );
        return this;
    }
    
    protected String encodeParam( Map.Entry<String,String> param ) {
        if (param.getKey().contains( "=" )) {
            throw new UnsupportedOperationException( "Connection param key must not contain '=' character." );
        }
        return Joiner.on( '=' ).join( 
                param.getKey(),
                param.getValue().replace( "=", "\\=" ) );
    }
    
    protected Pair<String,String> decodeParam( String param ) {
        param = param.replace( "\\=", "=" );
        int mark = param.indexOf( '=' );
        return Pair.of( param.substring( 0, mark ), param.substring( mark+1 ) );
    }
    
}
