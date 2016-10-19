package io.mapzone.controller.ui.project;

import static org.polymap.core.runtime.UIThreadExecutor.asyncFast;
import static org.polymap.rhei.batik.toolkit.md.dp.dp;
import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.ops.CreateProjectOperation;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.um.repository.Project;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.polymap.core.operation.OperationSupport;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper.Quadrant;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

import org.polymap.cms.ContentProvider;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CreateProjectPanel
        extends CtrlPanel {

    private static Log log = LogFactory.getLog( CreateProjectPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "createProject" );

    private final Image             icon = ControllerPlugin.images().svgOverlayedImage( 
            "map.svg", ControllerPlugin.HEADER_ICON_CONFIG,
            "plus-circle-filled.svg", SvgImageRegistryHelper.OVR12_ACTION, 
            Quadrant.TopRight );

    /**
     * The operation to be prepared and executed by this panel.
     */
    private CreateProjectOperation  op;

    private BatikFormContainer      projectForm;

    private BatikFormContainer      launcherForm;

    private Button                  fab;

    
    @Override
    public boolean wantsToBeShown() {
//        if (parentPanel().get() instanceof DashboardPanel) {
//            site().title.set( "" );
//            site().icon.set( icon );
//            return true;
//        }
        return false;
    }


    @Override
    public void init() {
        super.init();
        site().title.set( "New project" );
        
        op = new CreateProjectOperation( userPrincipal.get().getName() );
        op.createProject();
    }


    @Override
    public void dispose() {
        op.cleanup();
    }


    @Override
    public void createContents( Composite parent ) {
        MdToolkit tk = (MdToolkit)getSite().toolkit();
        
//        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( 10 ).create() );
        
        // welcome
        IPanelSection welcomeSection = tk.createPanelSection( parent, "Set up a new project" );
        welcomeSection.addConstraint( new PriorityConstraint( 100 ), new MinWidthConstraint( 1000, 1 ) );
        tk.createFlowText( welcomeSection.getBody(), 
                ContentProvider.instance().findContent( "ui/createProject-welcome.md" ).content() )
                .setEnabled( false );

        // Project
        IPanelSection projectSection = tk.createPanelSection( parent, "Project", SWT.BORDER );
        projectSection.addConstraint( new PriorityConstraint( 10 ) );
        projectSection.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( dp(10).pix() ).create() );
        
        ProjectForm formPage = new ProjectForm( op.umUow.get(), op.project.get(), op.user.get() ) {
            @Override
            protected void updateEnabled() {
                CreateProjectPanel.this.updateEnabled();
            }
        };
        projectForm = new BatikFormContainer( formPage.creation.put( true ) );
        projectForm.createContents( projectSection.getBody() );

//        // ProjectLauncher
//        IPanelSection launcherSection = tk.createPanelSection( parent, "Project launcher", SWT.BORDER );
//        launcherSection.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( dp(10).pix() ).create() );
//        
//        launcherForm = new BatikFormContainer( new ProjectForm() );
//        launcherForm.createContents( launcherSection.getBody() );

        // FAB
        fab = tk.createFab();
        fab.setToolTipText( "Create the new project" );
        fab.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                submit( ev );
            }
        } );
    }


    protected void submit( SelectionEvent ev ) {
        try {
            projectForm.submit( null );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "Unable to create project.", e );
            return;
        }
        
        // XXX execute sync as long as there is no progress indicator
        OperationSupport.instance().execute2( op, false, false, ev2 -> asyncFast( () -> {
            if (ev2.getResult().isOK()) {
                PanelPath panelPath = getSite().getPath();
                getContext().closePanel( panelPath );                        
            }
            else {
                StatusDispatcher.handleError( "Unable to create project.", ev2.getResult().getException() );
            }
        }));
        
    }
    
    protected void updateEnabled() {
        fab.setVisible( projectForm.isDirty() && projectForm.isValid() );
    }
    
    
    /**
     * {@link Project} settings. 
     */
    class ProjectLauncherForm
            extends DefaultFormPage { 
        
        @Override
        public void createFormContents( IFormPageSite site ) {
            super.createFormContents( site );
            
            Composite body = site.getPageBody();
            body.setLayout( ColumnLayoutFactory.defaults()
                    .spacing( 5 /*panelSite.getLayoutPreference( LAYOUT_SPACING_KEY ) / 4*/ )
                    .margins( getSite().getLayoutPreference().getSpacing() / 2 ).create() );
        }
    }
    
}
