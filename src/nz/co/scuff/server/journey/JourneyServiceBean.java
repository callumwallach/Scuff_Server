package nz.co.scuff.server.journey;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.util.TrackingState;
import nz.co.scuff.server.util.AbstractFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
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
@Stateless(name = "JourneyServiceEJB")
public class JourneyServiceBean extends AbstractFacade<Journey> {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public JourneyServiceBean() {
        super(Journey.class);
    }

    public Journey findActive(String journeyId) {
        if (l.isDebugEnabled()) l.debug("find active journey by journeyId="+journeyId);

        //create an ejbql expression
        String ejbQL = "select j from Journey j where j.journeyId =:journeyId and j.state !=:state";
        //create query
        Query query = em.createQuery(ejbQL);
        //substitute parameter.
        query.setParameter("journeyId", journeyId);
        query.setParameter("state", TrackingState.COMPLETED);
        //execute the query
        Journey found = (Journey)query.getSingleResult();
        if (l.isDebugEnabled()) l.debug("found journey=" + found);
        return found;

/*        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        // Create criteria query and pass the value object which needs to be populated as result
        final CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Journey.class);
        // Tell to criteria query which tables/entities you want to fetch
        final Root taRoot = criteriaQuery.from(Journey.class);
        // Time to define where clause in terms of Predicates
        // This list will contain all Predicates (where clauses)
        List<Predicate> criteriaList = new ArrayList<Predicate>();
        Predicate predicate1 = criteriaBuilder.equal(taRoot.get("journeyId"), journeyId);
        criteriaList.add(predicate1);
        Predicate predicate3 = criteriaBuilder.notEqual(taRoot.get("state"), TrackingState.COMPLETED);
        criteriaList.add(predicate3);
        criteriaQuery.select(taRoot);
        // Pass the criteria list to the where method of criteria query
        criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[criteriaList.size()])));
        // Here entity manager will create actual SQL query out of criteria query
        Journey found = null;
        try {
            found = (Journey) em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            // just return null
        }
        if (l.isDebugEnabled()) l.debug("found journey=" + found);
        return found;*/
    }

    public List<Journey> findActiveByRouteAndSchool(long routeId, long schoolId) {
        if (l.isDebugEnabled()) l.debug("find journey by routeId=" + routeId + " schoolId=" + schoolId);

        //create an ejbql expression
        String ejbQL = "select j from Journey j where j.route.routeId =:routeId and j.school.schoolId =:schoolId and j.state !=:state";
        //create query
        Query query = em.createQuery(ejbQL);
        //substitute parameter.
        query.setParameter("routeId", routeId);
        query.setParameter("schoolId", schoolId);
        query.setParameter("state", TrackingState.COMPLETED);
        //execute the query
        List<Journey> found = query.getResultList();
        if (l.isDebugEnabled()) {
            for (Journey journey : found) {
                l.debug("found journey=" + journey);
            }
        }
        return found;

/*        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        // Create criteria query and pass the value object which needs to be populated as result
        final CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Journey.class);
        // Tell to criteria query which tables/entities you want to fetch
        final Root taRoot = criteriaQuery.from(Journey.class);
        // Time to define where clause in terms of Predicates
        // This list will contain all Predicates (where clauses)
        List<Predicate> criteriaList = new ArrayList<Predicate>();
        Predicate predicate1 = criteriaBuilder.equal(taRoot.get("route").get("routeId"), routeId);
        criteriaList.add(predicate1);
        Predicate predicate2 = criteriaBuilder.equal(taRoot.get("school").get("schoolId"), schoolId);
        criteriaList.add(predicate2);
        Predicate predicate3 = criteriaBuilder.notEqual(taRoot.get("state"), TrackingState.COMPLETED);
        criteriaList.add(predicate3);
        criteriaQuery.select(taRoot);
        // Pass the criteria list to the where method of criteria query
        criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[criteriaList.size()])));
        // Here entity manager will create actual SQL query out of criteria query
        TypedQuery<Journey> query = em.createQuery(criteriaQuery);
        List<Journey> found = query.getResultList();
        if (l.isDebugEnabled()) {
            for (Journey journey : found) {
                l.debug("found journey=" + journey);
            }
        }
        return found;*/
    }


}
