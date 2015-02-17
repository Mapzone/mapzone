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
public class ProjectPanel
        extends DefaultPanel
        implements IPanel {

    private static Log log = LogFactory.getLog( ProjectPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "project" );

    
    @Override
    public boolean init( IPanelSite site, IAppContext context ) {
        super.init( site, context );
        return false;
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().setTitle( "Waldbesitzer" );
    }


    @Override
    public PanelIdentifier id() {
        return ID;
    }
    
}
