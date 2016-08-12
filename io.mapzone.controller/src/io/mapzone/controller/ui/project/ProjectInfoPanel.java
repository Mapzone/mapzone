package io.mapzone.controller.ui.project;

import static org.polymap.core.runtime.UIThreadExecutor.asyncFast;
import static org.polymap.core.ui.FormDataFactory.on;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.security.UserPrincipal;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.PlainValuePropertyAdapter;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

import io.mapzone.controller.ops.DeleteProjectOperation;
import io.mapzone.controller.ops.UpdateProjectOperation;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.ui.util.PropertyAdapter;
import io.mapzone.controller.um.repository.AuthToken;
import io.mapzone.controller.um.repository.Project;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectInfoPanel
        extends CtrlPanel {

    private static Log log = LogFactory.getLog( ProjectInfoPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "editProject" );
    
    @Mandatory
    @Scope("io.mapzone.controller")
    protected Context<UserPrincipal>    userPrincipal;
    
    @Mandatory
    @Scope("io.mapzone.controller")
    protected Context<Project>          selected;
    
    /**
     * The operation to be prepared and executed by this panel.
     */
    private UpdateProjectOperation      op;
    
    private BatikFormContainer          form;

    private Button                      fab;

    
    @Override
    public void init() {
        super.init();
        site().title.set( "Project" );
        
        op = new UpdateProjectOperation( userPrincipal.get().getName() );
        op.project.set( selected.get() );
    }


    @Override
    public void createContents( Composite parent ) {
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
        
        createFormSection( parent );
        createAuthSection( parent );
        createDeleteSection( parent );

        // FAB
        fab = tk().createFab();
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
    }


    protected void createFormSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "Basic settings" );
        section.addConstraint( new PriorityConstraint( 10 ), new MinWidthConstraint( 350, 1 ) );
        form = new BatikFormContainer( new ProjectForm() );
        form.createContents( section.getBody() );
    }


    protected void createAuthSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "API token" );
        section.addConstraint( new PriorityConstraint( 1 ), new MinWidthConstraint( 350, 1 ) );
        section.getBody().setLayout( FormLayoutFactory.defaults().margins( 3 ).spacing( 5 ).create() );
        
        Label msg = tk().createLabel( section.getBody(), "This token ..." );
        
        Optional<AuthToken> authToken = op.project.get().serviceAuthToken();
        Text text = tk().createText( section.getBody(), "<No auth token>", SWT.BORDER, SWT.READ_ONLY );
        authToken.ifPresent( t -> text.setText( t.toString() ) );
        //text.setBackground( text.getParent().getBackground() );
        
        Button createBtn = tk().createButton( section.getBody(), "New token" );
        createBtn.setToolTipText( "Creates a new token and <b>discards</b> the old one.<br/>Update/save project afterwards to <b>activate</b> the new token." );
        createBtn.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                AuthToken newToken = op.project.get().newServiceAuthToken( new NullProgressMonitor() );
                text.setText( newToken.toString() );
                updateEnabled();
            }
        });

        on( msg ).fill().noBottom();
        on( createBtn ).top( msg ).right( 100 );
        on( text ).top( msg, 1 ).left( 0 ).right( createBtn );
    }


    protected void createDeleteSection( Composite parent ) {
        // delete project
        IPanelSection section = tk().createPanelSection( parent, "Danger zone" );
        section.addConstraint( new PriorityConstraint( 0 ), new MinWidthConstraint( 350, 1 ) );
        Button deleteBtn = tk().createButton( section.getBody(), "Destroy this project", SWT.PUSH );
        deleteBtn.setToolTipText( "Delete everything!<br/><b>There is no way to get the data back!</b>" );
        deleteBtn.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
//                MdSnackbar snackbar = tk.createSnackbar();
//                snackbar.showIssue( MessageType.WARNING, "We are going to delete the project." );
                
                DeleteProjectOperation dop = new DeleteProjectOperation();
                dop.project.set( selected.get() );

                // execute sync as long as there is no progress indicator
                OperationSupport.instance().execute2( dop, false, false, ev2 -> asyncFast( () -> {
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
        fab.setVisible( 
                form.isDirty() && form.isValid() ||
                !Objects.equals( selected.get().serviceAuthToken.get(), op.project.get().serviceAuthToken.get() ) );
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
            site.newFormField( new PlainValuePropertyAdapter( "organizationOrUser", op.project.get().organization.get().name.get() ) )
                    .label.put( "Organization" )
                    .tooltip.put( "Changing organization is not yet supported." )
                    .fieldEnabled.put( false )
                    .create();
            
            // name
            site.newFormField( new PropertyAdapter( op.project.get().name ) )
                    .tooltip.put( "Changing name is not yet supported." )
                    .fieldEnabled.put( false )
                    .create();
            
            // description
            site.newFormField( new PropertyAdapter( op.project.get().description ) ).create();
            
            // website
            site.newFormField( new PropertyAdapter( op.project.get().website ) ).create();
            
            // location
            site.newFormField( new PropertyAdapter( op.project.get().location ) ).create();
            
            site.addFieldListener( this );
        }

        
        @Override
        public void fieldChange( FormFieldEvent ev ) {
            if (ev.getEventCode() == VALUE_CHANGE) {
//                if (ev.getFieldName().equals( "organizationOrUser" )) {
//                    ev.getNewModelValue().ifPresent( v -> op.organization.set( (Organization)v ) );
//                   // op.organization.set( (Organization)ev.getNewModelValue().orElse( null ) );
//                }
                updateEnabled();
            }
        }
        
    }
    
}
