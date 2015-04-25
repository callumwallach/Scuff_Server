package nz.co.scuff.server.journey;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.server.util.AbstractFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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


}
