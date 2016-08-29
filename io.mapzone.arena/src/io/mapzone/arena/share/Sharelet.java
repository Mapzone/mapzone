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

import java.util.List;
import org.polymap.rhei.batik.toolkit.IPanelToolkit;

/**
 * Base class for all sharelets.
 * 
 * @author Steffen Stundzig
 */
public abstract class Sharelet {

    private ShareletSite      site;


    public ShareletSite site() {
        return site;
    }


    public void init( @SuppressWarnings( "hiding" ) final ShareletSite site ) {
        this.site = site;
    }


    protected IPanelToolkit tk() {
        return site().tk.get();
    }


    /**
     * If the sharelet handles the share action itself, implement this method and return true.
     * Otherwise, a UI is created and createContents is called afterwards.
     *
     * @return true, if the sharelet handles the share action by itself, false otherwise
     */
    public boolean share() {
        return false;
    }


    public abstract List<ShareletSectionProvider> sections();
}
