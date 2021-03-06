package io.mapzone.controller.ui.user;

import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.action.Action;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;

import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.event.EventHandler;
import org.polymap.core.runtime.event.EventManager;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.toolkit.ConstraintData;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.md.MdActionbar;
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

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.ops.UpdateUserOperation;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.ui.DashboardPanel;
import io.mapzone.controller.ui.util.PropertyAdapter;
import io.mapzone.controller.um.repository.LoginCookie;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UserInfoPanel
        extends CtrlPanel {

    public static final PanelIdentifier ID = PanelIdentifier.parse( "editUser" );
    
    private UpdateUserOperation         op;

    private BatikFormContainer          profileForm;

    private BatikFormContainer          accountForm;
    
    private Optional<String>            newPassword = Optional.empty();

    private Action                      submit;

    
    @Override
    public boolean wantsToBeShown() {
        if (parentPanel().get() instanceof DashboardPanel) {
            site().title.set( "" );
            site().tooltip.set( "Edit account and profile settings" );
            site().icon.set( ControllerPlugin.images().svgImage( "account.svg", ControllerPlugin.HEADER_ICON_CONFIG ) );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        super.init();
        op = new UpdateUserOperation( userPrincipal.get().getName() );
        site().title.set( "Account: " + op.user.get().name.get() );
        site().setSize( SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH2, SIDE_PANEL_WIDTH2 );
    }


    @Override
    public void createContents( Composite parent ) {
        // profile form
        IPanelSection profileSection = tk().createPanelSection( parent, "Profile", SWT.BORDER );
        profileSection.getControl().setLayoutData( new ConstraintData( new MinWidthConstraint( 350, 1 ) ) );
        profileForm = new BatikFormContainer( new UserForm() );
        profileForm.createContents( profileSection.getBody() );

        // account form
        IPanelSection accountSection = tk().createPanelSection( parent, "Account", SWT.BORDER );
        accountForm = new BatikFormContainer( new AccountForm() );
        accountForm.createContents( accountSection.getBody() );

        // sign out
        IPanelSection signOutSection = tk().createPanelSection( parent, "Sign out" );
        Button btn = tk().createButton( signOutSection.getBody(), "Sign out", SWT.PUSH, SWT.FLAT );
        btn.setImage( ControllerPlugin.images().svgImage( "logout.svg", ControllerPlugin.ACTION_ICON_CONFIG ) );
        btn.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                LoginCookie.access().destroy();
                JavaScriptExecutor executor = RWT.getClient().getService( JavaScriptExecutor.class );
                executor.execute( "window.location.reload(true);" );
            }
        });

        // submit
        MdActionbar ab = tk().createFloatingActionbar();
        submit = ab.addSubmit( a -> submit() );
        
        // field events
        EventManager.instance().subscribe( this, ev -> 
                ((FormFieldEvent)ev).getEditor() == profileForm ||
                ((FormFieldEvent)ev).getEditor() == accountForm );

        updateEnabled( null );
    }

    
    protected void submit() {
        // submit form
        try {
            profileForm.submit( null );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "Unable to submit form.", e );
            return;
        }
        
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
    
    
    @EventHandler( display=true )
    protected void updateEnabled( FormFieldEvent ev ) {
        submit.setEnabled( profileForm.isDirty() && profileForm.isValid()
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
            site.newFormField( new PropertyAdapter( op.user.get().fullname ) ).create();
            
            // email
            site.newFormField( new PropertyAdapter( op.user.get().email ) )
                    .validator.put( Validators.AND( new NotEmptyValidator(), new EMailAddressValidator() {
                        @Override
                        public String validate( String fieldValue ) {
                            return Optional.ofNullable( super.validate( fieldValue ) )
                                    .orElse( op.umUow.get().findUser( fieldValue ).isPresent() && !fieldValue.equals( op.user.get().email.get() )
                                            ? "EMail is already taken" : null );
                        }
                    }))
                    .create();
            
            // website
            site.newFormField( new PropertyAdapter( op.user.get().website ) ).create();
            
            // company
            site.newFormField( new PropertyAdapter( op.user.get().company ) ).create();
            
            // location
            site.newFormField( new PropertyAdapter( op.user.get().location ) ).create();            
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
            site.newFormField( new PropertyAdapter( op.user.get().name ) )
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
