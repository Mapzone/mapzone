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
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Sets;

import org.polymap.model2.CollectionProperty;
import org.polymap.model2.Property;
import org.polymap.model2.runtime.CompositeStateVisitor;

import io.mapzone.controller.catalog.model.XML;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class CompositeWriter
        extends CompositeStateVisitor<XMLStreamException> {

    private static Log log = LogFactory.getLog( CompositeWriter.class );

    private XMLStreamWriter         out;
    
    /** The XML prefixes (see {@link XML}) to write out. */
    private Set<String>             namespaces;
    
    
    public CompositeWriter( XMLStreamWriter out, String... namespaces ) {
        this.out = out;
        this.namespaces = Sets.newHashSet( namespaces );
    }

    
    public XMLStreamWriter out() {
        return out;
    }

    
    @Override
    protected void visitProperty( Property prop ) throws XMLStreamException {
        XML xml = (XML)prop.info().getAnnotation( XML.class );
        if (xml != null && namespaces.contains( xml.namespace() )) {
            String name = xml.value().equals( XML.DEFAULT ) ? prop.info().getName() : xml.value();
            out().writeStartElement( xml.namespace(), name );
            out().writeCharacters( encodedValue( prop.get() ) );
            out().writeEndElement();
        }
    }
    
    
    @Override
    protected void visitCollectionProperty( CollectionProperty prop ) throws XMLStreamException {
        XML xml = (XML)prop.info().getAnnotation( XML.class );
        if (xml != null && namespaces.contains( xml.namespace() )) {
            String name = xml.value().equals( XML.DEFAULT ) ? prop.info().getName() : xml.value();
            for (Object value : prop) {
                out().writeStartElement( xml.namespace(), name );
                out().writeCharacters( encodedValue( value ) );
                out().writeEndElement();
            }
        }
    }


    protected String encodedValue( Object value ) {
        if (value == null) {
            return "";
        }
        else if (value instanceof String) {
            return (String)value;
        }
        else if (value instanceof Date) {
            return CswResponse.DF.format( value );
        }
        else {
            throw new UnsupportedOperationException( "Unknown value type: " + value );
        }
    }
    
}
