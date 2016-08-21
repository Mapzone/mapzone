package io.mapzone.controller.ui.util;

import java.util.Collection;
import java.util.Map;

import org.geotools.feature.NameImpl;
import org.geotools.feature.type.AttributeDescriptorImpl;
import org.geotools.feature.type.AttributeTypeImpl;
import org.opengis.feature.Property;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.Name;
import org.opengis.feature.type.PropertyDescriptor;
import org.opengis.feature.type.PropertyType;

import org.polymap.model2.CollectionProperty;
import org.polymap.model2.runtime.PropertyInfo;

/**
 * Adapts {@link org.polymap.model2.Property} or {@link CollectionProperty} to
 * {@link org.opengis.feature.Property} to be used by forms.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class PropertyAdapter
        implements Property {

    public static PropertyDescriptor descriptorFor( org.polymap.model2.Property prop ) {
        PropertyInfo info = prop.info();
        NameImpl name = new NameImpl( info.getName() );
        AttributeType type = new AttributeTypeImpl( name, info.getType(), true, false, null, null, null );
        return new AttributeDescriptorImpl( type, name, 1, 1, false, null );
    }


    public static PropertyDescriptor descriptorFor( String _name, Class binding ) {
        NameImpl name = new NameImpl( _name );
        AttributeType type = new AttributeTypeImpl( name, binding, true, false, null, null, null );
        return new AttributeDescriptorImpl( type, name, 1, 1, false, null );
    }

    
    // instance *******************************************
    
    private org.polymap.model2.PropertyBase delegate;
    

    public PropertyAdapter( org.polymap.model2.PropertyBase delegate ) {
        assert delegate != null;
        this.delegate = delegate;
    }

    @Override
    public Object getValue() {
        if (delegate instanceof org.polymap.model2.Property) {
            return ((org.polymap.model2.Property)delegate).get();
        }
        else if (delegate instanceof CollectionProperty) {
            return (CollectionProperty)delegate;
        }
        else {
            throw new UnsupportedOperationException( "unsupported: " + delegate );
        }
    }

    @Override
    public void setValue( Object newValue ) {
        if (delegate instanceof org.polymap.model2.Property) {
            if (newValue != null 
                    && !delegate.info().getType().isAssignableFrom( newValue.getClass() )) {
                throw new ClassCastException( "Wrong value for Property of type '" + delegate.info().getType() + "': " + newValue.getClass() );
            }
            ((org.polymap.model2.Property)delegate).set( newValue );
        }
        else if (delegate instanceof CollectionProperty) {
            CollectionProperty coll = (CollectionProperty)delegate;
            coll.clear();
            ((Collection)newValue).forEach( v -> coll.add( v ) );
        }
        else {
            throw new UnsupportedOperationException( "unsupported: " + delegate );
        }
    }

    @Override
    public PropertyType getType() {
        return new AttributeTypeImpl( getName(), delegate.info().getType(), false, false, null, null, null );
    }

    @Override
    public PropertyDescriptor getDescriptor() {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    @Override
    public Name getName() {
        return new NameImpl( delegate.info().getName() );
    }

    @Override
    public boolean isNillable() {
        return delegate.info().isNullable();
    }

    @Override
    public Map<Object, Object> getUserData() {
        throw new RuntimeException( "not yet implemented." );
    }
    
}
