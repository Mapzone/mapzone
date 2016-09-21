package io.mapzone.controller.ui.user;

import static org.polymap.rhei.batik.app.SvgImageRegistryHelper.NORMAL24;
import io.mapzone.controller.ControllerPlugin;
import io.mapzone.controller.Messages;
import io.mapzone.controller.ops.CreateUserOperation;
import io.mapzone.controller.ui.CtrlPanel;
import io.mapzone.controller.ui.DashboardPanel;
import io.mapzone.controller.ui.StartPanel;
import io.mapzone.controller.ui.util.PropertyAdapter;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import org.polymap.core.operation.OperationSupport;
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
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

import org.polymap.cms.ContentProvider;

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
            getSite().setTitle( "" ).setTooltip( "Sign up" );
            getSite().setIcon( ControllerPlugin.images().svgImage( "account-plus.svg", ControllerPlugin.HEADER_ICON_CONFIG ) ); 
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        super.init();
        site().setSize( SIDE_PANEL_WIDTH, SIDE_PANEL_WIDTH2, SIDE_PANEL_WIDTH2 );
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
        welcomeSection.addConstraint( new PriorityConstraint( 10 ), new MinWidthConstraint( 450, 0 ) );
        tk().createFlowText( welcomeSection.getBody(), ContentProvider.instance().findContent( "ui/register-welcome.md").content() );

        // form section
        formSection = tk().createPanelSection( parent, "Personal account settings", SWT.BORDER );
        formSection.getBody().setLayout( ColumnLayoutFactory.defaults().spacing( 8 ).margins( 0, 0 ).create() );
        
        form = new BatikFormContainer( new NamePasswordForm() );
        form.createContents( formSection );

        // btn
        okBtn = tk().createButton( form.getContents(), i18n.get( "okBtn" ), SWT.PUSH );
        okBtn.setEnabled( false );
        okBtn.addSelectionListener( new SelectionAdapter() {
            public void widgetSelected( SelectionEvent ev ) {
                try {
                    form.submit( null );
                }
                catch (Exception e ) {
                    site().toolkit().createSnackbar( Appearance.FadeIn, i18n.get( "errorText", e.getMessage() ) );
                    return;
                }

                OperationSupport.instance().execute( op, true, false, new JobChangeAdapter() {
                    @Override
                    public void done( IJobChangeEvent ev2 ) {
                        if (ev2.getResult().isOK()) {
                            String username = op.user.get().name.get();
                            userPrincipal.set( SecurityContext.instance().loginTrusted( username ) );
                            
                            //getSite().setStatus( new Status( IStatus.OK, ControllerPlugin.ID, i18n.get( "okText" ) ) );
                            getContext().closePanel( site().path() );
                            getContext().openPanel( site().path(), DashboardPanel.ID );                        
                        }
                        else {
                            site().toolkit().createSnackbar( Appearance.FadeIn, i18n.get( "errorText", ev2.getResult().getMessage() ) );                                
                        }
                    }
                });                    
            }
        });
        
        form.addFieldListener( this );
    }

    
    @Override
    public void fieldChange( FormFieldEvent ev ) {
        if (ev.getEventCode() == IFormFieldListener.VALUE_CHANGE
                && !okBtn.isDisposed()) {
            okBtn.setEnabled( false );            

            if (ev.getFieldName().equals( "password" )) {
                String password = (String)ev.getNewModelValue().orElse( null );
                op.password.set( password );
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
            
            Composite body = site.getPageBody();
            body.setLayout( ColumnLayoutFactory.defaults()
                    .spacing( 5 /*panelSite.getLayoutPreference( LAYOUT_SPACING_KEY ) / 4*/ )
                    .margins( getSite().getLayoutPreference().getSpacing() / 2 ).create() );

            site.newFormField( new PropertyAdapter( op.user.get().name ) )
                    .label.put( "Username" )
                    .validator.put( new NotEmptyValidator() {
                        @Override
                        public String validate( Object fieldValue ) {
                            return Optional.ofNullable( super.validate( fieldValue ) )
                                    .orElse( op.umUow.get().findUser( (String)fieldValue ).isPresent() ? "Username is already taken" : null );
                        }
                    }).create();
            
            site.newFormField( new PropertyAdapter( op.user.get().email ) )
                    .validator.put( Validators.AND( new NotEmptyValidator(), new EMailAddressValidator() {
                        @Override
                        public String validate( Object fieldValue ) {
                            return Optional.ofNullable( super.validate( fieldValue ) )
                                    .orElse( op.umUow.get().findUser( (String)fieldValue ).isPresent() ? "EMail is already taken" : null );
                        }
                    }))
                    .create();
            
            site.newFormField( new PlainValuePropertyAdapter( "password", "" ) )
                    .field.put( new StringFormField( Style.PASSWORD ) )
                    .label.put( "Password" )
                    .tooltip.put( "Minimum 7 chars containing at least one lower case, one upper case, one digit. " )
                    .validator.put( new PasswordValidator().minLength.put( 7 ) )
                    .create();
        }
        
    }
    
}
