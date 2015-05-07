package nz.co.scuff.server.school;

import nz.co.scuff.data.school.School;
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
@Stateless(name = "SchoolServiceEJB")
public class SchoolServiceBean extends AbstractFacade<School> {

    public static final int JOURNEYS = 1;

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public SchoolServiceBean() {
        super(School.class);
    }

    public School load(long schoolId, int[] properties) {

        School skeleton = find(schoolId);
        for (int property : properties) {
            switch (property) {
                case JOURNEYS:
                    if (!Hibernate.isInitialized(skeleton.getJourneys()))
                        Hibernate.initialize(skeleton.getJourneys());
                    break;
                default:
                    break;
            }
        }
        return skeleton;

    }

}
