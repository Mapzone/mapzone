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
package io.mapzone.controller.catalog;

import org.apache.commons.lang3.StringUtils;

import org.polymap.rhei.fulltext.model2.DuplicateHandler;
import org.polymap.rhei.fulltext.model2.EntityFeatureTransformer;

import org.polymap.model2.Property;
import org.polymap.model2.Queryable;
import org.polymap.model2.runtime.PropertyInfo;

import io.mapzone.controller.catalog.model.OGCQueryable;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class CatalogEntryFulltextTransformer 
        extends EntityFeatureTransformer {

    public CatalogEntryFulltextTransformer() {
        honorQueryableAnnotation.set( true );
        duplicateHandler.set( DuplicateHandler.CONCAT );
        fieldNameProvider.set( prop -> {
            OGCQueryable a = (OGCQueryable)prop.info().getAnnotation( OGCQueryable.class );
            return a != null
                ? a.value()
                : StringUtils.capitalize( prop.info().getName() );
        });
    }

    
    /**
     * Index only {@link Queryable} properties of type {@link String} or Enum.
     */
    @Override
    protected void visitProperty( Property prop ) {
        PropertyInfo info = prop.info();
        if (honorQueryableAnnotation.get() && !info.isQueryable()) {
            return;
        }
        Object value = prop.get();
        if (value == null) {
        }
        else if (value instanceof String) {
            putValue( prop, (String)value );
        }
        else if (value.getClass().isEnum()) {
            putValue( prop, value.toString() );
        }
    }
    
}
