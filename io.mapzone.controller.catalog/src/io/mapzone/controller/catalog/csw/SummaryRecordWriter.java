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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.model2.Composite;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class SummaryRecordWriter
        extends CompositeWriter {

    private static Log log = LogFactory.getLog( SummaryRecordWriter.class );

    public SummaryRecordWriter( XMLStreamWriter out ) {
        super( out );
    }

    
    @Override
    public void process( Composite composite ) throws XMLStreamException {
        out().writeStartElement( "SummaryRecord" );
        out().writeDefaultNamespace( "http://www.opengis.net/cat/csw/2.0.2" );
        super.process( composite );
        out().writeEndElement();
    }
    
}
