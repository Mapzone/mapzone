/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
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
package io.mapzone.controller.webcat.webdav;

import java.util.Date;

import org.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.model2.Association;
import org.polymap.model2.CollectionProperty;
import org.polymap.model2.Composite;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Property;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class Json2EntityTransformer
        extends Transformer {

    private static final Log log = LogFactory.getLog( Json2EntityTransformer.class );

    public static void transform( JSONObject source, Composite entity ) throws Exception {
        new Json2EntityTransformer( source ).process( entity );
    }
    
    // instance *******************************************
    
    protected JSONObject        source;

    
    protected Json2EntityTransformer( JSONObject source ) {
        this.source = source;
    }

    @Override
    protected void visitProperty( Property prop ) throws Exception {
        if (isAnnotated( prop.info() )) {
            String propName = propName( prop.info() );
            Object value = source.opt( propName );
            
            if (value == null) {
            }
            else if (value == JSONObject.NULL) {
                prop.set( null );
            }
            else if (String.class.isAssignableFrom( prop.info().getType() )
                    || Float.class.isAssignableFrom( prop.info().getType() )
                    || Boolean.class.isAssignableFrom( prop.info().getType() )) {
                prop.set( value );                
            }
            else if (Date.class.isAssignableFrom( prop.info().getType() )) {
                prop.set( new Date( (Long)value ) );                
            }
            else {
                throw new RuntimeException( "Unhandled property type: " + prop.info() );
            }
        }
    }

    @Override
    protected boolean visitCompositeProperty( Property prop ) throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    protected void visitCollectionProperty( CollectionProperty prop ) throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    protected boolean visitCompositeCollectionProperty( CollectionProperty prop ) throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    protected void visitAssociation( Association prop ) throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    protected void visitManyAssociation( ManyAssociation prop ) throws Exception {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
