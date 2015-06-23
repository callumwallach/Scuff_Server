package nz.co.scuff.server.service;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.family.Adult;
import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.WaypointSnapshot;
import nz.co.scuff.data.util.*;
import nz.co.scuff.server.base.CoordinatorServiceBean;
import nz.co.scuff.server.family.AdultServiceBean;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.institution.RouteServiceBean;
import nz.co.scuff.server.institution.InstitutionServiceBean;
import nz.co.scuff.server.journey.WaypointServiceBean;
import nz.co.scuff.server.place.PlaceServiceBean;
import nz.co.scuff.server.util.DataAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 16/05/2015.
 */
@Stateless(name = "DrivingServiceEJB")
public class DrivingServiceBean {

    public static final Logger l = LoggerFactory.getLogger(DrivingServiceBean.class.getCanonicalName());

    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private WaypointServiceBean waypointService;
    @EJB
    private CoordinatorServiceBean coordinatorService;
    @EJB
    private InstitutionServiceBean institutionService;
    @EJB
    private RouteServiceBean routeService;
    @EJB
    private AdultServiceBean adultService;
    @EJB
    private PlaceServiceBean placeService;

    public DrivingServiceBean() { }

    public DataPacket startJourney(DataPacket incomingData) {
        if (l.isDebugEnabled()) l.debug("create journey packet=" + incomingData);

        JourneySnapshot journeySnapshot = incomingData.getJourneySnapshots().get(Constants.JOURNEY_PRE_CREATED_KEY);
        Journey journey = journeyService.find(journeySnapshot.getJourneyId());
        assert (journey == null);
        // new journey
        if (l.isDebugEnabled()) l.debug("create waypoint");
        WaypointSnapshot waypointSnapshot = incomingData.getWaypointSnapshots().get(Constants.WAYPOINT_PRE_CREATED_KEY);
        Waypoint waypoint = new Waypoint(waypointSnapshot);
        waypointService.create(waypoint);

        if (l.isDebugEnabled()) l.debug("create journey");
        journey = new Journey(journeySnapshot);

        Coordinator owner = coordinatorService.find(journeySnapshot.getOwnerId());
        journey.setOwner(owner);
        Coordinator agent = coordinatorService.find(journeySnapshot.getAgentId());
        journey.setAgent(agent);
        Adult guide = adultService.find(journeySnapshot.getGuideId());
        Timestamp guidesLastRefresh = guide.getLastRefresh();
        journey.setGuide(guide);
        journey.setOrigin(placeService.find(journeySnapshot.getOriginId()));
        journey.setDestination(placeService.find(journeySnapshot.getDestinationId()));
        journey.setRoute(routeService.find(journeySnapshot.getRouteId()));
        journeyService.create(journey);

        owner.getCurrentJourneys().add(journey);
        coordinatorService.edit(owner);
        agent.getCurrentJourneys().add(journey);
        coordinatorService.edit(agent);
        guide.getCurrentJourneys().add(journey);
        guide.setLastRefresh(new Timestamp(System.currentTimeMillis()));
        adultService.edit(guide);

        journey.getWaypoints().add(waypoint);
        journeyService.edit(journey);

/*        // refresh
        journey = journeyService.find(journey.getJourneyId());*/

        DataPacket outgoingData = DataAssembler.assemble(guide, guidesLastRefresh.getTime());
        outgoingData.setItemOfInterest(journey.getJourneyId());
        if (l.isDebugEnabled()) l.debug("returning data=" + outgoingData);

        return outgoingData;

/*
        DataPacket returnPacket = new DataPacket();

        WaypointSnapshot returnWaypointSnapshot = journey.getMostRecentWaypoint().toSnapshot();
        JourneySnapshot returnJourneySnapshot = journey.toSnapshot();
        Set<Long> returnWaypointIds = new HashSet<>();
        returnWaypointIds.add(returnWaypointSnapshot.getWaypointId());
        returnPacket.getWaypointSnapshots().put(waypoint.getWaypointId(), returnWaypointSnapshot);

        returnJourneySnapshot.setWaypointIds(returnWaypointIds);
        returnPacket.getJourneySnapshots().put(journey.getJourneyId(), returnJourneySnapshot);

        // TODO if listed as agent that journey will not be represent on the client
        // TODO use full object copier??
        AdultSnapshot returnAdultSnapshot = guide.toSnapshot();
        for (Journey j : guide.getCurrentJourneys()) {
            returnAdultSnapshot.getCurrentJourneyIds().add(j.getJourneyId());
        }
        returnPacket.getAdultSnapshots().put(guide.getCoordinatorId(), guide.toSnapshot());

        return returnPacket;
        */
    }

