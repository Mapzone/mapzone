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

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.polymap.core.catalog.IUpdateableMetadata;

import io.mapzone.arena.csw.jaxb.RecordXML;
import io.mapzone.arena.jmx.ArenaConfigMBean;

/**
 * Based on {@link DCMIRecordType}.
 *
 * @author Falko Bräutigam
 */
public class CswMetadataDCMI
        extends CswMetadataBase {

    private static final Log log = LogFactory.getLog( CswMetadataDCMI.class );
    
    
    public CswMetadataDCMI( CswMetadataCatalog catalog, RecordXML record ) {
        super( catalog, record );
    }

    @Override
    public RecordXML record() {
        return (RecordXML)super.record();
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable( record().description );
    }

    @Override
    public IUpdateableMetadata setDescription( String description ) {
        record().description = description;
        return null;
    }

    @Override
    public Optional<String> getDescription( Field field ) {
        switch (field) {
            case Creator: return join( record().creator );
            case AccessRights: return join( record().accessRights );
            case Contributor: return join( record().contributor );
            case Publisher: return join( record().publisher );
            case Rights: return join( record().rights );
            case RightsHolder: return join( record().rightsHolder );
            default: throw new RuntimeException( "Unhandled field type." );
        }
    }

    protected Optional<String> join( Collection<String> parts ) {
        return Optional.ofNullable( parts != null && !parts.isEmpty()
                ? Joiner.on( "\n\n" ).skipNulls().join( parts )
                : null );
    }
    
    @Override
    public IUpdateableMetadata setDescription( Field field, String description ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public Set<String> getLanguages() {
        return record().language;
    }

    @Override
    public IUpdateableMetadata setLanguages( Set<String> langs ) {
        record().language.clear();
        record().language.addAll( langs );
        return this;
    }

    @Override
    public Optional<Date> getCreated() {
        return Optional.ofNullable( record().modified )
                .map( v -> v.toGregorianCalendar().getTime() );
    }

    @Override
    public Date[] getAvailable() {
        return new Date[] {};
    }

    @Override
    public Map<String,String> getConnectionParams() {
        return record().URI.stream()
                // io.mapzone.controller.um.repository.ProjectCatalogSynchronizer
                .filter( uri -> ArenaConfigMBean.CONNECTION_PARAM_NAME.equals( uri.description ) )
                .collect( Collectors.toMap( uri -> uri.name, uri -> uri.value) );
    }

    @Override
    public IUpdateableMetadata setConnectionParams( Map<String,String> params ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
