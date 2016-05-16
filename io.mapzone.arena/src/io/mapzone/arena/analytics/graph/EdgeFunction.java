package io.mapzone.arena.analytics.graph;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.geotools.data.FeatureSource;
import org.opengis.feature.Feature;
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


    /**
     * only necessary to use GraphFunction.edgeFunctionConfigurationDone XXX remove
     * by eventhandler
     *
     * @param graphFunction
     */
    default void init( final GraphFunction graphFunction ) {
    }


    boolean isConfigurationComplete();


    /**
     * @param tk
     * @param monitor
     * @param sourceFeatures a map with the feature id as key and the feature as
     *        value, for faster access
     * @return
     * @throws Exception
     */
    Collection<Edge> generateEdges( final MdToolkit tk, final IProgressMonitor monitor,
            final Map<String,Feature> sourceFeatures ) throws Exception;

}