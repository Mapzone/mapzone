/* 
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.dashboard.IDashlet;
import org.polymap.rhei.batik.dashboard.IDashletSite;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectsDashlet
        implements IDashlet {

    private static Log log = LogFactory.getLog( ProjectsDashlet.class );

    private IDashletSite            site;
    
    
    @Override
    public void init( @SuppressWarnings("hiding") IDashletSite site ) {
        this.site = site;
        site.title().set( "My projects" );
        site.layoutConstraints().get().add( new PriorityConstraint( 10 ) );
        site.layoutConstraints().get().add( new MinWidthConstraint( 450, 0 ) );
    }

    @Override
    public void createContents( Composite parent ) {
        site.toolkit().createFlowText( parent, Joiner.on( "\n" ).join( 
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
