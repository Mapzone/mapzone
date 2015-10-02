package io.mapzone.arena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.IPanel;
import org.polymap.rhei.batik.PanelIdentifier;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class StartPanel
        extends DefaultPanel
        implements IPanel {

    private static Log log = LogFactory.getLog( StartPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "start" );

    
    @Override
    public boolean wantsToBeShown() {
        return getSite().getPath().size() == 1;
    }


    @Override
    public void init() {
    }


    @Override
    public void createContents( Composite parent ) {
        // FIXME
        UIUtils.activateCallback( "fix-flowtext-link-actions" );
    }

}
