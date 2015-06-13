package nz.co.scuff.server.journey;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.util.TrackingState;
import nz.co.scuff.server.util.AbstractFacade;
import nz.co.scuff.server.util.AbstractModifiableEntityFacade;

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
public class JourneyServiceBean extends AbstractModifiableEntityFacade<Journey> {

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
    }

/*    // TODO complete
    public List<Journey> findActiveByOwnerOrAgent(long coordinatorId) {
        if (l.isDebugEnabled()) l.debug("find journey by owner or agent :coordinator=" + coordinatorId);

        //create an ejbql expression
        //String ejbQL = "select j from Journey j where j.owner.coordinatorId =:coordinatorId or j.agent.coordinatorId =:coordinatorId and j.state !=:state";
        select b.fname, b.lname from Users b JOIN b.groups c where c.groupName = :groupName

                select j from Journey j join j.owner o where o.coordinatorId member of
                select j from Journeys
        String jpQL = "SELECT c FROM Coordinator c WHERE :value MEMBER OF c.friends";
        String ejbQL = "select j from Journey j, Coordinator c, " +
                "IN (c.friends) as allFriends, Coordinator oneFriend where j.owner member of allFriends or j.agent member of allFriends" +
                "and j.owner.coordinatorId =:coordinatorId or j.agent.coordinatorId =:coordinatorId and j.state !=:state";
        //create query
        Query query = em.createQuery(jpQL);
        //substitute parameter.
        query.setParameter("coordinatorId", coordinatorId);
        query.setParameter("state", TrackingState.COMPLETED);
        //execute the query
        List<Journey> found = query.getResultList();
        if (l.isDebugEnabled()) {
            for (Journey journey : found) {
                l.debug("found journey=" + journey);
            }
        }
        return found;
    }*/

/*    public List<Journey> findActiveByRouteAndSchool(long routeId, long schoolId) {
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
    }*/
}
