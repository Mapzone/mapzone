package io.mapzone.controller.um.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.polymap.rhei.batik.tx.TxProvider;

import org.polymap.model2.runtime.EntityRepository;

/**
 * Holds the global {@link EntityRepository} of users and projects.
 *
 * @see TxProvider
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ProjectRepoProvider
        extends TxProvider<ProjectRepository> {

    private static Log log = LogFactory.getLog( ProjectRepoProvider.class );
    
    @Override
    protected ProjectRepository newTx( ProjectRepository parent ) {
        return parent != null ? parent.nestedRepo() : ProjectRepository.instance();
    }

    @Override
    protected void commitTx( ProjectRepository tx ) {
        tx.commit();
    }

    @Override
    protected void rollbackTx( ProjectRepository tx ) {
        tx.rollback();
    }

    @Override
    protected void closeTx( ProjectRepository tx ) {
        tx.close();
    }
    
}
