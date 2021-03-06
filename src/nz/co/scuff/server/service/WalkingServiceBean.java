package nz.co.scuff.server.service;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.base.snapshot.CoordinatorSnapshot;
import nz.co.scuff.data.family.Adult;
import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.family.snapshot.AdultSnapshot;
import nz.co.scuff.data.institution.snapshot.InstitutionSnapshot;
import nz.co.scuff.data.institution.snapshot.RouteSnapshot;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.WaypointSnapshot;
import nz.co.scuff.data.place.snapshot.PlaceSnapshot;
import nz.co.scuff.data.util.Constants;
import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.data.util.TicketState;
import nz.co.scuff.server.error.ErrorContextCode;
import nz.co.scuff.server.error.ScuffServerException;
import nz.co.scuff.server.family.AdultServiceBean;
import nz.co.scuff.server.family.ChildServiceBean;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.institution.TicketServiceBean;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Callum on 16/05/2015.
 */
@Stateless(name = "WalkingServiceEJB")
public class WalkingServiceBean {

    public static final Logger l = LoggerFactory.getLogger(WalkingServiceBean.class.getCanonicalName());

    @EJB
    private AdultServiceBean adultService;
    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private ChildServiceBean passengerService;
    @EJB
    private TicketServiceBean ticketService;

    public WalkingServiceBean() { }

/*    private Set<String> doWaypoints(SortedSet<Waypoint> waypoints, DataPacket packet) {

        Set<String> waypointIds = new HashSet<>();
        Waypoint mostRecentWaypoint = waypoints.last();
        if (l.isDebugEnabled()) l.debug("waypoint:"+mostRecentWaypoint);
        WaypointSnapshot mostRecentWaypointSnapshot = mostRecentWaypoint.toSnapshot();
        packet.getWaypointSnapshots().put(mostRecentWaypointSnapshot.getWaypointId(), mostRecentWaypointSnapshot);
        return waypointIds;
    }

    private Set<String> doJourneys(Set<Journey> journeys, DataPacket packet) {

        Set<String> journeyIds = new HashSet<>();
        for (Journey journey : journeys) {
            if (l.isDebugEnabled()) l.debug("journey:"+journey);
            journeyIds.add(journey.getJourneyId());
            JourneySnapshot journeySnapshot = journey.toSnapshot();
            journeySnapshot.setWaypointIds(doWaypoints(journey.getWaypoints(), packet));
            packet.getJourneySnapshots().put(journeySnapshot.getJourneyId(), journeySnapshot);
        }
        return journeyIds;
    }

    private Set<Long> doCoordinators(Set<Coordinator> coordinators, DataPacket packet) {

        Set<Long> coordinatorIds = new HashSet<>();
        for (Coordinator coordinator : coordinators) {
            if (l.isDebugEnabled()) l.debug("coordinator:"+coordinator);
            coordinatorIds.add(coordinator.getCoordinatorId());
            // if coordinator is dirty refresh with snapshot and route + place (as journey uses these)
            CoordinatorSnapshot coordinatorSnapshot = coordinator.toSnapshot();
            if (coordinator.isDirty(lastChecked)) {
                if (l.isDebugEnabled()) l.debug("coordinator=" + coordinator.getCoordinatorId() + " is dirty");
                coordinatorSnapshot.setRouteIds(doRoutes(lastChecked, coordinator.getRoutes(), packet));
                coordinatorSnapshot.setPlaceIds(doPlaces(lastChecked, coordinator.getPlaces(), packet));
            }
            // get journeys now separate from dirty flag (as journey waypoints are added independent of coordinator)
            coordinatorSnapshot.setCurrentJourneyIds(doJourneys(coordinator.getCurrentJourneys(), packet));

            // tomfoolery due to android (active android inability to process table inheritance)
            if (coordinatorSnapshot instanceof AdultSnapshot) {
                AdultSnapshot adultSnapshot = (AdultSnapshot)coordinatorSnapshot;
                AdultSnapshot alreadyPresentAdultSnapshot = packet.getAdultSnapshots().get(adultSnapshot.getCoordinatorId());
                if (alreadyPresentAdultSnapshot != null) {
                    // merge them
                    adultSnapshot.setGuideeIds(alreadyPresentAdultSnapshot.getGuideeIds());
                    adultSnapshot.setChildrenIds(alreadyPresentAdultSnapshot.getChildrenIds());
                }
                packet.getAdultSnapshots().put(adultSnapshot.getCoordinatorId(), adultSnapshot);
            } else {
                InstitutionSnapshot institutionSnapshot = (InstitutionSnapshot)coordinatorSnapshot;
                InstitutionSnapshot alreadyPresentInstitutionSnapshot = packet.getInstitutionSnapshots().get(institutionSnapshot.getCoordinatorId());
                if (alreadyPresentInstitutionSnapshot != null) {
                    // merge them
                    institutionSnapshot.setGuideIds(alreadyPresentInstitutionSnapshot.getGuideIds());
                }
                packet.getInstitutionSnapshots().put(institutionSnapshot.getCoordinatorId(), institutionSnapshot);
            }
        }
        return coordinatorIds;
    }*/

