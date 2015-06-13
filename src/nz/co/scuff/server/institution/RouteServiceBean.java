package nz.co.scuff.server.institution;

import nz.co.scuff.data.institution.Route;
import nz.co.scuff.server.util.AbstractFacade;
import nz.co.scuff.server.util.AbstractModifiableEntityFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Callum on 4/05/2015.
 */
@LocalBean
@Stateless(name = "RouteServiceEJB")
public class RouteServiceBean extends AbstractModifiableEntityFacade<Route> {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RouteServiceBean() {
        super(Route.class);
    }
}
