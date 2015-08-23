package io.mapzone.controller.provision;

import static io.mapzone.controller.provision.Provision.Status.Severity.OK;
import io.mapzone.controller.provision.Provision.Status;
import io.mapzone.controller.provision.Provision.Status.Severity;
import io.mapzone.controller.vm.provisions.HostRunning;
import io.mapzone.controller.vm.provisions.ProcessRunning;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    
    private Set<Class<? extends Provision>> provisions;
    
    
    public ProvisionExecutor( Class<? extends Provision>[] provisions ) {
        this.provisions = new HashSet( Arrays.asList( provisions ) );
        assert this.provisions.size() == provisions.length;
        
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


    public Provision.Status execute( Provision target ) throws Exception {
        Status status = target.execute();
        if (status.severityEquals( OK )) {
            return status;
        }
        status = createProvision( ProcessRunning.class ).execute();
        if (status.severityEquals( OK )) {
            return status;
        }
        
//        boolean ok = false;
//        while (!ok) {
//            ok = true;
//            for (Class<? extends Provision> type : types) {
//                if (!excluded.contains( type )) {
//                    Provision provision = type.newInstance();
//                    Status status = provision.execute();
//                    if (status.equals( Status.FAILED )) {
//                        return status;
//                    }
//                    else if (status.equals( Status.FAILED_CHECK_AGAIN )) {
//                        ok = false;
//                    }
//                    else if (status.equals( Status.OK )) {
//                        excluded.add( type );
//                    }
//                }
//            }
//        }
    }

    
    protected boolean checkStatus( Status status ) {
        if (status.equals( Status.OK )) {
            return true;
        }        
        else if (status.equals( Status.FA )) {
            return true;
        }        
    }
    
    
    public <T extends Provision> T newTargetProvision( Class<T> type ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }

    
    protected <T extends Provision> T createProvision( Class<T> type ) {
        // XXX Auto-generated method stub
        throw new RuntimeException( "not yet implemented." );
    }
    
}
