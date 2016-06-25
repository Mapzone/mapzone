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
package io.mapzone.arena.csw;

import static io.mapzone.arena.csw.Namespaces.CSW;
import static io.mapzone.arena.csw.Namespaces.OWS;
import static io.mapzone.arena.csw.Namespaces.XML;

import java.util.HashMap;
import java.util.Map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.runtime.CachedLazyInit;
import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;

import javanet.staxutils.IndentingXMLStreamWriter;
import net.opengis.cat.csw.v_2_0_2.ObjectFactory;

/**
 * 
 * @see <a href=
 *      "http://geonetwork-opensource.org/manuals/2.10.4/eng/developer/xml_services/csw_services.html">
 *      GeoNetwork Examples</a>
 * @author Falko Bräutigam
 */
public abstract class CswRequest<R>
        extends CatalogRequest<R> {

    private static final Log log = LogFactory.getLog( CswRequest.class );

    public static final ObjectFactory       JAXBF = new ObjectFactory();
    
    public static final String              CSW_JAXB_CONTEXT_PATH = "net.opengis.cat.csw.v_2_0_2";

    public static final String              DEFAULT_XML_ENCODING = "UTF-8";

    public static final DateFormat          DF = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSZ" );
    
    public static final Lazy<HttpClient>    httpClient;
    
    public static final Lazy<JAXBContext>   jaxbContext;
    
    static {
        httpClient = new CachedLazyInit( () -> {
           return new AutoCloseHttpClient( HttpClientBuilder.create().build() ); 
        });
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
    
    /**
     * Inbound:
     */
    @Mandatory
    public Config2<CswRequest,String>   baseUrl;
    
    /**
     * Inbound:
     */
    @Mandatory
    @DefaultString( "CSW" )
    @RequestParam( "Service" )
    @RequestAttr( "service" )
    public Config2<CswRequest,String>   service;
    
    /**
     * Inbound:
     */
    @Mandatory
    @DefaultString( "2.0.2" )
    @RequestParam( "Version" )
    @RequestAttr( "version" )
    public Config2<CswRequest,String>   version;
    
    /**
     * Inbound:
     */
    @Mandatory
    @Immutable
    @RequestParam( "Request" )
    public Config2<CswRequest,String>   request;

    protected ByteArrayOutputStream     buf;
    
    protected XMLStreamWriter           writer;
    

    protected abstract void prepare( IProgressMonitor monitor ) throws Exception;
    
    protected abstract R handleResponse( InputStream in, IProgressMonitor monitor ) throws Exception;
    
    
    @Override
    public R execute( IProgressMonitor monitor ) throws Exception {
        assert writer == null && buf == null;
        buf = new ByteArrayOutputStream( 4096 );
        writer = XMLOutputFactory.newInstance().createXMLStreamWriter( buf, DEFAULT_XML_ENCODING );
        writer = new IndentingXMLStreamWriter( writer );

        // write content
        out().writeStartDocument( DEFAULT_XML_ENCODING, "1.0" );
        writeRequest();
        prepare( monitor );
        out().writeEndElement();
        out().writeEndDocument();
        out().flush();

        String content = buf.toString( DEFAULT_XML_ENCODING );
        log.info( content );
        
        // execute POST
        // XXX streaming content would be cool, but probably waste of effort
        HttpPost post = new HttpPost( baseUrl.get() );
        StringEntity entity = new StringEntity( content, DEFAULT_XML_ENCODING );
        entity.setContentType( "application/xml" );
        post.setEntity( entity );
        HttpResponse response = httpClient.get().execute( post );
        
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() != 200) {
            throw new IOException( status.getReasonPhrase() + " (" + status.getStatusCode() + ")" );
        }
        // read response
        try (
            java.io.InputStream in = response.getEntity().getContent();
        ){
            System.out.print( "RESPONSE: " );
            TeeInputStream tee = new TeeInputStream( in, System.out );
            return handleResponse( tee, monitor );
        }
        finally {
            writer = null;
            buf = null;
        }
    }


    protected XMLStreamWriter out() {
        assert writer != null;
        return writer;        
    }

    
    /**
     * Write {@link #request} and namespace declarations.
     *
     * @throws XMLStreamException
     */
    protected void writeRequest() throws XMLStreamException {
        out().writeStartElement( "csw", request.get(), CSW  );
        writeAttributes( service, version );
        out().writeNamespace( "xml", XML );
        out().writeNamespace( "csw", CSW );
        out().writeNamespace( "ows", OWS );
    }

    
    @FunctionalInterface
    public interface Handler {
        public void handle() throws Exception;
    }
    
    
    protected void writeElement( String uri, String name, Handler hierarchyHandler ) throws Exception {
        out().writeStartElement( uri, name );
        hierarchyHandler.handle();
        out().writeEndElement();
    }
    
    
    protected void writeElement( Config prop, Handler hierarchyHandler ) throws Exception {
        RequestElement a = prop.info().getAnnotation( RequestElement.class );
        assert a != null : "No @RequestElement annotation on Config property: " + prop;
        out().writeStartElement( a.prefix(), a.value() );
        out().writeCharacters( prop.get().toString() );
        hierarchyHandler.handle();
        out().writeEndElement();
    }
    
    
    protected void writeAttributes( Config... props ) throws XMLStreamException {
        for (Config prop : props) {
            RequestAttr a = prop.info().getAnnotation( RequestAttr.class );
            assert a != null : "No @RequestAttr annotation on Config property: " + prop;
            out().writeAttribute( a.value(), prop.get().toString() );
        }
    }
    
    
    protected void writeObject( JAXBElement jaxb ) throws Exception {
        Marshaller marshaller = jaxbContext.get().createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
        marshaller.setProperty( Marshaller.JAXB_FRAGMENT, Boolean.TRUE );
        
        marshaller.marshal( jaxb, out() );
    }
    
    
////            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
////            DOMResult result = new DOMResult( doc );
////            marshaller.marshal( jaxb, result );
////            return (T)result.getNode().getFirstChild();
//            
////            StringWriter writer = new StringWriter( 4096 );
////            marshaller.marshal( jaxb, writer );
////
////            DocumentBuilder builder = DocumentBuilderFactory.newInstance( 
////                    "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl", getClass().getClassLoader() )
////                    .newDocumentBuilder();
////            Document doc = builder.parse( new InputSource( new StringReader( writer.toString() ) ) );
////            return (T)doc.getFirstChild();
    
    
    protected <T> T readObject( InputStream in, Class<T> type ) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.get().createUnmarshaller();
        JAXBElement<T> elm = unmarshaller.unmarshal( new StreamSource( in ), type );
        return elm.getValue();
    }

    
    protected String encodeRequestParams( Map<String,Object> params ) {
        try {
            StringBuilder result = new StringBuilder( 1024 );
            int count = 0;
            for (Map.Entry<String,Object> entry : params.entrySet() ) {
                result.append( count++==0 ? "?" : "&" );
                result.append( entry.getKey() )
                        .append( "=" )
                        .append( URLEncoder.encode( entry.getValue().toString(), "UTF-8" ) );
            };
            return result.toString();
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }
    }
    
    
    protected Map<String,Object> assembleParams() {
        Map<String,Object> result = new HashMap();
        
        // only public fields are allowed
        for (Field f : getClass().getFields()) {
            RequestParam a = f.getAnnotation( RequestParam.class );
            if (a != null) {
                try {
                    Object value = f.get( this );
                    if (value instanceof Config) {
                        result.put( a.value(), ((Config)value).get() );
                    }
                    else {
                        result.put( a.value(), value );                        
                    }
                }
                catch (RuntimeException e) {
                    throw e;
                }
                catch (Exception e) {
                    throw new RuntimeException( e );
                }
            }
        }
        return result;
    }
    
}
