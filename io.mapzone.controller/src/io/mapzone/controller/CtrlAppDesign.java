package io.mapzone.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.app.IAppDesign;
import org.polymap.rhei.batik.toolkit.md.MdAppDesign;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CtrlAppDesign
        extends MdAppDesign {

    
    @Override
    public Shell createMainWindow( @SuppressWarnings("hiding") Display display ) {
        super.createMainWindow( display );
        
        // FIXME
        UIUtils.activateCallback( "cms-fix-links" );
        
        return mainWindow;
    }

    
    @Override
    protected Composite fillHeaderArea( Composite parent ) {
        Composite result = new Composite( parent, SWT.NO_FOCUS | SWT.BORDER );
        UIUtils.setVariant( result, IAppDesign.CSS_HEADER );
        result.setLayout( FormLayoutFactory.defaults().margins( 0, 0 ).margins( 0, 3 ).create() );
        Label title = UIUtils.setVariant( new Label( result, SWT.NONE ), IAppDesign.CSS_HEADER );
        title.setText( "mapzone " );
        FormDataFactory.on( title ).fill();
        return result;
    }

}
