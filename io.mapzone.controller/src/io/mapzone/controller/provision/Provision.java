/* 
 * Copyright (C) 2015, the @authors. All rights reserved.
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
package io.mapzone.controller.provision;

import io.mapzone.controller.provision.Provision.Status.Severity;

import java.util.Optional;

/**
 * Provides a check and an action in order to allow a given <code>target</code>
 * Provision to execute successfully.
 * <p/>
 * A Provision decides in {@link #init(Provision, Status)} if it wants to run in the
 * current status and for a given target. The target is the Provision that is about
 * to be executed. <b>This</b> Provision is now asked to check for and provide the
 * resources for the <code>target</code>.
 * <p/>
 * A Provision can decide in {@link #init(Provision, Status)} to run prior to the
 * target are after the target has failed. A Provision never runs after the target
 * was successfully executed.
 * <p/>
 * Implementations must by <b>stateless</b>. Instances are created for every
 * check/run. Use {@link Context} members provide status to other Provisions or to
 * the next created instance.
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public interface Provision {    
    
    public static final Status  OK_STATUS = new Status( Severity.OK, "" );
    
    
    /**
     * Decides if this Provision wants to run in the current status and for the given
     * <code>target</code> Provision. All {@link Context} members are initialized.
     *
     * @param target The target Provision that is about to be executed.
     * @param cause The {@link Status} that caused the fail of the
     *        <code>target</code> {@link Provision}, or null if the target was not
     *        executed. Non null signals that the target was tried and failed at
     *        least once.
     * @return True if this Provision wants to be executed for the given context and
     *         <code>target</code> Provision.
     */
    public boolean init( Provision failed, Status cause );
 
    /**
     * 
     *
     * @return {@link #OK_STATUS}, or a status that describes why the run failed.
     * @throws Exception
     */
    public Status execute() throws Exception;

    
    /**
     * 
     */
    public static class Status {

        public enum Severity {
            OK, FAILED
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

        public boolean severity( Severity check ) {
            return this.severity.equals( check );
        }

        public Severity severity() {
            return severity;
        }
        
        public String cause() {
            return cause;
        }
        
        public Optional<Throwable> exception() {
            return Optional.ofNullable( exception );
        }

        @Override
        public String toString() {
            return "Status[severity=" + severity + ", cause=" + cause + ", exception=" + exception + "]";
        }
         
    }

}
