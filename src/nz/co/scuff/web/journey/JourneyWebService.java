package nz.co.scuff.web.journey;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.journey.WaypointServiceBean;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Set;

/**
 * Created by Callum on 20/04/2015.
 */
@Path("/journeys")
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class JourneyWebService {

    public static final Logger l = LoggerFactory.getLogger(JourneyWebService.class.getCanonicalName());

    @Resource private EJBContext ctx;

    @EJB private JourneyServiceBean journeyService;
    @EJB private WaypointServiceBean waypointService;

    @Path("/start")
    @POST
    @Consumes("application/json")
    public void startJourney(Journey journey) throws Exception {
        l.debug("startJourney");
        UserTransaction utx = ctx.getUserTransaction();
        try {
            utx.begin();
            journeyService.create(journey);
            utx.commit();
        } catch (Exception e) {
            l.error("failed creating new journey["+journey.getId()+"]");
            utx.rollback();
            throw e;
        }
    }

    @Path("/waypoint/current")
    @PUT
    @Consumes("application/json")
    public void addWaypoint(@QueryParam("journeyId") String journeyId, Waypoint waypoint) throws Exception {
        l.debug("addWaypoint journeyId="+journeyId);
        l.debug("startJourney");
        UserTransaction utx = ctx.getUserTransaction();
        try {
            utx.begin();
            waypointService.create(waypoint);
            Journey journey = journeyService.find(journeyId);
            journey.addWaypoint(waypoint);
            journeyService.edit(journey);
            utx.commit();
        } catch (Exception e) {
            l.error("failed adding waypoint["+waypoint.getId()+"] to journey["+journeyId+"]");
            utx.rollback();
            throw e;
        }

    }

    @Path("/end")
    @PUT
    @Consumes("application/json")
    public void endJourney(@QueryParam("journeyId") String journeyId, Waypoint waypoint) {
        l.debug("endJourney journeyId="+journeyId);
        Journey journey = journeyService.find(journeyId);
        waypointService.create(waypoint);
        journey.addWaypoint(waypoint);
        journeyService.edit(journey);
    }

    @Path("/{journeyId}")
    @GET
    @Produces("application/json")
    public Journey getJourney(@PathParam("journeyId") String journeyId) {
        l.debug("getJourney journeyId="+journeyId);
        return journeyService.find(journeyId);
    }

    @Path("/waypoint/current/{journeyId}")
    @GET
    @Produces("application/json")
    public Waypoint getWaypoint(@PathParam("journeyId") String journeyId) {
        l.debug("getWaypoint journeyId="+journeyId);
        Journey journey = journeyService.find(journeyId);
        Set<Waypoint> waypoints = journey.getWaypoints();
        l.debug("iterating waypoints for journey="+journeyId);
        for (Waypoint w : waypoints) {
            l.debug("waypoint="+w);
            return w;
        }
        return null;
    }

}
