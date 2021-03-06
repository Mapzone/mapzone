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

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import org.polymap.core.runtime.Lazy;
import org.polymap.core.runtime.PlainLazyInit;
import org.polymap.core.runtime.config.Config;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
abstract class ContextFactory {
    
    /**
     * Injects {@link Config} instances into the given object.
     */
    public <T> T inject( T instance ) {
        try {
            // init properties
            for (Class cl = instance.getClass(); cl != null; cl = cl.getSuperclass()) {
                for (Field f : cl.getDeclaredFields()) {
                    if (Context.class.isAssignableFrom( f.getType() )) {
                        f.setAccessible( true );
                        assert f.get( instance ) == null;
                        f.set( instance, new ContextImpl( f ) );
                    }
                }
            }
            return instance;
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
    }

    // instance *******************************************
    
    protected abstract void setValue( Class type, String scope, Object value );

    protected abstract Object getValue( Class type, String scope );

    
    /**
     * The default {@link Context} implementation.
     * <p/>
     * Impl. note: As provisioning runs in one thread there is no synchronization in
     * order to make check/set operations atomically.
     */
    private class ContextImpl<V>
            implements Context<V> {
    
        private Field                   f;
        
        private Lazy<Class>             type;

        private Lazy<String>            scope;
        
        protected ContextImpl( Field f ) {
            this.f = f;
            type = new PlainLazyInit( () -> ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0] );
            scope = new PlainLazyInit( () -> {
                Scope a = f.getAnnotation( Scope.class );
                return a != null ? a.value() : "";
            });
        }
        
        @Override
        public String toString() {
            return "Context[" + get() + "]";
        }

        @Override
        public Class<V> getDeclaredType() {
            return type.get();
        }

        @Override
        public String getScope() {
            return scope.get();
        }

        @Override
        public void set( V newValue ) {
            setValue( type.get(), scope.get(), newValue );
        }
    
        @Override
        public boolean compareAndSet( V expect, V update ) {
            throw new RuntimeException( "not yet implemented." );
        }

        @Override
        public V get() {
            return (V)getValue( type.get(), scope.get() );
        }
        
        @Override
        public V get( Supplier<V> supplier ) {
            if (!isPresent()) {
                set( supplier.get() );
            }
            return get();
        }

        @Override
        public boolean isPresent() {
            return get() != null;
        }

        @Override
        public void ifPresent( Consumer<V> consumer ) {
            if (isPresent()) {
                consumer.accept( get() );
            }
        }

        @Override
        public V orElse( V other ) {
            return isPresent() ? get() : other;
        }

        @Override
        public V orElse( Supplier<V> supplier ) {
            return isPresent() ? get() : supplier.get();
        }

        @Override
        public <U> Optional<U> map( Function<? super V,? extends U> mapper ) {
            assert mapper != null;
            return isPresent() 
                    ? Optional.ofNullable( mapper.apply( get() ) )
                    : Optional.empty();
        }
    }
    
}
