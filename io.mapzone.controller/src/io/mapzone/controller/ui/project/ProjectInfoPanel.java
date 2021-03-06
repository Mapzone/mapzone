package io.mapzone.controller.ui.project;

import static org.polymap.core.runtime.UIThreadExecutor.asyncFast;
import static org.polymap.core.runtime.event.TypeEventFilter.ifType;
import static org.polymap.core.ui.FormDataFactory.on;
import static org.polymap.rhei.batik.app.SvgImageRegistryHelper.ACTION24;
import static org.polymap.rhei.batik.app.SvgImageRegistryHelper.COLOR_DANGER;
import static org.polymap.rhei.batik.toolkit.IPanelSection.EXPANDABLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.jface.action.Action;

import org.eclipse.ui.forms.events.ExpansionEvent;

import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.BatikPlugin;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.ConstraintData;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinHeightConstraint;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdActionbar;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;
import org.polymap.rhei.form.batik.BatikFormContainer;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.ops.DeleteProjectOperation;
import io.mapzone.controller.ops.UpdateProjectOperation;
import io.mapzone.controller.ops.UpdateProjectSoftwareOperation;
import io.mapzone.controller.plugincat.ProjectPluginsDashlet;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.um.repository.AuthToken;
import io.mapzone.controller.um.repository.Project;
import io.mapzone.controller.vm.http.ProxyServlet;
import io.mapzone.controller.vm.http.ServiceAuthProvision;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectInfoPanel
        extends CtrlPanel {

    public static final PanelIdentifier ID = PanelIdentifier.parse( "editProject" );
    
    // XXX @Mandatory ; mimik @Propagate(ONESHOT) in dispose
    @Scope( "io.mapzone.controller" )
    protected Context<Project>          selected;
    
    private Project                     oneShot;
    
    /**
     * The operation to be prepared and executed by this panel.
     */
    private UpdateProjectOperation      op;
    
    private BatikFormContainer          form;

    private AuthToken                   newToken;
    
    private List<IPanelSection>         sections = new ArrayList();

    private Action                      submit;

    
    @Override
    public void init() {
        super.init();
        site().title.set( "Project" );
        site().setSize( SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH2, SIDE_PANEL_WIDTH2 );
        
        op = new UpdateProjectOperation( userPrincipal.get().getName() );
        op.project.set( selected.get() );
        oneShot = selected.get();
    }


    @Override
    public void dispose() {
        // XXX mimik @Propagate(ONESHOT)
        // don't reset if this panel is closed due to opening another
        if (selected.get() == oneShot) {
            selected.set( null );
        }
        
        EventManager.instance().unsubscribe( this );
    }


    @Override
    public void createContents( Composite parent ) {
        createLaunchSection( parent );        
        sections.add( createFormSection( parent ) );
        sections.add( createAuthSection( parent ).setExpanded( false ) );
        sections.add( createPluginsSection( parent ).setExpanded( false ) );
        sections.add( createDeleteSection( parent ).setExpanded( false ) );
        
        EventManager.instance().subscribe( this, ifType( ExpansionEvent.class, ev ->
                sections.contains( ev.getSource() ) ) );

        // submit
        MdActionbar ab = tk().createFloatingActionbar();
        submit = ab.addSubmit( a -> submit() );
    }


    @EventHandler( display=true )
    protected void onDashletExpansion( ExpansionEvent ev ) {
        if (ev.getState()) {
            for (IPanelSection section : sections) {
                if (section.isExpanded() && section != ev.getSource()) {
                    section.setExpanded( false );
                }
            }
        }
    }

    
    protected void submit() {
        // submit form
        try {
            form.submit( null );
            
            if (newToken != null) {
                op.project.get().serviceAuthToken.set( newToken.toString() );
            }
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
        String projectUrl = ProxyServlet.relativeClientUrl( selected.get() );
        
        CLabel btn = new CLabel( parent, SWT.CENTER );
        tk().adapt( btn, false, false );
        btn.setText( "<a target=\"_blank\" href=\"" + projectUrl + "\" "
                // 22px is not a standard size but used for section title :(
                + "style=\"font-size: 22px;\""
                + ">Launch...</a>" );
        btn.setImage( ControllerPlugin.images().svgImage( "rocket.svg", ControllerPlugin.OK_ICON_CONFIG ) );
        btn.setAlignment( SWT.CENTER );
        btn.setBackground( parent.getBackground() );
                
//        Button btn = tk().createButton( parent, "Launch", SWT.PUSH, SWT.FLAT );
//        btn.addSelectionListener( new SelectionAdapter() {
//            @Override
//            public void widgetSelected( SelectionEvent ev ) {
//                UrlLauncher launcher = RWT.getClient().getService( UrlLauncher.class );
//                launcher.openURL( projectUrl );
//            }
//        });
        
        btn.setLayoutData( new ConstraintData( new PriorityConstraint( 100 ), new MinHeightConstraint( 35, 1 ) ) );
        btn.setToolTipText( "Launch this project in another browser tab" );
    }


    protected IPanelSection createPluginsSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "Plugins", SWT.BORDER, EXPANDABLE );
        section.addConstraint( new PriorityConstraint( 5 ), new MinWidthConstraint( 350, 1 ) );
        
        ProjectPluginsDashlet dashlet = new ProjectPluginsDashlet();
        getContext().propagate( dashlet );
        dashlet.createContents( section.getBody(), (MdToolkit)site().toolkit() );
        
        return section;
    }


    protected IPanelSection createFormSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "Basic settings", SWT.BORDER, EXPANDABLE );
        section.addConstraint( new PriorityConstraint( 10 ), new MinWidthConstraint( 350, 1 ) );
        ProjectForm formPage = new ProjectForm( op.umUow.get(), op.project.get(), op.user.get() ) {
            @Override protected void updateEnabled() {
                ProjectInfoPanel.this.updateEnabled();
            }
        };
        formPage.creation.put( false ).tk.put( tk() );
        form = new BatikFormContainer( formPage );
        form.createContents( section.getBody() );
        return section;
    }


    protected IPanelSection createAuthSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "Auth token", SWT.BORDER, EXPANDABLE );
        section.addConstraint( new PriorityConstraint( 1 ), new MinWidthConstraint( 350, 1 ) );
        section.getBody().setLayout( FormLayoutFactory.defaults().margins( 3 ).spacing( 5 ).create() );
        
        Label msg = tk().createFlowText( section.getBody(), 
                "This token authenticates access to services of this project.<br/>Creating a new token **revokes** the current token and makes it invalid!",
                SWT.WRAP );
        msg.setEnabled( false );
        
        Optional<AuthToken> authToken = op.project.get().serviceAuthToken();
        Text text = tk().createText( section.getBody(), "<No auth token>", SWT.BORDER, SWT.READ_ONLY );
        authToken.ifPresent( t -> text.setText( t.toString() ) );
        //text.setBackground( text.getParent().getBackground() );
        
        Button createBtn = tk().createButton( section.getBody(), "New token" );
        createBtn.setToolTipText( "Creates a new token. This <b>revokes</b> the old one!<br/>Update/save project afterwards to <b>activate</b> the new token." );
        createBtn.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                op.project.get().serviceAuthToken.opt().ifPresent( current -> ServiceAuthProvision.revoke( current ) );

                newToken = AuthToken.create();
                text.setText( newToken.toString() );
                updateEnabled();
            }
        });

        on( msg ).fill().noBottom().height( 90 );
        on( createBtn ).top( msg ).right( 100 );
        on( text ).top( msg, 1 ).left( 0 ).right( createBtn );
        return section;
    }


    protected IPanelSection createDeleteSection( Composite parent ) {
        IPanelSection section = tk().createPanelSection( parent, "Danger zone", SWT.BORDER, EXPANDABLE );
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
                OperationSupport.instance().execute2( uop, true, false, ev2 -> asyncFast( () -> {
                    if (ev2.getResult().isOK()) {
                        tk().createSnackbar( Appearance.FadeIn, "Updated" );
                    }
                    else {
                        StatusDispatcher.handleError( "Unable to update project.", ev2.getResult().getException() );
                    }
                }));
            }
        });
        return section;
    }

    
    protected void updateEnabled() {
        submit.setEnabled( 
                form.isDirty() && form.isValid() ||
                newToken != null );
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
