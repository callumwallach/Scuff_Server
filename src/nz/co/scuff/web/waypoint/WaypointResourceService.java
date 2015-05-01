package nz.co.scuff.web.waypoint;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.journey.WaypointServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;

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

    // TODO update to route + school + date
    @GET
    @Produces("application/json")
    public Waypoint getCurrentWaypoint(@QueryParam("routeId") String routeId, @QueryParam("schoolId") String schoolId) {
        if (l.isDebugEnabled()) l.debug("getCurrentWaypoint routeId="+routeId+" schoolId="+schoolId);
        // TODO optimise by passenger getting journey and then looking up journey direct
        Journey journey = journeyService.findActiveByRouteAndSchool(routeId, schoolId);
        if (journey == null) {
            return null;
        }
        return journey.getMostRecentWaypoint();
    }

}
