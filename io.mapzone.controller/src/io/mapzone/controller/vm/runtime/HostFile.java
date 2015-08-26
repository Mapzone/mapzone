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
    
}
