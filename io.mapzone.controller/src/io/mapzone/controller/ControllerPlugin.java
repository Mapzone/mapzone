package io.mapzone.controller;

import io.mapzone.controller.ui.util.SvgImageRenderer;
import io.mapzone.controller.um.repository.ProjectRepository;
import io.mapzone.controller.vm.repository.VmRepository;

import org.osgi.framework.BundleContext;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.polymap.core.CorePlugin;
import org.polymap.core.security.SecurityContext;
import org.polymap.core.security.StandardConfiguration;

import org.polymap.rhei.batik.app.SvgImageRegistryHelper;
import org.polymap.rhei.batik.toolkit.DefaultToolkit;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;

/**
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ControllerPlugin extends AbstractUIPlugin {

	public static final String ID = "io.mapzone.controller"; //$NON-NLS-1$

	/** Default minimum width of sections. */
	public static final MinWidthConstraint MIN_WIDTH = new MinWidthConstraint( 500, 0 );
	
	private static ControllerPlugin        instance;
	

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
	}

    
	public void stop(BundleContext context) throws Exception {
		instance = null;
		super.stop(context);
	}

}
