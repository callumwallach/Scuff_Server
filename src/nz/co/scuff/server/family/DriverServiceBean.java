package nz.co.scuff.server.family;

import nz.co.scuff.data.family.Driver;
import nz.co.scuff.server.util.AbstractFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Callum on 20/04/2015.
 */
@LocalBean
@Stateless(name = "DriverServiceEJB")
public class DriverServiceBean extends AbstractFacade<Driver> {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public DriverServiceBean() {
        super(Driver.class);
    }

    public Driver findByEmail(String email) {
        if (l.isDebugEnabled()) l.debug("find driver by email="+email);

        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        // Create criteria query and pass the value object which needs to be populated as result
        final CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Driver.class);
        // Tell to criteria query which tables/entities you want to fetch
        final Root taRoot = criteriaQuery.from(Driver.class);
        // Time to define where clause in terms of Predicates
        // This list will contain all Predicates (where clauses)
        List<Predicate> criteriaList = new ArrayList<>();
        Predicate predicate1 = criteriaBuilder.equal(taRoot.get("email"), email);
        criteriaList.add(predicate1);
        criteriaQuery.select(taRoot);
        // Pass the criteria list to the where method of criteria query
        criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[criteriaList.size()])));
        // Here entity manager will create actual SQL query out of criteria query
        Driver found = null;
        try {
            found = (Driver) em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            // just return null
        }
        if (l.isDebugEnabled()) l.debug("found user=" + found);
        return found;
    }

}
