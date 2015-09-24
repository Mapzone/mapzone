package io.mapzone.controller.ui;

import org.apache.commons.lang3.StringUtils;

import io.mapzone.controller.Messages;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.runtime.i18n.IMessages;

import org.polymap.rhei.batik.dashboard.DashletSite;
import org.polymap.rhei.batik.dashboard.DefaultDashlet;
import org.polymap.rhei.batik.dashboard.IDashlet;
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
    
    private String          contentPath;
    
    private String          content;
    
    private String          title;

    
    public WelcomeDashlet( String contentPath ) {
        this( contentPath, null );
    }


    public WelcomeDashlet( String contentPath, String title ) {
        assert contentPath != null;
        this.contentPath = contentPath;
        this.title = title;
    }


    @Override
    public void init( DashletSite site ) {
        super.init( site );

        ContentProvider cp = ContentProvider.instance();
        ContentObject co = cp.findContent( contentPath );

        content = co.content();
        if (title == null && content.startsWith( "#" )) {
              title = StringUtils.substringBefore( content, "\n" ).substring( 1 );
              content = content.substring( title.length() + 2 );
        }

        site.title.set( title );
    }

    
    @Override
    public void createContents( Composite parent ) {
        getSite().toolkit().createFlowText( parent, content );
        
//        getSite().toolkit().createFlowText( parent, i18n.get( "msg" ) );
    }
    
}
