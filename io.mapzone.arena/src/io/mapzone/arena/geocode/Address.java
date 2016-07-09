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

import com.vividsolutions.jts.geom.Point;

/**
 * wraps a single address result
 * 
 * TODO should be a model2 composite/feature, but therefore i need support
 * 
 *
 * @author Steffen Stundzig
 */
public class Address
/* extends Composite */ {

    // public Property<Geometry> position;
    //
    // public Property<String> label;
    //
    // public Property<String> country;
    //
    // public Property<String> state;
    //
    // public Property<String> county;
    //
    // public Property<String> city;
    //
    // public Property<String> district;
    //
    // public Property<String> street;
    //
    // public Property<String> houseNumber;
    //
    // public Property<String> postalCode;
    public Point position;

    public String   id;

    public String   label;

    public String   country;

    public String   state;

    public String   county;

    public String   city;

    public String   district;

    public String   street;

    public String   houseNumber;

    public String   postalCode;
}