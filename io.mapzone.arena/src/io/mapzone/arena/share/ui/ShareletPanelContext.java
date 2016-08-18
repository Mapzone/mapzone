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

import org.polymap.core.runtime.config.Config;
import org.polymap.core.runtime.config.Configurable;
import org.polymap.core.runtime.config.Mandatory;

import io.mapzone.arena.share.Sharelet;

/**
 * A context to propagate data from the sharelet dashlet ui to the sharelet panel.
 *
 * @author Steffen Stundzig
 */
public class ShareletPanelContext
        extends Configurable {

    @Mandatory
    public Config<Sharelet>     sharelet;

    @Mandatory
    public Config<ShareContext> shareContext;
}
