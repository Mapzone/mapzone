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
package io.mapzone.controller.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.core.security.UserPrincipal;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import io.mapzone.controller.um.repository.ProjectRepository.ProjectUnitOfWork;
import io.mapzone.controller.um.repository.User;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class CtrlPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( CtrlPanel.class );

    public static final int                 SIDE_PANEL_WIDTH = 400;
    
    public static final int                 SIDE_PANEL_WIDTH2 = 450;
    
    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<UserPrincipal>        userPrincipal;

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<User>                 user;

    @Mandatory
    @Scope( "io.mapzone.controller" )
    protected Context<ProjectUnitOfWork>    uow;


    /**
     * {@inheritDoc}
     * <p/>
     * Sets size to: 
     * <pre>
     * SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH, Integer.MAX_VALUE
     * </pre>
     */
    @Override
    public void init() {
        site().setSize( SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH, Integer.MAX_VALUE );
    }

    public MdToolkit tk() {
        return (MdToolkit)site().toolkit();
    }

}
