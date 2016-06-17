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
package io.mapzone.controller.catalog.model;

import org.polymap.model2.Composite;
import org.polymap.model2.Property;
import org.polymap.model2.Queryable;

/**
 * Bounding box or Coverage. 
 *
 * @author Falko Bräutigam
 */
public class Extent
        extends Composite {

    /**
     * Western-most coordinate of the limit of the resource's extent, expressed in
     * longitude in decimal degrees (positive east)
     */
    @Queryable
    @OGCQueryable( "WestBoundLongitude" )
    public Property<Double>     westBoundLongitude;
    
    /**
     * Eastern-most coordinate of the limit of the resource's extent, expressed in
     * longitude in decimal degrees (positive east)
     */
    @Queryable
    @OGCQueryable( "EastBoundLongitude" )
    public Property<Double>     eastBoundLongitude;    
        

//    numeric
//
//    SouthBoundLatitude
//        
//
//    Southern-most coordinate of the limit of the resource's extent, expressed in latitude in decimal degrees (positive north)
//        
//
//    NorthBoundLatitude
//        
//
//    Northern-most, coordinate of the limit of the resource's extent, expressed in latitude in decimal degrees (positive north)
        

}
