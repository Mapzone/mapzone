package io.mapzone.controller.ui;

import org.apache.commons.lang3.StringUtils;

import io.mapzone.controller.Messages;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.runtime.i18n.IMessages;

import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.dashboard.IDashlet;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

import org.polymap.cms.ContentProvider;
import org.polymap.cms.ContentProvider.ContentObject;

/**
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class WelcomeDashlet
        extends DefaultDashlet
        implements IDashlet {
    
    private static final IMessages      i18n = Messages.forPrefix( "WelcomeMessage" );
    
    private String          content;
    
    private String          title;

    
    @Override
    public void init( DashletSite site ) {
        super.init( site );

        ContentProvider cp = ContentProvider.instance();
        ContentObject co = cp.findContent( "frontpage/welcome.md" );
        if (!co.exists()) {
            co.putContent( "# Welcome" );
        }

        content = co.content();
        if (content.startsWith( "#" )) {
              title = StringUtils.substringBefore( content, "\n" ).substring( 1 );
              content = content.substring( title.length() + 2 );
        }

        site.title.set( title );
        site.constraints.get().add( new PriorityConstraint( 100 ) );
    }

    
    @Override
    public void createContents( Composite parent ) {
        getSite().toolkit().createFlowText( parent, content );
        
//        getSite().toolkit().createFlowText( parent, i18n.get( "msg" ) );
    }
    
}
