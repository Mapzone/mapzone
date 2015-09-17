package io.mapzone.controller.ui;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.um.repository.EntityChangedEvent;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.um.repository.User;
import io.mapzone.controller.um.xauth.PasswordEncryptor;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.toolkit.ConstraintData;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;
import org.polymap.rhei.field.EMailAddressValidator;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.NotEmptyValidator;
import org.polymap.rhei.field.PasswordValidator;
import org.polymap.rhei.field.PlainValuePropertyAdapter;
import org.polymap.rhei.field.StringFormField;
import org.polymap.rhei.field.StringFormField.Style;
import org.polymap.rhei.field.Validators;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class EditUserPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( EditUserPanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "editUser" );
    
    @Scope("io.mapzone.controller")
    private Context<String>         username;
    
    private ProjectRepository       nested;
    
    private User                    user;

    private BatikFormContainer      profileForm;

    private BatikFormContainer      accountForm;
    
    private Optional<String>        newPassword = Optional.empty();

    private Button                  fab;

    
    @Override
    public boolean wantsToBeShown() {
        if (parentPanel().get() instanceof DashboardPanel) {
            getSite().setTitle( "" );
            getSite().setIcon( ControllerPlugin.images().svgImage( "account.svg", SvgImageRegistryHelper.NORMAL24 ) );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        nested = ProjectRepository.instance().newNested();
        user = nested.findUser( username.get() ).orElseThrow( () -> new RuntimeException( "No user!" ) );
        getSite().setTitle( "Account: " + user.name.get() );
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().setPreferredWidth( 350 );
        MdToolkit tk = (MdToolkit)getSite().toolkit();
        
        // profile form
        IPanelSection profileSection = tk.createPanelSection( parent, "Profile" );
        profileSection.getControl().setLayoutData( new ConstraintData( new MinWidthConstraint( 350, 1 ) ) );
        profileForm = new BatikFormContainer( new UserForm() );
        profileForm.createContents( profileSection.getBody() );

        // account form
        IPanelSection accountSection = tk.createPanelSection( parent, "Account" );
        accountForm = new BatikFormContainer( new AccountForm() );
        accountForm.createContents( accountSection.getBody() );

        // sign out
        IPanelSection signOutSection = tk.createPanelSection( parent, "Sign out" );
        tk.createButton( signOutSection.getBody(), "Sign out", SWT.PUSH );

        // FAB
        fab = tk.createFab();
        fab.setToolTipText( "Submit changes" );
        fab.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                // submit form
                try {
                    profileForm.submit();
                }
                catch (Exception e) {
                    StatusDispatcher.handleError( "Unable to submit form.", e );
                    return;
                }
                
                // operation
                DefaultOperation op = new DefaultOperation( "" ) {
                    @Override
                    public IStatus execute( IProgressMonitor monitor, IAdaptable info ) throws ExecutionException {
                        try {
                            monitor.beginTask( getLabel(), 3 );
                            
                            // password hash
                            newPassword.ifPresent( password -> {
                                PasswordEncryptor encryptor = PasswordEncryptor.instance();
                                String hash = encryptor.encryptPassword( password );
                                user.passwordHash.set( hash );
                            });
                            
                            nested.commit();
                            EventManager.instance().publish( new EntityChangedEvent( user ) );
                            
                            return Status.OK_STATUS;
                        }
                        catch (Exception e) {
                            throw new ExecutionException( e.getMessage(), e );
                        }
                    }
                };
                    
                // execute
                OperationSupport.instance().execute2( op, true, false, ev2 -> UIThreadExecutor.asyncFast( () -> {
                    if (ev2.getResult().isOK()) {
                        PanelPath panelPath = getSite().getPath();
                        getContext().closePanel( panelPath );                        
                    }
                    else {
                        StatusDispatcher.handleError( "Unable to update user data.", ev2.getResult().getException() );
                    }
                }));
            }
        });
        // field events
        EventManager.instance().subscribe( this, ev -> 
                ((FormFieldEvent)ev).getEditor() == profileForm ||
                ((FormFieldEvent)ev).getEditor() == accountForm );

        updateEnabled( null );
    }

    @EventHandler( display=true )
    protected void updateEnabled( FormFieldEvent ev ) {
        fab.setVisible( profileForm.isDirty() && profileForm.isValid()
                || accountForm.isDirty() && accountForm.isValid() );
    }
    
    
    /**
     * 
     */
    class UserForm
            extends DefaultFormPage { 
        
        @Override
        public void createFormContents( IFormPageSite site ) {
            super.createFormContents( site );
            
            Composite body = site.getPageBody();
            body.setLayout( ColumnLayoutFactory.defaults()
                    .spacing( 5 /*panelSite.getLayoutPreference( LAYOUT_SPACING_KEY ) / 4*/ )
                    .margins( getSite().getLayoutPreference().getSpacing() / 2 ).create() );
            
            // fullname
            site.newFormField( new PropertyAdapter( user.fullname ) ).create();
            
            // email
            site.newFormField( new PropertyAdapter( user.email ) )
                    .validator.put( Validators.AND( new NotEmptyValidator(), new EMailAddressValidator() {
                        @Override
                        public String validate( Object fieldValue ) {
                            return Optional.ofNullable( super.validate( fieldValue ) )
                                    .orElse( nested.findUser( (String)fieldValue ).isPresent() && !fieldValue.equals( user.email.get() )
                                            ? "EMail is already taken" : null );
                        }
                    }))
                    .create();
            
            // website
            site.newFormField( new PropertyAdapter( user.website ) ).create();
            
            // company
            site.newFormField( new PropertyAdapter( user.company ) ).create();
            
            // location
            site.newFormField( new PropertyAdapter( user.location ) ).create();            
        }
    }
    
    
    /**
     * 
     */
    class AccountForm
            extends DefaultFormPage 
            implements IFormFieldListener { 
        
        @Override
        public void createFormContents( IFormPageSite site ) {
            super.createFormContents( site );
            
            Composite body = site.getPageBody();
            body.setLayout( ColumnLayoutFactory.defaults()
                    .spacing( 5 /*panelSite.getLayoutPreference( LAYOUT_SPACING_KEY ) / 4*/ )
                    .margins( getSite().getLayoutPreference().getSpacing() / 2 ).create() );
            
            // name
            site.newFormField( new PropertyAdapter( user.name ) )
                    .fieldEnabled.put( false ).create();

            // password
            site.newFormField( new PlainValuePropertyAdapter( "password", null ) )
                    .field.put( new StringFormField( Style.PASSWORD ) )
                    .label.put( "New password" )
                    .tooltip.put( "Minimum 7 chars containing at least one lower case, one upper case, one digit. " )
                    .validator.put( new PasswordValidator() {
                        @Override
                        public String validate( Object fieldValue ) {
                            if (fieldValue == null || ((String)fieldValue).length() == 0) {
                                return null;
                            }
                            else {
                                return super.validate( fieldValue );
                            }
                        }
                    }.minLength.put( 7 ) )
                    .create();
            
            site.addFieldListener( this );
        }

        @Override
        public void fieldChange( FormFieldEvent ev ) {
            if (ev.getEventCode() == VALUE_CHANGE) {
                if (ev.getFieldName().equals( "password" )) {
                    newPassword = ev.getNewModelValue();
                }
            }
        }
    }
    
}
