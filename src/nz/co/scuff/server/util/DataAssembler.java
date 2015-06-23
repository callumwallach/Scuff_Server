package nz.co.scuff.server.util;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.base.snapshot.CoordinatorSnapshot;
import nz.co.scuff.data.family.Adult;
import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.family.snapshot.AdultSnapshot;
import nz.co.scuff.data.family.snapshot.ChildSnapshot;
import nz.co.scuff.data.institution.Institution;
import nz.co.scuff.data.institution.Route;
import nz.co.scuff.data.institution.snapshot.InstitutionSnapshot;
import nz.co.scuff.data.institution.snapshot.RouteSnapshot;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.data.journey.snapshot.WaypointSnapshot;
import nz.co.scuff.data.place.Place;
import nz.co.scuff.data.place.snapshot.PlaceSnapshot;
import nz.co.scuff.data.util.DataPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 15/06/2015.
 */
public class DataAssembler {

    public static final Logger l = LoggerFactory.getLogger(DataAssembler.class.getCanonicalName());

    private static final boolean SHALLOW = false;

    private static Set<Long> doAdults(Timestamp lastChecked, Collection<Adult> adults, DataPacket packet, boolean isDeepCopy) {

        Set<Long> adultIds = new HashSet<>();
        for (Adult adult : adults) {
            if (l.isDebugEnabled()) l.debug("adult:"+adult);
            adultIds.add(adult.getCoordinatorId());
            if (adult.isDirty(lastChecked)) {
                if (l.isDebugEnabled()) l.debug("adult="+adult.getCoordinatorId()+" is dirty");
                AdultSnapshot adultSnapshot = adult.toSnapshot();
                if (isDeepCopy) {
                    if (l.isDebugEnabled()) l.debug("adult="+adult.getCoordinatorId()+" deep copy");
                    adultSnapshot.setChildrenIds(doChildren(lastChecked, adult.getChildren(), packet));
                    adultSnapshot.setGuideeIds(doInstitutions(lastChecked, adult.getGuidees(), packet, SHALLOW));
                }
                packet.getAdultSnapshots().put(adultSnapshot.getCoordinatorId(), adultSnapshot);
            }
        }
        return adultIds;
    }

    private static Set<Long> doAdults(Timestamp lastChecked, Collection<Adult> adults, DataPacket packet) {

        return doAdults(lastChecked, adults, packet, true);

    }

    private static Set<Long> doChildren(Timestamp lastChecked, Collection<Child> children, DataPacket packet, boolean isDeepCopy) {

        Set<Long> childIds = new HashSet<>();
        for (Child child : children) {
            if (l.isDebugEnabled()) l.debug("child:"+child);
            childIds.add(child.getChildId());
            if (child.isDirty(lastChecked)) {
                if (l.isDebugEnabled()) l.debug("child="+child.getChildId()+" is dirty");
                ChildSnapshot childSnapshot = child.toSnapshot();
                if (isDeepCopy) {
                    if (l.isDebugEnabled()) l.debug("child="+child.getChildId()+" deep copy");
                    childSnapshot.setParentIds(doAdults(lastChecked, child.getParents(), packet, SHALLOW));
                    childSnapshot.setTicketIds(doTickets(lastChecked, child.getTickets(), packet));
                }
                packet.getChildSnapshots().put(childSnapshot.getChildId(), childSnapshot);
            }
        }
        return childIds;
    }

    private static Set<Long> doChildren(Timestamp lastChecked, Collection<Child> children, DataPacket packet) {

        return doChildren(lastChecked, children, packet, true);

    }

    private static Set<Long> doTickets(Timestamp lastChecked, Collection<Ticket> tickets, DataPacket packet) {

        Set<Long> ticketIds = new HashSet<>();
        for (Ticket ticket : tickets) {
            if (l.isDebugEnabled()) l.debug("ticket:"+ticket);
            ticketIds.add(ticket.getTicketId());
            if (ticket.isDirty(lastChecked)) {
                TicketSnapshot ticketSnapshot = ticket.toSnapshot();
                packet.getTicketSnapshots().put(ticketSnapshot.getTicketId(), ticketSnapshot);
            }
        }
        return ticketIds;
    }

    private static Set<Long> doRoutes(Timestamp lastChecked, Collection<Route> routes, DataPacket packet) {

        Set<Long> routeIds = new HashSet<>();
        for (Route route : routes) {
            if (l.isDebugEnabled()) l.debug("route:"+route);
            routeIds.add(route.getRouteId());
            if (route.isDirty(lastChecked)) {
                RouteSnapshot routeSnapshot = route.toSnapshot();
                packet.getRouteSnapshots().put(routeSnapshot.getRouteId(), routeSnapshot);
            }
        }
        return routeIds;
    }

