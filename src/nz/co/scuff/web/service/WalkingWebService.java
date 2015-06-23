package nz.co.scuff.web.service;

import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.service.WalkingServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;

/**
 * Created by Callum on 7/05/2015.
 */
@Path("/walking")
public class WalkingWebService {

    public static final Logger l = LoggerFactory.getLogger(WalkingWebService.class.getCanonicalName());

    @EJB
    private WalkingServiceBean walkingService;

/*    @Path("/buses/{id}")
    @GET
    @Produces("application/json")
    public BusSnapshot getActiveBus(@PathParam("id") String journeyId) {
        return walkingService.getActiveBus(journeyId);
    }*/

    /*@Path("/buses")
    @GET
    @Produces("application/json")
    public DataPacket getJourneys(@QueryParam("routeId") long routeId, @QueryParam("schoolId") long schoolId) {
        return walkingService.getJourneys(routeId, schoolId);
    }*/

    @Path("/journeys")
    @GET
    @Produces("application/json")
    public DataPacket getJourneys(@QueryParam("adultId") long adultId, @QueryParam("watchedIds[]") List<Long> watchedIds,
                                  @QueryParam("isActive") boolean active) {
        return walkingService.getJourneys(adultId, watchedIds, active);
    }

    @Path("/journeys/{id}/tickets")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public DataPacket issueTickets(@PathParam("id") long journeyId, List<Long> passengerIds) throws Exception {
        return walkingService.issueTickets(journeyId, passengerIds);
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
        InstitutionSnapshot cs = toPrune.getInstitution().toSnapshot();
        journeySnapshot.setInstitution(cs);
        AdultSnapshot ds = toPrune.getAdult().toSnapshot();
        journeySnapshot.setAdult(ds);
        RouteSnapshot rs = toPrune.getRoute().toSnapshot();
        journeySnapshot.setRoute(rs);
        journeySnapshot.getWaypointIds().add(toPrune.getMostRecentWaypoint().toSnapshot());
        return journeySnapshot;
    }

    private JourneySnapshot prune(Journey toPrune) {
        if (l.isDebugEnabled()) l.debug("pruning journey for transit");

        // prune for journey
        JourneySnapshot journeySnapshot = toPrune.toSnapshot();
        InstitutionSnapshot cs = toPrune.getInstitution().toSnapshot();
        journeySnapshot.setInstitution(cs);
        AdultSnapshot ds = toPrune.getAdult().toSnapshot();
        journeySnapshot.setAdult(ds);
        RouteSnapshot rs = toPrune.getRoute().toSnapshot();
        journeySnapshot.setRoute(rs);
        for (Waypoint w : toPrune.getWaypointIds()) {
            journeySnapshot.getWaypointIds().add(w.toSnapshot());
        }
        for (Child p : toPrune.getPassengers()) {
            journeySnapshot.getPassengers().add(p.toSnapshot());
        }
        return journeySnapshot;
    }*/
}
