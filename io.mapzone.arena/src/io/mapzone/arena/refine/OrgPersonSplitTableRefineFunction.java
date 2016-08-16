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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.DataAccess;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.polymap.core.catalog.resolve.IResolvableInfo;
import org.polymap.core.catalog.resolve.IResourceInfo;
import org.polymap.core.data.rs.catalog.RServiceInfo;
import org.polymap.core.data.util.NameImpl;
import org.polymap.core.operation.DefaultOperation;
import org.polymap.core.operation.OperationSupport;
import org.polymap.core.project.ILayer;
import org.polymap.core.project.IMap;
import org.polymap.core.project.ops.NewLayerOperation;
import org.polymap.core.style.DefaultStyle;
import org.polymap.core.style.model.FeatureStyle;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.StatusDispatcher;
import org.polymap.p4.P4Plugin;
import org.polymap.p4.project.ProjectRepository;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.toolkit.Snackbar.Appearance;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

/**
 * hardcoded *splittable* for teamq
 * 
 * @author Steffen Stundzig
 */
@SuppressWarnings( "hiding" )
public class OrgPersonSplitTableRefineFunction implements RefineFunction {

    private static Log log = LogFactory.getLog(OrgPersonSplitTableRefineFunction.class);

    private final static GeometryFactory GEOMETRYFACTORY = JTSFactoryFinder.getGeometryFactory(null);

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

    private Button fab;

    private FeatureSource fs;

    private Context<IMap> map;

    private Properties locations;

    @Override
    public String title() {
        return "Q - Split mapzone recherche";
    }

    @Override
    public String description() {
        return "Q - Split mapzone recherche into Persons, Organizations and Roles; Geocode also Organizations";
    }

    @Override
    public void init( Context<IMap> map) {
        this.map = map;
        this.locations = new Properties();
        try {
            this.locations.load(this.getClass().getResourceAsStream("locations.properties"));
        } catch (IOException e) {
            StatusDispatcher.handleError("", e);
        }
    }

