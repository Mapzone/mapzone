package io.mapzone.controller.ui.project;

import static org.polymap.core.runtime.UIThreadExecutor.asyncFast;
import static org.polymap.core.ui.FormDataFactory.on;
import static org.polymap.rhei.batik.app.SvgImageRegistryHelper.ACTION24;
import static org.polymap.rhei.batik.app.SvgImageRegistryHelper.COLOR_DANGER;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.UrlLauncher;

import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.BatikPlugin;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.ConstraintData;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.form.batik.BatikFormContainer;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.ops.DeleteProjectOperation;
import io.mapzone.controller.ops.UpdateProjectOperation;
import io.mapzone.controller.ops.UpdateProjectSoftwareOperation;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.um.repository.AuthToken;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.http.ProxyServlet;

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
    @Scope( "io.mapzone.controller" )
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
        createLaunchSection( parent );        
        createFormSection( parent );
        createAuthSection( parent );
        createDeleteSection( parent );

        // FAB
        fab = tk().createFab();
        fab.setToolTipText( "Submit changes" );
        fab.setVisible( false );
        fab.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                submit( ev );
            }
        });
    }

    
    protected void submit( SelectionEvent ev ) {
        // submit form
        try {
            form.submit( null );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "Unable to create project.", e );
            return;
        }
        // execute operation
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

    
    protected void createLaunchSection( Composite parent ) {
        Button btn = tk().createButton( parent, "Launch", SWT.PUSH, SWT.FLAT );
        btn.setLayoutData( new ConstraintData( new PriorityConstraint( 100 ) ) );
        btn.setImage( ControllerPlugin.images().svgImage( "rocket.svg", ControllerPlugin.OK_ICON_CONFIG ) );
        btn.setToolTipText( "Launch this project in another browser tab" );
        btn.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                String projectUrl = ProxyServlet.relativeClientUrl( selected.get() );
                UrlLauncher launcher = RWT.getClient().getService( UrlLauncher.class );
                launcher.openURL( projectUrl );
            }
        });
    }


    protected void createFormSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "Basic settings", SWT.BORDER );
        section.addConstraint( new PriorityConstraint( 10 ), new MinWidthConstraint( 350, 1 ) );
        ProjectForm formPage = new ProjectForm( op.umUow.get(), op.project.get(), op.user.get() ) {
            @Override
            protected void updateEnabled() {
                ProjectInfoPanel.this.updateEnabled();
            }
        };
        formPage.creation.set( false );
        form = new BatikFormContainer( formPage );
        form.createContents( section.getBody() );
    }


    protected void createAuthSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "Auth token" );
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
        IPanelSection section = tk().createPanelSection( parent, "Danger zone" );
        section.addConstraint( new PriorityConstraint( 0 ), new MinWidthConstraint( 350, 1 ) );
        section.getTitleControl().setForeground( UIUtils.getColor( COLOR_DANGER ) );
        section.getBody().setLayout( new FillLayout( SWT.HORIZONTAL ) );

        // delete project
        Button deleteBtn = tk().createButton( section.getBody(), "Destroy this project", SWT.PUSH, SWT.FLAT );
        deleteBtn.setImage( BatikPlugin.images().svgImage( "delete-circle.svg", ACTION24 ) );
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

        // update project binaries
        Button updateBtn = tk().createButton( section.getBody(), "Update", SWT.PUSH, SWT.FLAT );
        updateBtn.setToolTipText( "Update software version" );
        updateBtn.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                UpdateProjectSoftwareOperation uop = new UpdateProjectSoftwareOperation();
                uop.project.set( selected.get() );

                // execute sync as long as there is no progress indicator
                OperationSupport.instance().execute2( uop, false, false, ev2 -> asyncFast( () -> {
                    if (ev2.getResult().isOK()) {
                        tk().createSnackbar( Appearance.FadeIn, "Updated" );
                    }
                    else {
                        StatusDispatcher.handleError( "Unable to update project.", ev2.getResult().getException() );
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
    
    
//    /**
//     * 
//     */
//    class ProjectForm
//            extends DefaultFormPage 
//            implements IFormFieldListener {
//        
//        @Override
//        public void createFormContents( IFormPageSite site ) {
//            super.createFormContents( site );
//            
//            Composite body = site.getPageBody();
//            body.setLayout( ColumnLayoutFactory.defaults()
//                    .spacing( 5 /*panelSite.getLayoutPreference( LAYOUT_SPACING_KEY ) / 4*/ )
//                    .margins( getSite().getLayoutPreference().getSpacing() / 2 ).create() );
//            
//            // organization
//            site.newFormField( new PlainValuePropertyAdapter( "organizationOrUser", op.project.get().organization.get().name.get() ) )
//                    .label.put( "Organization" )
//                    .tooltip.put( "Changing organization is not yet supported." )
//                    .fieldEnabled.put( false )
//                    .create();
//            
//            // name
//            site.newFormField( new PropertyAdapter( op.project.get().name ) )
//                    .tooltip.put( "Changing name is not yet supported." )
//                    .fieldEnabled.put( false )
//                    .create();
//            
//            // description
//            site.newFormField( new PropertyAdapter( op.project.get().description ) ).create();
//            
//            // website
//            site.newFormField( new PropertyAdapter( op.project.get().website ) ).create();
//            
//            // location
//            site.newFormField( new PropertyAdapter( op.project.get().location ) ).create();
//            
//            site.addFieldListener( this );
//        }
//
//        
//        @Override
//        public void fieldChange( FormFieldEvent ev ) {
//            if (ev.getEventCode() == VALUE_CHANGE) {
////                if (ev.getFieldName().equals( "organizationOrUser" )) {
////                    ev.getNewModelValue().ifPresent( v -> op.organization.set( (Organization)v ) );
////                   // op.organization.set( (Organization)ev.getNewModelValue().orElse( null ) );
////                }
//                updateEnabled();
//            }
//        }
//        
//    }
    
}
