package nz.co.scuff.server.institution;

import nz.co.scuff.data.institution.Institution;
import nz.co.scuff.server.util.AbstractFacade;
import org.hibernate.Hibernate;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Callum on 4/05/2015.
 */
@LocalBean
@Stateless(name = "InstitutionServiceEJB")
public class InstitutionServiceBean extends AbstractFacade<Institution> {

    public static final int PAST_JOURNEYS = 1;

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public InstitutionServiceBean() {
        super(Institution.class);
    }

    public Institution load(long id, int[] properties) {

        Institution skeleton = find(id);
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
