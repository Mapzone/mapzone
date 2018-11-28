/*
 * polymap.org 
 * Copyright (C) 2016-2018, the @authors. All rights reserved.
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
package io.mapzone.arena.share.app;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import java.net.URL;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.ColumnDataFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;

import org.polymap.rap.updownload.download.DownloadService.ContentProvider;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;
import io.mapzone.arena.share.DefaultSectionProvider;
import io.mapzone.arena.share.Sharelet;
import io.mapzone.arena.share.ShareletSectionProvider;
import io.mapzone.arena.share.ShareletSite;
import io.mapzone.arena.share.content.ArenaContentProvider.ArenaContent;
import io.mapzone.arena.share.content.ImagePngContentProvider.ImagePngContent;
import io.mapzone.arena.share.content.ShareableContentProvider;

/**
 * Sharelet to send an email with a link to arena and an embedded image.
 *
 * @author Steffen Stundzig
 */
public class EMailSharelet
        extends Sharelet {

    private static final IMessages i18n = Messages.forPrefix( "EMailSharelet" );

    private ContentProvider        provider;

    private Session                session;


    @Override
    public void init( ShareletSite site ) {
        site.title.set( i18n.get( "title" ) );
        site.description.set( i18n.get( "description" ) );
        site.priority.set( 211 );
        site.image.set( ArenaPlugin.images().svgImage( "email-outline.svg", SvgImageRegistryHelper.NORMAL48 ) );
        super.init( site );
    }


    public List<ShareletSectionProvider> sections() {
        return Lists.newArrayList( new MailFormSectionProvider() );
    }


    /**
     * 
     */
    class MailFormSectionProvider
            extends DefaultSectionProvider {

        @Override
        public String title() {
            return i18n.get( "email_title" );
        }

        @Override
        public String[] supportedTypes() {
            return new String[] { "image/png", "application/arena" };
        }

        @Override
        public void createContents( final Composite parent, ShareableContentProvider... contentBuilders ) {
            ImagePngContent image = (ImagePngContent)contentBuilders[0].get();
            ArenaContent arena = (ArenaContent)contentBuilders[1].get();
            Button fab = tk().createFab( "Send" );
            fab.setToolTipText( "Send email" );

            // To:
            LabeledField toField = createField( tk(), parent, i18n.get( "email_to" ), null ); 
            Text to = tk().createText( toField, "", SWT.BORDER/*, SWT.WRAP*/ );
            to.setToolTipText( i18n.get( "email_toTooltip" ) );
            //ColumnDataFactory.on( to ).widthHint( width() ).heightHint( 40 );
            to.addModifyListener( new ModifyListener() {
                @Override
                public void modifyText( ModifyEvent event ) {
                    // TODO add check for correct email adresses here
                    if (!StringUtils.isBlank( to.getText().trim() )) {
                        fab.setVisible( true );
                        fab.setEnabled( true );
                        fab.getParent().layout( new Control[] {fab} );
                    }
                    else {
                        fab.setVisible( false );
                        fab.setEnabled( false );
                    }
                }
            });                

            // Subject:
            LabeledField subjectField = createField( tk(), parent, i18n.get( "email_subject" ), null ); 
            Text subject = adaptLayout( tk().createText( subjectField, "", SWT.BORDER ) );

            // message
            Text message = tk().createText( parent, i18n.get( "email_message_text", arena.arena ), SWT.BORDER, SWT.WRAP );
            ColumnDataFactory.on( message ).widthHint( width() ).heightHint( 120 );

            // attachment
            Button attachPreview = adaptLayout( tk().createButton( parent, i18n.get( "email_preview" ), SWT.CHECK ) );
            attachPreview.setSelection( true );

            StringBuilder oreview = new StringBuilder( 1024 )
                    .append( "<img width='" ).append( image.previewWidth )
                    .append( "' height='" ).append( image.previewHeight )
                    .append( "' src='" ).append( image.previewResource )
                    .append( "'/>" );

            ColumnDataFactory.on( tk().createLabel( parent, oreview.toString(), SWT.BORDER ) )
                    .widthHint( Math.min( preferredWidth( parent ), image.previewWidth ) )
                    .heightHint( image.previewHeight );

            fab.setVisible( false );
            fab.addSelectionListener( UIUtils.selectionListener( ev -> {
                try {
                    final String toText = to.getText();
                    final String subjectText = subject.getText();
                    final String messageText = message.getText();
                    final boolean attach = attachPreview.getSelection();
                    DefaultOperation op = new DefaultOperation( i18n.get( "email_title" ) ) {
                        @Override
                        public IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
                            sendEmail( toText, subjectText, messageText, attach, image );
                            return Status.OK_STATUS;
                        }
                    };
                    // execute
                    OperationSupport.instance().execute2( op, false, false, result -> {
                        UIThreadExecutor.async( () -> {
                            if (result.getResult().isOK()) {
                                fab.setEnabled( false );
                                site().tk.get().createSnackbar( Appearance.FadeIn, "Email sent successfully" );
                            }
                            else {
                                StatusDispatcher.handleError( "Unable to send email.", result.getResult().getException() );
                            }
                        });
                    });
                }
                catch (Exception e) {
                    StatusDispatcher.handleError( "Unable to send email.", e );
                }
            }));
        }
    }


    private void sendEmail( final String toText, final String subjectText, final String messageText,
            final boolean withAttachment,
            final ImagePngContent image ) throws Exception {
        MimeMessage msg = new MimeMessage( mailSession() );

        msg.addRecipients( RecipientType.TO, InternetAddress.parse( toText, false ) );
        // TODO we need the FROM from the current user
        msg.addFrom( InternetAddress.parse( "support@mapzone.io" ) );  //ArenaConfigMBean.SMTP_USER ) );
        msg.setReplyTo( InternetAddress.parse( "support@mapzone.io" ) );  //ArenaConfigMBean.SMTP_USER ) );

        msg.setSubject( subjectText, "utf-8" );
        if (withAttachment) {
            // add mime multiparts
            Multipart multipart = new MimeMultipart();

            BodyPart part = new MimeBodyPart();
            part.setText( messageText );
            multipart.addBodyPart( part );

            // Second part is attachment
            part = new MimeBodyPart();
            part.setDataHandler( new DataHandler( new URLDataSource( new URL( image.imgResource ) ) ) );
            part.setFileName( "preview.png" );
            part.setHeader( "Content-ID", "preview" );
            multipart.addBodyPart( part );

            // // third part in HTML with embedded image
            // part = new MimeBodyPart();
            // part.setContent( "<img src='cid:preview'>", "text/html" );
            // multipart.addBodyPart( part );

            msg.setContent( multipart );
        }
        else {
            msg.setText( messageText, "utf-8" );
        }
        msg.setSentDate( new Date() );
        Transport.send( msg );
    }


    private Session mailSession() throws Exception {
        if (session == null) {
            Properties props = new Properties();
            props.put( "mail.smtp.host", "mail.polymap.de" );  //ArenaConfigMBean.SMTP_HOST );
            props.put( "mail.smtp.port", "25" );
            props.put( "mail.smtp.auth", "true" );
            // TODO uncomment if the mail server contains a correct SSL certificate
            // props.put( "mail.smtp.starttls.enable", "true" ); // enable STARTTLS

            // create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                @Override protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication( "support@mapzone.io", "690332" );  // FIXME !!!
                }
            };
            session = Session.getInstance( props, auth );
        }
        return session;
    }


    private int preferredWidth( Composite parent ) {
        return Math.min( parent.getDisplay().getClientArea().width, site().preferredWidth.get() ) - 50;
    }
}
