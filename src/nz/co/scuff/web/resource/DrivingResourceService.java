package nz.co.scuff.web.resource;

import nz.co.scuff.data.family.Driver;
import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.data.journey.snapshot.WaypointSnapshot;
import nz.co.scuff.data.school.School;
import nz.co.scuff.server.family.DriverServiceBean;
import nz.co.scuff.server.family.PassengerServiceBean;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.school.RouteServiceBean;
import nz.co.scuff.server.school.SchoolServiceBean;
import nz.co.scuff.server.school.TicketServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Callum on 7/05/2015.
 */
@Path("/driving")
public class DrivingResourceService {

    public static final Logger l = LoggerFactory.getLogger(DrivingResourceService.class.getCanonicalName());

    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private SchoolServiceBean schoolService;
    @EJB
    private RouteServiceBean routeService;
    @EJB
    private DriverServiceBean driverService;
    @EJB
    private PassengerServiceBean passengerService;
    @EJB
    private TicketServiceBean ticketService;

    @Path("/journeys")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public TicketSnapshot postJourney(JourneySnapshot snapshot) {
        if (l.isDebugEnabled()) l.debug("post resource journey snapshot=" + snapshot);

        Journey journey = journeyService.find(snapshot.getJourneyId());
        assert(journey == null);
        // new journey
        if (l.isDebugEnabled()) l.debug("create journey");
        journey = new Journey(snapshot);
        School school = schoolService.load(snapshot.getSchoolId(), new int[] { SchoolServiceBean.JOURNEYS });
        journey.setSchool(school);
        journey.setRoute(routeService.find(snapshot.getRouteId()));
        Driver driver = driverService.find(snapshot.getDriverId());
        journey.setDriver(driver);
        journeyService.create(journey);

        school.getJourneys().add(journey);
        schoolService.edit(school);
        driver.getJourneys().add(journey);
        driverService.edit(driver);
        for (WaypointSnapshot ws : snapshot.getWaypoints()) {
            journey.getWaypoints().add(new Waypoint(ws));
        }
        journeyService.edit(journey);

        //return Response.ok().entity(journey.getJourneyId()).build();
        TicketSnapshot ticket = new TicketSnapshot();
        ticket.setJourneyId("ticket no. 3");
        return ticket;
    }

    @Path("/journeys/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public TicketSnapshot updateJourney(@PathParam("id") String journeyId, JourneySnapshot snapshot) {
        if (l.isDebugEnabled()) l.debug("put resource journey snapshot=" + snapshot);

        Journey journey = journeyService.find(snapshot.getJourneyId());
        assert(journey != null);
        // TODO
        /*if (foundJourney == null) {
            return null;
        }*/
        // update journey
        if (l.isDebugEnabled()) l.debug("update journey="+journey);
        journey.setTotalDistance(snapshot.getTotalDistance());
        journey.setTotalDuration(snapshot.getTotalDuration());
        journey.setCompleted(snapshot.getCompleted());
        journey.setState(snapshot.getState());
        for (WaypointSnapshot ws : snapshot.getWaypoints()) {
            journey.getWaypoints().add(new Waypoint(ws));
        }
        journeyService.edit(journey);

        //return Response.ok().entity(journey.getJourneyId()).build();
        TicketSnapshot ticket = new TicketSnapshot();
        ticket.setJourneyId("ticket no. 3");
        return ticket;
    }

    @Path("/journeys/{id}/tickets")
    @POST
    @Consumes("application/json")
    public void postTickets(@PathParam("id") String journeyId, List<TicketSnapshot> tickets) {
        if (l.isDebugEnabled()) l.debug("post tickets for journey="+journeyId);

        for (TicketSnapshot ticket : tickets) {
            if (l.isDebugEnabled()) l.debug("processing ticket=="+ticket);
        }
        /*Journey foundJourney = journeyService.find(journeyId);
        Passenger foundPassenger = passengerService.find(snapshot.getPassengerId());
        Ticket ticket = new Ticket(snapshot);
        ticket.setJourney(foundJourney);
        ticket.setPassenger(foundPassenger);
        ticketService.create(ticket);
        foundJourney.getTickets().add(ticket);
        foundPassenger.getTickets().add(ticket);
        journeyService.edit(foundJourney);
        passengerService.edit(foundPassenger);*/
    }

}
