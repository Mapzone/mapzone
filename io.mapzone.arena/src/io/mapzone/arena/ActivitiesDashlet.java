/* 
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.dashboard.IDashlet;
import org.polymap.rhei.batik.dashboard.DashletSite;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ActivitiesDashlet
        implements IDashlet {

    private static Log log = LogFactory.getLog( ActivitiesDashlet.class );
    
    private DashletSite            site;

    
    @Override
    public void init( @SuppressWarnings("hiding") DashletSite site ) {
        this.site = site;
        site.title.set( "My activities" );
        //site.isBoxStyle().set( true );
    }


    @Override
    public void createContents( Composite parent ) {
        site.toolkit().createFlowText( parent, Joiner.on( "\n" ).join( 
                "### Projects  ",
                "\n  * **15/02/2015** - Project created: Waldbesitzerverzeichnis",
                "\n  * **14/02/2015** - Project updated: Waldbesitzerverzeichnis",
                "\n### Data  ",
                "\n  * **01/01/2015** - Waldbesitzer: 5 Objects modified",
                "\n  * **02/01/2015** - Waldbesitzer: 1 Objects added",
                "\n  * **10/01/2015** - Waldbesitzer: 1 Objects added",
                "\n  * **11/01/2015** - Waldbesitzer: 1 Objects modified"
                ));

        //site.toolkit().createButton( parent, "Press me ...", SWT.PUSH );
        //site.toolkit().createFlowText( parent, "Noch einbißchen Text zum stopfen. Muss hier hin, um zu sehen wie das Layout dann wirkt." );
    }
    
}
