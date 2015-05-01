package nz.co.scuff.web.waypoint;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.journey.WaypointServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Callum on 27/04/2015.
 */
@Path("/waypoints")
public class WaypointResourceService {

    public static final Logger l = LoggerFactory.getLogger(WaypointResourceService.class.getCanonicalName());

    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private WaypointServiceBean waypointService;

    // Assumes only one ACTIVE bus per route per school
    @GET
    @Produces("application/json")
    public List<Waypoint> getCurrentWaypoint(@QueryParam("routeId") String routeId, @QueryParam("schoolId") String schoolId) {
        if (l.isDebugEnabled()) l.debug("getCurrentWaypoint routeId="+routeId+" schoolId="+schoolId);

        // TODO optimise by passenger getting journey and then looking up journey direct
        List<Journey> journeys = journeyService.findActiveByRouteAndSchool(routeId, schoolId);
        List<Waypoint> waypoints = new ArrayList<>();
        for (Journey journey : journeys) {
            waypoints.add(journey.getMostRecentWaypoint());
        }
        return waypoints;
    }

}
