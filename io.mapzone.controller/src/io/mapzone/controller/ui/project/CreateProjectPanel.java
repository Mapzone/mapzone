package io.mapzone.controller.ui.project;

import static org.polymap.rhei.batik.toolkit.md.dp.dp;
import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.ops.CreateProjectOperation;
import io.mapzone.controller.ui.DashboardPanel;
import io.mapzone.controller.ui.util.PropertyAdapter;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectHolder;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.ui.forms.widgets.ColumnLayoutData;

import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.security.UserPrincipal;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper.Quadrant;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.NotEmptyValidator;
import org.polymap.rhei.field.PicklistFormField;
import org.polymap.rhei.field.PlainValuePropertyAdapter;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class CreateProjectPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( CreateProjectPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "createProject" );

    private final Image             icon = ControllerPlugin.images().svgOverlayedImage( 
            "map.svg", SvgImageRegistryHelper.NORMAL24,
            "plus-circle-filled.svg", SvgImageRegistryHelper.OVR12_ACTION, 
            Quadrant.TopRight );

    @Scope("io.mapzone.controller")
    protected Context<UserPrincipal> userPrincipal;
    
    private ProjectRepository       nested;
    
    private Project                 project;
    
    private User                    user;

    private BatikFormContainer      form;

    private Button                  fab;

    private Optional<ProjectHolder> organizationOrUser = Optional.empty();


    
    @Override
    public boolean wantsToBeShown() {
        if (parentPanel().get() instanceof DashboardPanel) {
            getSite().setTitle( "" );
            getSite().setIcon( icon );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        getSite().setTitle( "New project" );
        nested = ProjectRepository.instance().newNested();
        user = nested.findUser( userPrincipal.get().getName() )
                .orElseThrow( () -> new RuntimeException( "No such user: " + userPrincipal.get() ) );
        project = nested.createEntity( Project.class, null );
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().setPreferredWidth( 350 );
        MdToolkit tk = (MdToolkit)getSite().toolkit();
        
//        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( 10 ).create() );
        
//        // welcome
//        IPanelSection welcomeSection = tk.createPanelSection( parent, "New project" );
//        welcomeSection.addConstraint( new MinWidthConstraint( 350, 1 ) );
//        tk.createFlowText( welcomeSection.getBody(), "Choose an **Organization** your are member of. Or you choose to create a **personal** project. Personal projects can be asigned to an Organization later." );

        // form
        IPanelSection formSection = tk.createPanelSection( parent, "Set up a new project" );
        formSection.getBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( dp(10).pix() ).create() );
        
        tk.createFlowText( formSection.getBody(), "Choose an **Organization** your are member of. Or you choose to create a **personal** project. Personal projects can be asigned to an Organization later." )
                .setLayoutData( new ColumnLayoutData( 350 ) );

        form = new BatikFormContainer( new ProjectForm() );
        form.createContents( formSection.getBody() );

        // FAB
        fab = tk.createFab();
        fab.setToolTipText( "Create the new project" );
        fab.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                try {
                    form.submit();
                }
                catch (Exception e) {
                    StatusDispatcher.handleError( "Unable to create project.", e );
                    return;
                }
                
                // associate to organization or user
                organizationOrUser.get().projects.add( project );
                
                // prepare operation
                CreateProjectOperation op = new CreateProjectOperation();
                op.repo.set( nested );
                op.project.set( project );
                op.organizationOrUser.set( organizationOrUser.get() );
                
                // execute
                OperationSupport.instance().execute2( op, true, false, ev2 -> UIThreadExecutor.asyncFast( () -> {
                    if (ev2.getResult().isOK()) {
                        PanelPath panelPath = getSite().getPath();
                        getContext().closePanel( panelPath );                        
                    }
                    else {
                        StatusDispatcher.handleError( "Unable to create project.", ev2.getResult().getException() );
                    }
                }));
            }
        } );
    }

    
    protected void updateEnabled() {
        fab.setVisible( form.isDirty() && form.isValid() );
    }
    
    
    /**
     * 
     */
    class ProjectForm
            extends DefaultFormPage 
            implements IFormFieldListener {
        
        @Override
        public void createFormContents( IFormPageSite site ) {
            super.createFormContents( site );
            
            Composite body = site.getPageBody();
            body.setLayout( ColumnLayoutFactory.defaults()
                    .spacing( 5 /*panelSite.getLayoutPreference( LAYOUT_SPACING_KEY ) / 4*/ )
                    .margins( getSite().getLayoutPreference().getSpacing() / 2 ).create() );
            
            // organization
            Map<String,ProjectHolder> orgs = user.organizations.stream().collect( Collectors.toMap( o -> o.name.get(), o -> o ) );
            orgs.put( user.name.get(), user );
            site.newFormField( new PlainValuePropertyAdapter( "organizationOrUser", null ) )
                    .label.put( "Organization or User" )
                    .field.put( new PicklistFormField( orgs ) )
                    .tooltip.put( "" )
                    .create();
            
            // name
            site.newFormField( new PropertyAdapter( project.name ) )
                    .validator.put( new NotEmptyValidator<String,String>() {
                        @Override
                        public String validate( String fieldValue ) {
                            String result = super.validate( fieldValue );
                            if (result == null) {
                                if (organizationOrUser.isPresent()) {
                                    if (nested.findProject( organizationOrUser.get().name.get(), (String)fieldValue ).isPresent()) { 
                                        result = "Project name is already taken";
                                    }
                                }
                                else {
                                    result = "Choose an organization first";
                                }
                            };
                            return result;
                        }
                    }).create();
            
            // description
            site.newFormField( new PropertyAdapter( project.description ) ).create();
            
            // website
            site.newFormField( new PropertyAdapter( project.website ) ).create();
            
            // location
            site.newFormField( new PropertyAdapter( project.location ) ).create();
            
            site.addFieldListener( this );
        }

        
        @Override
        public void fieldChange( FormFieldEvent ev ) {
            if (ev.getEventCode() == VALUE_CHANGE) {
                if (ev.getFieldName().equals( "organizationOrUser" )) {
                    organizationOrUser = ev.getNewModelValue();
                }
                updateEnabled();
            }
        }
        
    }
    
}
