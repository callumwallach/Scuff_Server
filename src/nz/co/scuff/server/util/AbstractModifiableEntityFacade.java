package nz.co.scuff.server.util;

import nz.co.scuff.data.base.ModifiableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

/**
 * Created by Callum on 13/06/2015.
 */
public abstract class AbstractModifiableEntityFacade<T extends ModifiableEntity> extends AbstractFacade<T> {

    public static final Logger l = LoggerFactory.getLogger(AbstractModifiableEntityFacade.class.getCanonicalName());

    public AbstractModifiableEntityFacade(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public void edit(T entity) {
        if (l.isDebugEnabled()) l.debug("edit "+ entity.getClass().getCanonicalName());
        entity.setLastModified(new Timestamp(System.currentTimeMillis()));
        getEntityManager().merge(entity);
    }

}
