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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
@XmlRootElement( name="GetRecordsResponse" )
@XmlAccessorType( XmlAccessType.FIELD )
@XmlType( propOrder={"requestId","searchStatus","searchResults"} )
public class GetRecordsResponseXML {

    @XmlElement( name="RequestId" )
    @XmlSchemaType( name="anyURI" )
    public String               requestId;

    @XmlElement( name="SearchStatus", required=true )
    public RequestStatusXML     searchStatus;

    @XmlElement( name="SearchResults", required=true )
    public SearchResultsXML     searchResults;

//    @XmlAttribute( name = "version" )
//    protected String            version;

}