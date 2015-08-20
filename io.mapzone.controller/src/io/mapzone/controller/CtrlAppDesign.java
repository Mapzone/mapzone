package io.mapzone.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

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

    protected Composite fillHeaderArea( Composite parent ) {
        Composite result = new Composite( parent, SWT.NO_FOCUS | SWT.BORDER );
        UIUtils.setVariant( result, IAppDesign.CSS_HEADER );
        result.setLayout( FormLayoutFactory.defaults().margins( 0, 0 ).create() );
        Label title = UIUtils.setVariant( new Label( result, SWT.NONE ), IAppDesign.CSS_HEADER );
        title.setText( "Mapzone" );
        return result;
    }

}
