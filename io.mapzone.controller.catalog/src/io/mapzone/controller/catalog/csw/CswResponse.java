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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.mapzone.controller.catalog.http.CatalogRequest;
import io.mapzone.controller.catalog.http.Response;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class CswResponse
        extends Response {

    private static Log log = LogFactory.getLog( CswResponse.class );
    
    public static final String      DEFAULT_XML_ENCODING = "UTF8";
    
    private XMLStreamWriter         writer;
    
    
    protected abstract void doExecute() throws Exception;

    
    @Override
    public void execute( CatalogRequest request ) throws IOException, ServletException {
        super.execute( request );
        try {
            out().writeStartDocument( DEFAULT_XML_ENCODING, "1.0" );
            doExecute();
            out().writeEndDocument();
            out().flush();
            request.flushResponse();
        }
        catch (Exception e) {
            // XXX handle error: HTTP code, response encode
            throw new RuntimeException( e );        
        }
    }
    
    
    protected XMLStreamWriter out() throws XMLStreamException, FactoryConfigurationError, IOException {
        if (writer == null) {
            writer = XMLOutputFactory.newInstance().createXMLStreamWriter( 
                    request().httpResponse().getOutputStream(), "UTF8" );
            //writer = new IndentingXMLStreamWriter( writer );
        }
        return writer;        
    }
    
}
