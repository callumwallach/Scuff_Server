package nz.co.scuff.web.resource;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.snapshot.BusSnapshot;
import nz.co.scuff.server.journey.JourneyServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;

/**
 * Created by Callum on 7/05/2015.
 */
@Path("/walking")
public class WalkingResourceService {

    public static final Logger l = LoggerFactory.getLogger(WalkingResourceService.class.getCanonicalName());

    @EJB
    private JourneyServiceBean journeyService;

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public BusSnapshot getActiveBus(@PathParam("id") String journeyId) {
        if (l.isDebugEnabled()) l.debug("getActiveBus busId="+journeyId);

        Journey journey = journeyService.findActiveByPK(journeyId);
        BusSnapshot snapshot = null;
        if (journey != null) {
            snapshot = new BusSnapshot(journey);
        }
        if (l.isDebugEnabled()) l.debug("found snapshot="+ snapshot);
        return snapshot;
    }

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

    /*@Path("/{id}")
    @GET
    @Produces("application/json")
    public JourneySnapshot getActiveJourneySnapshot(@PathParam("id") String journeyId, @QueryParam("prune") boolean prune) {
        if (l.isDebugEnabled()) l.debug("getPrunedActiveJourneySnapshot journeyId="+journeyId);

        Journey journey = journeyService.findActiveByPK(journeyId);
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