    public DataPacket getJourneys(long adultId, List<Long> watchedJourneyIds, boolean active) {
        if (l.isDebugEnabled()) l.debug("getJourneys for friends of adult="+adultId+" watchedIds="+watchedJourneyIds);

        Adult adult = adultService.find(adultId);
        DataPacket packet = new DataPacket();
/*

        AdultSnapshot adultSnapshot = adult.toSnapshot();
        adultSnapshot.setFriendIds(doCoordinators(lastChecked, adult.getFriends(), packet));
        packet.getAdultSnapshots().put(adultSnapshot.getCoordinatorId(), adultSnapshot);

        //adultSnapshot.setPastJourneyIds(Constants.STRING_COLLECTION_NOT_RETRIEVED_PLACEHOLDER);
        adultSnapshot.setCurrentJourneyIds(doJourneys(lastChecked, adult.getCurrentJourneys(), packet));
        adultSnapshot.setPlaceIds(doPlaces(lastChecked, adult.getPlaces(), packet));
            adultSnapshot.setRouteIds(doRoutes(lastChecked, adult.getRoutes(), packet));
*/

        // do watched first (reduces size of journey snapshot returned)
        for (long watchedJourneyId : watchedJourneyIds) {
            if (l.isDebugEnabled()) l.debug("processing watchedJourneyIds=" + watchedJourneyId);
            // retrieve watched entity (is completed)
            Journey completedJourney = journeyService.find(watchedJourneyId);
            JourneySnapshot completedJourneySnapshot = completedJourney.toSnapshot();
            WaypointSnapshot lastWaypointSnapshot = completedJourney.getMostRecentWaypoint().toSnapshot();
            Set<Long> lastWaypointIds = new HashSet<>();
            lastWaypointIds.add(lastWaypointSnapshot.getWaypointId());
            completedJourneySnapshot.setWaypointIds(lastWaypointIds);
            // no need to retrieve relationships as already stored by user
            packet.getWaypointSnapshots().put(lastWaypointSnapshot.getWaypointId(), lastWaypointSnapshot);
            packet.getJourneySnapshots().put(completedJourneySnapshot.getJourneyId(), completedJourneySnapshot);
        }
        // do remainder (new journeys)
        for (Coordinator friend : adult.getFriends()) {
            for (Journey journey : friend.getCurrentJourneys()) {
                if (!watchedJourneyIds.stream().anyMatch(jId -> jId.equals(journey.getJourneyId()))) {
                    if (l.isDebugEnabled()) l.debug("processing new journeyId="+journey.getJourneyId());
                    // process new journey (not already being watched)
                    JourneySnapshot js = journey.toSnapshot();
                    WaypointSnapshot ws = journey.getMostRecentWaypoint().toSnapshot();
                    CoordinatorSnapshot os = journey.getOwner().toSnapshot(); // guide/guidee
                    CoordinatorSnapshot as = journey.getAgent().toSnapshot(); // guidee (guided) or friend (solo)
                    AdultSnapshot gs = journey.getGuide().toSnapshot(); // guide/guidee
                    PlaceSnapshot ors = journey.getOrigin().toSnapshot();
                    PlaceSnapshot des = journey.getDestination().toSnapshot();
                    RouteSnapshot rs = journey.getRoute().toSnapshot();

                    // waypoints
                    Set<Long> waypointIds = new HashSet<>();
                    waypointIds.add(ws.getWaypointId());
                    js.setWaypointIds(waypointIds);
                    // tickets
                    js.setIssuedTicketIds(Constants.LONG_COLLECTION_NOT_RETRIEVED_PLACEHOLDER);

                    packet.getJourneySnapshots().put(js.getJourneyId(), js);
                    packet.getWaypointSnapshots().put(ws.getWaypointId(), ws);
                    if (os instanceof AdultSnapshot) {
                        packet.getAdultSnapshots().put(os.getCoordinatorId(), (AdultSnapshot) os);
                    } else {
                        packet.getInstitutionSnapshots().put(os.getCoordinatorId(), (InstitutionSnapshot) os);
                    }
                    if (as instanceof AdultSnapshot) {
                        packet.getAdultSnapshots().put(os.getCoordinatorId(), (AdultSnapshot) as);
                    } else {
                        packet.getInstitutionSnapshots().put(os.getCoordinatorId(), (InstitutionSnapshot) as);
                    }
                    packet.getAdultSnapshots().put(gs.getCoordinatorId(), gs);
                    packet.getPlaceSnapshots().put(ors.getPlaceId(), ors);
                    packet.getPlaceSnapshots().put(des.getPlaceId(), des);
                    packet.getRouteSnapshots().put(rs.getRouteId(), rs);
                }
            }
        }
        if (l.isDebugEnabled()) l.debug("found journeys as datapacket="+packet);
        return packet;
    }

    public DataPacket issueTickets(long journeyId, List<Long> childIds) throws Exception {
        if (l.isDebugEnabled()) l.debug("post tickets for journey="+journeyId+" and child ids="+childIds);

        Journey journey = journeyService.findActive(journeyId);
        // if journey == null then it has been completed since this ticket was requested
        if (journey == null) {
            throw new ScuffServerException("Resource not found", "The selected journey may have been completed",
                    Response.Status.NOT_FOUND, ErrorContextCode.JOURNEY_NOT_FOUND);
        }
        DataPacket packet = new DataPacket();
        for (Long id : childIds) {
            if (l.isDebugEnabled()) l.debug("processing children=" + id);
            // ensure no duplicates
            if (!journey.getIssuedTickets().stream().anyMatch(t -> t.getChild().getChildId() == id)) {
                if (!journey.getStampedTickets().stream().anyMatch(t -> t.getChild().getChildId() == id)) {
                    Ticket ticket = new Ticket();
                    ticket.setJourney(journey);
                    Child child = passengerService.find(id);
                    assert (child != null);
                    ticket.setChild(child);
                    ticketService.create(ticket);
                    child.getTickets().add(ticket);
                    passengerService.edit(child);
                    journey.getIssuedTickets().add(ticket);
                    if (l.isDebugEnabled()) l.debug("created ticket=" + ticket);
                    packet.getTicketSnapshots().put(ticket.getTicketId(), ticket.toSnapshot());
                }
            }
        }
        journeyService.edit(journey);
        return packet;
    }
}
