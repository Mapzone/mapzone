/* 
 * polymap.org
 * Copyright (C) 2013, Falko Bräutigam. All rights reserved.
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
package io.mapzone.controller.email;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

import org.polymap.core.runtime.CachedLazyInit;
import org.polymap.core.runtime.Lazy;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class EmailService {

    private static Log log = LogFactory.getLog( EmailService.class );

    /** One, global email service per VM. */
    private static final Lazy<EmailService>     instance = new CachedLazyInit( () -> new EmailService() );
    
    public static EmailService instance() {
        return instance.get(  );
    }

    
    // instance ******************************************* 
    
    public void send( Email email ) throws EmailException {
        String env = System.getProperty( "io.mapzone.controller.SMTP" );
        if (env == null) {
            throw new IllegalStateException( "Environment variable missing: io.mapzone.controller.SMTPP. Format: <host>|<login>|<passwd>|<from>");
        }
        String[] parts = StringUtils.split( env, "|" );
        if (parts.length < 3 || parts.length > 4) {
            throw new IllegalStateException( "Environment variable wrong: io.mapzone.controller.SMTP. Format: <host>|<login>|<passwd>|<from> : " + env );
        }

        email.setDebug( true );
        email.setHostName( parts[0] );
        //email.setSmtpPort( 465 );
        //email.setSSLOnConnect( true );
        email.setAuthenticator( new DefaultAuthenticator( parts[1], parts[2] ) );
        if (email.getFromAddress() == null && parts.length == 4) {
            email.setFrom( parts[3] );
        }
        if (email.getSubject() == null) {
            throw new EmailException( "Missing subject." );
        }
        email.send();
    }
    
    
    public void send( String templateName, Map<String,String> replacements ) {
//        Session session = Session.getDefaultInstance(properties);
//
//        try{
//           // Create a default MimeMessage object.
//           MimeMessage message = new MimeMessage(session);
//
//           // Set From: header field of the header.
//           message.setFrom(new InternetAddress(from));
//
//           // Set To: header field of the header.
//           message.addRecipient(Message.RecipientType.TO,
//                                    new InternetAddress(to));
//
//           // Set Subject: header field
//           message.setSubject("This is the Subject Line!");
//
//           // Now set the actual message
//           message.setText("This is actual message");
//
//           // Send message
//           Transport.send(message);
//           System.out.println("Sent message successfully....");
//        }catch (MessagingException mex) {
//           mex.printStackTrace();
//        }
    }
}
