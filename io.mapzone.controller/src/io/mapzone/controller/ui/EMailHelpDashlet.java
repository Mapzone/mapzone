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
package io.mapzone.controller.ui;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.ConfigurationFactory;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.help.HelpDashlet;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.field.EMailAddressValidator;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.HorizontalFieldLayout;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.NotEmptyValidator;
import org.polymap.rhei.field.PlainValuePropertyAdapter;
import org.polymap.rhei.field.TextFormField;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

import io.mapzone.controller.Messages;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class EMailHelpDashlet
        extends HelpDashlet {

    private static final Log log = LogFactory.getLog( EMailHelpDashlet.class );
    
    public static final IMessages i18n = Messages.forPrefix( "EMailHelpDashlet" );
    
    @Mandatory
    public Config2<EMailHelpDashlet,String> smtpHost;
    
    @Mandatory
    public Config2<EMailHelpDashlet,String> smtpUser;
    
    @Mandatory
    public Config2<EMailHelpDashlet,String> smtpPassword;
    
    @Mandatory
    public Config2<EMailHelpDashlet,String> to;
    
    @Mandatory
    public Config2<EMailHelpDashlet,String> from;
    
    /** Initialized by {@link #mailSession()}. */
    private Session                 session;

    private MimeMessage             msg;

    private BatikFormContainer      form;

    private Button                  sendBtn;

    
    public EMailHelpDashlet() {
        ConfigurationFactory.inject( this );
    }


    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site().title.set( i18n.get( "title" ) );
    }


    @Override
    public void createContents( Composite parent ) {
        initMailSession();
        msg = new MimeMessage( session );
        
        parent.setLayout( new FillLayout() );
        form = new BatikFormContainer( new EMailForm() );
        form.createContents( parent );
        
        sendBtn = site().toolkit().createButton( form.getContents(), "Send", SWT.PUSH );
        sendBtn.setEnabled( false );
        sendBtn.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                try {
                    send();
                    site().toolkit().createSnackbar( Appearance.FadeIn, "Message sent successfully" );
                    sendBtn.getDisplay().timerExec( 2500, () -> {
                        BatikApplication.instance().getContext().closePanel( site().panelSite().getPath() );
                    });
                }
                catch (Exception e) {
                    StatusDispatcher.handleError( "Unable to send email.", e );
                }
            }
        });
    }

    
    protected void send() throws Exception {
        msg.addRecipients( RecipientType.TO, InternetAddress.parse( to.get(), false ) );
        msg.setSentDate( new Date() );
        Transport.send( msg );
    }


    protected void initMailSession() {
        Properties props = new Properties();
        props.put( "mail.smtp.host", System.getProperty( "mail.smtp.host", smtpHost.get() ) );
        props.put( "mail.smtp.port", "25" );
        props.put( "mail.smtp.auth", "true" );
        // TODO uncomment if the mail server contains a correct SSL certificate
        // props.put( "mail.smtp.starttls.enable", "true" ); // enable STARTTLS

        // create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication( smtpUser.get(), smtpPassword.get() );
            }
        };
        assert session == null;
        session = Session.getInstance( props, auth );
    }


    /**
     * 
     */
    public class EMailForm
            extends DefaultFormPage
            implements IFormFieldListener {

        @Override
        public void createFormContents( IFormPageSite site ) {
            super.createFormContents( site );
            
            site.addFieldListener( this );
            
            Composite body = site.getPageBody();
            body.setLayout( ColumnLayoutFactory.defaults().spacing( 5 ).margins( 5 ).create() );

            ColumnDataFactory.on( site.getToolkit().createLabel( body, i18n.get( "welcome" ), SWT.WRAP ) )
                    .heightHint( 130 ).widthHint( 100 ).control()
                    .setEnabled( false );
            
            // To:
            site.newFormField( new PlainValuePropertyAdapter( "to", to.get() ) )
                    .layout.put( HorizontalFieldLayout.INSTANCE )
                    .label.put( "To:" )
                    .fieldEnabled.put( false )
                    .create().setEnabled( false );
            
            // From:
            site.newFormField( new PlainValuePropertyAdapter( "from", from.orElse( "" ) ) )
                    .layout.put( HorizontalFieldLayout.INSTANCE )
                    .label.put( "From:" )
                    .validator.put( new InternetAddressValidator() )
                    .tooltip.put( "The email address where you want to receive the answer" )
                    .create();

            // Subject:
            site.newFormField( new PlainValuePropertyAdapter( "subject", "" ) )
                    .layout.put( HorizontalFieldLayout.INSTANCE )
                    .label.put( "Subject:" )
                    .validator.put( new NotEmptyValidator() )
                    .create();

            // Text
            site.newFormField( new PlainValuePropertyAdapter( "text", "" ) )
                    .layout.put( HorizontalFieldLayout.NO_LABEL )
                    .field.put( new TextFormField() )
                    .validator.put( new NotEmptyValidator() )
                    .create().setLayoutData( ColumnDataFactory.defaults().heightHint( 250 ).create() );
        }

        @Override
        public void fieldChange( FormFieldEvent ev ) {
            if (ev.getEventCode() == VALUE_CHANGE) {
                if (form.isDirty()) {
                    sendBtn.setEnabled( form.isDirty() && form.isValid() );
                }

                try {
                    if (ev.getFieldName().equals( "from" ) && ev.getNewModelValue().isPresent()) {
                        InternetAddress[] address = InternetAddress.parse( (String)ev.getNewModelValue().get() );
                        msg.setFrom( address[0] );
                        msg.setReplyTo( address );
                    }
                    else if (ev.getFieldName().equals( "subject" ) && ev.getNewModelValue().isPresent()) {
                        msg.setSubject( (String)ev.getNewModelValue().get(), "utf-8" );
                    }
                    else if (ev.getFieldName().equals( "text" ) && ev.getNewModelValue().isPresent()) {
                        msg.setText( (String)ev.getNewModelValue().get(), "utf-8" );
                    }
                }
                catch (MessagingException e) {
                    throw new RuntimeException( e );
                }
            }
        }
    }
    
    
    public static class InternetAddressValidator
            extends EMailAddressValidator {

        @Override
        public String validate( String fieldValue ) {
            String result = super.validate( fieldValue );
            if (result == null) {
                try {
                    InternetAddress.parse( fieldValue );
                }
                catch (AddressException e) {
                    result = e.getLocalizedMessage();
                }
            }
            if (StringUtils.isBlank( fieldValue )) {
                result = "Field must not be empty.";
            }
            return result;
        }
    }
    
}
