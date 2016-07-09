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

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

import com.vividsolutions.jts.geom.Point;

import io.mapzone.arena.geocode.Address;
import io.mapzone.arena.geocode.GeocodeQuery;

/**
 * @author Steffen Stundzig
 */
public class HereGeocodeServiceTest {

    protected static HereGeocodeService service;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        service = new HereGeocodeService();
        service.baseUrl.set( "geocoder.cit.api.here.com" );
        service.appId.set( "xcN9xaVYVUGlEa3FEoaY" );
        service.appCode.set( "QRkyvORJqAxkEBLWqN4RKw" );
    }


    // instance *******************************************

    @Test
    public void lindhardtByText() throws Exception {
        GeocodeQuery query = new GeocodeQuery();
        query.text.set( "04683 Lindhardt Karl Liebknecht 10" );
        List<Address> result = service.geocode( query );
        assertEquals( 1, result.size() );
        Address one = result.get( 0 );
        assertEquals( "Karl-Liebknecht-Straße 10, 04683 Naunhof, Deutschland", one.label );
        assertEquals( "Naunhof", one.city );
        assertEquals( "04683", one.postalCode );
        assertEquals( "Karl-Liebknecht-Straße", one.street );
        assertEquals( "10", one.houseNumber );
        Point position = (Point)one.position;
        assertEquals( 12.59345d, position.getCoordinate().x, 0.0d );
        assertEquals( 51.25662d, position.getCoordinate().y, 0.0d );
        
        
    }


    @Test
    public void lindhardtByParameters() throws Exception {
        GeocodeQuery query = new GeocodeQuery();
        query.houseNumber.set( "10" );
        query.street.set( "Karl Liebknecht St" );
        query.city.set( "Lindhardt" );
        query.country.set( Locale.GERMANY );
        query.targetLanguage.set( Locale.GERMAN );
        List<Address> result = service.geocode( query );
        assertEquals( 1, result.size() );
        Address one = result.get( 0 );
        assertEquals( "Karl-Liebknecht-Straße 10, 04683 Naunhof, Deutschland", one.label );
        assertEquals( "Naunhof", one.city );
        assertEquals( "04683", one.postalCode );
        assertEquals( "Karl-Liebknecht-Straße", one.street );
        assertEquals( "10", one.houseNumber );
        Point position = (Point)one.position;
        assertEquals( 12.59345d, position.getCoordinate().x, 0.0d );
        assertEquals( 51.25662d, position.getCoordinate().y, 0.0d );
    }
}
