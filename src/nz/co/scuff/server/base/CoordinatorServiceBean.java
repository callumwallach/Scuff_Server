package nz.co.scuff.server.base;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.server.util.AbstractFacade;
import org.hibernate.Hibernate;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Callum on 4/06/2015.
 */
@LocalBean
@Stateless(name = "CoordinatorServiceEJB")
public class CoordinatorServiceBean extends AbstractFacade<Coordinator> {

    public static final int PAST_JOURNEYS = 1;

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CoordinatorServiceBean() {
        super(Coordinator.class);
    }

    public Coordinator load(long id, int[] properties) {

        Coordinator skeleton = find(id);
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

}