    public DataPacket updateJourney(long journeyId, DataPacket incomingData) {
        if (l.isDebugEnabled()) l.debug("update journey packet=" + incomingData);

        JourneySnapshot journeySnapshot = incomingData.getJourneySnapshots().get(journeyId);
        Journey journey = journeyService.find(journeySnapshot.getJourneyId());
        assert (journey != null);
        // TODO
        /*if (foundJourney == null) {
            return null;
        }*/

        // update journey
        if (l.isDebugEnabled()) l.debug("update journey=" + journey.getJourneyId());
        journey.setTotalDistance(journeySnapshot.getTotalDistance());
        journey.setTotalDuration(journeySnapshot.getTotalDuration());
        journey.setCompleted(journeySnapshot.getCompleted());
        journey.setState(journeySnapshot.getState());

        // should only be one waypoint
        for (long wId : journeySnapshot.getWaypointIds()) {
            Waypoint waypoint = new Waypoint(incomingData.getWaypointSnapshots().get(wId));
            journey.getWaypoints().add(waypoint);
        }
        journeyService.edit(journey);

        DataPacket outgoingData = new DataPacket();

        if (journey.getState().equals(TrackingState.COMPLETED)) {
            if (l.isDebugEnabled()) l.debug("moving journey from current to past");
            Coordinator owner = coordinatorService.load(journey.getOwner().getCoordinatorId(), new int[]{CoordinatorServiceBean.PAST_JOURNEYS});
            owner.getCurrentJourneys().remove(journey);
            owner.getPastJourneys().add(journey);
            coordinatorService.edit(owner);
            Coordinator agent = coordinatorService.load(journey.getAgent().getCoordinatorId(), new int[]{CoordinatorServiceBean.PAST_JOURNEYS});
            agent.getCurrentJourneys().remove(journey);
            agent.getPastJourneys().add(journey);
            coordinatorService.edit(agent);
            Adult guide = adultService.load(journey.getGuide().getCoordinatorId(), new int[]{AdultServiceBean.PAST_JOURNEYS});
            Timestamp guidesLastRefresh = guide.getLastRefresh();
            guide.setLastRefresh(new Timestamp(System.currentTimeMillis()));
            guide.getCurrentJourneys().remove(journey);
            guide.getPastJourneys().add(journey);
            adultService.edit(guide);

            // sync data at journey completion
            outgoingData = DataAssembler.assemble(guide, guidesLastRefresh.getTime());
            outgoingData.setItemOfInterest(journey.getJourneyId());
        } else {
            // get tickets
            Timestamp lastChecked = journey.getLastModified();
            if (l.isDebugEnabled()) l.debug("processing journey="+journey.getJourneyId()+" tickets");
            for (Ticket ticket : journey.getTickets()) {
                if (l.isDebugEnabled()) l.debug("ticket issue="+ticket.getIssueDate()+" lastChecked="+lastChecked);
                //if (ticket.getIssueDate().after(lastChecked)) {
                if (l.isDebugEnabled()) l.debug("processing ticket="+ticket);
                outgoingData.getChildSnapshots().put(ticket.getChild().getChildId(), ticket.getChild().toSnapshot());
                outgoingData.getTicketSnapshots().put(ticket.getTicketId(), ticket.toSnapshot());
                //}
            }
        }

        if (l.isDebugEnabled()) l.debug("returning data=" + outgoingData);
        return outgoingData;
    }

}
