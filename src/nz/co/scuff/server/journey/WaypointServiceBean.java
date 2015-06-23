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

}
