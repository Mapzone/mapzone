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

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.catalog.IMetadataCatalog;
import org.polymap.core.catalog.IUpdateableMetadata;

import io.mapzone.arena.csw.jaxb.AbstractRecordXML;

/**
 *
 *
 * @author Falko Bräutigam
 */
public abstract class CswMetadataBase
        implements IUpdateableMetadata {

    private static final Log log = LogFactory.getLog( CswMetadataBase.class );
    
    private IMetadataCatalog        catalog;
    
    private AbstractRecordXML       record;
    
    public CswMetadataBase( CswMetadataCatalog catalog, AbstractRecordXML record ) {
        this.catalog = catalog;
        this.record = record;
    }

    public AbstractRecordXML record() {
        return record;
    }
    
    CswMetadataBase setCatalog( IMetadataCatalog catalog ) {
        this.catalog = catalog;
        return this;
    }

    @Override
    public IMetadataCatalog getCatalog() {
        return catalog;
    }

    @Override
    public String getIdentifier() {
        return record().identifier;
    }

    @Override
    public IUpdateableMetadata setIdentifier( String identifier ) {
        record().identifier = identifier;
        return this;
    }

    @Override
    public String getTitle() {
        return record().title;
    }

    @Override
    public IUpdateableMetadata setTitle( String title ) {
        record().title = title;
        return this;
    }

    @Override
    public Optional<String> getType() {
        return Optional.ofNullable( record().type );
    }

    @Override
    public IUpdateableMetadata setType( String type ) {
        record().type = type;
        return this;
    }

    @Override
    public Set<String> getFormats() {
        return record().format;
    }

    @Override
    public IUpdateableMetadata setFormats( Set<String> formats ) {
        record().format.clear();
        record().format.addAll( formats );
        return this;
    }

    @Override
    public Optional<Date> getModified() {
        return Optional.ofNullable( record().modified )
                .map( v -> v.toGregorianCalendar().getTime() );
    }

    @Override
    public Set<String> getKeywords() {
        return record().subject;
    }

    @Override
    public IUpdateableMetadata setKeywords( Set<String> keywords ) {
        record().subject.clear();
        record().subject.addAll( keywords );
        return this;
    }


//    protected String encodeParam( Map.Entry<String,String> param ) {
//        if (param.getKey().contains( "=" )) {
//            throw new UnsupportedOperationException( "Connection param key must not contain '=' character." );
//        }
//        return Joiner.on( '=' ).join( 
//                param.getKey(),
//                param.getValue().replace( "=", "\\=" ) );
//    }
//    
//    protected Pair<String,String> decodeParam( String param ) {
//        param = param.replace( "\\=", "=" );
//        int mark = param.indexOf( '=' );
//        return Pair.of( param.substring( 0, mark ), param.substring( mark+1 ) );
//    }    

}
