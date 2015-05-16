package nz.co.scuff.server.service;

import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.snapshot.BusSnapshot;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.server.error.ErrorContextCode;
import nz.co.scuff.server.error.ScuffServerException;
import nz.co.scuff.server.family.PassengerServiceBean;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.school.TicketServiceBean;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Callum on 16/05/2015.
 */
@Stateless(name = "WalkingServiceEJB")
public class WalkingServiceBean {

    public static final Logger l = LoggerFactory.getLogger(WalkingServiceBean.class.getCanonicalName());

    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private PassengerServiceBean passengerService;
    @EJB
    private TicketServiceBean ticketService;

    public WalkingServiceBean() { }

    public BusSnapshot getActiveBus(String journeyId) {
        if (l.isDebugEnabled()) l.debug("getActiveBus busId="+journeyId);

        Journey journey = journeyService.findActive(journeyId);
        BusSnapshot snapshot = null;
        if (journey != null) {
            snapshot = new BusSnapshot(journey);
        }
        if (l.isDebugEnabled()) l.debug("found snapshot="+ snapshot);
        return snapshot;
    }

    public List<BusSnapshot> getActiveBuses(long routeId, long schoolId) {
        if (l.isDebugEnabled()) l.debug("getActiveBuses routeId="+routeId+" schoolId="+schoolId);

        // only need location details as data is matched up to local copy (and constant fields ignored)
        // so set driver school and route as ids only
        List<Journey> journeys = journeyService.findActiveByRouteAndSchool(routeId, schoolId);
        List<BusSnapshot> snapshots = new ArrayList<>();
        for (Journey journey : journeys) {
            snapshots.add(new BusSnapshot(journey));
        }
        if (l.isDebugEnabled()) {
            for (BusSnapshot js : snapshots) {
                l.debug("found snapshot="+js);
            }
        }
        return snapshots;
    }

    public List<TicketSnapshot> requestTickets(String journeyId, List<Long> passengerIds) throws Exception {
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
                ticketService.create(ticket);
                passenger.getTickets().add(ticket);
                passengerService.edit(passenger);
                journey.getTickets().add(ticket);
                if (l.isDebugEnabled()) l.debug("created ticket=" + ticket);
                tickets.add(ticket.toSnapshot());
            }
        }
        journeyService.edit(journey);
        return tickets;
    }
}
