/* 
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.IPanel;
import org.polymap.rhei.batik.PanelIdentifier;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ActivitiesPanel
        extends DefaultPanel
        implements IPanel {

    private static Log log = LogFactory.getLog( ActivitiesPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "activities" );

    
    @Override
    public boolean wantsToBeShown() {
        if (getSite().getPath().size() == 1) {
            getSite().setTitle( "Audit log" );
            return true;            
        }
        return false;
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().toolkit().createFlowText( parent, Joiner.on( "\n" ).join( 
                "### Projects  ",
                "\n  * **15/02/2015** - Project created: Waldbesitzerverzeichnis",
                "\n  * **14/02/2015** - Project updated: Waldbesitzerverzeichnis",
                "\n### Data  ",
                "\n  * **01/01/2015** - Waldbesitzer: 5 Objects modified",
                "\n  * **02/01/2015** - Waldbesitzer: 1 Objects added",
                "\n  * **10/01/2015** - Waldbesitzer: 1 Objects added",
                "\n  * **11/01/2015** - Waldbesitzer: 1 Objects modified"
                ));
    }

}