    private static Set<Long> doPlaces(Timestamp lastChecked, Collection<Place> places, DataPacket packet) {

        Set<Long> placeIds = new HashSet<>();
        for (Place place : places) {
            if (l.isDebugEnabled()) l.debug("place:"+place);
            placeIds.add(place.getPlaceId());
            if (place.isDirty(lastChecked)) {
                PlaceSnapshot placeSnapshot = place.toSnapshot();
                packet.getPlaceSnapshots().put(placeSnapshot.getPlaceId(), placeSnapshot);
            }
        }
        return placeIds;
    }

    private static Set<Long> doWaypoints(Timestamp lastChecked, Collection<Waypoint> waypoints, DataPacket packet) {

        Set<Long> waypointIds = new HashSet<>();
        for (Waypoint waypoint : waypoints) {
            if (l.isDebugEnabled()) l.debug("waypoint:"+waypoint);
            waypointIds.add(waypoint.getWaypointId());
            WaypointSnapshot waypointSnapshot = waypoint.toSnapshot();
            packet.getWaypointSnapshots().put(waypointSnapshot.getWaypointId(), waypointSnapshot);
        }
        return waypointIds;
    }

    private static Set<Long> doJourneys(Timestamp lastChecked, Collection<Journey> journeys, DataPacket packet) {

        Set<Long> journeyIds = new HashSet<>();
        for (Journey journey : journeys) {
            if (l.isDebugEnabled()) l.debug("journey:"+journey);
            journeyIds.add(journey.getJourneyId());
            if (journey.isDirty(lastChecked)) {
                if (l.isDebugEnabled()) l.debug("journey="+journey.getJourneyId()+" is dirty");
                JourneySnapshot journeySnapshot = journey.toSnapshot();
                journeySnapshot.setWaypointIds(doWaypoints(lastChecked, journey.getWaypoints(), packet));
                journeySnapshot.setTicketIds(doTickets(lastChecked, journey.getTickets(), packet));
                packet.getJourneySnapshots().put(journeySnapshot.getJourneyId(), journeySnapshot);
            }
        }
        return journeyIds;
    }

    private static Set<Long> doInstitutions(Timestamp lastChecked, Collection<Institution> institutions, DataPacket packet, boolean isDeepCopy) {

        Set<Long> institutionIds = new HashSet<>();
        for (Institution institution : institutions) {
            if (l.isDebugEnabled()) l.debug("institution:"+institution);
            institutionIds.add(institution.getCoordinatorId());
            if (institution.isDirty(lastChecked)) {
                if (l.isDebugEnabled()) l.debug("institution="+institution.getCoordinatorId()+" is dirty");
                InstitutionSnapshot institutionSnapshot = institution.toSnapshot();
                if (isDeepCopy) {
                    if (l.isDebugEnabled()) l.debug("institution="+institution.getCoordinatorId()+" deep copy");
                    institutionSnapshot.setGuideIds(doAdults(lastChecked, institution.getGuides(), packet, SHALLOW));
                    institutionSnapshot.setRouteIds(doRoutes(lastChecked, institution.getRoutes(), packet));
                    institutionSnapshot.setPlaceIds(doPlaces(lastChecked, institution.getPlaces(), packet));
                }
                packet.getInstitutionSnapshots().put(institutionSnapshot.getCoordinatorId(), institutionSnapshot);
            }
        }
        return institutionIds;
    }

    private static Set<Long> doInstitutions(Timestamp lastChecked, Collection<Institution> institutions, DataPacket packet) {

        return doInstitutions(lastChecked, institutions, packet, true);

    }

