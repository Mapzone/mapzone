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
import org.polymap.model2.Entity;
import org.polymap.model2.ManyAssociation;
import org.polymap.model2.Property;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class Entity2JsonTransformer
        extends Transformer {

    private static final Log log = LogFactory.getLog( Entity2JsonTransformer.class );
 
    public static JSONObject transform( Composite c ) throws Exception {
        Entity2JsonTransformer transformer = new Entity2JsonTransformer( new JSONObject() );
        transformer.process( c );
        if (c instanceof Entity) {
            transformer.result.putOnce( "id", ((Entity)c).id() );
        }
        return transformer.result;
    }
    
    // instance *******************************************
    
    protected JSONObject        result;

    
    protected Entity2JsonTransformer( JSONObject result ) {
        this.result = result;
    }
    
    @Override
    protected void visitProperty( Property prop ) throws Exception {
        if (isAnnotated( prop.info() )) {
            String propName = propName( prop.info() );
            if (!prop.opt().isPresent()) {
                result.putOnce( propName, JSONObject.NULL );
            }
            else if (String.class.isAssignableFrom( prop.info().getType() )
                    || Float.class.isAssignableFrom( prop.info().getType() )
                    || Boolean.class.isAssignableFrom( prop.info().getType() )) {
                result.putOnce( propName, prop.get() );                
            }
            else if (Date.class.isAssignableFrom( prop.info().getType() )) {
                result.putOnce( propName, ((Date)prop.get()).getTime() );                
            }
            else {
                throw new RuntimeException( "Unhandled property type: " + prop.info() );
            }
        }
    }

    @Override
    protected boolean visitCompositeProperty( Property prop ) throws Exception {
        if (isAnnotated( prop.info() ) && prop.opt().isPresent()) {
            JSONObject child = new JSONObject();
            new Entity2JsonTransformer( child ).process( (Composite)prop.get() );
            result.put( propName( prop.info() ), child );
        }
        return false;
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
