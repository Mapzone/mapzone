package io.mapzone.arena.analytics.graph;

import java.util.Map;

import org.geotools.data.FeatureSource;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polymap.rhei.batik.toolkit.md.MdToolkit;

/**
 * Base interface for edge functions.
 *
 * @author Steffen Stundzig
 */
public interface EdgeFunction {

    String title();


    String description();


    /**
     * creates the configuration part of this graph function
     */
    void createContents( final MdToolkit tk, final Composite parent, final FeatureSource sourceFeatureSource );


    /**
     * @return the preferred configuration panel height
     */
    default int preferredHeight() {
        return 100;
    }


    boolean isConfigurationComplete();


    default void init( final GraphFunction graphFunction ) {
    }


    /**
     * @param tk
     * @param monitor
     * @param sourceFeatures a map with the feature id as key and the feature as
     *        value, for faster access
     * @return the number of generated edges
     * @throws Exception
     */
    int generateEdges( final MdToolkit tk, final IProgressMonitor monitor,
            final Map<String,Node> sourceFeatures, final Graph graph ) throws Exception;

}