    @Override
    public void createContents(final MdToolkit tk, final Composite parent) {
        try {
            Control combo = new FeatureSourceSelectorUI(tk, parent, fs -> onSelectSource(fs)).control();

            fab = tk.createFab();
            fab.setVisible(false);
            fab.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    DefaultOperation op = new DefaultOperation("Split Table") {

                        @Override
                        public IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws Exception {
                            splitSource(tk, monitor);
                            return Status.OK_STATUS;
                        }
                    };
                    // execute
                    OperationSupport.instance().execute2(op, true, false);
                    fab.setVisible(false);
                }
            });
            final Label selectLabel = tk.createLabel(parent, "Choose your source table: ", SWT.NONE);
            FormDataFactory.on(selectLabel).top(5).left(1);
            FormDataFactory.on(combo).top(selectLabel, 3).left(1);
        } catch (Exception e) {
            StatusDispatcher.handleError("", e);
        }
    }

    private void onSelectSource(final FeatureSource featureSource) {
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
            fab.setVisible(true);
            fab.setEnabled(true);
        }
    }

    protected void splitSource(final MdToolkit tk, final IProgressMonitor monitor) {
        try {
            final SimpleFeatureType organisationType = buildOrganisationType(fs);
            final SimpleFeatureBuilder organisationBuilder = new SimpleFeatureBuilder(organisationType);
            final DefaultFeatureCollection organisations = new DefaultFeatureCollection(null, organisationType);

            final SimpleFeatureType personType = buildPersonType(fs);
            final SimpleFeatureBuilder personBuilder = new SimpleFeatureBuilder(personType);
            final DefaultFeatureCollection persons = new DefaultFeatureCollection(null, personType);

            final SimpleFeatureType associationType = buildAssociationType(fs);
            final SimpleFeatureBuilder associationBuilder = new SimpleFeatureBuilder(associationType);
            final DefaultFeatureCollection associations = new DefaultFeatureCollection(null, associationType);

            final Map<String, SimpleFeature> organisationCache = Maps.newHashMap();
            final Map<String, SimpleFeature> personCache = Maps.newHashMap();

            // iterate on features
            // create OlFeature for each organisation
            // increase weight for each entry per organisation
            if (!tk.isClosed()) {
                tk.createSnackbar(Appearance.FadeIn, "Splitting started - stay tuned...");
            }
            FeatureIterator iterator = fs.getFeatures().features();
            int max = fs.getFeatures().size();
            monitor.beginTask("Split Table", max);
            int i = 0;
            while (iterator.hasNext()) {
                i++;
                SimpleFeature baseFeature = (SimpleFeature) iterator.next();
                String organisationKey = (String) baseFeature.getAttribute("Organisation");
                if (!StringUtils.isBlank(organisationKey) && !"?".equals(organisationKey)
                        && !StringUtils.isBlank((String) baseFeature.getAttribute("Name"))
                        && !StringUtils.isBlank((String) baseFeature.getAttribute("Vorname"))) {
                    organisationKey = organisationKey.trim();
                    SimpleFeature organisation = organisationCache.get(organisationKey);
                    if (organisation == null) {
                        organisation = createOrganisation(organisationBuilder, baseFeature);
                        organisationCache.put(organisationKey, organisation);
                        organisations.add(organisation);
                    }
                    String personKey = (String) baseFeature.getAttribute("Name") + " "
                            + (String) baseFeature.getAttribute("Vorname");
                    SimpleFeature person = personCache.get(personKey);
                    if (person == null) {
                        person = createPerson(personBuilder, baseFeature);
                        personCache.put(personKey, person);
                        persons.add(person);
                    }
                    // create also the association
                    associations.add(createAssociation(associationBuilder, baseFeature, organisation, person));
                }
                monitor.worked(i);
                if (i % 100 == 0) {
                    if (!tk.isClosed()) {
                        tk.createSnackbar(Appearance.FadeIn, i + " von " + max + " migrated and geocoded");
                    }
                }
            }
            addLayerAndStore(organisations);
            addLayerAndStore(persons);
            store(associations);
            if (!tk.isClosed()) {
                tk.createSnackbar( Appearance.FadeIn, organisations.size() + " Organisations, " + persons.size()
                        + " Persons and " + associations.size() + " PersonOrganisationRelations added" );
            }
        } catch (Exception e) {
            StatusDispatcher.handleError("", e);
        }
    }

    private void addLayerAndStore(final DefaultFeatureCollection features) throws IOException {
        store(features);
        FeatureStyle featureStyle = P4Plugin.styleRepo().newFeatureStyle();
        DefaultStyle.createAllStyles(featureStyle);
        // log.info( "FeatureStyle.id: " + featureStyle.id() );
        featureStyle.store();

        NewLayerOperation op = new NewLayerOperation()
                .uow.put( ProjectRepository.unitOfWork() )
                .map.put( map.get() )
                .initializer.put( (ILayer proto) -> {
                    proto.label.set( features.getSchema().getName().getLocalPart() );
                    proto.resourceIdentifier.set( resourceIdentifier( features ) );
                    proto.styleIdentifier.set( featureStyle.id() );
                    return proto;
                });
        OperationSupport.instance().execute(op, true, false);
    }

    public String resourceIdentifier(final DefaultFeatureCollection features) throws IOException {
        IResolvableInfo info = P4Plugin.localCatalog().localFeaturesStoreInfo();
        IResourceInfo res = ((RServiceInfo) info.getServiceInfo()).resource(
                P4Plugin.localCatalog().localFeaturesStore().getFeatureSource(features.getSchema().getName()));
        return P4Plugin.localResolver().resourceIdentifier(res);
    }

    private void store(final DefaultFeatureCollection features) throws IOException {
        DataAccess ds = P4Plugin.localCatalog().localFeaturesStore();
        // XXX transaction that spans createSchema() and addFeatures()!?
        ds.createSchema(features.getSchema());
        ((FeatureStore) ds.getFeatureSource(features.getSchema().getName())).addFeatures(features);
    }

    private SimpleFeatureType buildOrganisationType(final FeatureSource fs) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName(new NameImpl("Organisation_" + sdf.format(new Date())));
        builder.setCRS(DefaultGeographicCRS.WGS84);
        builder.setDefaultGeometry("theGeom");
        builder.add("theGeom", Point.class);

        builder.add("Name", String.class);
        builder.add("PLZ", String.class);
        builder.add("Ort", String.class);
        builder.add("Strasse", String.class);
        builder.add("Hausnummer", String.class);
        builder.add("Kategorie", String.class);
        return builder.buildFeatureType();
    }

    private SimpleFeature createOrganisation(SimpleFeatureBuilder builder, SimpleFeature baseFeature) throws Exception {
        final String plzOrt = (String) baseFeature.getAttribute("Orga_Adresse_PLZ_Ort");
        String plz = "";
        String ort = "";
        if (!StringUtils.isBlank(plzOrt)) {
            int index = plzOrt.indexOf(" ");
            if (index != -1) {
                plz = plzOrt.substring(0, index);
                ort = plzOrt.substring(index + 1);
            } else {
                ort = plzOrt;
            }
        }
        final String strasseHausNr = (String) baseFeature.getAttribute("Orga_Adresse_Str_HN");
        String strasse = "";
        String hausNr = "";
        if (!StringUtils.isBlank(strasseHausNr)) {
            int index = strasseHausNr.lastIndexOf(" ");
            if (index != -1) {
                strasse = strasseHausNr.substring(0, index);
                hausNr = strasseHausNr.substring(index + 1);
            } else {
                strasse = strasseHausNr;
            }
        }
        builder.add(coordinateForOrganisation((String) baseFeature.getAttribute("Organisation"),
                !StringUtils.isBlank(plz) ? plz : plzOrt, strasseHausNr));

        builder.add(((String) baseFeature.getAttribute("Organisation")).trim());
        builder.add(plz);
        builder.add(ort);
        builder.add(strasse);
        builder.add(hausNr);
        builder.add(baseFeature.getAttribute("Orga_Kategorie"));
        return builder.buildFeature(null);
    }

    protected Point coordinateForOrganisation(final String organisation, final String plz, final String strasseHausNr)
            throws Exception {
        if (StringUtils.isBlank(plz) || StringUtils.isBlank(strasseHausNr)) {
            return null;
        }

        final String search1 = "\"lat\":";
        final String search2 = ",\"lng\":";
        final String search3 = "}";
        final String key = "AAdiimU0EwFAoY3G7M7hFx694EdRTuSa";
        final String location = URLEncoder.encode(Joiner.on(", ").join(plz, strasseHausNr), "UTF-8");

        if (locations.containsKey(location)) {
            String coord = locations.getProperty(location);
            String[] coords = coord.split("\\|");
            return GEOMETRYFACTORY.createPoint(
                    new Coordinate(Double.parseDouble(coords[0]), Double.parseDouble(coords[1])));
        } else {
            final String url = String.format("http://www.mapquestapi.com/geocoding/v1/address?key=%1$2s&location=%2$2s",
                    key, location);
            final HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.addRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true);
            try {
                if (con.getResponseCode() != 200) {
                    System.err.println("Keine Koordinate für " + organisation + ": " + con.getResponseMessage());
                } else {
                    final String out = IOUtils.toString(con.getInputStream());
                    int index1 = out.indexOf(search1);
                    if (index1 > 0) {
                        String out2 = out.substring(index1 + search1.length());
                        int index3 = out2.indexOf(search3);
                        if (index3 > 0) {
                            out2 = out2.substring(0, index3);
                            int index2 = out2.indexOf(search2);
                            if (index2 > 0) {
                                final String latitude = out2.substring(0, index2);
                                final String longitude = out2.substring(index2 + search2.length());
                                Point point = (latitude != null && longitude != null) ? GEOMETRYFACTORY.createPoint(
                                        new Coordinate(Double.parseDouble(longitude), Double.parseDouble(latitude)))
                                        : null;
                                return point;
                            }
                        }
                    }
                }
            } finally {
                con.disconnect();
            }
        }
        return null;
    }

    private SimpleFeatureType buildPersonType(final FeatureSource fs) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName(new NameImpl("Person_" + sdf.format(new Date())));
        builder.setCRS(DefaultGeographicCRS.WGS84);
        builder.setDefaultGeometry("theGeom");
        builder.add("theGeom", Point.class);

        builder.add("Name", String.class);
        builder.add("Vorname", String.class);
        builder.add("Name komplett", String.class);
        builder.add("Titel", String.class);
        builder.add("Position_1", String.class);
        builder.add("Position_2", String.class);
        builder.add("Str_HN", String.class);
        builder.add("PLZ_Ort", String.class);
        builder.add("Anmerkung 1", String.class);
        builder.add("Quelle", String.class);
        builder.add("Bearbeitet am", String.class);
        builder.add("Bearbeitet von", String.class);
        builder.add("Internetquelle_fuer_Gemeinderat", String.class);
        builder.add("weitere_Internetquelle", String.class);
        return builder.buildFeatureType();
    }

    private SimpleFeature createPerson(SimpleFeatureBuilder builder, SimpleFeature baseFeature) {
        builder.add(null); // theGeom
        String name = ((String) baseFeature.getAttribute("Name")).trim();
        String vorname = ((String) baseFeature.getAttribute("Vorname")).trim();
        String titel = (String) baseFeature.getAttribute("Titel");
        builder.add(name);
        builder.add(vorname);
        StringBuffer komplett = new StringBuffer();
        if (!StringUtils.isBlank(titel)) {
            komplett.append(titel).append(" ");
        }
        komplett.append(vorname).append(" ").append(name);
        builder.add(komplett.toString());
        builder.add(titel);
        builder.add(baseFeature.getAttribute("Position_1"));
        builder.add(baseFeature.getAttribute("Position_2"));
        builder.add(baseFeature.getAttribute("Pers_Adresse_Str_HN"));
        builder.add(baseFeature.getAttribute("Pers_Adresse_PLZ_Ort"));
        builder.add(baseFeature.getAttribute("Anmerkung 1"));
        builder.add(baseFeature.getAttribute("Quelle"));
        builder.add(baseFeature.getAttribute("Bearbeitet am"));
        builder.add(baseFeature.getAttribute("Bearbeitet von"));
        builder.add(baseFeature.getAttribute("Internet-Quelle für Gemeinderat ( nur einmal pro Person ausfüllen)"));
        builder.add(baseFeature.getAttribute("weitere Internet-Quelle"));
        return builder.buildFeature(null);
    }

    private SimpleFeatureType buildAssociationType(final FeatureSource fs) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName(new NameImpl("PersonOrganisation_" + sdf.format(new Date())));
        builder.setCRS(DefaultGeographicCRS.WGS84);
        builder.setDefaultGeometry("theGeom");
        builder.add("theGeom", Point.class);

        builder.add("Position_1", String.class);
        builder.add("Position_2", String.class);
        builder.add("PersonId", String.class);
        builder.add("OrganisationId", String.class);
        builder.add("Person", String.class);
        builder.add("Organisation", String.class);
        return builder.buildFeatureType();
    }

    private SimpleFeature createAssociation(SimpleFeatureBuilder builder, SimpleFeature baseFeature,
            SimpleFeature organisation, SimpleFeature person) {
        builder.add(null); // theGeom
        builder.add(baseFeature.getAttribute("Orga_Position_1"));
        builder.add(baseFeature.getAttribute("Orga_Position_2"));
        builder.add(person.getID());
        builder.add(organisation.getID());
        builder.add(person.getAttribute("Name komplett"));
        builder.add(organisation.getAttribute("Name"));
        return builder.buildFeature(null);
    }

    public static void main(String[] args) {
        try {
            Point coordinateForOrganisation = new OrgPersonSplitTableRefineFunction().coordinateForOrganisation("foo",
                    "49565", "Fasanenweg 12");
            System.err.println(
                    coordinateForOrganisation.getCoordinate().x + " " + coordinateForOrganisation.getCoordinate().y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
