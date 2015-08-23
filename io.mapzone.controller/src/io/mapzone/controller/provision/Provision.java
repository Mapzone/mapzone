package io.mapzone.controller.provision;

import io.mapzone.controller.provision.Provision.Status.Severity;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public interface Provision {    
    
    public static final Status  OK_STATUS = new Status( Severity.OK, "" );
    
    /**
     * 
     *
     * @param failed
     * @return True if this Provision wants to be executed for the given context and
     *         failed Provision.
     */
    public boolean init( Provision failed );
    
    public Status execute() throws Exception;

    
    /**
     * 
     */
    public static class Status {

        public enum Severity {
            OK,
            OK_CHECK_AGAIN,
            FAILED_CHECK_AGAIN,
            FAILED
        }

        private Severity            severity;
        
        private String              cause;

        
        public Status( Severity severity, String cause ) {
            this.severity = severity;
            this.cause = cause;
        }

        
        public boolean severityEquals( Severity check ) {
            return this.severity.equals( check );
        }
        
    }

}
