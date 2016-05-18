package io.mapzone.arena.analytics.graph;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.runtime.IProgressMonitor;

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
    void createContents( final MdToolkit tk, final Composite parent, final Graph graph );


    /**
     * @return the preferred configuration panel height
     */
    default int preferredHeight() {
        return 100;
    }


    void generate( final MdToolkit tk, final IProgressMonitor monitor, final Graph graph ) throws Exception;
}