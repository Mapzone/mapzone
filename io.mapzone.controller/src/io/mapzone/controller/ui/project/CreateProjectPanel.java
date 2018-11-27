package io.mapzone.controller.ui.project;

import static org.polymap.core.runtime.UIThreadExecutor.asyncFast;
import static org.polymap.rhei.batik.toolkit.md.dp.dp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.action.Action;

import org.polymap.core.operation.OperationSupport;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Propagate;
import org.polymap.rhei.batik.Propagate.Propagation;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper.Quadrant;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;
import org.polymap.rhei.batik.toolkit.md.MdActionbar;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

import org.polymap.cms.ContentProvider;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.ops.CreateProjectOperation;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.um.repository.Project;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CreateProjectPanel
        extends CtrlPanel {

    private static final Log log = LogFactory.getLog( CreateProjectPanel.class );

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

    private Action                  submit;

    @Propagate( {Propagation.UP, Propagation.ONESHOT} )
    @Scope( "io.mapzone.controller" )
    protected Context<Project>      created;

    
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
        site().title.set( "New Project" );
        site().setSize( SIDE_PANEL_WIDTH2, SIDE_PANEL_WIDTH2, SIDE_PANEL_WIDTH2 );
        
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
            @Override protected void updateEnabled() {
                CreateProjectPanel.this.updateEnabled();
            }
        };
        formPage.creation.put( true ).tk.put( tk() );
        projectForm = new BatikFormContainer( formPage );
        projectForm.createContents( projectSection.getBody() );

//        // ProjectLauncher
//        IPanelSection launcherSection = tk.createPanelSection( parent, "Project launcher", SWT.BORDER );
//        launcherSection.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( dp(10).pix() ).create() );
//        
//        launcherForm = new BatikFormContainer( new ProjectForm() );
//        launcherForm.createContents( launcherSection.getBody() );

        // submit
        MdActionbar ab = tk().createFloatingActionbar();
        submit = ab.addSubmit( "Create Project", a -> submit() );
    }


    protected void submit() {
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
                //getContext().closePanel( panelPath );
                
                created.set( op.project.get() );
                getContext().openPanel( panelPath.removeLast( 1 ), ProjectInfoPanel.ID );                        
            }
            else {
                StatusDispatcher.handleError( "Unable to create project.", ev2.getResult().getException() );
            }
        }));
    }

    
    protected void updateEnabled() {
        submit.setEnabled( projectForm.isDirty() && projectForm.isValid() );
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
