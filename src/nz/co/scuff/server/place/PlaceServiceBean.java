package nz.co.scuff.server.place;

import nz.co.scuff.data.place.Place;
import nz.co.scuff.server.util.AbstractFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Callum on 20/04/2015.
 */
@LocalBean
@Stateless(name = "PlaceServiceEJB")
public class PlaceServiceBean extends AbstractFacade<Place> {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaceServiceBean() {
        super(Place.class);
    }

}
