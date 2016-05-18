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
package io.mapzone.arena.analytics.graph;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

public abstract class AbstractGraphFunction
        implements GraphFunction {

    private static final IMessages i18n = Messages.forPrefix( "AbstractGraphFunction" );

    protected Button               fab;

    private static Log             log  = LogFactory.getLog( AbstractGraphFunction.class );


    @Override
    public void createContents( final MdToolkit tk, final Composite parent, final Graph graph ) {
        fab = tk.createFab();
        fab.setVisible( false );
        fab.setEnabled( false );
        fab.setToolTipText( i18n.get( "fabTooltip" ) );
        fab.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {

            @Override
            public void widgetSelected( SelectionEvent e ) {
                try {
                    graph.startGeneration( AbstractGraphFunction.this, tk, new NullProgressMonitor() );
                }
                catch (Exception e1) {
                    StatusDispatcher.handleError( "", e1 );
                }
                fab.setVisible( false );
            }
        } );
    }
}
