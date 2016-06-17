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

import java.util.Optional;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class Param<T> {

    public abstract String name();

    public abstract String description();
    
    public abstract Optional<T> opt( CatalogRequest request );
    
    public T get( CatalogRequest request ) throws MalformedRequestException {
        return opt( request ).orElseThrow( () -> new MalformedRequestException( "Request parameter missing: " + name() ) );
    }
    

    /**
     * 
     */
    public static class SimpleValueParam
            extends Param<String> {
        
        private String          name;
        
        private String          description;

        public SimpleValueParam( String name, String description ) {
            assert name != null && description != null;
            this.name = name;
            this.description = description;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public String description() {
            return description;
        }

        @Override
        public Optional<String> opt( CatalogRequest request ) {
            String value = request.httpRequest().getParameter( name() );
            return Optional.ofNullable( value );
        }
    }
    
}
