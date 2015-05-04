package nz.co.scuff.server.school;

import nz.co.scuff.data.school.Route;
import nz.co.scuff.server.util.AbstractFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Callum on 4/05/2015.
 */
@LocalBean
@Stateless(name = "RouteServiceEJB")
public class RouteServiceBean extends AbstractFacade<Route> {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RouteServiceBean() {
        super(Route.class);
    }
}
