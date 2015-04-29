package nz.co.scuff.web.journey;

//import nz.co.scuff.data.journey.FlatJourney;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.journey.WaypointServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

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

    @Path("/start")
    @POST
    @Consumes("application/json")
    public void start(Journey journey) {
        l.debug("start resource journey=" + journey);
        l.debug("create journey");
        // cascading create
        journeyService.create(journey);
        l.debug("create journey complete");
    }

    @Path("/update")
    @POST
    @Consumes("application/json")
    public void update(Journey journey) {
        l.debug("update resource journey="+journey);
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
