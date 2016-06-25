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
package io.mapzone.controller.catalog.csw;

import static org.polymap.model2.query.Expressions.and;
import java.util.Collection;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.model2.Property;
import org.polymap.model2.query.Expressions;
import org.polymap.model2.query.grammar.BooleanExpression;
import org.polymap.model2.runtime.PropertyInfo;

import io.mapzone.controller.catalog.model.CatalogEntry;
import io.mapzone.controller.catalog.model.OGCQueryable;
import net.opengis.cat.csw.v_2_0_2.QueryConstraintType;
import net.opengis.filter.v_1_1_0.BinaryComparisonOpType;
import net.opengis.filter.v_1_1_0.ComparisonOperatorType;
import net.opengis.filter.v_1_1_0.ComparisonOpsType;
import net.opengis.filter.v_1_1_0.FilterType;
import net.opengis.filter.v_1_1_0.LiteralType;
import net.opengis.filter.v_1_1_0.LogicOpsType;
import net.opengis.filter.v_1_1_0.PropertyIsLikeType;
import net.opengis.filter.v_1_1_0.PropertyNameType;
import net.opengis.filter.v_1_1_0.SpatialOpsType;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class FilterParser {

    private static final Log log = LogFactory.getLog( FilterParser.class );
    
    public static BooleanExpression parse( FilterType filter ) {
        return new FilterParser( filter ).parse();
    }

    public static BooleanExpression parse( QueryConstraintType constraint ) {
        if (constraint.getCqlText() != null) {
            throw new UnsupportedOperationException( "CQL ist not supported yet." );            
        }
        return new FilterParser( constraint.getFilter() ).parse();
    }

    // instance *******************************************
    
    private FilterType          filter;
    
    private BooleanExpression   result = Expressions.TRUE;

    public FilterParser( FilterType filter ) {
        this.filter = filter;
    }

    
    public BooleanExpression parse() {
        handleComparison( filter.getComparisonOps() );
        handleLogic( filter.getLogicOps() );
        handleSpatial( filter.getSpatialOps() );
        return result;
    }

    
    protected void handleLogic( JAXBElement<? extends LogicOpsType> jaxb ) {
        if (jaxb != null && jaxb.getValue() != null) {
            throw new UnsupportedOperationException( "Logic ops are not supported yet." );
        }
    }


    protected void handleComparison( JAXBElement<? extends ComparisonOpsType> jaxb ) {
        if (jaxb != null && jaxb.getValue() != null) {
            ComparisonOpsType op = jaxb.getValue();

            // BinaryComparison
            if (op instanceof BinaryComparisonOpType) {
                BinaryComparisonOpType binary = (BinaryComparisonOpType)op;
                binary.getExpression().stream().forEach( expr -> log.info( "EXPRESSION: " + expr.getValue() ) );
                
                // FIXME super hack; PropertyIsEqulTo is unmarshalled as BinaryComparison; I don't know
                // whre to get op type from
                PropertyNameType propName = (PropertyNameType)binary.getExpression().get( 0 ).getValue();
                LiteralType literal = (LiteralType)binary.getExpression().get( 1 ).getValue();
                handleComparison( (String)propName.getContent().get( 0 ), 
                        (String)literal.getContent().get( 0 ), 
                        ComparisonOperatorType.EQUAL_TO );
            }
            
            // PropertyIsLike
            else if (op instanceof PropertyIsLikeType) {
                PropertyIsLikeType isLike = (PropertyIsLikeType)op;
                if (!"*".equals( isLike.getWildCard() )) {
                    throw new UnsupportedOperationException( "Wildcard other than '*' is not supported." );
                }
                String propName = (String)isLike.getPropertyName().getContent().get( 0 );
                String literal = (String)isLike.getLiteral().getContent().get( 0 );
                handleComparison( propName, literal, ComparisonOperatorType.LIKE );
            }
            // unhandled
            else {
                throw new UnsupportedOperationException( "Unhandled Comparison type: " + op.getClass().getName() );
            }
        }
    }

    
    protected void handleComparison( String propName, String literal, ComparisonOperatorType op ) {
        // AnyText: fulltext
        if (propName.equalsIgnoreCase( "AnyText" )) {
            log.warn( "Fulltext search not yet supported: " + literal );
        }
        // property
        else {
            Collection<PropertyInfo> props = CatalogEntry.TYPE.info().getProperties();
            for (PropertyInfo prop : props) {
                OGCQueryable a = (OGCQueryable)prop.getAnnotation( OGCQueryable.class );
                if (a != null && a.value().equalsIgnoreCase( propName )) {
                    Property<String> p = (Property<String>)prop.get( CatalogEntry.TYPE );
                    if (ComparisonOperatorType.LIKE.equals( op )) {
                        result = and( result, Expressions.matches( p, literal ) );
                    }
                    else if (ComparisonOperatorType.EQUAL_TO.equals( op )) {
                        result = and( result, Expressions.eq( p, literal ) );
                    }
                    else {
                        throw new UnsupportedOperationException( "Unhandled comparison type: " + op );
                    }
                    break;
                }
            }
        }

    }

    protected void handleSpatial( JAXBElement<? extends SpatialOpsType> jaxb ) {
        if (jaxb != null && jaxb.getValue() != null) {
            throw new UnsupportedOperationException( "Spatial ops are not supported." );
        }
    }
    
}
