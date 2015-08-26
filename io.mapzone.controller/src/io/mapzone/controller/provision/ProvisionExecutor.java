package io.mapzone.controller.provision;

import io.mapzone.controller.http.DefaultProvision;
import io.mapzone.controller.model.ProjectRepository;
import io.mapzone.controller.provision.Provision.Status;
import io.mapzone.controller.provision.Provision.Status.Severity;
import io.mapzone.controller.vm.repository.VmRepository;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProvisionExecutor {

    private ContextFactory                  contextFactory;

    private Map<Pair<Class,String>,Object>  contextValues;
    
    private Class<? extends Provision>[]    provisions;
    
    
    public ProvisionExecutor( Class<? extends Provision>[] provisions ) {
        this.provisions = provisions;
        
        contextValues = new HashMap();
        contextFactory = new ContextFactory() {
            @Override
            protected void setValue( Class type, String scope, Object value ) {
                contextValues.put( ImmutablePair.of( type, scope ), value ); 
            }
            @Override
            protected Object getValue( Class type, String scope ) {
                return contextValues.get( ImmutablePair.of( type, scope ) ); 
            }
        };
    }


    public Status execute( Provision target ) throws Exception {
        int provisionIndex = 0;
        Provision failed = null;
        Status cause = null;
        
        while (provisionIndex < provisions.length) {
            Class<? extends Provision> type = provisions[ provisionIndex ];
            Provision provision = createProvision( type );
            assert provision.init( failed, cause );
            
            // execute
            Status status = executeProvision( provision );
            
            // OK: one step up, or return if index==0
            if (status.severityEquals( Severity.OK )) {
                if (provisionIndex == 0) {
                    return status;
                }
                else {
                    failed = null;
                    cause = null;
                    provisionIndex --;
                }
            }
            // FAILED: return
            else if (status.severityEquals( Severity.FAILED )) {
                return status;
            }
            // FAILED_CHECK_AGAIN: one provision step down
            else if (status.severityEquals( Severity.FAILED_CHECK_AGAIN )) {
                failed = provision;
                cause = status;
                provisionIndex ++;
            }
            // not supported (yet)
            else {
                throw new RuntimeException( "Not supported status: " + status );
            }
        }
        throw new RuntimeException( "we should never get here." );
    }

    
    protected Status executeProvision( Provision provision ) throws Exception {
        VmRepository vmRepo = VmRepository.instance();
        ProjectRepository projectRepo = ProjectRepository.instance();
        try {
            ((DefaultProvision)provision).vmRepo.set( vmRepo );
            ((DefaultProvision)provision).projectRepo.set( projectRepo );
            
            Status result = provision.execute();

            vmRepo.commit();
            projectRepo.commit();
            return result;
        }
        catch (Exception e) {
            vmRepo.rollback();
            projectRepo.rollback();
            throw e;
        }
    }
    
    
    public <T extends Provision> T newTargetProvision( Class<T> type ) throws Exception {
        return createProvision( type );
    }

    
    protected <T extends Provision> T createProvision( Class<T> type ) throws InstantiationException, IllegalAccessException {
        T result = type.newInstance();
        contextFactory.inject( result );
        return result;
    }
    
}
