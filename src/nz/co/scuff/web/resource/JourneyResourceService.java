package nz.co.scuff.web.resource;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.JourneySnapshot;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.journey.WaypointServiceBean;
import nz.co.scuff.server.util.Constants;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @GET
    @Produces("application/json")
    public List<Journey> getJourneys(@QueryParam("routeId") String routeId, @QueryParam("schoolId") String schoolId) {
        l.debug("get journey routeId="+routeId+" schoolId="+schoolId);
        return journeyService.findActiveByRouteAndSchool(routeId, schoolId);
    }

    ///

    @Path("/snapshots/{id}")
    @GET
    @Produces("application/json")
    public JourneySnapshot getSnapshot(@PathParam("id") String journeyId) {
        if (l.isDebugEnabled()) l.debug("getSnapshot journeyId="+journeyId);

        Journey journey = journeyService.findActiveByPK(journeyId);
        JourneySnapshot snapshot = null;
        if (journey != null) {
            snapshot = toSnapshot(journey, journey.getMostRecentWaypoint());
        }
        if (l.isDebugEnabled()) l.debug("found snapshot="+ snapshot);
        return snapshot;
    }

    @Path("/snapshots")
    @GET
    @Produces("application/json")
    public List<JourneySnapshot> getSnapshotsByRouteAndSchool(@QueryParam("routeId") String routeId, @QueryParam("schoolId") String schoolId) {
        if (l.isDebugEnabled()) l.debug("getSnapshotsByRouteAndSchool routeId="+routeId+" schoolId="+schoolId);

        // TODO add auto completion to journeys based on expected length of journey. combats unfinished journeys due to power failure etc
        List<Journey> journeys = journeyService.findActiveByRouteAndSchool(routeId, schoolId);
        List<JourneySnapshot> snapshots = new ArrayList<>();
        for (Journey journey : journeys) {
            JourneySnapshot snapshot = toSnapshot(journey, journey.getMostRecentWaypoint());
            snapshots.add(snapshot);
        }
        if (l.isDebugEnabled()) {
            for (JourneySnapshot js : snapshots) {
                l.debug("found snapshot="+js);
            }
        }
        return snapshots;
    }

    private JourneySnapshot toSnapshot(Journey journey, Waypoint waypoint) {
        if (l.isDebugEnabled()) l.debug("toSnapshot journey="+journey+" waypoint="+waypoint);

        JourneySnapshot snapshot = new JourneySnapshot();
        snapshot.setJourneyId(journey.getJourneyId());
        snapshot.setSchoolId(journey.getSchoolId());
        snapshot.setDriverId(journey.getDriverId());
        snapshot.setRouteId(journey.getRouteId());
        snapshot.setStarted(journey.getCreated());
        snapshot.setExpiry(new Timestamp(DateTime.now().plusSeconds(Constants.SNAPSHOT_LIFESPAN_IN_SECONDS).getMillis()));

        snapshot.setLatitude(waypoint.getLatitude());
        snapshot.setLongitude(waypoint.getLongitude());
        snapshot.setSpeed(waypoint.getSpeed());
        snapshot.setBearing(waypoint.getBearing());
        snapshot.setAccuracy(waypoint.getAccuracy());
        snapshot.setAltitude(waypoint.getAltitude());
        snapshot.setState(waypoint.getState());
        snapshot.setTimestamp(waypoint.getCreated());

        return snapshot;

    }

}
