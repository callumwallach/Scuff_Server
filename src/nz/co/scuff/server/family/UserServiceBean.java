package nz.co.scuff.server.family;

import nz.co.scuff.data.family.Parent;
import nz.co.scuff.data.journey.Journey;
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
@Stateless(name = "UserServiceEJB")
public class UserServiceBean extends AbstractFacade<Parent> {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UserServiceBean() {
        super(Parent.class);
    }

    public Parent findByEmail(String email) {
        if (l.isDebugEnabled()) l.debug("find user by email="+email);

        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        // Create criteria query and pass the value object which needs to be populated as result
        final CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Parent.class);
        // Tell to criteria query which tables/entities you want to fetch
        final Root taRoot = criteriaQuery.from(Parent.class);
        // Time to define where clause in terms of Predicates
        // This list will contain all Predicates (where clauses)
        List<Predicate> criteriaList = new ArrayList<>();
        Predicate predicate1 = criteriaBuilder.equal(taRoot.get("email"), email);
        criteriaList.add(predicate1);
        criteriaQuery.select(taRoot);
        // Pass the criteria list to the where method of criteria query
        criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[criteriaList.size()])));
        // Here entity manager will create actual SQL query out of criteria query
        Parent found = null;
        try {
            found = (Parent) em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            // just return null
        }
        if (l.isDebugEnabled()) l.debug("found user=" + found);
        return found;
    }

}
