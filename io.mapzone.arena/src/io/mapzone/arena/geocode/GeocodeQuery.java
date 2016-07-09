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
package io.mapzone.arena.geocode;

import java.util.Locale;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.config.DefaultInt;

/**
 * Wraps a list of query parameters.
 * 
 *
 * @author Steffen Stundzig
 */
public class GeocodeQuery
        extends Configurable {

    // full text search
    public Config<String>  text;

    public Config<String>  city;

    public Config<String>  street;

    public Config<String>  houseNumber;

    public Config<String>  postalCode;

    @DefaultInt( value = 10 )
    public Config<Integer> maxResults;

    public Config<Locale>  country;

    public Config<Locale>  targetLanguage;
}
