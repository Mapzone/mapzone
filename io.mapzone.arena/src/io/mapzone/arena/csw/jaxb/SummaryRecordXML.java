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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.mapzone.arena.csw.Namespaces;

/**
 * 
 *
 * @author Falko Bräutigam
 */
@XmlRootElement( name="SummaryRecord" )
@XmlAccessorType( XmlAccessType.FIELD )
public class SummaryRecordXML
        extends AbstractRecordXML {

    @XmlElement( namespace=Namespaces.DCT, name="abstract" )
    public String               _abstract;
    
//  @XmlElement(name = "abstract", namespace = "http://purl.org/dc/terms/")
//  protected List<SimpleLiteral> _abstract;
  
//  @XmlElement(namespace = "http://purl.org/dc/terms/")
//  protected List<SimpleLiteral> spatial;
  
//  @XmlElementRef(name = "BoundingBox", namespace = "http://www.opengis.net/ows", type = JAXBElement.class)
//  protected List<JAXBElement<BoundingBoxType>> boundingBox;


}
