/* 
 * polymap.org
 * Copyright (C) 2016, the @authors. All rights reserved.
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
package io.mapzone.arena.analytics.graph;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.polymap.core.mapeditor.MapViewer;
import org.polymap.rap.openlayers.base.OlFeature;
import org.polymap.rap.openlayers.layer.VectorLayer;
import org.polymap.rap.openlayers.source.VectorSource;
import org.polymap.rap.openlayers.style.Base;
import org.polymap.rap.openlayers.style.StrokeStyle;
import org.polymap.rap.openlayers.style.Style;
import org.polymap.rap.openlayers.style.StyleFunction;
import org.polymap.rap.openlayers.types.Color;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class OrganisationPersonGraphFunction extends GraphFunction {

    private static Log log = LogFactory.getLog(OrganisationPersonGraphFunction.class);

    private final Style edgeStyle = new Style().stroke
            .put(new StrokeStyle().color.put(new Color("black")).width.put(1f)).zIndex.put(0f);

    @Override
    public void init(VectorSource source, MapViewer<VectorLayer> map) {
        super.init(source, map);
    }

    @Override
    public void addFeatures(FeatureCollection featureCollection) throws Exception {

        final Map<String, OlFeature> organisations = Maps.newHashMap();
        final Map<String, OlFeature> persons = Maps.newHashMap();
        final Multimap<OlFeature, OlFeature> organisation2Persons = ArrayListMultimap.create();
        final Multimap<OlFeature, OlFeature> person2Organisations = ArrayListMultimap.create();

        // iterate on features
        // create OlFeature for each organisation
        // increase weight for each entry per organisation
        FeatureIterator iterator = featureCollection.features();
        int i = 0;
        while (iterator.hasNext() && i < 5000) {
            i++;
            SimpleFeature feature = (SimpleFeature) iterator.next();
            String organisationKey = (String) feature.getAttribute("Organisation");
            OlFeature organisationFeature = organisations.get(organisationKey);
            if (organisationFeature == null) {
                organisationFeature = new OlFeature("o:" + feature.getID()).name.put(organisationKey).style
                        .put(organisationStyle(1));
                organisations.put(organisationKey, organisationFeature);
                graph.addOrUpdateNode(organisationFeature);
            } else {
                // add weight
                int size = organisation2Persons.get(organisationFeature).size() + 1;
                if (size <= 15) {
                    organisationFeature.style.set(organisationStyle(size));
                }
                graph.addOrUpdateNode(organisationFeature, 1);
            }
            String personKey = (String) feature.getAttribute("Name") + " " + (String) feature.getAttribute("Vorname");
            OlFeature personFeature = persons.get(personKey);
            if (personFeature == null) {
                personFeature = new OlFeature("p:" + feature.getID()).name.put(personKey).style.put(personStyle(1));
                persons.put(personKey, personFeature);
                graph.addOrUpdateNode(personFeature, 1);
            } else {
                int size = person2Organisations.get(personFeature).size() + 1;
                if (size <= 15) {
                    personFeature.style.set(personStyle(size));
                }
                graph.addOrUpdateNode(personFeature, 1);
            }
            // add also the person to the organisation
            organisation2Persons.put(organisationFeature, personFeature);
            person2Organisations.put(personFeature, organisationFeature);

            OlFeature edgeFeature = new OlFeature(organisationFeature.id.get() + "_" + personFeature.id.get()).name
                    .put((String) feature.getAttribute("Orga_Position_1")).style.put(edgeStyle());
            graph.addOrUpdateEdge(edgeFeature, organisationFeature, personFeature, 1);

            if (i % 100 == 0) {
                log.info("added " + i);
            }
        }
        log.info(organisations.size() + " orgs, " + persons.size() + " persons, " + organisation2Persons.size()
                + " egdes");
        organisations.clear();
        persons.clear();
        organisation2Persons.clear();
        person2Organisations.clear();
        graph.reload();
    }

    private Base edgeStyle() {
        return edgeStyle;
    }

    private Base organisationStyle(int weight) {
        return new StyleFunction(circle(weight, "blue"));
    }

    private Base personStyle(int weight) {
        return new StyleFunction(circle(weight, "red"));
    }

    private String circle(int radius, String color) {
        StringBuffer sb = new StringBuffer();
        // sb.append(
        // "console.log('singlefeature');console.log(feature);console.log(this);"
        // );
        sb.append("return [new ol.style.Style({");
        sb.append("  zIndex: 1,");
        sb.append("  image: new ol.style.Circle({");
        sb.append("      radius: ").append(radius).append(",");
        sb.append("    stroke: new ol.style.Stroke({");
        sb.append("      color: '").append(color).append("'");
        sb.append("    }),");
        sb.append("    fill: new ol.style.Fill({");
        sb.append("      color: '").append(color).append("'");
        sb.append("    })");
        sb.append("  }),");
        sb.append("  text: new ol.style.Text({");
        sb.append("    text: this.get('name'),");
        sb.append("    fill: new ol.style.Fill({");
        sb.append("      color: '").append(color).append("'");
        sb.append("    })");
        sb.append("  })");
        sb.append("})];");
        return sb.toString();
    }
}
