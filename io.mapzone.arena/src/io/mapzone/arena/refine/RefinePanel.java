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

import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.geotools.data.FeatureSource;
import org.polymap.core.project.IMap;
import org.polymap.core.runtime.i18n.IMessages;
import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.UIUtils;
import org.polymap.p4.P4Panel;
import org.polymap.p4.P4Plugin;
import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.Mandatory;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.Scope;
import org.polymap.rhei.batik.toolkit.IPanelSection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.mapzone.arena.ArenaPlugin;
import io.mapzone.arena.Messages;

/**
 * Proof-of-concept for starting with refine functions.
 *
 * @author Steffen Stundzig
 */
public class RefinePanel
        extends P4Panel {

    private static final IMessages i18n = Messages.forPrefix( "RefinePanel" );

    private static Log log = LogFactory.getLog( RefinePanel.class );

    public static final PanelIdentifier ID = PanelIdentifier.parse( "refine" );

    private FeatureSource fs;

    @Mandatory
    @Scope(P4Plugin.Scope)
    protected Context<IMap> map;

    private PanelPath path;

    private Composite functionContainer;


    @Override
    public boolean wantsToBeShown() {
        if (site().path().size() == 2) {
            site().icon.set( ArenaPlugin.images().svgImage( "refine.svg", P4Plugin.HEADER_ICON_CONFIG ) );
            site().tooltip.set(i18n.get( "tooltip"));
            site().title.set( "" );
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        site().title.set( i18n.get( "title" ));
    }


    @Override
    public void dispose() {
        super.dispose();
    }

    // XXX replace with extension point
    public static final Class<RefineFunction>[] availableFunctions = new Class[] { OrgPersonSplitTableRefineFunction.class,
            SplitTableRefineFunction.class, ClusterColumnValuesRefineFunction.class };


    @Override
    public void createContents( final Composite parent ) {
        parent.setLayout( FormLayoutFactory.defaults().create() );

        final TreeMap<String,RefineFunction> functions = Maps.newTreeMap();
        for (Class<RefineFunction> cl : availableFunctions) {
            try {
                RefineFunction function = cl.newInstance();
                function.init( map );
                functions.put( function.title(), function );
            }
            catch (Exception e) {
                throw new RuntimeException( e );
            }
        }

        Combo combo = new Combo( parent, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN );
        @SuppressWarnings( "hiding" )
        final Composite functionContainer = tk().createComposite( parent, SWT.NONE );

        final List<String> content = Lists.newArrayList( functions.keySet() );
        combo.setItems( content.stream().toArray( String[]::new ) );
        combo.addSelectionListener( new SelectionAdapter() {

            @Override
            public void widgetSelected( SelectionEvent e ) {
                String functionTitle = content.get( combo.getSelectionIndex() );
                RefineFunction function = functions.get( functionTitle );

                UIUtils.disposeChildren( functionContainer );

                // create panel
                IPanelSection section = tk().createPanelSection( functionContainer, function.description(),
                        SWT.BORDER );
                section.setExpanded( true );
                section.getBody().setLayout( FormLayoutFactory.defaults().create() );
                function.createContents( tk(), section.getBody() );
                FormDataFactory.on( section.getBody() ).fill();

                functionContainer.layout();
            }
        } );

        // layout
        final Label selectLabel = tk().createLabel( parent, i18n.get( "selectFunction" ), SWT.NONE );
        FormDataFactory.on( selectLabel ).top( 1 ).left( 1 );
        FormDataFactory.on( combo ).top( selectLabel, 3 ).left( 1 ).noBottom();
        FormDataFactory.on( functionContainer ).fill().top( combo, 5 );
    }
}
