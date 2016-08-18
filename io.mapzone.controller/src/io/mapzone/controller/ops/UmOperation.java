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

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Immutable;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.model2.runtime.TwoPhaseCommit;
import org.polymap.model2.runtime.TwoPhaseCommit.CommitType;
import org.polymap.model2.runtime.TwoPhaseCommit.UnitOfWorkAdapter;

import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.vm.repository.VmRepository;
import io.mapzone.controller.vm.repository.VmRepository.VmUnitOfWork;

/**
 * An {@link UmOperation} typically is user interface operations. It modifies
 * entities of the {@link ProjectRepository} and maybe the {@link VmRepository}.
 * Modifications are committed in a {@link TwoPhaseCommit}.
 *
 * @author Falko Bräutigam
 */
public abstract class UmOperation
        extends DefaultOperation {

    private static final Log log = LogFactory.getLog( UmOperation.class );

    @Mandatory
    @Immutable
    public Config<ProjectUnitOfWork>    umUow;

    @Mandatory
    @Immutable
    public Config<VmUnitOfWork>         vmUow;

    
    public UmOperation( String label ) {
        super( label );
        ConfigurationFactory.inject( this );
    }


    /**
     *
     */
    protected abstract IStatus doWithCommit( IProgressMonitor monitor, IAdaptable info ) throws Exception;

    /**
     * Override in order to perform task after commit.
     */
    protected void onSuccess() {
    }

    /**
     * Override in order to perform task after rollback.
     */
    protected void onError( Throwable e ) {
//        StatusDispatcher.handleError( "", e );
    }
    
    
    @Override
    public final IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
        try {
            IStatus result = doWithCommit( monitor, info );
            twoPhaseCommit().commit( CommitType.KEEP_OPEN );
            onSuccess();
            return result;
        }
        catch (Throwable e) {
            log.warn( "", e );
            twoPhaseCommit().rollback( CommitType.KEEP_OPEN );
            onError( e );
            throw e;
        }
    }

    
    protected TwoPhaseCommit twoPhaseCommit() {
        TwoPhaseCommit twoPhaseCommit = new TwoPhaseCommit();
        umUow.ifPresent( uow -> twoPhaseCommit.register( new UnitOfWorkAdapter( uow ) ) );
        vmUow.ifPresent( uow -> twoPhaseCommit.register( new UnitOfWorkAdapter( uow ) ) );
        return twoPhaseCommit;
    }
    
}