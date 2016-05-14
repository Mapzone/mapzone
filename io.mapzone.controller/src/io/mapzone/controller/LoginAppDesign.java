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
package io.mapzone.controller;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.rap.rwt.RWT;

import org.polymap.core.runtime.ConcurrentReferenceHashMap;
import org.polymap.core.runtime.ConcurrentReferenceHashMap.ReferenceType;
import org.polymap.core.security.SecurityContext;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.PanelSite;
import org.polymap.rhei.batik.toolkit.SimpleDialog;
import org.polymap.rhei.batik.toolkit.md.MdAppDesign;
import org.polymap.rhei.form.batik.BatikFormContainer;

import io.mapzone.controller.http.LoginProvision;
import io.mapzone.controller.ui.user.LoginPanel.LoginForm;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

/**
 * Provides the UI for logging into a project instance directly (bypassing the
 * Dashboard). Used by {@link LoginProvision}.
 *
 * @author Falko Bräutigam
 */
public class LoginAppDesign
        extends MdAppDesign {

    private static Log log = LogFactory.getLog( LoginAppDesign.class );
    
    private static Map<String,Consumer<User>>   handlers = new ConcurrentReferenceHashMap( 
            16, 0.75f, 2, ReferenceType.STRONG, ReferenceType.SOFT, null );

    /**
     * 
     *
     * @param handler The handler that consumes a pair of username/passwd.
     * @return The of the newly registered handler.
     */
    public static String registerHandler( Consumer<User> handler ) {
        String handlerId = String.valueOf( new Object().hashCode() );
        if (handlers.put( handlerId, handler ) != null) {
            throw new IllegalStateException( "handlerId already registered: " + handlerId );
        }
        return handlerId;
    }
    
    
    // instance *******************************************
    
    @Override
    public Shell createMainWindow( @SuppressWarnings( "hiding" ) Display display ) {
        this.display = display;
        mainWindow = new Shell( display, SWT.NO_TRIM );
        mainWindow.setMaximized( true );
        UIUtils.setVariant( mainWindow, CSS_SHELL );

        mainWindow.setLayout( new FillLayout() );
        
        String handlerId = RWT.getRequest().getParameter( "id" );

        SimpleDialog dialog = new SimpleDialog( mainWindow );
        dialog.title.set( "Login required" );
        dialog.setContents( parent -> {
            LoginForm loginForm = new LoginForm() {
                @Override
                protected boolean login( String name, String passwd ) {
                    Optional<User> loggedIn = tryLogin( name, passwd );
                    if (loggedIn.isPresent()) {
                        Consumer<User> handler = handlers.remove( handlerId );
                        handler.accept( loggedIn.get() );
                        return true;
                    }
                    else {
                        formSite.setFieldValue( "password", "" );
                        return false;
                    }
                }
                @Override
                protected PanelSite panelSite() {
                    throw new RuntimeException( "not yet implemented." );
                }
            };        
            loginForm.showRegisterLink.set( false );
            loginForm.showStoreCheck.set( false );
            loginForm.showLostLink.set( false );  // XXX implement!

            new BatikFormContainer( loginForm ).createContents( parent );
        });
        dialog.open();
        
        mainWindow.open();
        return mainWindow;
    }
    
    
    protected Optional<User> tryLogin( String name, String passwd ) {
        SecurityContext sc = SecurityContext.instance();
        return sc.login( name, passwd )
                ? ProjectRepository.newInstance().findUser( name )
                : Optional.empty();
    }
    
}
