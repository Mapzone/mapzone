package io.mapzone.controller.http;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public interface Provision {

    public enum Status {
        OK,
        OK_CHECK_AGAIN,
        FAILED,
        FAILED_CHECK_AGAIN
    }
    
    /**
     * 
     *
     * @param failed
     * @return
     */
    public boolean init( Provision failed );
    
    public Status execute() throws Exception;
    
}
