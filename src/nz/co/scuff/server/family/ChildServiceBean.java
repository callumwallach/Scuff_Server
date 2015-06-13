package nz.co.scuff.server.family;

import nz.co.scuff.data.family.Child;
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
@Stateless(name = "ChildServiceEJB")
public class ChildServiceBean extends AbstractModifiableEntityFacade<Child> {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildServiceBean() {
        super(Child.class);
    }
}
