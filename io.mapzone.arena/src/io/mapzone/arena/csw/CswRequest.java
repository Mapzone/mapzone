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

import java.util.HashMap;
import java.util.Map;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class CswRequest<R>
        extends CatalogRequest<R> {

    public static final String          CSW_JAXB_CONTEXT_PATH = "net.opengis.cat.csw.v_2_0_2";

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
    @RequestParam( "SERVICE" )
    public Config2<CswRequest,String>   service;
    
    /**
     * Inbound:
     */
    @Mandatory
    @DefaultString( "2.0.2" )
    @RequestParam( "VERSION" )
    public Config2<CswRequest,String>   version;
    
    /**
     * Inbound:
     */
    @Mandatory
    @Immutable
    @RequestParam( "REQUEST" )
    public Config2<CswRequest,String>   request;
    

    protected <T> T read( Class<T> type, String url ) throws Exception {
        JAXBContext context = JAXBContext.newInstance( CSW_JAXB_CONTEXT_PATH );
        Unmarshaller unmarshaller = context.createUnmarshaller();
        JAXBElement<T> elm = unmarshaller.unmarshal( new StreamSource( url ), type );
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
