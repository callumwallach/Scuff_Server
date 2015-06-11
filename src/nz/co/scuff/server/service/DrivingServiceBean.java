package nz.co.scuff.server.service;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.family.Adult;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.data.util.TrackingState;
import nz.co.scuff.server.base.CoordinatorServiceBean;
import nz.co.scuff.server.family.AdultServiceBean;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.institution.RouteServiceBean;
import nz.co.scuff.server.institution.InstitutionServiceBean;
import nz.co.scuff.server.place.PlaceServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by Callum on 16/05/2015.
 */
@Stateless(name = "DrivingServiceEJB")
public class DrivingServiceBean {

    public static final Logger l = LoggerFactory.getLogger(DrivingServiceBean.class.getCanonicalName());

    @EJB
    private JourneyServiceBean journeyService;
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

    public DataPacket postJourney(DataPacket packet) {
        if (l.isDebugEnabled()) l.debug("create journey packet=" + packet);

        for (String jId : packet.getJourneySnapshots().keySet()) {
            JourneySnapshot snapshot = packet.getJourneySnapshots().get(jId);
            Journey journey = journeyService.find(snapshot.getJourneyId());
            assert (journey == null);
            // new journey
            if (l.isDebugEnabled()) l.debug("create journey");
            journey = new Journey(snapshot);

            Coordinator owner = coordinatorService.find(snapshot.getOwnerId());
            journey.setOwner(owner);
            Coordinator agent = coordinatorService.find(snapshot.getAgentId());
            journey.setAgent(agent);
            Adult guide = adultService.find(snapshot.getGuideId());
            journey.setGuide(guide);
            journey.setOrigin(placeService.find(snapshot.getOriginId()));
            journey.setDestination(placeService.find(snapshot.getDestinationId()));
            journey.setRoute(routeService.find(snapshot.getRouteId()));

            journeyService.create(journey);

            owner.getCurrentJourneys().add(journey);
            coordinatorService.edit(owner);
            agent.getCurrentJourneys().add(journey);
            coordinatorService.edit(agent);
            guide.getCurrentJourneys().add(journey);
            adultService.edit(guide);

            for (String wId : snapshot.getWaypointIds()) {
                Waypoint waypoint = new Waypoint(packet.getWaypointSnapshots().get(wId));
                journey.getWaypoints().add(waypoint);
            }
            journeyService.edit(journey);

        }

        return null;

    }

    public DataPacket updateJourney(String journeyId, DataPacket packet) {
        if (l.isDebugEnabled()) l.debug("update journey packet=" + packet);

        DataPacket returnPacket = new DataPacket();

        for (String jId : packet.getJourneySnapshots().keySet()) {
            JourneySnapshot snapshot = packet.getJourneySnapshots().get(jId);
            Journey journey = journeyService.find(snapshot.getJourneyId());
            assert (journey != null);
            // TODO
            /*if (foundJourney == null) {
                return null;
            }*/
            // update journey
            if (l.isDebugEnabled()) l.debug("update journey=" + journey);
            journey.setTotalDistance(snapshot.getTotalDistance());
            journey.setTotalDuration(snapshot.getTotalDuration());
            journey.setCompleted(snapshot.getCompleted());
            journey.setState(snapshot.getState());
            journey.setLastModified(snapshot.getLastModified());

            for (String wId : snapshot.getWaypointIds()) {
                Waypoint waypoint = new Waypoint(packet.getWaypointSnapshots().get(wId));
                journey.getWaypoints().add(waypoint);
            }
            journeyService.edit(journey);

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
                guide.getCurrentJourneys().remove(journey);
                guide.getPastJourneys().add(journey);
                adultService.edit(guide);
            } else {
                if (l.isDebugEnabled()) l.debug("processing journey tickets=" + journey.getTickets());
                for (Ticket ticket : journey.getTickets()) {
                    if (l.isDebugEnabled()) l.debug("processing ticket=" + ticket);
                    returnPacket.getTicketSnapshots().put(ticket.getTicketId(), ticket.toSnapshot());
                }
            }
        }

        if (l.isDebugEnabled()) l.debug("returning tickets=" + returnPacket);
        return returnPacket;

    }

}
