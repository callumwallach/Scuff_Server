package nz.co.scuff.server.school;

import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.server.util.AbstractFacade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Callum on 12/05/2015.
 */
@Stateless(name = "TicketServiceEJB")
public class TicketServiceBean extends AbstractFacade<Ticket> {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketServiceBean() {
        super(Ticket.class);
    }

}
