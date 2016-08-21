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
package io.mapzone.controller.ui.project;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import org.polymap.rhei.field.IFormFieldValidator;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class KeywordsValidator
        implements IFormFieldValidator<String,Collection<String>> {

    private static final Log log = LogFactory.getLog( KeywordsValidator.class );

    @Override
    public String validate( String value ) {
        return null;
    }

    @Override
    public String transform2Field( Collection<String> modelValue ) throws Exception {
        return modelValue != null 
                ? Joiner.on( ", " ).join( modelValue )
                : "";
    }

    @Override
    public Collection<String> transform2Model( String fieldValue ) throws Exception {
        return fieldValue != null
                ? Splitter.on( ',' ).omitEmptyStrings().trimResults().splitToList( fieldValue )
                : null;
    }
    
}
