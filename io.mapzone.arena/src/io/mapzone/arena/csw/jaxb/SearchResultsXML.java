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

import java.util.LinkedList;
import java.util.List;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 
 *
 * @author Falko Bräutigam
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class SearchResultsXML {

//  @XmlElementRef( name="AbstractRecord", namespace="http://www.opengis.net/cat/csw/2.0.2", type=JAXBElement.class)
//  public List<JAXBElement<? extends AbstractRecordXML>> abstractRecord;
  
    @XmlElementRef( type=AbstractRecordXML.class )
    public List<? extends AbstractRecordXML> records = new LinkedList();

//    @XmlAnyElement( lax=true )
//    public List<Object>         any;
    
    @XmlAttribute
    @XmlSchemaType( name="anyURI" )
    public String                   resultSetId;
    
//    @XmlAttribute
//    public ElementSetType       elementSet;
    
    @XmlAttribute
    @XmlSchemaType( name="anyURI" )
    public String                   recordSchema;
    
    @XmlAttribute( required=true )
    @XmlSchemaType( name="nonNegativeInteger" )
    public BigInteger               numberOfRecordsMatched;
    
    @XmlAttribute( required=true )
    @XmlSchemaType( name="nonNegativeInteger" )
    public BigInteger               numberOfRecordsReturned;
    
    @XmlAttribute
    @XmlSchemaType( name="nonNegativeInteger")
    public BigInteger               nextRecord;
    
    @XmlAttribute
    @XmlSchemaType( name="dateTime" )
    public XMLGregorianCalendar     expires;

}
