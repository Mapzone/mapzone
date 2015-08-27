package io.mapzone.controller.provision;

import io.mapzone.controller.provision.Provision.Status.Severity;

import java.util.Optional;

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
     * @param failed The upper {@link Provision} that failed.
     * @param cause The {@link Status} that caused the fail of the upper {@link Provision}.
     * @return True if this Provision wants to be executed for the given context and
     *         failed Provision.
     */
    public boolean init( Provision failed, Status cause );
    
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
        
        private Throwable           exception;    

        
        public Status( Severity severity, String cause ) {
            this( severity, cause, null );
        }

        public Status( Severity severity, String cause, Throwable e ) {
            this.severity = severity;
            this.cause = cause;
            this.exception = e;
        }

        public boolean severityEquals( Severity check ) {
            return this.severity.equals( check );
        }

        public Severity getSeverity() {
            return severity;
        }
        
        public String getCause() {
            return cause;
        }
        
        public Optional<Throwable> getException() {
            return Optional.ofNullable( exception );
        }

        @Override
        public String toString() {
            return "Status[severity=" + severity + ", cause=" + cause + ", exception=" + exception + "]";
        }
         
    }

}
