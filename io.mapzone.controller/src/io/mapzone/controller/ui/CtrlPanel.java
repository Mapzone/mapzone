/* 
 * mapzone.io
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
package io.mapzone.controller.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.toolkit.md.MdToolkit;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public abstract class CtrlPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( CtrlPanel.class );

    
    public MdToolkit tk() {
        return (MdToolkit)site().toolkit();
    }

}
