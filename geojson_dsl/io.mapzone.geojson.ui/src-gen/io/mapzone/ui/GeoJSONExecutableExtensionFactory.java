/*
 * generated by Xtext
 */
package io.mapzone.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import io.mapzone.ui.internal.GeoJSONActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class GeoJSONExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return GeoJSONActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return GeoJSONActivator.getInstance().getInjector(GeoJSONActivator.IO_MAPZONE_GEOJSON);
	}
	
}
