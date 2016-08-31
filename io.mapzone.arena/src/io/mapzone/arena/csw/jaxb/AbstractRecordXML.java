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

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;

import io.mapzone.arena.csw.Namespaces;

/**
 * ...
 * <p/>
 * This does not strictly conform to CSW XSD but it is easier to have base fields
 * here instead of having copies in {@link SummaryRecordXML} and
 * {@link DCMIRecordXML}.
 *
 * @author Falko Bräutigam
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlSeeAlso({
    SummaryRecordXML.class,
    DCMIRecordXML.class
})
public class AbstractRecordXML {

    @XmlElement( namespace=Namespaces.DC )
    public String               identifier;
    
    @XmlElement( namespace=Namespaces.DC )
    public String               title;
    
    @XmlElement( namespace=Namespaces.DC )
    public String               type;
    
    @XmlElement( namespace=Namespaces.DC )
    public Set<String>          format = new TreeSet();
    
    @XmlElement( namespace=Namespaces.DC )
    public Set<String>          subject = new TreeSet();
    
    @XmlElement( namespace=Namespaces.DCT )
    @XmlSchemaType( name="dateTime" )
    public XMLGregorianCalendar modified;

    @XmlElement( namespace=Namespaces.DCT )
    public List<String>         spatial;
    

//    @XmlElementRef(name = "title", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class)
//    protected List<JAXBElement<SimpleLiteral>> title;
    
//    @XmlElement(namespace = "http://purl.org/dc/elements/1.1/")
//    protected SimpleLiteral type;
    
//    @XmlElement(namespace = "http://purl.org/dc/elements/1.1/")
//    protected List<SimpleLiteral> subject;
    
//    @XmlElementRef(name = "format", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class)
//    protected List<JAXBElement<SimpleLiteral>> format;
    
//    @XmlElementRef(name = "relation", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class)
//    protected List<JAXBElement<SimpleLiteral>> relation;
    
//    @XmlElement(namespace = "http://purl.org/dc/terms/")
//    protected List<SimpleLiteral> modified;
    
}
