/* Copyright (C) 2015-2018 Falko Bräutigam. All rights reserved. */
package io.mapzone.controller.provision;

import io.mapzone.controller.provision.Provision.Status;
import io.mapzone.controller.provision.Provision.Status.Severity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The shiny new provisioning algorithm :)
 * 
 * @author Falko Bräutigam
 */
public class ProvisionExecutor2
        extends ProvisionExecutor {

    private static final Log log = LogFactory.getLog( ProvisionExecutor2.class );
    
    public ProvisionExecutor2( Class<Provision>[] preliminaries ) {
        super( preliminaries );
    }

    
    @Override
    public Status execute( Provision target ) throws Exception {
        return doExecute( target, 0 );
    }
    
    
    /**
     * Algorithm:
     * <ul>
     * <li>loop over <b>preliminaries</b> until <b>none</b> wants to execute</li>
     * <li>no particular order; Provisions are checked again and again for each run</li>
     * <li>when executed a preliminary is executed as target in a <b>recursive</b> call of this method</li>
     * <br/>
     * <li>try to execute <b>target</b>
     * <li>OK -> return</li>
     * <li>FAILED -> set targetStatus and start again</li>
     * 
     * </ul>
     *
     * @param target The target to execute.
     * @param level The recursion level.
     * @return
     * @throws Exception
     */
    protected Status doExecute( Provision target, int level ) throws Exception {
        log.debug( prefix( level ) + "Target: " + target.getClass().getSimpleName() );
        Status targetStatus = null;
        for (;;) {
            // loop until no condition wants to execute
            boolean conditionExecuted = false;
            while (findAndExecuteNextCondition( target, targetStatus, level )) {
                conditionExecuted = true;
            }
            
            // target tried at least once but failed and no condition can do something
            if (!conditionExecuted && targetStatus != null) {
                return new Status( Severity.FAILED, "No way to execute provision successfully: " + target.getClass().getSimpleName() );                
            }

            // try target
            log.debug( prefix( level ) + "Attempt: " 
                    + "[" + StringUtils.right( Thread.currentThread().getName(), 2 ) + "] " 
                    + target.getClass().getSimpleName() + " ..." );
            targetStatus = executeProvision( target );
            if (targetStatus.severity( Severity.OK )) {
                // ok -> return
                return targetStatus;
            }
        }
    }
    
    
    /**
     * 
     *
     * @param target
     * @param targetStatus
     * @param level
     * @return
     * @throws Exception
     */
    protected boolean findAndExecuteNextCondition( Provision target, Status targetStatus, int level ) throws Exception {
        for (Class cl : preliminaries) {
            Provision candidate = newProvision( cl );
            if (candidate.init( target, targetStatus )) {
                Status status = doExecute( candidate, level+1 );
                log.debug( prefix( level ) + "    status: " + status );
                return true;
            }
        }
        return false;
    }
    
    
    protected String prefix( int level ) {
        return StringUtils.leftPad( "", level*4 );
    }
    
}
