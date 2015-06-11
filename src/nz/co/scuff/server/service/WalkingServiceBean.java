package nz.co.scuff.server.service;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.base.snapshot.CoordinatorSnapshot;
import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.family.snapshot.AdultSnapshot;
import nz.co.scuff.data.institution.snapshot.InstitutionSnapshot;
import nz.co.scuff.data.institution.snapshot.RouteSnapshot;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.WaypointSnapshot;
import nz.co.scuff.data.place.snapshot.PlaceSnapshot;
import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.base.CoordinatorServiceBean;
import nz.co.scuff.server.error.ErrorContextCode;
import nz.co.scuff.server.error.ScuffServerException;
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
import java.util.List;

/**
 * Created by Callum on 16/05/2015.
 */
@Stateless(name = "WalkingServiceEJB")
public class WalkingServiceBean {

    public static final Logger l = LoggerFactory.getLogger(WalkingServiceBean.class.getCanonicalName());

    @EJB
    private CoordinatorServiceBean coordinatorService;
    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private ChildServiceBean passengerService;
    @EJB
    private TicketServiceBean ticketService;

    public WalkingServiceBean() { }

/*    public BusSnapshot getActiveBus(String journeyId) {
        if (l.isDebugEnabled()) l.debug("getActiveBus busId="+journeyId);

        Journey journey = journeyService.findActive(journeyId);
        BusSnapshot snapshot = null;
        if (journey != null) {
            snapshot = new BusSnapshot(journey);
        }
        if (l.isDebugEnabled()) l.debug("found snapshot="+ snapshot);
        return snapshot;
    }*/

    /*public List<BusSnapshot> getActiveBuses(long routeId, long schoolId) {
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
    }*/

    public DataPacket getActiveJourneys(long coordinatorId) {
        if (l.isDebugEnabled()) l.debug("getActiveJourneys for friends of coordinator="+coordinatorId);

        Coordinator coordinator = coordinatorService.find(coordinatorId);
        DataPacket packet = new DataPacket();
        for (Coordinator friend : coordinator.getFriends()) {
            for (Journey journey : friend.getCurrentJourneys()) {

                JourneySnapshot js = journey.toSnapshot();
                WaypointSnapshot ws = journey.getMostRecentWaypoint().toSnapshot();
                CoordinatorSnapshot os = journey.getOwner().toSnapshot();
                CoordinatorSnapshot as = journey.getAgent().toSnapshot();
                AdultSnapshot gs = journey.getGuide().toSnapshot();
                PlaceSnapshot ors = journey.getOrigin().toSnapshot();
                PlaceSnapshot des = journey.getDestination().toSnapshot();
                RouteSnapshot rs = journey.getRoute().toSnapshot();

                js.getWaypointIds().add(ws.getWaypointId());

                packet.getJourneySnapshots().put(js.getJourneyId(), js);
                packet.getWaypointSnapshots().put(ws.getWaypointId(), ws);
                if (os instanceof AdultSnapshot) {
                    packet.getAdultSnapshots().put(os.getCoordinatorId(), (AdultSnapshot)os);
                } else {
                    packet.getInstitutionSnapshots().put(os.getCoordinatorId(), (InstitutionSnapshot)os);
                }
                if (as instanceof AdultSnapshot) {
                    packet.getAdultSnapshots().put(os.getCoordinatorId(), (AdultSnapshot)as);
                } else {
                    packet.getInstitutionSnapshots().put(os.getCoordinatorId(), (InstitutionSnapshot)as);
                }
                packet.getAdultSnapshots().put(gs.getCoordinatorId(), gs);
                packet.getPlaceSnapshots().put(ors.getPlaceId(), ors);
                packet.getPlaceSnapshots().put(des.getPlaceId(), des);
                packet.getRouteSnapshots().put(rs.getRouteId(), rs);
            }
        }
        if (l.isDebugEnabled()) l.debug("found journeys as datapacket="+packet);
        return packet;
    }

    /*public DataPacket getActiveJourneys(long routeId, long schoolId) {
        if (l.isDebugEnabled()) l.debug("getActiveBuses routeId="+routeId+" schoolId="+schoolId);

        // only need location details as data is matched up to local copy (and constant fields ignored)
        // so set driver school and route as ids only
        List<Journey> journeys = journeyService.findActiveByRouteAndSchool(routeId, schoolId);
        DataPacket packet = new DataPacket();
        for (Journey journey : journeys) {
            JourneySnapshot js = journey.toSnapshot();
            WaypointSnapshot ws = journey.getMostRecentWaypoint().toSnapshot();
            AdultSnapshot ds = journey.getDriver().toSnapshot();
            js.getWaypointIds().add(ws);
            packet.getJourneySnapshots().put(js.getJourneyId(), js);
            packet.getWaypointSnapshots().put(ws.getWaypointId(), ws);
            packet.getAdultSnapshots().put(ds.getPersonId(), ds);
        }
        if (l.isDebugEnabled()) l.debug("found journeys as datapacket="+packet);
        return packet;
    }*/

    public DataPacket requestTickets(String journeyId, List<Long> childIds) throws Exception {
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
            if (!journey.getTickets().stream().anyMatch(t -> t.getChild().getChildId() == id)) {
                Ticket ticket = new Ticket();
                ticket.setIssueDate(new Timestamp(DateTimeUtils.currentTimeMillis()));
                ticket.setJourney(journey);
                Child child = passengerService.find(id);
                assert (child != null);
                ticket.setChild(child);
                ticketService.create(ticket);
                child.getTickets().add(ticket);
                passengerService.edit(child);
                journey.getTickets().add(ticket);
                if (l.isDebugEnabled()) l.debug("created ticket=" + ticket);
                packet.getTicketSnapshots().put(ticket.getTicketId(), ticket.toSnapshot());
            }
        }
        journeyService.edit(journey);
        return packet;
    }
}
