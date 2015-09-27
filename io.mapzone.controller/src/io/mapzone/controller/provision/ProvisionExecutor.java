/* 
 * Copyright (C) 2015, the @authors. All rights reserved.
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
package io.mapzone.controller.provision;

import io.mapzone.controller.provision.Provision.Status;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Find a way to execute a given target {@link Provision}.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class ProvisionExecutor {

    protected ContextFactory                  contextFactory;

    protected Map<Pair<Class,String>,Object>  contextValues;
    
    protected Class<? extends Provision>[]    preliminaries;
    
    
    public ProvisionExecutor( Class<? extends Provision>[] preliminaries ) {
        this.preliminaries = preliminaries;
        
        contextValues = new HashMap();
        contextFactory = new ContextFactory() {
            @Override
            protected void setValue( Class type, String scope, Object value ) {
                contextValues.put( ImmutablePair.of( type, scope ), value ); 
            }
            @Override
            protected Object getValue( Class type, String scope ) {
                return contextValues.get( ImmutablePair.of( type, scope ) ); 
            }
        };
    }

    
    public Map<Pair<Class,String>,Object> getContextValues() {
        return contextValues;
    }
    
    
    public ProvisionExecutor setContextValues( Map<Pair<Class,String>,Object> contextValues ) {
        this.contextValues = new HashMap( contextValues );
        return this;
    }


    public <T extends Provision> T newProvision( Class<T> type ) throws Exception {
        T result = type.newInstance();
        contextFactory.inject( result );
        return result;
    }


    /**
     * Try to find a way to execute the given <code>target</code> together with the
     * given {@link #preliminaries}.
     */
    public abstract Status execute( Provision target ) throws Exception;

    
    /**
     * Execute this very single Provision. Allows to entercept algorithm.
     */
    protected Status executeProvision( Provision provision ) throws Exception {
        return provision.execute();
    }
    
}
