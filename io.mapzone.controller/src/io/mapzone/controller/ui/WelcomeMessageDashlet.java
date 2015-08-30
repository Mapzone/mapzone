package io.mapzone.controller.ui;

import io.mapzone.controller.Messages;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.runtime.i18n.IMessages;

import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.dashboard.IDashlet;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

/**
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class WelcomeMessageDashlet
        extends DefaultDashlet
        implements IDashlet {
    
    private static final IMessages      i18n = Messages.forPrefix( "WelcomeMessage" );

    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( i18n.get( "title" ) );
        site.constraints.get().add( new PriorityConstraint( 100 ) );
    }

    @Override
    public void createContents( Composite parent ) {
        getSite().toolkit().createFlowText( parent, i18n.get( "msg" ) );
    }
    
}
