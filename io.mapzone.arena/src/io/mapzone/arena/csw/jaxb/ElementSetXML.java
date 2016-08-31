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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
@XmlEnum
@XmlType( name="ElementSet" )
public enum ElementSetXML {

    @XmlEnumValue("brief")
    BRIEF("brief"),
 
    @XmlEnumValue("summary")
    SUMMARY("summary"),
    
    @XmlEnumValue("full")
    FULL("full");
    
    private final String value;

    ElementSetXML(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}