package nz.co.scuff.web.journey;

//import nz.co.scuff.data.journey.FlatJourney;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.journey.WaypointServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.Set;

/**
 * Created by Callum on 27/04/2015.
 */
@Path("/journeys")
public class JourneyResourceService {

    public static final Logger l = LoggerFactory.getLogger(JourneyResourceService.class.getCanonicalName());

    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private WaypointServiceBean waypointService;

/*    @Path("/start")
    @POST
    @Consumes("application/json")
    public void start(Journey journey) {
        l.debug("start resource journey=" + journey);
        l.debug("create journey");
        // cascading create
        journeyService.create(journey);
        l.debug("create journey complete");
    }*/

    /*@Path("/update")*/
    @POST
    @Consumes("application/json")
    public void postJourney(Journey journey) {
        l.debug("post resource journey="+journey);
        Journey foundJourney = journeyService.find(journey.getJourneyId());
        if (foundJourney == null) {
            // new journey
            l.debug("create journey");
            journeyService.create(journey);
        } else {
            foundJourney.setTotalDistance(journey.getTotalDistance());
            foundJourney.setTotalDuration(journey.getTotalDuration());
            foundJourney.setCompleted(journey.getCompleted());
            foundJourney.setState(journey.getState());
            for (Waypoint wp : journey.getWaypoints()) {
                l.debug("adding waypoint");
                // TODO should always be only be one waypoint
                foundJourney.addWaypoint(wp);
            }
            l.debug("editing journey");
            journeyService.edit(foundJourney);
        }
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Journey getJourney(@PathParam("id") String journeyId) {
        l.debug("get journey journeyId="+journeyId);
        return journeyService.find(journeyId);
    }

    // TODO update to route + school + date
    @GET
    @Produces("application/json")
    public Journey getJourney(@QueryParam("routeId") String routeId, @QueryParam("schoolId") String schoolId) {
        l.debug("get journey routeId="+routeId+" schoolId="+schoolId);
        return journeyService.findActiveByRouteAndSchool(routeId, schoolId);
    }

    //////////////////////////////////////////////////////////////////////////////

/*    @Path("/waypoint")
    @GET
    @Produces("application/json")
    public Waypoint getCurrentWaypoint(@QueryParam("routeId") String routeId, @QueryParam("schoolId") String schoolId) {
        l.debug("getCurrentWaypoint journeyId="+journeyId);
        Journey journey = journeyService.find(journeyId);
        if (journey == null) {
            return null;
        }
        return journey.getMostRecentWaypoint();
    }*/

/*    @Path("/pause")
    @POST
    @Consumes("application/json")
    public void pauseJourney(Journey journey) {
        l.debug("pauseJourney resource journey="+journey);
        Journey foundJourney = journeyService.find(journey.getJourneyId());
        foundJourney.setTotalDistance(journey.getTotalDistance());
        foundJourney.setTotalDuration(journey.getTotalDuration());
        foundJourney.setState(journey.getState());
        for (Waypoint wp : journey.getWaypoints()) {
            l.debug("adding waypoint");
            foundJourney.addWaypoint(wp);
        }
        l.debug("editing journey");
        journeyService.edit(foundJourney);
    }

    @Path("/continue")
    @POST
    @Consumes("application/json")
    public void continueJourney(Journey journey) {
        l.debug("continueJourney resource journey="+journey);
        Journey foundJourney = journeyService.find(journey.getJourneyId());
        foundJourney.setTotalDistance(journey.getTotalDistance());
        foundJourney.setTotalDuration(journey.getTotalDuration());
        foundJourney.setState(journey.getState());
        for (Waypoint wp : journey.getWaypoints()) {
            l.debug("adding waypoint");
            foundJourney.addWaypoint(wp);
        }
        l.debug("editing journey");
        journeyService.edit(foundJourney);
    }

    @Path("/stop")
    @POST
    @Consumes("application/json")
    public void stopJourney(Journey journey) {
        l.debug("stopJourney resource journey="+journey);
        Journey foundJourney = journeyService.find(journey.getJourneyId());
        foundJourney.setTotalDistance(journey.getTotalDistance());
        foundJourney.setTotalDuration(journey.getTotalDuration());
        foundJourney.setState(journey.getState());
        for (Waypoint wp : journey.getWaypoints()) {
            l.debug("adding waypoint");
            foundJourney.addWaypoint(wp);
        }
        l.debug("editing journey");
        journeyService.edit(foundJourney);
    }

    @Path("/record")
    @POST
    @Consumes("application/json")
    public void recordJourney(Journey journey) {
        l.debug("pauseJourney resource journey=" + journey);
        Journey foundJourney = journeyService.find(journey.getJourneyId());
        foundJourney.setTotalDistance(journey.getTotalDistance());
        foundJourney.setTotalDuration(journey.getTotalDuration());
        for (Waypoint wp : journey.getWaypoints()) {
            l.debug("adding waypoint");
            foundJourney.addWaypoint(wp);
        }
        l.debug("editing journey");
        journeyService.edit(foundJourney);
    }*/

}
