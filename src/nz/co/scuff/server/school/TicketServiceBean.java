package nz.co.scuff.server.school;

import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.server.error.ErrorContextCode;
import nz.co.scuff.server.error.ScuffServerException;
import nz.co.scuff.server.family.PassengerServiceBean;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.util.AbstractFacade;
import org.joda.time.DateTimeUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Callum on 12/05/2015.
 */
@Stateless(name = "TicketServiceEJB")
public class TicketServiceBean extends AbstractFacade<Ticket> {

    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private PassengerServiceBean passengerService;

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
            if (!journey.getTickets().stream().anyMatch(t -> t.getPassenger().getPersonId() == id)) {
                Ticket ticket = new Ticket();
                ticket.setIssueDate(new Timestamp(DateTimeUtils.currentTimeMillis()));
                ticket.setJourney(journey);
                Passenger passenger = passengerService.find(id);
                assert (passenger != null);
                ticket.setPassenger(passenger);
                create(ticket);
                passenger.getTickets().add(ticket);
                passengerService.edit(passenger);
                journey.getTickets().add(ticket);
                if (l.isDebugEnabled()) l.debug("created ticket=" + ticket);
                tickets.add(ticket.toSnapshot());
            }
        }
        journeyService.edit(journey);
        return tickets;
    }*/

}
