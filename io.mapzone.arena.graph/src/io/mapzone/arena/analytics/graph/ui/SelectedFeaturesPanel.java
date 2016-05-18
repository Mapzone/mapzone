/*
 * polymap.org Copyright (C) 2015, Falko Bräutigam. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package io.mapzone.arena.analytics.graph.ui;

import static org.apache.commons.lang3.StringUtils.abbreviate;
import static org.polymap.core.runtime.UIThreadExecutor.async;

import java.util.List;

import org.geotools.data.FeatureStore;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vividsolutions.jts.geom.Geometry;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.polymap.core.data.unitofwork.CommitOperation;
import org.polymap.core.data.unitofwork.UnitOfWork;
import org.polymap.core.operation.OperationSupport;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.SelectionListenerAdapter;
import org.polymap.core.ui.StatusDispatcher;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.field.FormFieldEvent;
import org.polymap.rhei.field.IFormFieldListener;
import org.polymap.rhei.form.DefaultFormPage;
import org.polymap.rhei.form.IFormPageSite;
import org.polymap.rhei.form.batik.BatikFormContainer;

import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;

/**
 * Displays a {@link StandardFeatureForm} for the selected feature
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 * @author Steffen Stundzig
 */
public class SelectedFeaturesPanel
        extends P4Panel
        implements IFormFieldListener {

    public static class FeatureSelectionWithRelations {

        private final FeatureStore  sourceFS;

        private final Feature       sourceFeature;

        private final List<Feature> relatedFeatures;

        private final FeatureStore  relatedFS;


        public FeatureSelectionWithRelations( final FeatureStore sourceFS, final Feature sourceFeature,
                final FeatureStore relatedFS, final List<Feature> relatedFeatures ) {
            this.sourceFS = sourceFS;
            this.sourceFeature = sourceFeature;
            this.relatedFS = relatedFS;
            this.relatedFeatures = relatedFeatures;
        }


        public FeatureStore relatedFS() {
            return relatedFS;
        }


        public FeatureStore sourceFS() {
            return sourceFS;
        }


        public Feature sourceFeature() {
            return sourceFeature;
        }
    }

    private static Log                               log             = LogFactory.getLog( SelectedFeaturesPanel.class );

    public static final PanelIdentifier              ID              = PanelIdentifier.parse( "selectedFeatures" );

    private FeatureStore                             fs;

    private Feature                                  feature;

    private UnitOfWork                               uow;

    private Button                                   fab;

    private BatikFormContainer                       form;

    private boolean                                  previouslyValid = true;

    @Scope( P4Plugin.Scope )
    protected Context<FeatureSelectionWithRelations> selectedFeatures;


    @Override
    public void init() {
    }


    @Override
    public void createContents( Composite parent ) {
        try {
            fs = selectedFeatures.get().sourceFS();
            feature = selectedFeatures.get().sourceFeature();

            uow = new UnitOfWork( fs );
            uow.track( feature );
            form = new BatikFormContainer( new StandardFeatureForm() );
            form.createContents( parent );

            form.addFieldListener( this );

            fab = tk().createFab();
            fab.setToolTipText( "Save changes" );
            fab.setVisible( false );
            fab.addSelectionListener( new SelectionListenerAdapter( ev -> submit() ) );
        }
        catch (Exception e) {
            createErrorContents( parent, "Unable to display feature.", e );
        }
    }


    @Override
    public void fieldChange( FormFieldEvent ev ) {
        if (ev.hasEventCode( VALUE_CHANGE )) {
            boolean isDirty = form.isDirty();
            boolean isValid = form.isValid();

            fab.setVisible( isDirty );
            fab.setEnabled( isDirty && isValid );

            if (previouslyValid && !isValid) {
                tk().createSnackbar( Appearance.FadeIn, "There are invalid settings" );
            }
            if (!previouslyValid && isValid) {
                tk().createSnackbar( Appearance.FadeIn, "Settings are ok" );
            }
            previouslyValid = isValid;
        }
    }


    protected void submit() {
        try {
            // XXX doing this inside operation cause "Invalid thread access"
            form.submit( null );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "Unable to submit form.", e );
        }

        CommitOperation op = new CommitOperation().uow.put( uow );
        OperationSupport.instance().execute2( op, false, false, ev -> {
            async( () -> {
                tk().createSnackbar( Appearance.FadeIn, ev.getResult().isOK()
                        ? "Saved" : abbreviate( "Unable to save: " + ev.getResult().getMessage(), 50 ) );
            } );
        } );
    }


    /**
     * 
     */
    class StandardFeatureForm
            extends DefaultFormPage {

        @Override
        public void createFormContents( IFormPageSite site ) {
            super.createFormContents( site );
            site.getPageBody().setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).spacing( 3 ).create() );

            for (Property prop : SelectedFeaturesPanel.this.feature.getProperties()) {
                if (Geometry.class.isAssignableFrom( prop.getType().getBinding() )) {
                    // skip Geometry
                }
                else {
                    site.newFormField( prop ).create();
                }
            }
        }
    }

}
