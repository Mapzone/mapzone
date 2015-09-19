/* 
 * polymap.org
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

import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.security.UserPrincipal;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UserProfileDashlet
        extends DefaultDashlet {

    private static Log log = LogFactory.getLog( UserProfileDashlet.class );
    
    @Scope("io.mapzone.controller")
    protected Context<UserPrincipal>        userPrincipal;
    
    private ProjectRepository               repo = ProjectRepository.instance();
    
    private User                            user;
    
    private Label                           flowtext;

    
    @Override
    public void init( DashletSite site ) {
        super.init( site );
        user = repo.findUser( userPrincipal.get().getName() )
                .orElseThrow( () -> new RuntimeException( "No such user: " + userPrincipal.get() ) );
        
        site.title.set( "Profile of " + user.name.get() );
        
        EventManager.instance().subscribe( this, ev -> ev instanceof EntityChangedEvent && 
                ((EntityChangedEvent)ev).getEntity().id().equals( user.id() ) );
    }

    
    @EventHandler( display=true )
    protected void userChanged( EntityChangedEvent ev ) {
        flowtext.setText( createFlowtext() );
    }

    
    @Override
    public void createContents( Composite parent ) {
        MdToolkit tk = (MdToolkit)getSite().toolkit();
        flowtext = tk.createFlowText( parent, createFlowtext() );
    }
    
    
    protected String createFlowtext() {
        return Joiner.on( "\n" ).join(
                "## " + user.fullname.get(),
                createLine( "account-multiple-outline.svg", user.company.get(), null ),
                createLine( "email-outline.svg", user.email.get(), "mailto:" + user.email.get() ),
                createLine( "link-variant.svg", user.website.get(), "http://" + user.website.get() ),
                createLine( "map-marker.svg", user.location.get(), null ),
                createLine( "clock.svg", "Joined on ...", null ),
                "<br/>" );
    }

    
    protected String createLine( String svg, String text, String link ) {
        return Joiner.on( "" ).join( 
                "* <span style=\"vertical-align:middle\">![", svg, "](#", svg, "@normal12)</span> ",
                (link != null
                        ? Joiner.on( "" ).join( "[", text, "](", link, ")" )
                        : text),
                "<br/>" );
    }
}
