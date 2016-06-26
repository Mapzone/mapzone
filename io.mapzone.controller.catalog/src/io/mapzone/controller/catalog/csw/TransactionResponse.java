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
package io.mapzone.controller.catalog.csw;

import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.model2.query.ResultSet;
import org.polymap.model2.query.grammar.BooleanExpression;
import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.controller.catalog.CatalogPlugin;
import io.mapzone.controller.catalog.model.CatalogEntry;
import net.opengis.cat.csw.v_2_0_2.DeleteType;
import net.opengis.cat.csw.v_2_0_2.InsertType;
import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;
import net.opengis.cat.csw.v_2_0_2.TransactionType;
import net.opengis.cat.csw.v_2_0_2.UpdateType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class TransactionResponse
        extends CswResponse {

    private static Log log = LogFactory.getLog( TransactionResponse.class );

    public static final String          REQUEST = "Transaction";

    
    @Override
    protected void doExecute() throws Exception {
        UnitOfWork uow = CatalogPlugin.instance().catalog().unitOfWork();
        try (
            UnitOfWork nested = uow.newUnitOfWork();
        ){
            // XXX check origin for authorisation
            // when fixed then also change the catalog server URL the controller
            // sends to clients via ArenaConfig
            log.info( "Remote:" + request().httpRequest().getRemoteHost() );
            if (!request().httpRequest().getRemoteHost().equals( "127.0.0.1" )) {
                throw new SecurityException( "Authorisation missing." );
            }
            
            TransactionType tx = request().<TransactionType>parsedBody().get();

            for (Object op : tx.getInsertOrUpdateOrDelete()) {
                if (op instanceof InsertType) {
                    handleInsert( (InsertType)op, nested );    
                }
                else if (op instanceof UpdateType) {
                    handleUpdate( (UpdateType)op, nested );    
                }
                else if (op instanceof DeleteType) {
                    handleDelete( (DeleteType)op, nested );    
                }
                else {
                    throw new RuntimeException( "Unhandled op type: " + op );
                }
            }
            nested.commit();
            uow.commit();
        }
    }

    
    protected void handleInsert( InsertType op, UnitOfWork uow ) throws JAXBException {
        for (Element any : op.getAny()) {
            Unmarshaller unmarshaller = jaxbContext.get().createUnmarshaller();
            SummaryRecordType record = unmarshaller.unmarshal( any, SummaryRecordType.class ).getValue();
            
            CatalogEntry entry = uow.createEntity( CatalogEntry.class, null, CatalogEntry.defaults );
            updateEntry( entry, record );
        }
    }
    
    
    protected void handleUpdate( UpdateType op, UnitOfWork uow ) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.get().createUnmarshaller();
        SummaryRecordType record = unmarshaller.unmarshal( op.getAny(), SummaryRecordType.class ).getValue();
            
        BooleanExpression expr = FilterParser.parse( op.getConstraint() );
        ResultSet<CatalogEntry> rs = uow.query( CatalogEntry.class ).where( expr ).maxResults( 2 ).execute();
        if (rs.size() == 0) {
            log.warn( "No entry found for: " + expr );
        }
        else if (rs.size() > 1) {
            log.warn( "Multiple entries found for: " + expr );            
        }
        else {
            updateEntry( rs.stream().findAny().get(), record );
        }
    }
    
    
    protected void handleDelete( DeleteType op, UnitOfWork uow ) throws JAXBException {
        BooleanExpression expr = FilterParser.parse( op.getConstraint() );
        ResultSet<CatalogEntry> rs = uow.query( CatalogEntry.class )
                .where( expr ).maxResults( 2 )
                .execute();
        if (rs.size() == 0) {
            log.warn( "No entry found for: " + expr );
        }
        else if (rs.size() > 1) {
            log.warn( "Multiple entries found for: " + expr );            
        }
        else {
            CatalogEntry entity = rs.stream().findAny().get();
            uow.removeEntity( entity );
        }
    }
    
    
    protected void updateEntry( CatalogEntry entry, SummaryRecordType record ) {
        first2( record.getModified() ).ifPresent( value -> entry.modified.set( new Date() ) );
        
        first( record.getIdentifier() ).ifPresent( value -> entry.identifier.set( value ) );
        first( record.getTitle() ).ifPresent( value -> entry.title.set( value ) );
        first2( record.getAbstract() ).ifPresent( value -> entry.description.set( value ) );        
        first( record.getFormat() ).ifPresent( value -> entry.format.set( value ) );
        if (record.getType() != null) {
            entry.type.set( record.getType().getContent().get( 0 ) );
        }
        entry.subject.clear();
        record.getSubject().stream()
                .map( l -> l.getContent().get( 0 ) )
                .forEach( s -> entry.subject.add( s ) );
        entry.spatial.clear();
        record.getSpatial().stream()
                .map( l -> l.getContent().get( 0 ) )
                .forEach( s -> entry.spatial.add( s ) );
    }
    
}
