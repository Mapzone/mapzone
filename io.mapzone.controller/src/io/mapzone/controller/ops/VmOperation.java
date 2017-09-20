/* 
 * mapzone.io
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
package io.mapzone.controller.ops;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;

import org.polymap.model2.Entity;
import org.polymap.model2.runtime.UnitOfWork;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.vm.repository.VmRepository.VmUnitOfWork;

/**
 * A {@link VmOperation} is typically an operation executed during provision or to
 * support an {@link UmOperation}. 
 *
 * @author Falko Bräutigam
 */
public abstract class VmOperation
        extends DefaultOperation {

    private static final Log log = LogFactory.getLog( VmOperation.class );

    /**
     * Inbound: The {@link VmUnitOfWork} to use to manipulate Entities. During
     * provision this UnitOfWork is not committed or rolled back after th
     */
    @Mandatory
    @Immutable
    public Config<VmUnitOfWork>     vmUow;


    public VmOperation( String label ) {
        super( label );
        ConfigurationFactory.inject( this );
    }

    
    /**
     * Execute. this operation.
     * <p/>
     * The given {@link UnitOfWork} is a <b>nested</b> instance of {@link #vmUow}. It
     * is automatically handled (committed or rolled back) by this operation. All
     * modifications of {@link Entity}s should by done within this UnitOfWork in
     * order to by properly rolled back on error. Entities from outside *must* be
     * converted via {@link UnitOfWork#entity(Entity)}.
     * <p/>
     * The given {@link IProgressMonitor} has been started via
     * <code>beginTask( getLabel(), IProgressMonitor.UNKNOWN )</code> and it is
     * automatically {@link IProgressMonitor#done()} afterwards.
     *
     * @param monitor
     * @param uow Nested {@link UnitOfWork} of {@link #vmUow}.
     * @throws Exception
     */
    protected abstract void doWithException( IProgressMonitor monitor, UnitOfWork uow ) throws Exception;

    
    /**
     * This is called after {@link #doWithException(IProgressMonitor, UnitOfWork) doWithException()}
     * returned without throwing an Exception.
     * <p/>
     * The default implementation of this method {@link UnitOfWork#commit()}s
     * the nested UnitOfWork. Subclasses should invoke super.
     *
     * @param monitor
     * @param uow Nested {@link UnitOfWork} of {@link #vmUow}.
     * @throws Exception
     */
    protected void onSuccess( IProgressMonitor monitor, UnitOfWork uow ) throws Exception {
        uow.commit();        
    }

    
    /**
     * This is called after {@link #doWithException(IProgressMonitor, UnitOfWork)
     * doWithException()} <b>or</b> {@link #onSuccess(IProgressMonitor, UnitOfWork)
     * onSuccess()} failed with the given Exception.
     * <p/>
     * The default implementation of this method just re-throws the given Exception.
     * Subclasses should invoke super. It does not {@link UnitOfWork#rollback()} the
     * nested UnitOfWork. Instead is just closed after this method returns,
     * discarding any modifications.
     *
     * @param monitor
     * @param e The Exception thrown by
     *        {@link #doWithException(IProgressMonitor, UnitOfWork)
     *        doWithException()} or {@link #onSuccess(IProgressMonitor, UnitOfWork)
     *        onSuccess()}.
     * @throws Exception
     */
    protected void onError( IProgressMonitor monitor, Throwable e ) throws Exception {
        if (e instanceof Error) {
            throw (Error)e;
        }
        else {
            throw (Exception)e;
        }
    }

    
    @Override
    protected final IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        try (
            UnitOfWork nested = vmUow.get().newUnitOfWork();
        ){
            monitor = monitor != null ? monitor : new NullProgressMonitor();
            monitor.beginTask( getLabel(), IProgressMonitor.UNKNOWN );
            
            doWithException( monitor, nested );
            onSuccess( monitor, nested );
            return monitor != null && monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS;
        }
        catch (Throwable e) {
            log.warn( "Error while doWithException() or onSuccess().", e );
            onError( monitor, e );
            return new Status( IStatus.ERROR, ControllerPlugin.ID, e.getLocalizedMessage(), e );
        }
        finally {
            monitor.done();
        }
    }
    
}
