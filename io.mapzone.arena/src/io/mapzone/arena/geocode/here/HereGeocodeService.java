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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.geotools.factory.Hints;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.config.DefaultString;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.arena.geocode.Address;
import io.mapzone.arena.geocode.GeocodeQuery;
import io.mapzone.arena.geocode.GeocodeService;

/**
 * @author Steffen Stundzig
 */
public class HereGeocodeService
        extends Configurable
        implements GeocodeService {

    private static Log                   log             = LogFactory.getLog( HereGeocodeService.class );

    private final static GeometryFactory GEOMETRYFACTORY = JTSFactoryFinder.getGeometryFactory( new Hints( Hints.JTS_SRID, 4326 ) );

    @Mandatory
    @Immutable
    public Config<String>                appId;

    @Mandatory
    @Immutable
    public Config<String>                appCode;

    @Mandatory
    @Immutable
    @DefaultString( value = "geocoder.api.here.com" )
    public Config<String>                baseUrl;


    public HereGeocodeService() {
    }


    public HereGeocodeService( final String appId, final String appCode ) {
        if (StringUtils.isBlank( appId ) || StringUtils.isBlank( appCode )) {
            throw new IllegalStateException( "appId and appCode must not be null or empty" );
        }
        this.appId.set( appId );
        this.appCode.set( appCode );
    }


    @Override
    public List<Address> geocode( final GeocodeQuery query ) {
        final List<Address> results = Lists.newArrayList();
        CloseableHttpResponse response = null;
        try {
            final CloseableHttpClient httpClient = HttpClients.createDefault();
            final HttpGet httpGet = new HttpGet( createUrl( query ) );
            response = httpClient.execute( httpGet );

            final String content = EntityUtils.toString( response.getEntity(), Charsets.UTF_8 );
            JSONObject json = new JSONObject( content );
            JSONObject jsonResponse = json.optJSONObject( "response" );
            if (jsonResponse != null) {
                JSONArray jsonViews = jsonResponse.optJSONArray( "view" );
                if (jsonViews != null) {
                    for (int i = 0; i < jsonViews.length(); i++) {
                        JSONObject jsonView = jsonViews.optJSONObject( i );
                        JSONArray jsonResults = jsonView.optJSONArray( "result" );
                        if (jsonResults != null) {
                            for (int j = 0; j < jsonResults.length(); j++) {
                                JSONObject jsonResult = jsonResults.optJSONObject( j );
                                results.add( transform( jsonResult ) );
                            }
                        }
                    }
                }
            }
        }
        catch (URISyntaxException | IOException e) {
            throw new RuntimeException( e );
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                }
                catch (IOException e) {
                    throw new RuntimeException( e );
                }
            }
        }
        return results;
    }


    protected Address transform( final JSONObject jsonResult ) {
        final Address address = new Address();
        final JSONObject jsonLocation = jsonResult.optJSONObject( "location" );
        if (jsonLocation != null) {
            address.id = jsonLocation.optString( "locationId" );
            // geometry
            final JSONObject jsonGeometry = jsonLocation.optJSONObject( "displayPosition" );
            if (jsonGeometry != null) {
                final Double latitude = jsonGeometry.optDouble( "latitude" );
                final Double longitude = jsonGeometry.optDouble( "longitude" );
                if (latitude != null && longitude != null) {
                    final Point point = GEOMETRYFACTORY.createPoint( new Coordinate( longitude.doubleValue(), latitude.doubleValue() ) );
                    address.position = point;
                }
            }
            // details
            final JSONObject jsonAddress = jsonLocation.optJSONObject( "address" );
            if (jsonAddress != null) {
                address.label = jsonAddress.optString( "label" );
                address.country = jsonAddress.optString( "country" );
                address.state = jsonAddress.optString( "state" );
                address.county = jsonAddress.optString( "county" );
                address.city = jsonAddress.optString( "city" );
                address.postalCode = jsonAddress.optString( "postalCode" );
                address.district = jsonAddress.optString( "district" );
                address.street = jsonAddress.optString( "street" );
                address.houseNumber = jsonAddress.optString( "houseNumber" );
            }
        }
        return address;
    }


    protected URI createUrl( GeocodeQuery query ) throws URISyntaxException {
        URIBuilder builder = new URIBuilder().setScheme( "https" ).setHost( baseUrl.get() ).setPath( "/6.2/geocode.json" ).setParameter( "app_code", appCode.get() ).setParameter( "app_id", appId.get() ).setParameter( "jsonattributes", "1" ).setParameter( "gen", "9" );
        if (query.country.isPresent()) {
            builder.setParameter( "country", query.country.get().getISO3Country() );
        }
        if (query.targetLanguage.isPresent()) {
            builder.setParameter( "language", query.targetLanguage.get().toLanguageTag() );
        }
        builder.setParameter( "maxresults", String.valueOf( query.maxResults.get() ) );
        if (query.text.isPresent()) {
            builder.setParameter( "searchtext", query.text.get() );
        }
        else {
            final StringBuilder text = new StringBuilder();
            if (query.postalCode.isPresent()) {
                text.append( query.postalCode.get() ).append( " " );
            }
            if (query.city.isPresent()) {
                text.append( query.city.get() ).append( " " );
            }
            if (query.street.isPresent()) {
                text.append( query.street.get() ).append( " " );
            }
            if (query.houseNumber.isPresent()) {
                text.append( query.houseNumber.get() ).append( " " );
            }
            builder.setParameter( "searchtext", text.toString() );
        }
        return builder.build();
    }
}
