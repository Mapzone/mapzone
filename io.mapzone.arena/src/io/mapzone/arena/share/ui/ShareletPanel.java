/*
 * polymap.org Copyright (C) 2016, the @authors. All rights reserved.
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
package io.mapzone.arena.share.ui;

import static org.eclipse.ui.forms.widgets.ExpandableComposite.TREE_NODE;

import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.Section;

import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.ui.ColumnLayoutFactory;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.Context;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.Scope;

import org.polymap.p4.P4Panel;

import io.mapzone.arena.share.Sharelet;
import io.mapzone.arena.share.ShareletSectionProvider;
import io.mapzone.arena.share.content.ShareableContentProvider;
import io.mapzone.arena.share.content.ShareableContentProviders;

/**
 * A Panel to show the content of a sharelet.
 *
 * @author Steffen Stundzig
 */
public class ShareletPanel
        extends P4Panel {

    public static final PanelIdentifier     ID    = PanelIdentifier.parse( "sharelet" );

    @Mandatory
    @Scope( SharePanel.SCOPE )
    protected Context<ShareletPanelContext> shareletPanelContext;

    protected final static int              width = 350;


    @Override
    public void init() {
        site().minWidth.set( shareletPanelContext.get().sharelet.get().site().preferredWidth.get() );
        site().preferredWidth.set( shareletPanelContext.get().sharelet.get().site().preferredWidth.get() );
        site().title.set( shareletPanelContext.get().sharelet.get().site().title.get() );
    }


    @Override
    public void createContents( Composite parent ) {
        parent.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 10, 0, 0, 0 ).spacing( 10 ).create() );
        Sharelet sharelet = shareletPanelContext.get().sharelet.get();
        for (ShareletSectionProvider provider : sharelet.sections()) {
            Optional<ShareableContentProvider> contentBuilder = ShareableContentProviders.instance().get( provider.supportedType(), shareletPanelContext.get().shareContext.get() );
            if (contentBuilder.isPresent()) {
                Section section = tk().createSection( parent, provider.title(), TREE_NODE, Section.SHORT_TITLE_BAR, Section.FOCUS_TITLE, SWT.BORDER );
                section.setExpanded( false );
                section.setBackground( UIUtils.getColor( 235, 235, 235 ) );
                Composite panel = (Composite)section.getClient();
                panel.setLayout( ColumnLayoutFactory.defaults().columns( 1, 1 ).margins( 1 ).spacing( 10 ).create() );
                section.addExpansionListener( new ExpansionAdapter() {

                    boolean firstExpansion = true;


                    @Override
                    public void expansionStateChanging( ExpansionEvent e ) {
                        if (firstExpansion && e.getState()) {
                            provider.createContents( panel, contentBuilder.get() );
                            panel.layout();
                            firstExpansion = false;
                        }
                    }
                } );
            }
        }
    }
}
