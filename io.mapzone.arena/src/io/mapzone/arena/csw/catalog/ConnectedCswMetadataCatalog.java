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
package io.mapzone.arena.csw.catalog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.core.catalog.DefaultMetadata;
import org.polymap.core.catalog.IMetadata;
import org.polymap.core.catalog.IMetadataCatalog;
import org.polymap.core.catalog.IUpdateableMetadataCatalog;
import org.polymap.core.catalog.MetadataQuery;
import org.polymap.core.project.ILayer;
import org.polymap.core.project.IMap;

import org.polymap.p4.catalog.AllResolver;
import org.polymap.p4.project.ProjectRepository;

/**
 * A facade for delegate catalog. For {@link IMetadataCatalog#ALL_QUERY} the method
 * {@link #query(String, IProgressMonitor)} returns all resources that are currently
 * connected to a layer.
 *
 * @author Falko Bräutigam
 */
public class ConnectedCswMetadataCatalog
        implements IUpdateableMetadataCatalog {

    /**
     * 
     */
    public static final IMetadata DUMMY = new DefaultMetadata( null ) {
        @Override
        public String getTitle() {
            return "More entries...";
        }
        @Override
        public Optional<String> getDescription() {
            return Optional.of( "Enter a search to get more entries!" );
        }
        @Override
        public String getIdentifier() {
            throw new RuntimeException( "not yet implemented." );
        }
    };

    /**
     * 
     */
    protected class DummyMetadataQuery
            extends MetadataQuery {
        
        private List<IMetadata>     result;
        
        public DummyMetadataQuery( List<IMetadata> result ) {
            this.result = result;
        }

        @Override
        public ResultSet execute() throws Exception {
            return new ResultSet() {
                @Override
                public Iterator<IMetadata> iterator() {
                    return result.iterator();
                }
                @Override
                public int size() {
                    return result.size();
                }
                @Override
                public void close() {
                }
            };
        }        
    }
    

    // instance *******************************************
    
    private IUpdateableMetadataCatalog  delegate;

    
    public ConnectedCswMetadataCatalog( IUpdateableMetadataCatalog delegate ) {
        this.delegate = delegate;
    }

    @Override
    public MetadataQuery query( String query, IProgressMonitor monitor ) throws Exception {
        if (query.equals( ALL_QUERY )) {
            IMap map = ProjectRepository.unitOfWork().entity( IMap.class, ProjectRepository.ROOT_MAP_ID );
            List<IMetadata> results = new ArrayList();
            for (ILayer layer : map.layers) {
                AllResolver.instance().metadata( layer, monitor )
                    .filter( result -> result instanceof CswMetadataBase )
                    .ifPresent( result -> results.add( result ) );
            }
            results.add( DUMMY );
            return new DummyMetadataQuery( results );
        }
        else {
            return delegate.query( query, monitor );
        }
    }

    @Override
    public Iterable<String> propose( String prefix, int maxResults, IProgressMonitor monitor ) throws Exception {
        return delegate.propose( prefix, maxResults, monitor );
    }

    @Override
    public Optional<? extends IMetadata> entry( String identifier, IProgressMonitor monitor ) throws Exception {
        return delegate.entry( identifier, monitor )
                .map( result -> ((CswMetadataBase)result).setCatalog( this ) );
    }

    @Override
    public String getTitle() {
        return delegate.getTitle();
    }

    @Override
    public String getDescription() {
        return delegate.getDescription();
    }

    @Override
    public Updater prepareUpdate() {
        return delegate.prepareUpdate();
    }

    @Override
    public void close() {
        delegate.close();
    }
    
}
