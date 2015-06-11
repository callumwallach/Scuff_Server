package nz.co.scuff.server.institution;

import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.server.family.ChildServiceBean;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.util.AbstractFacade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Callum on 12/05/2015.
 */
@Stateless(name = "TicketServiceEJB")
public class TicketServiceBean extends AbstractFacade<Ticket> {

    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private ChildServiceBean passengerService;

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketServiceBean() {
        super(Ticket.class);
    }

/*    public List<TicketSnapshot> requestTickets(String journeyId, List<Long> passengerIds) throws Exception {
        if (l.isDebugEnabled()) l.debug("post tickets for journey="+journeyId+" and passenger ids="+passengerIds);

        Journey journey = journeyService.findActive(journeyId);
        // if journey == null then it has been completed since this ticket was requested
        if (journey == null) {
            throw new ScuffServerException("Resource not found", "The selected journey may have been completed",
                    Response.Status.NOT_FOUND, ErrorContextCode.JOURNEY_NOT_FOUND);
        }
        List<TicketSnapshot> tickets = new ArrayList<>();
        for (Long id : passengerIds) {
            if (l.isDebugEnabled()) l.debug("processing passenger=" + id);
            // ensure no duplicates
            if (!journey.getTicketIds().stream().anyMatch(t -> t.getChild().getPersonId() == id)) {
                Ticket ticket = new Ticket();
                ticket.setIssueDate(new Timestamp(DateTimeUtils.currentTimeMillis()));
                ticket.setJourney(journey);
                Child passenger = passengerService.find(id);
                assert (passenger != null);
                ticket.setChild(passenger);
                create(ticket);
                passenger.getTicketIds().add(ticket);
                passengerService.edit(passenger);
                journey.getTicketIds().add(ticket);
                if (l.isDebugEnabled()) l.debug("created ticket=" + ticket);
                tickets.add(ticket.toSnapshot());
            }
        }
        journeyService.edit(journey);
        return tickets;
    }*/

}