    private static Set<Long> doCoordinators(Timestamp lastChecked, Collection<Coordinator> coordinators, DataPacket packet, boolean isDeepCopy) {

        Set<Long> coordinatorIds = new HashSet<>();
        for (Coordinator coordinator : coordinators) {
            if (l.isDebugEnabled()) l.debug("coordinator:"+coordinator);
            coordinatorIds.add(coordinator.getCoordinatorId());
            if (coordinator.isDirty(lastChecked)) {
                if (l.isDebugEnabled()) l.debug("coordinator="+coordinator.getCoordinatorId()+" is dirty");
                CoordinatorSnapshot coordinatorSnapshot = coordinator.toSnapshot();
                if (isDeepCopy) {
                    if (l.isDebugEnabled()) l.debug("coordinator="+coordinator.getCoordinatorId()+" deep copy");
                    coordinatorSnapshot.setRouteIds(doRoutes(lastChecked, coordinator.getRoutes(), packet));
                    coordinatorSnapshot.setPlaceIds(doPlaces(lastChecked, coordinator.getPlaces(), packet));
                }
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
        }
        return coordinatorIds;
    }

    private static Set<Long> doCoordinators(Timestamp lastChecked, Collection<Coordinator> coordinators, DataPacket packet) {

        return doCoordinators(lastChecked, coordinators, packet, true);

    }

    public static DataPacket assemble(Adult adult, long lastCheckedMillis) {
        if (l.isDebugEnabled()) l.debug("assemble data for transit");

        // assemble for user
        Timestamp lastChecked = new Timestamp(lastCheckedMillis);
        DataPacket packet = new DataPacket();

        if (l.isDebugEnabled()) l.debug("last modified="+adult.getLastModified().getTime()+" lastaccess="+lastCheckedMillis);

        // construct snapshot and process all relationships. if relationship entity is dirty it will be added to
        // the return packet, else only the relationship entities id is added to the snapshot (ie. no change)
        AdultSnapshot adultSnapshot = adult.toSnapshot();
        adultSnapshot.setChildrenIds(doChildren(lastChecked, adult.getChildren(), packet));
        //adultSnapshot.setPastJourneyIds(Constants.STRING_COLLECTION_NOT_RETRIEVED_PLACEHOLDER);
        adultSnapshot.setCurrentJourneyIds(doJourneys(lastChecked, adult.getCurrentJourneys(), packet));
        adultSnapshot.setGuideeIds(doInstitutions(lastChecked, adult.getGuidees(), packet));
        adultSnapshot.setFriendIds(doCoordinators(lastChecked, adult.getFriends(), packet));
        adultSnapshot.setPlaceIds(doPlaces(lastChecked, adult.getPlaces(), packet));
        adultSnapshot.setRouteIds(doRoutes(lastChecked, adult.getRoutes(), packet));
        // if the requesting user is dirty, then return its data along with the ids of the relationship ids
        // (dirty relationship entities have already been added to the return packet)
        if (adult.isDirty(lastChecked)) {
            packet.getAdultSnapshots().put(adultSnapshot.getCoordinatorId(), adultSnapshot);
        }
        if (l.isDebugEnabled()) l.debug("assembled packet="+packet);
        return packet;

/*
            for (Child c : adult.getChildren()) {
                if (l.isDebugEnabled()) l.debug("child:"+c);
                ChildSnapshot cs = c.toSnapshot();
                for (Adult a : c.getParents()) {
                    if (l.isDebugEnabled()) l.debug("parent:"+a);
                    cs.getParentIds().add(a.getCoordinatorId());
                    if (a.isDirty(lastChecked)) {
                        AdultSnapshot as = a.toSnapshot();
                        packet.getAdultSnapshots().put(as.getCoordinatorId(), as);
                    }
                }
                for (Ticket t : c.getTickets()) {
                    if (l.isDebugEnabled()) l.debug("ticket:"+t);
                    cs.getTicketIds().add(t.getTicketId());
                    if (t.isDirty(lastChecked)) {
                        TicketSnapshot ts = t.toSnapshot();
                        packet.getTicketSnapshots().put(ts.getTicketId(), ts);
                    }
                }
                adultSnapshot.getChildrenIds().add(cs.getChildId());
                packet.getChildSnapshots().put(cs.getChildId(), cs);
            }

            for (Journey j : adult.getCurrentJourneys()) {
                if (l.isDebugEnabled()) l.debug("current journey:"+j);
                JourneySnapshot js = j.toSnapshot();
                for (Waypoint w : j.getWaypoints()) {
                    if (l.isDebugEnabled()) l.debug("waypoint:"+w);
                    WaypointSnapshot ws = w.toSnapshot();
                    js.getWaypointIds().add(ws.getWaypointId());
                    packet.getWaypointSnapshots().put(ws.getWaypointId(), ws);
                }
                for (Ticket t : j.getTickets()) {
                    if (l.isDebugEnabled()) l.debug("ticket:"+t);
                    TicketSnapshot ts = t.toSnapshot();
                    js.getTicketIds().add(ts.getTicketId());
                    packet.getTicketSnapshots().put(ts.getTicketId(), ts);
                }
                adultSnapshot.getCurrentJourneyIds().add(js.getJourneyId());
                packet.getJourneySnapshots().put(js.getJourneyId(), js);
            }

            for (Institution i : adult.getGuidees()) {
                if (l.isDebugEnabled()) l.debug("guidee:"+i);
                InstitutionSnapshot is = i.toSnapshot();
                adultSnapshot.getGuideeIds().add(is.getCoordinatorId());
                packet.getInstitutionSnapshots().put(is.getCoordinatorId(), is);
                // journey placeholder
                is.getCurrentJourneyIds().add(Constants.STRING_COLLECTION_NOT_RETRIEVED);
                is.getPastJourneyIds().add(Constants.STRING_COLLECTION_NOT_RETRIEVED);
                *//*for (Journey j : i.getCurrentJourneys()) {
                    if (l.isDebugEnabled()) l.debug("current journey:"+j);
                    if (j.getLastModified().after(lastChecked)) {
                        JourneySnapshot js = j.toSnapshot();
                        for (Waypoint w : j.getWaypoints()) {
                            if (l.isDebugEnabled()) l.debug("waypoint:"+w);
                            WaypointSnapshot ws = w.toSnapshot();
                            js.getWaypointIds().add(ws.getWaypointId());
                            packet.getWaypointSnapshots().put(ws.getWaypointId(), ws);
                        }
                        // ignore tickets
                        js.getTicketIds().add(Constants.LONG_COLLECTION_NOT_RETRIEVED);
                        is.getCurrentJourneyIds().add(js.getJourneyId());
                        packet.getJourneySnapshots().put(js.getJourneyId(), js);
                    }
                }*//*
                for (Adult g : i.getGuides()) {
                    if (l.isDebugEnabled()) l.debug("guide:"+g);
                    AdultSnapshot as = g.toSnapshot();
                    is.getGuideIds().add(as.getCoordinatorId());
                    packet.getAdultSnapshots().put(as.getCoordinatorId(), as);
                }
                for (Route r : i.getRoutes()) {
                    if (l.isDebugEnabled()) l.debug("route:"+r);
                    RouteSnapshot rs = r.toSnapshot();
                    is.getRouteIds().add(rs.getRouteId());
                    packet.getRouteSnapshots().put(rs.getRouteId(), rs);
                }
                for (Place p : i.getPlaces()) {
                    if (l.isDebugEnabled()) l.debug("place:"+p);
                    PlaceSnapshot ps = p.toSnapshot();
                    is.getPlaceIds().add(ps.getPlaceId());
                    packet.getPlaceSnapshots().put(ps.getPlaceId(), ps);
                }
            }
            for (Coordinator c : adult.getFriends()) {
                if (l.isDebugEnabled()) l.debug("friend:"+c);
                CoordinatorSnapshot cs = c.toSnapshot();
                adultSnapshot.getFriendIds().add(cs.getCoordinatorId());
                if (cs instanceof AdultSnapshot) {
                    packet.getAdultSnapshots().put(cs.getCoordinatorId(), (AdultSnapshot)cs);
                } else {
                    packet.getInstitutionSnapshots().put(cs.getCoordinatorId(), (InstitutionSnapshot)cs);
                }
                // journey placeholder
                cs.getCurrentJourneyIds().add(Constants.STRING_COLLECTION_NOT_RETRIEVED);
                cs.getPastJourneyIds().add(Constants.STRING_COLLECTION_NOT_RETRIEVED);
*//*                    for (Journey j : c.getCurrentJourneys()) {
                    if (l.isDebugEnabled()) l.debug("journey:"+j);
                    if (j.getLastModified().after(lastChecked)) {
                        JourneySnapshot js = j.toSnapshot();
                        for (Waypoint w : j.getWaypoints()) {
                            WaypointSnapshot ws = w.toSnapshot();
                            js.getWaypointIds().add(ws.getWaypointId());
                            packet.getWaypointSnapshots().put(ws.getWaypointId(), ws);
                        }
                        // ignore tickets
                        js.getTicketIds().add(Constants.LONG_COLLECTION_NOT_RETRIEVED);
                        cs.getCurrentJourneyIds().add(js.getJourneyId());
                        packet.getJourneySnapshots().put(js.getJourneyId(), js);
                    }
                }*//*
                for (Route r : c.getRoutes()) {
                    if (l.isDebugEnabled()) l.debug("route:"+r);
                    RouteSnapshot rs = r.toSnapshot();
                    cs.getRouteIds().add(rs.getRouteId());
                    packet.getRouteSnapshots().put(rs.getRouteId(), rs);
                }
                for (Place p : c.getPlaces()) {
                    if (l.isDebugEnabled()) l.debug("place:"+p);
                    PlaceSnapshot ps = p.toSnapshot();
                    cs.getPlaceIds().add(ps.getPlaceId());
                    packet.getPlaceSnapshots().put(ps.getPlaceId(), ps);
                }
            }
            for (Route r : adult.getRoutes()) {
                if (l.isDebugEnabled()) l.debug("route:"+r);
                RouteSnapshot rs = r.toSnapshot();
                adultSnapshot.getRouteIds().add(rs.getRouteId());
                packet.getRouteSnapshots().put(rs.getRouteId(), rs);
            }
            for (Place p : adult.getPlaces()) {
                if (l.isDebugEnabled()) l.debug("place:"+p);
                PlaceSnapshot ps = p.toSnapshot();
                adultSnapshot.getPlaceIds().add(ps.getPlaceId());
                packet.getPlaceSnapshots().put(ps.getPlaceId(), ps);
            }
            packet.getAdultSnapshots().put(adultSnapshot.getCoordinatorId(), adultSnapshot);
        }*/

    }
}
