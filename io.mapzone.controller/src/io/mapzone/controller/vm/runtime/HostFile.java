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
package io.mapzone.controller.vm.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class HostFile {

    public abstract InputStream inputStream();
    
    public abstract OutputStream outputStream();
    
    public abstract boolean exists();

    public abstract String content() throws IOException;
    
    public abstract String content( String charset ) throws IOException;

    /**
     * 
     *
     * @param in The source to read from. Closed after return.
     * @throws IOException
     */
    public abstract void write( InputStream in ) throws IOException;
    
}
