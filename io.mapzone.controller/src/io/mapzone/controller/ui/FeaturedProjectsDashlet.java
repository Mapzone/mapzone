package io.mapzone.controller.ui;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.Messages;

import com.google.common.base.Joiner;

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
public class FeaturedProjectsDashlet
        extends DefaultDashlet
        implements IDashlet {
    
    private static final IMessages      i18n = Messages.forPrefix( "FeaturedProjects" );

    @Override
    public void init( DashletSite site ) {
        super.init( site );
        site.title.set( i18n.get( "title" ) );
        site.constraints.get().add( new PriorityConstraint( 0 ) );
        site.constraints.get().add( ControllerPlugin.MIN_WIDTH );
    }

    
    @Override
    public void createContents( Composite parent ) {
        getSite().toolkit().createFlowText( parent, Joiner.on( "\n" ).join( 
                "### [Waldbesitzerverzeichnis](@open/project)  ",
                "Unsere Waldbesitzer als Karte und Dienst. Zugriff von außen möglich.  ",
                "\n  * Admin: Steffen Stundzig",
                "  * Last update: 6 hours ago",
                "\n----\n",
                "\n### [Wahlbeteiligung Sachsen](@open/project)  ",
                "Offene Daten aus OpenGov.de und OSM Karte",
                "\n  * Admin: Steffen Stundzig",
                "  * Last update: 25.02.1871",
                "\n----\n",
                "\n### [Überflutungsgebiete Westsachsen]()  ",
                "Wissenschaftliches Projekt aus Höhendaten und Andreas Modulen",
                "\n  * Admin: Steffen Stundzig",
                "  * Last update: irgendwann"
                ));
    }
    
}
