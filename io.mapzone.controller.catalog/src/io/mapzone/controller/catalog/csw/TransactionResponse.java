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

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.controller.catalog.CatalogPlugin;
import io.mapzone.controller.catalog.model.CatalogEntry;
import net.opengis.cat.csw.v_2_0_2.InsertType;
import net.opengis.cat.csw.v_2_0_2.SummaryRecordType;
import net.opengis.cat.csw.v_2_0_2.TransactionType;

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
            // check origin
            log.info( "Remote:" + request().httpRequest().getRemoteHost() );
            
            TransactionType tx = request().<TransactionType>parsedBody().get();

            for (Object op : tx.getInsertOrUpdateOrDelete()) {
                if (op instanceof InsertType) {
                    handleInsert( (InsertType)op, nested );    
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
            entry.title.set( first( record.getTitle() ).get() );
            entry.description.set( first2( record.getAbstract() ).orElse( null ) );
        }
    }
    
}
