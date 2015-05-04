package nz.co.scuff.server.school;

import nz.co.scuff.data.school.School;
import nz.co.scuff.server.util.AbstractFacade;

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

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public SchoolServiceBean() {
        super(School.class);
    }
}
