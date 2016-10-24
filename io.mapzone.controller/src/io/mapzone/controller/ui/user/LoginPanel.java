/* 
 * mapzone.io
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
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
package io.mapzone.controller.ui.user;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.DefaultBoolean;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.security.SecurityContext;
import org.polymap.core.security.UserPrincipal;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnLayoutFactory;

import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelSite;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.field.CheckboxFormField;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.NotEmptyValidator;
import org.polymap.rhei.field.PlainValuePropertyAdapter;
import org.polymap.rhei.field.StringFormField;
import org.polymap.rhei.field.StringFormField.Style;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.Messages;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.ui.StartPanel;
import io.mapzone.controller.um.repository.LoginCookie;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class LoginPanel
        extends CtrlPanel {

    public static final PanelIdentifier ID = new PanelIdentifier( "login" );
    
    private static final IMessages      i18n = Messages.forPrefix( "LoginForm" );

    
    @Override
    public boolean wantsToBeShown() {
        if (parentPanel().get() instanceof StartPanel) {
            getSite().setTitle( "" ).setTooltip( i18n.get( "title" ) );
            getSite().setIcon( ControllerPlugin.images().svgImage( "account-key.svg", ControllerPlugin.HEADER_ICON_CONFIG ) ); 

//            getSite().setIcon( ControllerPlugin.images().svgOverlayedImage( 
//                    "account.svg", SvgImageRegistryHelper.NORMAL24, 
//                    "plus-circle-filled.svg", SvgImageRegistryHelper.OVR12_ACTION,
//                    Quadrant.TopLeft ) );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        super.init();
        site().setSize( SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH2, SIDE_PANEL_WIDTH2 );
        site().title.set( i18n.get( "title" ) );
    }
    
    
    @Override
    public void createContents( Composite parent ) {
        IPanelSection section = getSite().toolkit().createPanelSection( parent, i18n.get( "title" ), SWT.BORDER );
        
        LoginForm loginForm = new LoginForm() {
            @Override
            protected boolean login( String name, String passwd ) {
                SecurityContext sc = SecurityContext.instance();
                if (sc.login( name, passwd )) {
                    userPrincipal.set( (UserPrincipal)sc.getUser() );
                    getContext().closePanel( getSite().getPath() );
                    return true;
                }
                else {
                    getSite().toolkit().createSnackbar( Appearance.FadeIn, i18n.get( "notCorrect" ) );
                    return false;
                }
            }

            @Override
            protected PanelSite panelSite() {
                return LoginPanel.this.site();
            }
        };        
        loginForm.showRegisterLink.set( false );
        loginForm.showStoreCheck.set( true );
        loginForm.showLostLink.set( false );  // XXX implement!

        new BatikFormContainer( loginForm ).createContents( section );
    }

    
    /**
     * 
     */
    public static abstract class LoginForm
            extends DefaultFormPage {

        protected Button                        loginBtn;

        protected String                        username, password;

        /**
         * Defaults to true. Otherwise project instance logins ask for login even if
         * /dashboard has correct login.
         */
        protected boolean                       storeLogin = true;
        
        protected IFormPageSite                 formSite;
        
        private IFormFieldListener              fieldListener;
        
        @Mandatory @DefaultBoolean( false )
        public Config<Boolean>                  showRegisterLink;

        @Mandatory @DefaultBoolean( false )
        public Config<Boolean>                  showStoreCheck;
        
        @Mandatory @DefaultBoolean( false )
        public Config<Boolean>                  showLostLink;

        
        public LoginForm() {
            ConfigurationFactory.inject( this );
        }
        
        
        protected abstract boolean login( final String name, final String passwd );

        protected abstract PanelSite panelSite();
        
        
        @Override
        public void createFormContents( final IFormPageSite site ) {
            formSite = site;
            Composite body = site.getPageBody();
            body.setLayout( ColumnLayoutFactory.defaults()
                    .spacing( 5 /*panelSite.getLayoutPreference( LAYOUT_SPACING_KEY ) / 4*/ )
                    .margins( 10 /*panelSite().getLayoutPreference().getSpacing() / 2*/ ).create() );
            // username
            Composite nameField = site.newFormField( new PlainValuePropertyAdapter( "username", username ) )
                    .field.put( new StringFormField() ).validator.put( new NotEmptyValidator() )
                    .label.put( i18n.get( "username" ) ).tooltip.put( i18n.get( "usernameTip" ) )
                    .create();
            // default width for use in dialog
            nameField.setLayoutData( ColumnDataFactory.defaults().widthHint( 300 ).create() );
            nameField.setFocus();
            
            // password
            site.newFormField( new PlainValuePropertyAdapter( "password", password ) )
                    .field.put( new StringFormField( Style.PASSWORD ) )
                    .validator.put( new NotEmptyValidator() )
                    .label.put( i18n.get( "password" ) )
                    .create();

            // store login
            if (showStoreCheck.get()) {
                site.newFormField( new PlainValuePropertyAdapter( "store", storeLogin ) )
                        .field.put( new CheckboxFormField() )
                        .label.put( i18n.get( "storeLogin" ) ).tooltip.put( i18n.get( "storeLoginTip" ) )
                        .create();
            }
            // btn
            loginBtn = site.getToolkit().createButton( body, i18n.get( "login" ), SWT.PUSH );
            loginBtn.setLayoutData( ColumnDataFactory.defaults().heightHint( 35 ).create() );
            loginBtn.setEnabled( username != null );
            loginBtn.addSelectionListener( new SelectionAdapter() {
                public void widgetSelected( SelectionEvent ev ) {
                    login( username, password );
                    if (storeLogin) {
                        LoginCookie.access().create( username );
                    }
                }
            });

            Composite links = null;
            if (showLostLink.get()) {
                links = panelSite().toolkit().createComposite( body );
                Link lnk = panelSite().toolkit().createLink( links, i18n.get( "lost" ) );
                lnk.setToolTipText( i18n.get( "lostTip" ) );
                lnk.addSelectionListener( new SelectionAdapter() {
                    public void widgetSelected( SelectionEvent ev ) {
                        if (username != null && username.length() > 0) {
                            sendNewPassword( username );
                        }
                    }
                });
            }

            if (showRegisterLink.get()) {
                links = links != null ? links : panelSite().toolkit().createComposite( body );
                Link registerLnk = panelSite().toolkit().createLink( links, i18n.get( "register" ) );
                registerLnk.addSelectionListener( new SelectionAdapter() {
                    public void widgetSelected( SelectionEvent e ) {
                        BatikApplication.instance().getContext().openPanel( panelSite().path(), RegisterPanel.ID );
                    }
                });
            }

            // listener
            site.addFieldListener( fieldListener = new IFormFieldListener() {
                public void fieldChange( FormFieldEvent ev ) {
                    if (ev.getEventCode() != VALUE_CHANGE) {
                        return;
                    }
                    else if (ev.getFieldName().equals( "store" ) ) {
                        storeLogin = (Boolean)ev.getNewModelValue().orElse( null );
                    }
                    else if (ev.getFieldName().equals( "username" ) ) {
                        username = (String)ev.getNewModelValue().orElse( null );
                    }
                    else if (ev.getFieldName().equals( "password" ) ) {
                        password = (String)ev.getNewModelValue().orElse( null );
                    }
                    if (loginBtn != null && !loginBtn.isDisposed()) {
                        // don't check dirty to allow login with stored credentials
                        loginBtn.setEnabled( /*site.isDirty() &&*/ site.isValid() );
                    }
                }
            });
        }

        
        protected void sendNewPassword( String name ) {
            throw new RuntimeException( "Not yet..." );
//            User umuser = repo.findUser( name );
//            if (umuser != null) {
//                try {
//                    IUndoableOperation op = new NewPasswordOperation( umuser );
//                    OperationSupport.instance().execute( op, true, false );
//                    panelSite.setStatus( new Status( IStatus.OK, UmPlugin.ID, i18n.get( "passwordSent", name ) ) );
//                }
//                catch (ExecutionException e) {
//                    log.warn( "", e );
//                }
//            }
//            else {
//                panelSite.setStatus( new Status( IStatus.WARNING, UmPlugin.ID, i18n.get( "noSuchUser", name ) ) );
//            }
        }

    }        

}
