package io.mapzone.arena.analytics.graph;

import org.eclipse.swt.widgets.Composite;
import org.polymap.rap.openlayers.base.OlMap;
import org.polymap.rap.openlayers.source.VectorSource;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

public interface GraphFunction {

    String title();


    String description();


    /**
     * creates the configuration part of this graph function
     * 
     * @param olMap
     * @param source
     */
    void createContents( final MdToolkit tk, final Composite parent, final VectorSource source, final OlMap olMap );


    /**
     * @return the preferred configuration panel height
     */
    default int preferredHeight() {
        return 100;
    }


    /**
     * method call signals that edge function is completely configured
     * XXX remove bei EventHandler
     */
    default void edgeFunctionConfigurationDone() {
    }

}