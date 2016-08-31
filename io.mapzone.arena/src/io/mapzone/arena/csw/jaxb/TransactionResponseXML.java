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
package io.mapzone.arena.csw.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 * @author Falko Bräutigam
 */
@XmlRootElement( name="TransactionResponse" )
@XmlAccessorType( XmlAccessType.FIELD )
public class TransactionResponseXML {

    @XmlElement( name="TransactionSummary", required=true )
    public TransactionSummaryXML    summary;
    
//    @XmlElement( name="InsertResult")
//    protected List<InsertResultType>    insertResult;
    
    @XmlAttribute( name="version" )
    public String                   version;
}
