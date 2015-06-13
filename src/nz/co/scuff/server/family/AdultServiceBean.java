package nz.co.scuff.server.family;

import nz.co.scuff.data.family.Adult;
import nz.co.scuff.server.util.AbstractFacade;
import nz.co.scuff.server.util.AbstractModifiableEntityFacade;
import org.hibernate.Hibernate;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@Stateless(name = "AdultServiceEJB")
public class AdultServiceBean extends AbstractModifiableEntityFacade<Adult> {

    public static final int PAST_JOURNEYS = 1;

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public AdultServiceBean() {
        super(Adult.class);
    }

    public Adult load(long id, int[] properties) {

        Adult skeleton = find(id);
        for (int property : properties) {
            switch (property) {
                case PAST_JOURNEYS:
                    if (!Hibernate.isInitialized(skeleton.getPastJourneys()))
                        Hibernate.initialize(skeleton.getPastJourneys());
                    break;
                default:
                    break;
            }
        }
        return skeleton;

    }

    public Adult findByEmail(String email) {
        if (l.isDebugEnabled()) l.debug("find driver by email="+email);

        //create an ejbql expression
        String ejbQL = "select a from Adult a where a.personalData.email =:email";
        //create query
        Query query = em.createQuery(ejbQL);
        //substitute parameter.
        query.setParameter("email", email);
        //execute the query
        Adult found = (Adult)query.getSingleResult();
        if (l.isDebugEnabled()) l.debug("found adult=" + found);
        return found;

/*
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        // Create criteria query and pass the value object which needs to be populated as result
        final CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Adult.class);
        // Tell to criteria query which tables/entities you want to fetch
        final Root taRoot = criteriaQuery.from(Adult.class);
        // Time to define where clause in terms of Predicates
        // This list will contain all Predicates (where clauses)
        List<Predicate> criteriaList = new ArrayList<>();
        Predicate predicate1 = criteriaBuilder.equal(taRoot.get("email"), email);
        criteriaList.add(predicate1);
        criteriaQuery.select(taRoot);
        // Pass the criteria list to the where method of criteria query
        criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[criteriaList.size()])));
        // Here entity manager will create actual SQL query out of criteria query
        Adult found = null;
        try {
            found = (Adult) em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            // just return null
        }
        if (l.isDebugEnabled()) l.debug("found user=" + found);
        return found;*/
    }

}
