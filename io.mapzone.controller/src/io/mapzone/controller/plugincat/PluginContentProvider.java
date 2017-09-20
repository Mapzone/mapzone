/* 
 * mapzone.io
 * Copyright (C) 2017, the @authors. All rights reserved.
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
package io.mapzone.controller.plugincat;

import io.mapzone.controller.webcat.CatalogBase;
import io.mapzone.controller.webcat.webdav.WebcatContentProvider;

/**
 * 
 *
 * @author Falko Bräutigam
 */
public class PluginContentProvider
        extends WebcatContentProvider {

    @Override
    public String baseFolderName() {
        return "plugins";
    }

    @Override
    protected CatalogBase catalog() {
        return PluginCatalog.instance();
    }
    
}
