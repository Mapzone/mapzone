package io.mapzone.controller.ui.project;

import static org.polymap.core.runtime.UIThreadExecutor.asyncFast;
import io.mapzone.controller.ops.DeleteProjectOperation;
import io.mapzone.controller.ui.util.PropertyAdapter;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.um.repository.ProjectHolder;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.security.UserPrincipal;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.PlainValuePropertyAdapter;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
@SuppressWarnings("deprecation")
public class EditProjectPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( EditProjectPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "editProject" );
    
    @Mandatory
    @Scope("io.mapzone.controller")
    protected Context<UserPrincipal>    userPrincipal;
    
    @Mandatory
    @Scope("io.mapzone.controller")
    protected Context<Project>          selected;
    
    private ProjectRepository           nested;
    
    /** The instance that belongs to {@link #nested}. */
    private Project                     nestedProject;
    
    private User                        user;

    private BatikFormContainer          form;

    private Button                      fab;

    private Optional<ProjectHolder>     organizationOrUser = Optional.empty();

    
    @Override
    public void init() {
        getSite().setTitle( "Project" );
        nested = ProjectRepository.instance().newNested();
        user = nested.findUser( userPrincipal.get().getName() )
                .orElseThrow( () -> new RuntimeException( "No such user: " + userPrincipal.get() ) );
        nestedProject = nested.entity( selected.get() );
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().setPreferredWidth( 350 );
        MdToolkit tk = (MdToolkit)getSite().toolkit();
        
//        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( 10 ).create() );
        
//        // welcome
//        IPanelSection welcomeSection = tk.createPanelSection( parent, null );
//        welcomeSection.addConstraint( new MinWidthConstraint( 350, 1 ) );
//        tk.createFlowText( welcomeSection.getBody(), "Changing orginization or name is not yet supported." );

//        // toolbar
//        tk.createToolbar( "Toolbar", SWT.NONE ).addAction( new ActionConfiguration()
//                .name.put( "remove" )
//                .image.put( BatikPlugin.images().svgImage( "ic_delete_48px.svg", SvgImageRegistryHelper.NORMAL24 ) )
//                .showName.put( false )
//                .tooltipText.put( "Remove this project altogether" ) );
        
        // form
        IPanelSection formSection = tk.createPanelSection( parent, "Basic settings" );
        formSection.addConstraint( new MinWidthConstraint( 350, 1 ) );
        form = new BatikFormContainer( new ProjectForm() );
        form.createContents( formSection.getBody() );

        // FAB
        fab = tk.createFab();
        fab.setToolTipText( "Update project" );
        fab.setVisible( false );
        fab.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                try {
                    form.submit( null );
                }
                catch (Exception e) {
                    StatusDispatcher.handleError( "Unable to create project.", e );
                    return;
                }
                // operation
                DefaultOperation op = new DefaultOperation( "Update project" ) {
                    @Override
                    public IStatus doExecute( IProgressMonitor monitor, IAdaptable info ) throws Exception {
                        monitor.beginTask( getLabel(), 1 );
                        nested.commit();
                        EventManager.instance().publish( new EntityChangedEvent( nestedProject ) );
                        return Status.OK_STATUS;
                    }
                };
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
        });
        
        // delete project
        IPanelSection deleteSection = tk.createPanelSection( parent, "Danger zone" );
        Button deleteBtn = tk.createButton( deleteSection.getBody(), "Destroy this project", SWT.PUSH );
        deleteBtn.setToolTipText( "Delete everything!<br/><b>There is no way to get the data back!</b>" );
        deleteBtn.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
//                MdSnackbar snackbar = tk.createSnackbar();
//                snackbar.showIssue( MessageType.WARNING, "We are going to delete the project." );
                
                DeleteProjectOperation op = new DeleteProjectOperation();
                op.repo.set( nested );
                op.project.set( nestedProject );

                // execute sync as long as there is no progress indicator
                OperationSupport.instance().execute2( op, false, false, ev2 -> asyncFast( () -> {
                    if (ev2.getResult().isOK()) {
                        getContext().closePanel( site().path() );
                    }
                    else {
                        StatusDispatcher.handleError( "Unable to delete project.", ev2.getResult().getException() );
                    }
                }));
            }
        });
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
            site.newFormField( new PlainValuePropertyAdapter( "organizationOrUser", nestedProject.organizationOrUser().name.get() ) )
                    .label.put( "Organization" )
                    .tooltip.put( "Changing organization is not yet supported." )
                    .fieldEnabled.put( false )
                    .create();
            
            // name
            site.newFormField( new PropertyAdapter( nestedProject.name ) )
                    .tooltip.put( "Changing name is not yet supported." )
                    .fieldEnabled.put( false )
                    .create();
            
            // description
            site.newFormField( new PropertyAdapter( nestedProject.description ) ).create();
            
            // website
            site.newFormField( new PropertyAdapter( nestedProject.website ) ).create();
            
            // location
            site.newFormField( new PropertyAdapter( nestedProject.location ) ).create();
            
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
