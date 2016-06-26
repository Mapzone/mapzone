package io.mapzone.controller;

import java.util.Locale;

import org.osgi.framework.BundleContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.polymap.core.CorePlugin;
import org.polymap.core.security.SecurityContext;
import org.polymap.core.security.StandardConfiguration;

import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.toolkit.DefaultToolkit;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;

import org.polymap.cms.ContentProvider;

import io.mapzone.controller.ui.util.SvgImageRenderer;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.vm.repository.VmRepository;

/**
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ControllerPlugin extends AbstractUIPlugin {

    private static final Log log = LogFactory.getLog( ControllerPlugin.class );
    
	public static final String             ID = "io.mapzone.controller";

	/** Default minimum width of sections. */
    public static final MinWidthConstraint  MIN_WIDTH = new MinWidthConstraint( 500, 0 );
    
    public static final String              HEADER_ICON_CONFIG = SvgImageRegistryHelper.WHITE24;
    
    public static final String              TOOLBAR_ICON_CONFIG = SvgImageRegistryHelper.NORMAL24;

    public static final String              ACTION_ICON_CONFIG = SvgImageRegistryHelper.ACTION24;

    public static final String              OK_ICON_CONFIG = SvgImageRegistryHelper.OK24;

    private static ControllerPlugin         instance;
	

	public static ControllerPlugin instance() {
    	return instance;
    }

	
	public static SvgImageRegistryHelper images() {
	    return instance().images;
	}

	
	// instance *******************************************
	
	private SvgImageRegistryHelper         images = new SvgImageRegistryHelper( this );
	
	
    public void start( BundleContext context ) throws Exception {
		super.start( context );
		instance = this;
		
		// english is default
		ContentProvider.instance().defaultLocale.set( Locale.ENGLISH );
		
		// allow SVG images in markdown
		DefaultToolkit.registerMarkdownRenderer( () -> new SvgImageRenderer() );
		
		// JAAS config: no dialog; let LoginPanel create UI
        SecurityContext.registerConfiguration( () -> new StandardConfiguration() {
            @Override
            public String getConfigName() {
                return SecurityContext.SERVICES_CONFIG_NAME;
            }
        });

        // init repositories
        ProjectRepository.init( CorePlugin.getDataLocation( ControllerPlugin.instance() ) );
        VmRepository.init( CorePlugin.getDataLocation( ControllerPlugin.instance() ) );
        log.info( "Running on HTTP port: " + httpPort() );
	}

    
	public void stop( BundleContext context ) throws Exception {
		instance = null;
		super.stop( context );
	}

	
	public String httpPort() {
	    return System.getProperty( "org.osgi.service.http.port" );
	}
	
}
