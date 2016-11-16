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

import static org.polymap.core.ui.FormDataFactory.filled;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.polymap.core.operation.IOperationConcernFactory;
import org.polymap.core.operation.OperationConcernAdapter;
import org.polymap.core.operation.OperationInfo;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.ui.FormLayoutFactory;

import org.polymap.rhei.batik.toolkit.DefaultToolkit;
import org.polymap.rhei.batik.toolkit.SimpleDialog;

import org.polymap.p4.catalog.AllResolver;
import org.polymap.p4.layer.NewLayerOperation;

import io.mapzone.arena.csw.catalog.MapzoneProjectResolver.MapzoneProjectResourceInfo;

/**
 * If resource is a {@link MapzoneProjectResourceInfo} then provide UI chooser for
 * WMS/WFS/Clone.
 *
 * @author Falko Bräutigam
 */
public class NewLayerOperationConcern
        extends IOperationConcernFactory {

    private static Log log = LogFactory.getLog( NewLayerOperationConcern.class );

    @Override
    public IUndoableOperation newInstance( IUndoableOperation op, OperationInfo info ) {
        return op instanceof NewLayerOperation 
                ? new Concern( (NewLayerOperation)op, info ) : null;
    }

    /**
     * 
     */
    class Concern
            extends OperationConcernAdapter {

        private OperationInfo           info;
        
        private NewLayerOperation       op;

        private String                  resId;
        
        
        protected Concern( NewLayerOperation op, OperationInfo info ) {
            this.op = op;
            this.info = info;
        }

        @Override
        public IStatus execute( IProgressMonitor monitor, IAdaptable a ) throws ExecutionException {
            if (op.resId.isPresent()
                    || !op.res.isPresent() 
                    || !(op.res.get() instanceof MapzoneProjectResourceInfo)) {
                return super.execute( monitor, a );
            }

            // default: WMS
            resId = AllResolver.resourceIdentifier( op.res.get() );
            
            UIThreadExecutor.sync( () -> {
                new SimpleDialog()
                        .title.put( "Mapzone service" )
                        .addOkAction( () -> true )
                        .setContents( parent -> createDialogContents( parent ) )
                        .open();
                return null;
            });
            
            assert resId != null;
            op.resId.set( resId );
            return super.execute( monitor, a );
        }

        protected void createDialogContents( Composite parent ) {
            parent.setLayout( FormLayoutFactory.defaults().spacing( 10 ).create() );
            
            Label msg = DefaultToolkit._adapt( new Label( parent, SWT.NONE ), true, true );
            msg.setLayoutData( filled().noBottom().create() );
            msg.setEnabled( false );
            msg.setText( "This data set was created and is maintained by another user of mapzone.<br/>"
                    + "You can use this in different ways.");
            
            Button wmsBtn = DefaultToolkit._adapt( new Button( parent, SWT.RADIO ), true, true );
            wmsBtn.setLayoutData( filled().noBottom().top( msg ).create() );
            wmsBtn.setText( "Image (WMS)" );
            wmsBtn.addSelectionListener( new SelectionAdapter() {
                @Override
                public void widgetSelected( SelectionEvent e ) {
                    resId = AllResolver.resourceIdentifier( op.res.get() );
                }
            });
            wmsBtn.setSelection( true );

            Label wmsMsg = DefaultToolkit._adapt( new Label( parent, SWT.NONE ), true, true );
            wmsMsg.setLayoutData( filled().noBottom().top( wmsBtn, -5 ).left( 0, 28 ).create() );
            wmsMsg.setEnabled( false );
            wmsMsg.setText( "No styling. No data manipulation. No problems :)" );

            Button wfsBtn = DefaultToolkit._adapt( new Button( parent, SWT.RADIO ), true, true );
            wfsBtn.setLayoutData( filled().noBottom().top( wmsMsg ).create() );
            wfsBtn.setText( "Use data (WFS)" );
            wfsBtn.setEnabled( false );
            wfsBtn.addSelectionListener( new SelectionAdapter() {
                @Override
                public void widgetSelected( SelectionEvent e ) {
                    throw new UnsupportedOperationException();
                }
            });
            
            Label wfsMsg = DefaultToolkit._adapt( new Label( parent, SWT.NONE ), true, true );
            wfsMsg.setLayoutData( filled().noBottom().top( wfsBtn, -5 ).left( 0, 28 ).create() );
            wfsMsg.setEnabled( false );
            wfsMsg.setText( "Allows styling. No data manipulation." );

            Button forkBtn = DefaultToolkit._adapt( new Button( parent, SWT.RADIO ), true, true );
            forkBtn.setLayoutData( filled().noBottom().top( wfsMsg ).create() );
            forkBtn.setText( "Fork data" );
            forkBtn.setEnabled( false );
            forkBtn.addSelectionListener( new SelectionAdapter() {
                @Override
                public void widgetSelected( SelectionEvent e ) {
                    throw new UnsupportedOperationException();
                }
            });
            
            Label forkMsg = DefaultToolkit._adapt( new Label( parent, SWT.NONE ), true, true );
            forkMsg.setLayoutData( filled().noBottom().top( forkBtn, -5 ).left( 0, 28 ).create() );
            forkMsg.setEnabled( false );
            forkMsg.setText( "Allows styling and data manipulation." );
        }
        
        @Override
        protected OperationInfo getInfo() {
            return info;
        }
    }
}
