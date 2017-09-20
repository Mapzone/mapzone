/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
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
package io.mapzone.controller.webcat;

import static org.polymap.model2.query.Expressions.or;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import org.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Strings;

import org.polymap.core.security.SecurityContext;

import org.polymap.rhei.fulltext.FulltextIndex;

import org.polymap.model2.query.Expressions;
import org.polymap.model2.query.Query;
import org.polymap.model2.query.ResultSet;
import org.polymap.model2.query.grammar.BooleanExpression;
import org.polymap.model2.runtime.EntityRepository;
import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.controller.um.repository.Organization;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.webcat.model.CatalogEntry;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class CatalogBase {

    private static final Log log = LogFactory.getLog( CatalogBase.class );

    /**
     * The model repository.
     */
    public abstract EntityRepository repo();

    /**
     * The fulltext index of the model.
     */
    public abstract FulltextIndex index();
    
    /**
     * The base directory for item files.
     */
    public abstract File fileRepo();
    
    /**
     * The actual type of the {@link CatalogEntry} in the repository.
     */
    public abstract Class<? extends CatalogEntry> entryClass();

    
    public UnitOfWork newUnitOfWork() {
        return repo().newUnitOfWork();
    }

    /**
     * 
     *
     * @param uow
     * @param luceneQuery The query in Lucene syntax, or null to return all entries.
     */
    public ResultSet<? extends CatalogEntry> query( UnitOfWork uow, BooleanExpression baseFilter, String luceneQuery ) throws Exception {
        Query<? extends CatalogEntry> query = uow.query( entryClass() );
        
        // baseFilter
        query = query.where( baseFilter != null ? baseFilter : Expressions.TRUE );
        
        // all
        if (Strings.isNullOrEmpty( luceneQuery )) {
            return query.execute();
        }

        // search/query
        Iterable<JSONObject> indexResults = index().search( luceneQuery, -1 );
        
        List<BooleanExpression> ids = new ArrayList( 256 );
        for (JSONObject record : indexResults) {
            if (record.optString( FulltextIndex.FIELD_ID ).length() > 0) {
                ids.add( Expressions.id( record.getString( FulltextIndex.FIELD_ID ) ) );
            }
            else {
                log.warn( "No FIELD_ID in record: " + record );
            }
        }

        if (ids.isEmpty()) {
            query = query.andWhere( Expressions.FALSE );
        }
        else if (ids.size() == 1) {
            query = query.andWhere( ids.get( 0 ) );
        }
        else if (ids.size() == 2) {
            query = query.andWhere( or( ids.get( 0 ), ids.get( 1 ) ) );
        }
        else {
            BooleanExpression[] more = ids.subList( 2, ids.size() ).toArray( new BooleanExpression[ids.size()-2] );
            query = query.andWhere( or( ids.get( 0 ), ids.get( 1 ), more ) );
        }
        return query.execute();
    }

    
    public CatalogEntry createEntry( UnitOfWork uow, String id ) {
        try {
            CatalogEntry result = uow.createEntity( entryClass(), id, (proto) -> {
                CatalogEntry.defaults.initialize( proto );

                String username = SecurityContext.instance().getUser().getName();
                ProjectUnitOfWork puow = ProjectRepository.session();
                Organization org = puow.findOrganization( username ).get();
                proto.vendorId.set( (String)org.id() );
                proto.vendor.set( org.name.get() );
                proto.vendorUrl.set( org.website.get() );
                
                return proto;
            });
            uow.commit();
            return result;
        }
        catch (RuntimeException e) {
            uow.rollback();
            throw e;
        }
    }
    
    
    public void revokeEntry( UnitOfWork uow, CatalogEntry entry, boolean revoked ) {
        try {
            //uow.removeEntity( entry );
            entry.isRevoked.set( revoked );
            uow.commit();
        }
        catch (RuntimeException e) {
            uow.rollback();
            throw e;
        }
    }
    
    
    public void releaseEntry( UnitOfWork uow, CatalogEntry entry, boolean released ) {
        try {
            entry.isReleased.set( released );
            uow.commit();
        }
        catch (RuntimeException e) {
            uow.rollback();
            throw e;
        }
    }
    
    
    public File entryItems( CatalogEntry entry ) {
        File result = new File( fileRepo(), entry.id() );
        result.mkdir();
        return result;
    }
    
}
