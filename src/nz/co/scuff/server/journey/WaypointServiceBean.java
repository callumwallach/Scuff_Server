package nz.co.scuff.server.journey;

import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.server.util.AbstractFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Callum on 20/04/2015.
 */
@LocalBean
@Stateless(name = "WaypointServiceEJB")
public class WaypointServiceBean extends AbstractFacade<Waypoint> {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public WaypointServiceBean() {
        super(Waypoint.class);
    }

/*    public Waypoint findByMostRecent(String username, long tradeId) {
        if (l.isDebugEnabled()) l.debug("getting waypoint by journey[" + journey + "] and trade [" + tradeId + "]");

        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        // Create criteria query and pass the value object which needs to be populated as result
        final CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(TradeItem.class);
        // Tell to criteria query which tables/entities you want to fetch
        final Root taRoot = criteriaQuery.from(TradeItem.class);
        // Time to define where clause in terms of Predicates
        // This list will contain all Predicates (where clauses)
        List<Predicate> criteriaList = new ArrayList<Predicate>();
        // [1] where condition: ta.user.username = ?
        Predicate predicate1 = criteriaBuilder.equal(taRoot.get("user").get("username"), username);
        criteriaList.add(predicate1);
        // [2] where condition: ta.trade.id = ?
        Predicate predicate2= criteriaBuilder.equal(taRoot.get("trade"). get("id"), tradeId);
        criteriaList.add(predicate2);
        criteriaQuery.select(taRoot);
        // Pass the criteria list to the where method of criteria query
        criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[criteriaList.size()])));
        // Here entity manager will create actual SQL query out of criteria query
        return (TradeItem)em.createQuery(criteriaQuery).getSingleResult();
    }*/


}
