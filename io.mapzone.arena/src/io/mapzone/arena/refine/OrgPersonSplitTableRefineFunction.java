/* 
 * polymap.org
 * Copyright (C) 2016, Falko Bräutigam. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package io.mapzone.arena.refine;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.DataAccess;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.Name;
import org.polymap.core.catalog.resolve.IResolvableInfo;
import org.polymap.core.catalog.resolve.IResourceInfo;
import org.polymap.core.data.rs.catalog.RServiceInfo;
import org.polymap.core.data.util.NameImpl;
import org.polymap.core.operation.OperationSupport;
import org.polymap.core.project.IMap;
import org.polymap.core.project.operations.NewLayerOperation;
import org.polymap.core.style.DefaultStyle;
import org.polymap.core.style.model.FeatureStyle;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.p4.P4Plugin;
import org.polymap.p4.project.ProjectRepository;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import com.google.common.collect.Maps;
import com.vividsolutions.jts.geom.Point;

/**
 * hardcoded *splittable* for teamq
 * 
 * @author Steffen Stundzig
 */
public class OrgPersonSplitTableRefineFunction
        implements RefineFunction {

    private static Log log = LogFactory.getLog( OrgPersonSplitTableRefineFunction.class );

    private final static SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmm" );

    private Button fab;

    private FeatureSource fs;

    private Context<IMap> map;


    @Override
    public String title() {
        return "Recherchedaten aufspalten";
    }


    @Override
    public String description() {
        return "Recherche Daten aufspalten in Personen, Organisationen und Rollen";
    }


    @Override
    public void init( Context<IMap> map ) {
        this.map = map;
    }


    @Override
    public void createContents( final MdToolkit tk, final Composite parent ) {
        try {
            Combo combo = new Combo( parent, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN );

            final List<Name> featureSourceNames = P4Plugin.localCatalog().localFeaturesStore().getNames();
            // final List<String> featureSources = featureSourceNames.stream().map(
            // name -> name.getLocalPart() ).collect( Collectors.toList() );
            combo.setItems( featureSourceNames.stream().map( name -> name.getLocalPart() ).toArray( String[]::new ) );
            combo.addSelectionListener( new SelectionAdapter() {

                @Override
                public void widgetSelected( SelectionEvent e ) {
                    try {
                        Name featureSourceName = featureSourceNames.get( combo.getSelectionIndex() );
                        onSelectSource(
                                P4Plugin.localCatalog().localFeaturesStore().getFeatureSource( featureSourceName ) );
                    }
                    catch (IOException e1) {
                        StatusDispatcher.handleError( "", e1 );
                    }
                }

            } );

            fab = tk.createFab();
            fab.setVisible( false );
            fab.addSelectionListener( new SelectionAdapter() {

                @Override
                public void widgetSelected( SelectionEvent e ) {
                    splitSource( tk );
                    fab.setVisible( false );
                }
            } );
            final Label selectLabel = tk.createLabel( parent, "Wähle die Ausgangsdaten: ", SWT.NONE );
            FormDataFactory.on( selectLabel ).top( 5 ).left( 1 );
            FormDataFactory.on( combo ).top( selectLabel, 1 ).left( 1 );
        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }


    private void onSelectSource( final FeatureSource featureSource ) {
        if (featureSource != null) {
            fs = featureSource;
            // Collection<PropertyDescriptor> schemaDescriptors =
            // featureSource.getSchema().getDescriptors();
            // final List<String> columns = Lists.newArrayList();
            // for (PropertyDescriptor descriptor : schemaDescriptors) {
            // if (geometryDescriptor == null || !geometryDescriptor.equals(
            // descriptor )) {
            // columns.add( descriptor.getName().getLocalPart() );
            // }
            // }
            fab.setVisible( true );
            fab.setEnabled( true );
        }
    }


    protected void splitSource( final MdToolkit tk ) {
        try {
            final SimpleFeatureType organisationType = buildOrganisationType( fs );
            final SimpleFeatureBuilder organisationBuilder = new SimpleFeatureBuilder( organisationType );
            final DefaultFeatureCollection organisations = new DefaultFeatureCollection( null, organisationType );

            final SimpleFeatureType personType = buildPersonType( fs );
            final SimpleFeatureBuilder personBuilder = new SimpleFeatureBuilder( personType );
            final DefaultFeatureCollection persons = new DefaultFeatureCollection( null, personType );

            final SimpleFeatureType associationType = buildAssociationType( fs );
            final SimpleFeatureBuilder associationBuilder = new SimpleFeatureBuilder( associationType );
            final DefaultFeatureCollection associations = new DefaultFeatureCollection( null, associationType );

            final Map<String,SimpleFeature> organisationCache = Maps.newHashMap();
            final Map<String,SimpleFeature> personCache = Maps.newHashMap();

            // iterate on features
            // create OlFeature for each organisation
            // increase weight for each entry per organisation
            FeatureIterator iterator = fs.getFeatures().features();
            int i = 0;
            while (iterator.hasNext()) {
                i++;
                SimpleFeature baseFeature = (SimpleFeature)iterator.next();
                if (!StringUtils.isBlank( (String)baseFeature.getAttribute( "Organisation" ) )
                        && !StringUtils.isBlank( (String)baseFeature.getAttribute( "Name" ) )
                        && !StringUtils.isBlank( (String)baseFeature.getAttribute( "Vorname" ) )) {
                    String organisationKey = (String)baseFeature.getAttribute( "Organisation" );
                    SimpleFeature organisation = organisationCache.get( organisationKey );
                    if (organisation == null) {
                        organisation = createOrganisation( organisationBuilder, baseFeature );
                        organisationCache.put( organisationKey, organisation );
                        organisations.add( organisation );
                    }
                    String personKey = (String)baseFeature.getAttribute( "Name" ) + " "
                            + (String)baseFeature.getAttribute( "Vorname" );
                    SimpleFeature person = personCache.get( personKey );
                    if (person == null) {
                        person = createPerson( personBuilder, baseFeature );
                        personCache.put( personKey, person );
                        persons.add( person );
                    }
                    // create also the association
                    associations.add( createAssociation( associationBuilder, baseFeature, organisation, person ) );
                }
            }
            addLayerAndStore( organisations );
            addLayerAndStore( persons );
            store( associations );

            tk.createSnackbar( Appearance.FadeIn, organisations.size() + " Organisationen, " + persons.size()
                    + " Personen, " + associations.size() + " Beziehungen angelegt" );

        }
        catch (Exception e) {
            StatusDispatcher.handleError( "", e );
        }
    }


    private void addLayerAndStore( final DefaultFeatureCollection features ) throws IOException {
        store( features );
        FeatureStyle featureStyle = P4Plugin.styleRepo().newFeatureStyle();
        DefaultStyle.createAllStyle( featureStyle );
        // log.info( "FeatureStyle.id: " + featureStyle.id() );
        featureStyle.store();

        NewLayerOperation op = new NewLayerOperation().uow.put( ProjectRepository.unitOfWork().newUnitOfWork() ).map
                .put( map.get() ).label.put( features.getSchema().getName().getLocalPart() ).resourceIdentifier
                        .put( resourceIdentifier( features ) ).styleIdentifier.put( featureStyle.id() );
        OperationSupport.instance().execute( op, true, false );
    }


    public String resourceIdentifier( final DefaultFeatureCollection features ) throws IOException {
        IResolvableInfo info = P4Plugin.localCatalog().localFeaturesStoreInfo();
        IResourceInfo res = ((RServiceInfo)info.getServiceInfo()).resource(
                P4Plugin.localCatalog().localFeaturesStore().getFeatureSource( features.getSchema().getName() ) );
        return P4Plugin.localResolver().resourceIdentifier( res );
    }


    private void store( final DefaultFeatureCollection features ) throws IOException {
        DataAccess ds = P4Plugin.localCatalog().localFeaturesStore();
        // XXX transaction that spans createSchema() and addFeatures()!?
        ds.createSchema( features.getSchema() );
        ((FeatureStore)ds.getFeatureSource( features.getSchema().getName() )).addFeatures( features );
    }


    private SimpleFeatureType buildOrganisationType( final FeatureSource fs ) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName( new NameImpl( "Organisation_" + sdf.format( new Date() ) ) );
        builder.setCRS( DefaultGeographicCRS.WGS84 );
        builder.setDefaultGeometry( "theGeom" );
        builder.add( "theGeom", Point.class );

        builder.add( "Name", String.class );
        builder.add( "PLZ_Ort", String.class );
        builder.add( "Str_HN", String.class );
        builder.add( "Kategorie", String.class );
        return builder.buildFeatureType();
    }


    private SimpleFeature createOrganisation( SimpleFeatureBuilder builder, SimpleFeature baseFeature ) {
        builder.add( coordinateForOrganisation( (String)baseFeature.getAttribute( "Organisation" ) ) );

        builder.add( baseFeature.getAttribute( "Organisation" ) );
        builder.add( baseFeature.getAttribute( "Orga_Adresse_PLZ_Ort" ) );
        builder.add( baseFeature.getAttribute( "Orga_Adresse_Str_HN" ) );
        builder.add( baseFeature.getAttribute( "Orga_Kategorie" ) );
        return builder.buildFeature( (String)baseFeature.getAttribute( "Organisation" ) );
    }


    private Object coordinateForOrganisation( String organisation ) {
        return null;
    }


    private SimpleFeatureType buildPersonType( final FeatureSource fs ) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName( new NameImpl( "Person_" + sdf.format( new Date() ) ) );
        builder.setCRS( DefaultGeographicCRS.WGS84 );
        builder.setDefaultGeometry( "theGeom" );
        builder.add( "theGeom", Point.class );

        builder.add( "Name", String.class );
        builder.add( "Vorname", String.class );
        builder.add( "Titel", String.class );
        builder.add( "Position_1", String.class );
        builder.add( "Position_2", String.class );
        builder.add( "Str_HN", String.class );
        builder.add( "PLZ_Ort", String.class );
        builder.add( "Anmerkung 1", String.class );
        builder.add( "Quelle", String.class );
        builder.add( "Bearbeitet am", String.class );
        builder.add( "Bearbeitet von", String.class );
        builder.add( "Internetquelle_fuer_Gemeinderat", String.class );
        builder.add( "weitere_Internetquelle", String.class );
        return builder.buildFeatureType();
    }


    private SimpleFeature createPerson( SimpleFeatureBuilder builder, SimpleFeature baseFeature ) {
        builder.add( null ); // theGeom
        builder.add( baseFeature.getAttribute( "Name" ) );
        builder.add( baseFeature.getAttribute( "Vorname" ) );
        builder.add( baseFeature.getAttribute( "Titel" ) );
        builder.add( baseFeature.getAttribute( "Position_1" ) );
        builder.add( baseFeature.getAttribute( "Position_2" ) );
        builder.add( baseFeature.getAttribute( "Pers_Adresse_Str_HN" ) );
        builder.add( baseFeature.getAttribute( "Pers_Adresse_PLZ_Ort" ) );
        builder.add( baseFeature.getAttribute( "Anmerkung 1" ) );
        builder.add( baseFeature.getAttribute( "Quelle" ) );
        builder.add( baseFeature.getAttribute( "Bearbeitet am" ) );
        builder.add( baseFeature.getAttribute( "Bearbeitet von" ) );
        builder.add( baseFeature.getAttribute( "Internet-Quelle für Gemeinderat ( nur einmal pro Person ausfüllen)" ) );
        builder.add( baseFeature.getAttribute( "weitere Internet-Quelle" ) );
        return builder.buildFeature( baseFeature.getAttribute( "Vorname" ) + "_" + baseFeature.getAttribute( "Name" ) );
    }


    private SimpleFeatureType buildAssociationType( final FeatureSource fs ) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName( new NameImpl( "PersonOrganisation_" + sdf.format( new Date() ) ) );
        builder.setCRS( DefaultGeographicCRS.WGS84 );
        builder.setDefaultGeometry( "theGeom" );
        builder.add( "theGeom", Point.class );

        builder.add( "Position_1", String.class );
        builder.add( "Position_2", String.class );
        builder.add( "Person", String.class );
        builder.add( "Organisation", String.class );
        return builder.buildFeatureType();
    }


    private SimpleFeature createAssociation( SimpleFeatureBuilder builder, SimpleFeature baseFeature,
            SimpleFeature organisation, SimpleFeature person ) {
        builder.add( null ); // theGeom
        builder.add( baseFeature.getAttribute( "Orga_Position_1" ) );
        builder.add( baseFeature.getAttribute( "Orga_Position_2" ) );
        builder.add( person.getID() );
        builder.add( organisation.getID() );
        return builder.buildFeature( person.getID() + "_" + organisation.getID() );
    }

}
