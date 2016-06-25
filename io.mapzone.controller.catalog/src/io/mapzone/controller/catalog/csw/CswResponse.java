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

import java.util.List;
import java.util.Optional;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.runtime.CachedLazyInit;
import org.polymap.core.runtime.Lazy;

import io.mapzone.controller.catalog.http.CatalogRequest;
import io.mapzone.controller.catalog.http.Response;
import javanet.staxutils.IndentingXMLStreamWriter;
import net.opengis.cat.csw.v_2_0_2.dc.elements.ObjectFactory;
import net.opengis.cat.csw.v_2_0_2.dc.elements.SimpleLiteral;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class CswResponse
        extends Response {

    private static Log log = LogFactory.getLog( CswResponse.class );
    
    public static final ObjectFactory       JAXBF = new ObjectFactory();
    
    public static final String              CSW_JAXB_CONTEXT_PATH = "net.opengis.cat.csw.v_2_0_2";

    public static final String              DEFAULT_XML_ENCODING = "UTF-8";
    
    public static final DateFormat          DF = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSZ" );
    
    public static final Lazy<JAXBContext>   jaxbContext;
    
    static {
        jaxbContext = new CachedLazyInit( () -> {
            try {
                return JAXBContext.newInstance( CSW_JAXB_CONTEXT_PATH/*, CswRequest.class.getClassLoader()*/ );
            }
            catch (Exception e) {
                throw new RuntimeException( e );
            }
        });
    }
    
    // instance *******************************************

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

    
    protected <T> T readObject( InputStream in, Class<T> type ) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.get().createUnmarshaller();
        JAXBElement<T> elm = unmarshaller.unmarshal( new StreamSource( in ), type );
        return elm.getValue();
    }

    
    protected Optional<String> get( SimpleLiteral literal ) {
        List<String> content = literal.getContent();
        return Optional.ofNullable( !content.isEmpty() ? literal.getContent().get( 0 ) : null );
    }
    
    protected Optional<String> first( List<JAXBElement<SimpleLiteral>> elms ) {
        return !elms.isEmpty() ? get( elms.get( 0 ).getValue() ) : Optional.empty();
    }
    
    protected Optional<String> first2( List<SimpleLiteral> elms ) {
        return !elms.isEmpty() ? get( elms.get( 0 ) ) : Optional.empty();
    }

    
    protected XMLStreamWriter out() throws XMLStreamException, FactoryConfigurationError, IOException {
        if (writer == null) {
            writer = XMLOutputFactory.newInstance().createXMLStreamWriter( 
                    request().httpResponse().getOutputStream(), "UTF8" );
            writer = new IndentingXMLStreamWriter( writer );
        }
        return writer;        
    }
    
}
