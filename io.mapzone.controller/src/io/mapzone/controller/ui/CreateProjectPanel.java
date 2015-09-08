package io.mapzone.controller.ui;

import io.mapzone.controller.um.operations.CreateProjectOperation;
import io.mapzone.controller.um.repository.Named;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.NotEmptyValidator;
import org.polymap.rhei.field.PicklistFormField;
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
    
    @Scope("io.mapzone.controller")
    private Context<String>         username;
    
    private ProjectRepository       nested;
    
    private Project                 project;
    
    private User                    user;

    private BatikFormContainer      form;

    private Button                  fab;
    
    
    @Override
    public boolean wantsToBeShown() {
        if (parentPanel().get() instanceof DashboardPanel) {
            getSite().setTitle( "Create project" );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        nested = ProjectRepository.instance().newNested();
        user = nested.findUser( username.get() ).orElseThrow( () -> new RuntimeException( "No user!" ) );
        project = nested.createEntity( Project.class, null );
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().setPreferredWidth( 350 );
        MdToolkit tk = (MdToolkit)getSite().toolkit();
        
//        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( 10 ).create() );
        
        // welcome
        IPanelSection welcomeSection = tk.createPanelSection( parent, "New project" );
        welcomeSection.addConstraint( new MinWidthConstraint( 350, 1 ) );
        tk.createFlowText( welcomeSection.getBody(), "Choose an **Organization** your are member of. Or you choose to create a **personal** project. Personal projects can be asigned to an Organization later." );

        // form
        IPanelSection formSection = tk.createPanelSection( parent, "Set up the project" );
        form = new BatikFormContainer( new ProjectForm() );
        form.createContents( formSection.getBody() );

        // FAB
        fab = tk.createFab();
        fab.setToolTipText( "Create the new project" );
        fab.setEnabled( false );
        fab.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                CreateProjectOperation op = new CreateProjectOperation();
                op.repo.set( nested );
                op.project.set( project );
                
                OperationSupport.instance().execute2( op, true, true, ev2 -> UIThreadExecutor.asyncFast( () -> {
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
        fab.setEnabled( form.isDirty() && form.isValid() );
    }
    
    
    /**
     * 
     */
    class ProjectForm
            extends DefaultFormPage 
            implements IFormFieldListener {
        
        private Optional<Named>     organizationOrUser = Optional.empty();

        @Override
        public void createFormContents( IFormPageSite site ) {
            super.createFormContents( site );
            
            Composite body = site.getPageBody();
            body.setLayout( ColumnLayoutFactory.defaults()
                    .spacing( 5 /*panelSite.getLayoutPreference( LAYOUT_SPACING_KEY ) / 4*/ )
                    .margins( getSite().getLayoutPreference().getSpacing() / 2 ).create() );
            
            // organization
            Map<String,Named> orgs = user.organizations.stream().collect( Collectors.toMap( o -> o.name.get(), o -> o ) );
            orgs.put( user.name.get(), user ); 
            site.newFormField( new AssociationAdapter( project.organization ) )
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
                if (ev.getFieldName().equals( project.organization.info().getName() )) {
                    organizationOrUser = ev.getNewModelValue();
                }
                updateEnabled();
            }
        }
        
    }
    
}
