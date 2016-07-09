/*
 * polymap.org Copyright (C) 2016, the @authors. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package io.mapzone.arena.geocode.here;

import java.util.List;
import java.util.Set;

import java.awt.RenderingHints.Key;
import java.io.IOException;

import org.geotools.data.DataAccess;
import org.geotools.data.FeatureListener;
import org.geotools.data.FeatureReader;
import org.geotools.data.Query;
import org.geotools.data.QueryCapabilities;
import org.geotools.data.ResourceInfo;
import org.geotools.data.Transaction;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.Name;
import org.opengis.filter.Filter;
import org.opengis.filter.identity.FeatureId;

/**
 * FeatureStore Wrapper for the here geocode service.
 * 
 *
 * @author Steffen Stundzig
 */
public class HereFeatureStore
        implements SimpleFeatureStore {

    @Override
    public List<FeatureId> addFeatures( FeatureCollection<SimpleFeatureType,SimpleFeature> featureCollection )
            throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void removeFeatures( Filter filter ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void modifyFeatures( Name[] attributeNames, Object[] attributeValues, Filter filter ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void modifyFeatures( AttributeDescriptor[] type, Object[] value, Filter filter ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void modifyFeatures( Name attributeName, Object attributeValue, Filter filter ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void modifyFeatures( AttributeDescriptor type, Object value, Filter filter ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void setFeatures( FeatureReader<SimpleFeatureType,SimpleFeature> reader ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void setTransaction( Transaction transaction ) {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public Transaction getTransaction() {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public Name getName() {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public ResourceInfo getInfo() {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public DataAccess<SimpleFeatureType,SimpleFeature> getDataStore() {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public QueryCapabilities getQueryCapabilities() {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void addFeatureListener( FeatureListener listener ) {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void removeFeatureListener( FeatureListener listener ) {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public SimpleFeatureType getSchema() {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public ReferencedEnvelope getBounds() throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public ReferencedEnvelope getBounds( Query query ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public int getCount( Query query ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public Set<Key> getSupportedHints() {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void modifyFeatures( String name, Object attributeValue, Filter filter ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public void modifyFeatures( String[] names, Object[] attributeValues, Filter filter ) throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public SimpleFeatureCollection getFeatures() throws IOException {

        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public SimpleFeatureCollection getFeatures( Filter filter ) throws IOException {
        throw new RuntimeException( "not yet implemented." );
    }


    @Override
    public SimpleFeatureCollection getFeatures( Query query ) throws IOException {
        throw new RuntimeException( "not yet implemented." );
    }
}
