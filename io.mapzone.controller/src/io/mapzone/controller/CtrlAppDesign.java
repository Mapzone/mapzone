package io.mapzone.controller;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.polymap.core.ui.UIUtils;

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
        return null;
//        Composite result = new Composite( parent, SWT.NO_FOCUS | SWT.BORDER );
//        UIUtils.setVariant( result, IAppDesign.CSS_HEADER );
//        result.setLayout( FormLayoutFactory.defaults().margins( 6, 3 ).create() );
//        
//        DefaultToolkit tk = new DefaultToolkit( null );
//        Label title = tk.createFlowText( result, "<div style=\"font-size:15px;\">" + 
//                "<a href=\"http://mapzone.io\">mapzone</a> | " +
//                "<a href=\"http://mapzone.io\">Blog</a>" +
//                //"Sign in: <a href=\"http://mapzone.io\">falko</a>" +
//                "</div>", SWT.LEFT );
//        tk.close();
//        
////        Label title = UIUtils.setVariant( new Label( result, SWT.NONE ), IAppDesign.CSS_HEADER );
////        title.setText( "mapzone " );
//
//        FormDataFactory.on( title ).fill().noRight().height( 18 ).width( 300 ).noBottom();
//        return result;
    }

}
