package nz.co.scuff.web.journey;

import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.journey.WaypointServiceBean;

import javax.annotation.Resource;
import javax.ejb.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Created by Callum on 20/04/2015.
 */
/*@Path("/journeys")
@LocalBean*/
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
public class JourneyWebServiceBean implements JourneyWebService {

    public static final Logger l = LoggerFactory.getLogger(JourneyWebServiceBean.class.getCanonicalName());

    @Resource private EJBContext ctx;

    @EJB private JourneyServiceBean journeyService;
    @EJB private WaypointServiceBean waypointService;

/*    @Path("/start/map")
    @POST
    @Consumes("application/json")
    public void start(HashMap map) {
        l.debug("start");
        Journey newJourney = new Journey();
        newJourney.setId((String)map.get("journeyId"));
        newJourney.setAppId((Long) map.get("appid"));
        newJourney.setSchoolId((String) map.get("schoolid"));
        newJourney.setDriverId((String) map.get("driverid"));
        newJourney.setRouteId((String) map.get("routeid"));
        newJourney.setSource((String) map.get("source"));
        newJourney.setState((Journey.TrackingState)map.get("state"));
        journeyService.create(newJourney);
    }*/

/*    @Path("/start")
    @POST
    @Consumes("application/json")*/
/*    public void start(FlatJourney journey) *//*throws Exception*//* {
        l.debug("start journey="+journey);
        //journeyService.create(journey);

    }*/

/*    public void start(FlatJourney journey) *//*throws Exception*//* {
        l.debug("start journey="+journey);
        //journeyService.create(journey);

    }*/

/*    public void start(Journey journey) *//*throws Exception*//* {
        l.debug("start");

        journeyService.create(journey);
        // not needed with cascading persist?
*//*        for (Waypoint wp : journey.getWaypoints()) {
            // save waypoint
            waypointService.create(wp);
        }*//*


*//*        UserTransaction utx = ctx.getUserTransaction();
        try {
            utx.begin();
            journeyService.create(journey);
            utx.commit();
        } catch (Exception e) {
            l.error("failed creating new journey["+journey.getId()+"]");
            utx.rollback();
            throw e;
        }*//*
    }*/

/*    public void pauseJourney(Journey journey) {
        l.debug("pauseJourney");
        Journey foundJourney = journeyService.find(journey.getId());
        foundJourney.setState(journey.getState());
        journeyService.edit(journey);
    }

    public void continueJourney(Journey journey) {
        l.debug("continueJourney");
        Journey foundJourney = journeyService.find(journey.getId());
        foundJourney.setState(journey.getState());
        journeyService.edit(journey);
    }

    public void stopJourney(Journey journey) {
        l.debug("stopJourney");
        Journey foundJourney = journeyService.find(journey.getId());
        foundJourney.setState(journey.getState());
        journeyService.edit(journey);
    }

    public void recordJourney(Journey journey) {
        l.debug("recordJourney");
        journeyService.edit(journey);
    }*/

/*    @Path("/waypoint")
    @PUT
    @Consumes("application/json")
    public void addWaypoint(@QueryParam("journeyId") String journeyId, Waypoint waypoint) *//*throws Exception*//* {
        l.debug("addWaypoint journeyId="+journeyId);
        waypointService.create(waypoint);
        Journey journey = journeyService.find(journeyId);
        journey.addWaypoint(waypoint);
        journeyService.edit(journey);

*//*        UserTransaction utx = ctx.getUserTransaction();
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
        }*//*

    }*/

/*    @Path("/end")
    @PUT
    @Consumes("application/json")
    public void stopJourney(@QueryParam("journeyId") String journeyId, Waypoint waypoint) {
        l.debug("endJourney journeyId="+journeyId);
        Journey journey = journeyService.find(journeyId);
        waypointService.create(waypoint);
        journey.addWaypoint(waypoint);
        journeyService.edit(journey);
    }*/

/*    public Journey getJourney(@PathParam("journeyId") String journeyId) {
        l.debug("getJourney journeyId="+journeyId);
        return journeyService.find(journeyId);
    }

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
    }*/

}
