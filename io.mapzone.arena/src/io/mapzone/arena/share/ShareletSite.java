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
package io.mapzone.arena.share;

import org.eclipse.swt.graphics.Image;

import org.polymap.core.runtime.config.Concern;
import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.config.DefaultInt;
import org.polymap.core.runtime.config.Mandatory;
import org.polymap.core.runtime.config.PropertyChangeSupport;

import org.polymap.rhei.batik.toolkit.IPanelToolkit;

import io.mapzone.arena.share.ui.ShareContext;

/**
 * All necessary informations to configure and run a sharelet.
 * 
 *
 * @author Steffen Stundzig
 */
public class ShareletSite
        extends Configurable {

    /**
     * The title of the sharelet.
     * <p/>
     * Allow null to allow no header at all.
     */
    @Concern( PropertyChangeSupport.class )
    public Config<String>        title;

    @Concern( PropertyChangeSupport.class )
    public Config<String>        description;

    @Concern( PropertyChangeSupport.class )
    public Config<Image>         image;

    @Mandatory
    public Config<ShareContext>  context;

    @Mandatory
    public Config<IPanelToolkit> tk;

    @Mandatory
    public Config<Integer>       preferredWidth;

    @Mandatory
    @DefaultInt(value=100)
    public Config<Integer>       priority;
}
