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
import javax.xml.bind.annotation.XmlValue;

/**
 * 
 *
 * @author Falko Bräutigam
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class URIXML {

    @XmlAttribute
    public String               name;

    @XmlAttribute
    public String               protocol;

    @XmlAttribute
    public String               description;

    @XmlValue
    public String               value;

    @Override
    public String toString() {
        return "URIXML[name=" + name + ", protocol=" + protocol + ", description=" + description + ", value=" + value
                + "]";
    }
    
    
}
