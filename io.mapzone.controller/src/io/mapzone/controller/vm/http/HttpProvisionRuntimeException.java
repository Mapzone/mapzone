/* 
 * mapzone.io
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
package io.mapzone.controller.vm.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.mapzone.controller.provision.ProvisionRuntimeException;

/**
 * {@link ProvisionRuntimeException} for {@link HttpProxyProvision}s. The given
 * {@link #message} is displayed to the user via {@link ProvisionErrorResponse}.
 *
 * @author Falko Bräutigam
 */
public class HttpProvisionRuntimeException
        extends ProvisionRuntimeException {

    private static Log log = LogFactory.getLog( HttpProvisionRuntimeException.class );
    
    public int          statusCode;
    
    public String       message;

    
    public HttpProvisionRuntimeException( int statusCode, String message ) {
        this.statusCode = statusCode;
        this.message = message;
    }
    
}
