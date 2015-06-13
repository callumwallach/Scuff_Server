package nz.co.scuff.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Callum on 7/08/2014.
 */
public abstract class AbstractFacade<T> implements AbstractFacadeInterface<T> {

    public static final Logger l = LoggerFactory.getLogger(AbstractFacade.class.getCanonicalName());

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        if (l.isDebugEnabled()) l.debug("create "+ entityClass.getCanonicalName());
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        if (l.isDebugEnabled()) l.debug("edit "+ entityClass.getCanonicalName());
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        if (l.isDebugEnabled()) l.debug("remove "+ entityClass.getCanonicalName());
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    // returns null if entity not found
    public T find(Object id) {
        if (l.isDebugEnabled()) l.debug("find "+ entityClass.getCanonicalName());
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        if (l.isDebugEnabled()) l.debug("findAll "+ entityClass.getCanonicalName());
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        if (l.isDebugEnabled()) l.debug("findRange "+ entityClass.getCanonicalName());
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        if (l.isDebugEnabled()) l.debug("count "+ entityClass.getCanonicalName());
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
