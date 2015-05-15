package nz.co.scuff.web.resource;

import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.snapshot.BusSnapshot;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.server.error.ScuffServerException;
import nz.co.scuff.server.family.PassengerServiceBean;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.school.TicketServiceBean;
import nz.co.scuff.server.error.ErrorContextCode;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Callum on 7/05/2015.
 */
@Path("/walking")
public class WalkingResourceService {

    public static final Logger l = LoggerFactory.getLogger(WalkingResourceService.class.getCanonicalName());

    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private PassengerServiceBean passengerService;
    @EJB
    private TicketServiceBean ticketService;

    @Path("/buses/{id}")
    @GET
    @Produces("application/json")
    public BusSnapshot getActiveBus(@PathParam("id") String journeyId) {
        if (l.isDebugEnabled()) l.debug("getActiveBus busId="+journeyId);

        Journey journey = journeyService.findActive(journeyId);
        BusSnapshot snapshot = null;
        if (journey != null) {
            snapshot = new BusSnapshot(journey);
        }
        if (l.isDebugEnabled()) l.debug("found snapshot="+ snapshot);
        return snapshot;
    }

    @Path("/buses")
    @GET
    @Produces("application/json")
    public List<BusSnapshot> getActiveBuses(@QueryParam("routeId") long routeId, @QueryParam("schoolId") long schoolId) {
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

    @Path("/buses/{id}/tickets")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public List<TicketSnapshot> requestTickets(@PathParam("id") String journeyId, List<Long> passengerIds) throws Exception {
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
                journey.getTickets()
                    .stream()
                    .map(Ticket::getTicketId)
                    .forEach(ticketId -> l.debug("added ticket=" + ticketId));
                if (l.isDebugEnabled()) l.debug("created ticket=" + ticket);
                tickets.add(ticket.toSnapshot());
                //journeyService.edit(journey);
            }
        }
        // TODO work out why tickets not sticking
        journey.getTickets()
                .stream()
                .map(Ticket::getTicketId)
                .forEach(ticketId -> l.debug("final ticket="+ticketId));
        journeyService.edit(journey);
        return tickets;
    }

    /*@Path("/{id}")
    @GET
    @Produces("application/json")
    public JourneySnapshot getActiveJourneySnapshot(@PathParam("id") String journeyId, @QueryParam("prune") boolean prune) {
        if (l.isDebugEnabled()) l.debug("getPrunedActiveJourneySnapshot journeyId="+journeyId);

        Journey journey = journeyService.findActive(journeyId);
        JourneySnapshot snapshot = null;
        if (journey != null) {
            if (prune) {
                snapshot = pruneToMostRecentWaypoint(journey);
            } else {
                snapshot = prune(journey);
            }
        }
        if (l.isDebugEnabled()) l.debug("found snapshot="+ snapshot);
        return snapshot;
    }

    @GET
    @Produces("application/json")
    public List<JourneySnapshot> getActiveJourneySnapshots(@QueryParam("routeId") String routeId, @QueryParam("schoolId") String schoolId, @QueryParam("prune") boolean prune) {
        if (l.isDebugEnabled()) l.debug("getPrunedActiveJourneySnapshots routeId="+routeId+" schoolId="+schoolId);

        // TODO add auto completion to journeys based on expected length of journey. combats unfinished journeys due to power failure etc
        List<Journey> journeys = journeyService.findActiveByRouteAndSchool(routeId, schoolId);
        List<JourneySnapshot> snapshots = new ArrayList<>();
        for (Journey journey : journeys) {
            if (prune) {
                snapshots.add(pruneToMostRecentWaypoint(journey));
            } else {
                snapshots.add(prune(journey));
            }
        }
        if (l.isDebugEnabled()) {
            for (JourneySnapshot js : snapshots) {
                l.debug("found snapshot="+js);
            }
        }
        return snapshots;
    }*/

/*    private JourneySnapshot pruneToMostRecentWaypoint(Journey toPrune) {
        if (l.isDebugEnabled()) l.debug("pruning journey to waypoint for transit");

        // prune for journey
        JourneySnapshot journeySnapshot = toPrune.toSnapshot();
        SchoolSnapshot cs = toPrune.getSchool().toSnapshot();
        journeySnapshot.setSchool(cs);
        DriverSnapshot ds = toPrune.getDriver().toSnapshot();
        journeySnapshot.setDriver(ds);
        RouteSnapshot rs = toPrune.getRoute().toSnapshot();
        journeySnapshot.setRoute(rs);
        journeySnapshot.getWaypoints().add(toPrune.getMostRecentWaypoint().toSnapshot());
        return journeySnapshot;
    }

    private JourneySnapshot prune(Journey toPrune) {
        if (l.isDebugEnabled()) l.debug("pruning journey for transit");

        // prune for journey
        JourneySnapshot journeySnapshot = toPrune.toSnapshot();
        SchoolSnapshot cs = toPrune.getSchool().toSnapshot();
        journeySnapshot.setSchool(cs);
        DriverSnapshot ds = toPrune.getDriver().toSnapshot();
        journeySnapshot.setDriver(ds);
        RouteSnapshot rs = toPrune.getRoute().toSnapshot();
        journeySnapshot.setRoute(rs);
        for (Waypoint w : toPrune.getWaypoints()) {
            journeySnapshot.getWaypoints().add(w.toSnapshot());
        }
        for (Passenger p : toPrune.getPassengers()) {
            journeySnapshot.getPassengers().add(p.toSnapshot());
        }
        return journeySnapshot;
    }*/
}
