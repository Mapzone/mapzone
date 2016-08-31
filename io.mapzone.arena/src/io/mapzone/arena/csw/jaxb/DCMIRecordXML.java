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

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;

import io.mapzone.arena.csw.Namespaces;

/**
 * 
 *
 * @author Falko Bräutigam
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlSeeAlso( RecordXML.class )
public class DCMIRecordXML
        extends AbstractRecordXML {

    @XmlElement( namespace=Namespaces.DC )
    public String                   description;

    @XmlElement( namespace=Namespaces.DC )
    @XmlSchemaType( name="dateTime" )
    public XMLGregorianCalendar     created;

    @XmlElement( namespace=Namespaces.DC )
    public Set<String>              creator = new TreeSet();

    @XmlElement( namespace=Namespaces.DC )
    public Set<String>              rights = new TreeSet();

    @XmlElement( namespace=Namespaces.DC )
    public Set<String>              rightsHolder = new TreeSet();

    @XmlElement( namespace=Namespaces.DC )
    public Set<String>              publisher = new TreeSet();

    @XmlElement( namespace=Namespaces.DC )
    public Set<String>              contributor = new TreeSet();

    @XmlElement( namespace=Namespaces.DC )
    public Set<String>              accessRights = new TreeSet();

    @XmlElement( namespace=Namespaces.DC )
    public Set<String>              language = new TreeSet();

    @XmlElement( namespace=Namespaces.DC )
    @XmlSchemaType( name="dateTime" )
    public XMLGregorianCalendar     date;

    @XmlElement( namespace=Namespaces.DC )
    public List<URIXML>             URI = new ArrayList();

}
