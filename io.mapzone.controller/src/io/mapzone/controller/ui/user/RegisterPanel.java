package io.mapzone.controller.ui.user;

import static org.polymap.rhei.batik.app.SvgImageRegistryHelper.NORMAL24;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.polymap.core.operation.OperationSupport;
import org.polymap.core.runtime.UIThreadExecutor;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.security.SecurityContext;
import org.polymap.core.ui.ColumnLayoutFactory;

import org.polymap.rhei.batik.BatikPlugin;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.field.EMailAddressValidator;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.field.NotEmptyValidator;
import org.polymap.rhei.field.PasswordValidator;
import org.polymap.rhei.field.PlainValuePropertyAdapter;
import org.polymap.rhei.field.StringFormField;
import org.polymap.rhei.field.StringFormField.Style;
import org.polymap.rhei.field.Validators;
import org.polymap.rhei.field.VerticalFieldLayout;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

import org.polymap.cms.ContentProvider;

import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.Messages;
import io.mapzone.controller.ops.CreateUserOperation;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.ui.StartPanel;
import io.mapzone.controller.ui.util.PropertyAdapter;
import io.mapzone.controller.um.repository.LoginCookie;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class RegisterPanel
        extends CtrlPanel
        implements IFormFieldListener {

    private static Log log = LogFactory.getLog( RegisterPanel.class );

    public static final PanelIdentifier ID = new PanelIdentifier( "register" );

    public static final IMessages       i18n = Messages.forPrefix( "RegisterPanel" );

    private CreateUserOperation         op;
    
    private Button                      okBtn;

    private BatikFormContainer          form;

    private Composite                   panelContainer;

    private IPanelSection               welcomeSection;

    private IPanelSection               formSection;
    

    @Override
    public boolean wantsToBeShown() {
        if (parentPanel().get() instanceof StartPanel) {
            getSite().setTitle( "" ).setTooltip( i18n.get( "title" ) );
            getSite().setIcon( ControllerPlugin.images().svgImage( "account-plus.svg", ControllerPlugin.HEADER_ICON_CONFIG ) ); 
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        super.init();
        site().setSize( SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH2 );
        op = new CreateUserOperation();
        op.createEntity();
    }


    @Override
    public void dispose() {
        if (form != null) {
            form.removeFieldListener( this );
            form = null;
        }
        op.cleanup();
    }


    public Composite getPanelContainer() {
        return panelContainer;
    }
    
    public IPanelSection getWelcomeSection() {
        return welcomeSection;
    }
    
    public IPanelSection getPersonSection() {
        return formSection;
    }


    @Override
    public void createContents( Composite parent ) {
        this.panelContainer = parent;
        site().title.set( i18n.get( "title" ) );
        site().icon.set( BatikPlugin.images().svgImage( "account.svg", NORMAL24 ) );

        // welcome section
        welcomeSection = tk().createPanelSection( parent, i18n.get( "title" ) );
        welcomeSection.addConstraint( new PriorityConstraint( 10 ), 
                new MinWidthConstraint( site().preferredWidth.get(), 0 ) );
        tk().createFlowText( welcomeSection.getBody(), ContentProvider.instance().findContent( "ui/register-welcome.md").content() );

        // form section
        formSection = tk().createPanelSection( parent, "Create your personal account", SWT.BORDER );
        formSection.getBody().setLayout( ColumnLayoutFactory.defaults().spacing( 8 ).margins( 0, 0 ).create() );
        
        form = new BatikFormContainer( new NamePasswordForm() );
        form.createContents( formSection );

        // btn
        okBtn = tk().createButton( form.getContents(), i18n.get( "okBtn" ), SWT.PUSH );
        okBtn.setEnabled( false );
        okBtn.addSelectionListener( new SelectionAdapter() {
            public void widgetSelected( SelectionEvent ev ) {
                submit();
            }
        });
        
        form.addFieldListener( this );
    }

    
    protected void submit() {
        try {
            form.submit( null );
        }
        catch (Exception e ) {
            site().toolkit().createSnackbar( Appearance.FadeIn, i18n.get( "errorText", e.getMessage() ) );
            return;
        }

        OperationSupport.instance().execute2( op, true, false, ev2 -> {
            UIThreadExecutor.async( () -> {
                if (ev2.getResult().isOK()) {
                    String username = op.user.get().name.get();
                    // triggers StartPanel#userLoggedIn() call openDashboard() now
                    userPrincipal.set( SecurityContext.instance().loginTrusted( username ) );
                    // XXX keep us logged in, even if user has not confirmed
                    LoginCookie.access().create( username );
                }
                else {
                    site().toolkit().createSnackbar( Appearance.FadeIn, i18n.get( "errorText", ev2.getResult().getMessage() ) );                                
                }
            });
        });                    
    }
    
    
    @Override
    public void fieldChange( FormFieldEvent ev ) {
        if (ev.getEventCode() == IFormFieldListener.VALUE_CHANGE
                && !okBtn.isDisposed()) {
            okBtn.setEnabled( false );            

            if (ev.getFieldName().equals( "password" )) {
                ev.getNewModelValue().ifPresent( password -> op.password.set( (String)password ) );
            }
            
            okBtn.setEnabled( form.isValid() );
            
//            if (form.isValid()
//                    // XXX "short" login for test :)
//                    || name != null && name.startsWith( "@" ) && email != null) {
//                getSite().setStatus( Status.OK_STATUS );
//
//                if (UserRepository.instance().findUser( email ) == null) {
//                    okBtn.setEnabled( true );
//                }
//                else {
//                    getSite().setStatus( new Status( IStatus.WARNING, UmPlugin.ID, "Der Nutzername existiert bereits: " + email ) );
//                }
//            }
        }
    }

    
    /**
     * 
     */
    class NamePasswordForm
            extends DefaultFormPage {

        @Override
        public void createFormContents( IFormPageSite site ) {
            super.createFormContents( site );
            
            site.setDefaultFieldLayout( VerticalFieldLayout.INSTANCE );
            Composite body = site.getPageBody();
            body.setLayout( ColumnLayoutFactory.defaults()
                    .spacing( 8 /*panelSite.getLayoutPreference( LAYOUT_SPACING_KEY ) / 4*/ )
                    .margins( 8 /*getSite().getLayoutPreference().getSpacing() / 2*/ ).create() );

            site.newFormField( new PropertyAdapter( op.user.get().name ) )
                    .label.put( "Username" )
                    .validator.put( new NotEmptyValidator() {
                        @Override
                        public String validate( Object fieldValue ) {
                            String isEmpty = super.validate( fieldValue );
                            if (isEmpty != null) {
                                return isEmpty;
                            }
                            else if (op.umUow.get().findUser( (String)fieldValue ).isPresent()) {
                                return "Username is already taken";                                
                            }
                            return null;
                        }
                    }).create();
            
            site.newFormField( new PlainValuePropertyAdapter( "password", "" ) )
                    .field.put( new StringFormField( Style.PASSWORD ) )
                    .label.put( "Password" )
                    .tooltip.put( "At least 7 chars, one uppercase letter, one numeral." )
                    .validator.put( new PasswordValidator().minLength.put( 7 ).oneDigit.put( true ).oneUpperCase.put( true ) )
                    .create();
            
            site.newFormField( new PropertyAdapter( op.user.get().email ) )
                    .tooltip.put( "Valid email allows to reset password if lost" )
                    .validator.put( Validators.AND( /*new NotEmptyValidator(),*/ new EMailAddressValidator() {
                        @Override
                        public String validate( String fieldValue ) {
                            if (StringUtils.isBlank( fieldValue )) {   // empty is ok
                                return null;
                            }
                            String invalidSyntax = super.validate( fieldValue );
                            if (invalidSyntax != null) {// valid syntax
                                return invalidSyntax; 
                            }
                            else if (op.umUow.get().findUserForEmail( fieldValue ).isPresent() ) {
                                return "EMail is already taken";
                            }
                            return null;
                        }
                    }))
                    .create();
        }
        
    }
    
}
