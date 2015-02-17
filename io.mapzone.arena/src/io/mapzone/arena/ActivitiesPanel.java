/* 
 * Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 */
package io.mapzone.arena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.IAppContext;
import org.polymap.rhei.batik.IPanel;
import org.polymap.rhei.batik.IPanelSite;
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
    public boolean init( IPanelSite site, IAppContext context ) {
        super.init( site, context );
        if (site.getPath().size() == 1) {
            getSite().setTitle( "Audit log" );
            return true;
        }
        return false;
    }


    @Override
    public void createContents( Composite parent ) {
    }


    @Override
    public PanelIdentifier id() {
        return ID;
    }
    
}
