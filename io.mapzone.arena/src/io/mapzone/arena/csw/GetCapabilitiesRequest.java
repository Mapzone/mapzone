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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.opengis.cat.csw.v_2_0_2.CapabilitiesType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class GetCapabilitiesRequest
        extends CswRequest<CapabilitiesType> {

    private static Log log = LogFactory.getLog( GetCapabilitiesRequest.class );
    
    public GetCapabilitiesRequest() {
        super();
        request.set( "GetCapabilities" );
    }


    @Override
    public CapabilitiesType execute( IProgressMonitor monitor ) throws Exception {
        String url = baseUrl.get() + encodeRequestParams( assembleParams() );
        return read( CapabilitiesType.class, url );
    }

    
    /**
     * Test.
     */
    public static final void main( String[] args ) throws Exception {
        GetCapabilitiesRequest getCapabilities = new GetCapabilitiesRequest()
                .baseUrl.put( "http://www.geokatalog-mittelsachsen.de/geonetwork2.10/srv/eng/csw" );
        
        CapabilitiesType capabilities = getCapabilities.execute( new NullProgressMonitor() );
        System.out.println( capabilities.toString() );
        
//        JAXBContext context = JAXBContext.newInstance( "net.opengis.cat.csw.v_2_0_2" );
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        
//        // GetCapabilities URL of the Demis WorldMap WMS Server
//        String url = "http://www.geokatalog-mittelsachsen.de/geonetwork2.10/srv/eng/csw?"
//                + "REQUEST=GetCapabilities&SERVICE=CSW";
//        
//        JAXBElement<CapabilitiesType> elm = unmarshaller.unmarshal( new StreamSource( url ), CapabilitiesType.class );
//        
//        CapabilitiesType capabilities = elm.getValue();
//        System.out.println( capabilities.toString() );
    }
    
}
