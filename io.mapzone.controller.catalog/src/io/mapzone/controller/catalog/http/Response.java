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
package io.mapzone.controller.catalog.http;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 *  
 *
 * @author Falko Bräutigam
 */
public abstract class Response {
    
    private CatalogRequest          request;
    
    
    public void execute( @SuppressWarnings( "hiding" ) CatalogRequest request ) throws IOException, ServletException {
        assert request != null;
        this.request = request;
    }

    
    public CatalogRequest request() {
        return request;
    }
    
}
