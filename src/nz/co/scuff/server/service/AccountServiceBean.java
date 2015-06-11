package nz.co.scuff.server.service;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.base.snapshot.CoordinatorSnapshot;
import nz.co.scuff.data.family.Adult;
import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.family.snapshot.AdultSnapshot;
import nz.co.scuff.data.family.snapshot.ChildSnapshot;
import nz.co.scuff.data.institution.Institution;
import nz.co.scuff.data.institution.Route;
import nz.co.scuff.data.institution.snapshot.RouteSnapshot;
import nz.co.scuff.data.institution.snapshot.InstitutionSnapshot;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Ticket;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.data.journey.snapshot.WaypointSnapshot;
import nz.co.scuff.data.place.Place;
import nz.co.scuff.data.place.snapshot.PlaceSnapshot;
import nz.co.scuff.data.util.Constants;
import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.error.ErrorContextCode;
import nz.co.scuff.server.error.ScuffServerException;
import nz.co.scuff.server.family.AdultServiceBean;
import nz.co.scuff.server.institution.InstitutionServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

/**
 * Created by Callum on 16/05/2015.
 */
@Stateless(name = "AccountServiceEJB")
public class AccountServiceBean {

    public static final Logger l = LoggerFactory.getLogger(AccountServiceBean.class.getCanonicalName());

    @EJB
    private AdultServiceBean driverService;
    @EJB
    private InstitutionServiceBean schoolService;

    public AccountServiceBean() {}

    public DataPacket getDriver(String email, long lastChecked) throws Exception {
        if (l.isDebugEnabled()) l.debug("get driver by email="+email+" lastChecked="+lastChecked);

        Adult found = driverService.findByEmail(email);
        if (found == null) {
            throw new ScuffServerException("Resource not found", "The requested person does not exist",
                    Response.Status.NOT_FOUND, ErrorContextCode.PERSON_NOT_FOUND);
        }
        // assemble return data based on modified data (can be empty but never null)
        return assemble(found, lastChecked);
    }

/*    public DataPacket getSchools(double latitude, double longitude, int radius) {
        if (l.isDebugEnabled()) l.debug("getLocalSchools lat="+latitude+" long="+longitude+" radius="+radius);

        // TODO find by local area
        // TODO http://stackoverflow.com/questions/8428209/show-current-location-and-nearby-places-and-route-between-two-places-using-googl
        List<Institution> institutions = schoolService.findAll();
        DataPacket dataPacket = new DataPacket();
        for (Institution institution : institutions) {
            InstitutionSnapshot ss = institution.toSnapshot();
            if (l.isDebugEnabled()) l.debug("found institution="+ss);
            for (Route route : institution.getRoutes()) {
                RouteSnapshot rs = route.toSnapshot();
                rs.setOwnerId(ss.getSchoolId());
                dataPacket.getRouteSnapshots().put(rs.getRouteId(), rs);
                ss.getGuideIds().add(rs.getRouteId());
                if (l.isDebugEnabled()) l.debug("found route="+rs);
            }
            dataPacket.getInstitutionSnapshots().put(ss.getSchoolId(), ss);
        }
        return dataPacket;
    }*/

    private DataPacket assemble(Adult adult, long lastCheckedMillis) {
        if (l.isDebugEnabled()) l.debug("assemble data for transit");

        // assemble for user
        Timestamp lastChecked = new Timestamp(lastCheckedMillis);
        DataPacket packet = new DataPacket();

        if (l.isDebugEnabled()) l.debug("last modified="+adult.getLastModified().getTime()+" lastaccess="+lastCheckedMillis);

        // TODO check dates here
        if (adult.getLastModified().after(lastChecked)) {
            AdultSnapshot ass = adult.toSnapshot();

            // ignore past journeys
            ass.getPastJourneyIds().add(Constants.STRING_COLLECTION_NOT_RETRIEVED);

            for (Child p : adult.getChildren()) {
                if (l.isDebugEnabled()) l.debug("child:"+p);
                if (p.getLastModified().after(lastChecked)) {
                    ChildSnapshot ps = p.toSnapshot();
                    for (Adult a : p.getParents()) {
                        if (l.isDebugEnabled()) l.debug("parent:"+a);
                        AdultSnapshot as = a.toSnapshot();
                        ps.getParentIds().add(as.getCoordinatorId());
                        packet.getAdultSnapshots().put(as.getCoordinatorId(), as);
                    }
                    for (Ticket t : p.getTickets()) {
                        if (l.isDebugEnabled()) l.debug("ticket:"+t);
                        TicketSnapshot ts = t.toSnapshot();
                        ps.getTicketIds().add(ts.getTicketId());
                        packet.getTicketSnapshots().put(ts.getTicketId(), ts);
                    }
                    ass.getChildrenIds().add(ps.getChildId());
                    packet.getChildSnapshots().put(ps.getChildId(), ps);
                }
            }
            for (Journey j : adult.getCurrentJourneys()) {
                if (l.isDebugEnabled()) l.debug("current journey:"+j);
                if (j.getLastModified().after(lastChecked)) {
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
                    ass.getCurrentJourneyIds().add(js.getJourneyId());
                    packet.getJourneySnapshots().put(js.getJourneyId(), js);
                }
            }
            for (Institution i : adult.getGuidees()) {
                if (l.isDebugEnabled()) l.debug("guidee:"+i);
                if (i.getLastModified().after(lastChecked)) {
                    InstitutionSnapshot is = i.toSnapshot();
                    ass.getGuideeIds().add(is.getCoordinatorId());
                    packet.getInstitutionSnapshots().put(is.getCoordinatorId(), is);
                    // journey placeholder
                    is.getCurrentJourneyIds().add(Constants.STRING_COLLECTION_NOT_RETRIEVED);
                    is.getPastJourneyIds().add(Constants.STRING_COLLECTION_NOT_RETRIEVED);
                    /*for (Journey j : i.getCurrentJourneys()) {
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
                    }*/
                    for (Route r : i.getRoutes()) {
                        if (l.isDebugEnabled()) l.debug("route:"+r);
                        if (r.getLastModified().after(lastChecked)) {
                            RouteSnapshot rs = r.toSnapshot();
                            is.getRouteIds().add(rs.getRouteId());
                            packet.getRouteSnapshots().put(rs.getRouteId(), rs);
                        }
                    }
                    for (Place p : i.getPlaces()) {
                        if (l.isDebugEnabled()) l.debug("place:"+p);
                        if (p.getLastModified().after(lastChecked)) {
                            PlaceSnapshot ps = p.toSnapshot();
                            is.getPlaceIds().add(ps.getPlaceId());
                            packet.getPlaceSnapshots().put(ps.getPlaceId(), ps);
                        }
                    }
                }
            }
            for (Coordinator c : adult.getFriends()) {
                if (l.isDebugEnabled()) l.debug("friend:"+c);
                if (c.getLastModified().after(lastChecked)) {
                    CoordinatorSnapshot cs = c.toSnapshot();
                    ass.getFriendIds().add(cs.getCoordinatorId());
                    if (cs instanceof AdultSnapshot) {
                        packet.getAdultSnapshots().put(cs.getCoordinatorId(), (AdultSnapshot)cs);
                    } else {
                        packet.getInstitutionSnapshots().put(cs.getCoordinatorId(), (InstitutionSnapshot)cs);
                    }
                    // journey placeholder
                    cs.getCurrentJourneyIds().add(Constants.STRING_COLLECTION_NOT_RETRIEVED);
                    cs.getPastJourneyIds().add(Constants.STRING_COLLECTION_NOT_RETRIEVED);
/*                    for (Journey j : c.getCurrentJourneys()) {
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
                    }*/
                    for (Route r : c.getRoutes()) {
                        if (l.isDebugEnabled()) l.debug("route:"+r);
                        if (r.getLastModified().after(lastChecked)) {
                            RouteSnapshot rs = r.toSnapshot();
                            cs.getRouteIds().add(rs.getRouteId());
                            packet.getRouteSnapshots().put(rs.getRouteId(), rs);
                        }
                    }
                    for (Place p : c.getPlaces()) {
                        if (l.isDebugEnabled()) l.debug("place:"+p);
                        if (p.getLastModified().after(lastChecked)) {
                            PlaceSnapshot ps = p.toSnapshot();
                            cs.getPlaceIds().add(ps.getPlaceId());
                            packet.getPlaceSnapshots().put(ps.getPlaceId(), ps);
                        }
                    }
                }
            }
            for (Route r : adult.getRoutes()) {
                if (l.isDebugEnabled()) l.debug("route:"+r);
                if (r.getLastModified().after(lastChecked)) {
                    RouteSnapshot rs = r.toSnapshot();
                    ass.getRouteIds().add(rs.getRouteId());
                    packet.getRouteSnapshots().put(rs.getRouteId(), rs);
                }
            }
            for (Place p : adult.getPlaces()) {
                if (l.isDebugEnabled()) l.debug("place:"+p);
                if (p.getLastModified().after(lastChecked)) {
                    PlaceSnapshot ps = p.toSnapshot();
                    ass.getPlaceIds().add(ps.getPlaceId());
                    packet.getPlaceSnapshots().put(ps.getPlaceId(), ps);
                }
            }
            packet.getAdultSnapshots().put(ass.getCoordinatorId(), ass);
        }

        if (l.isDebugEnabled()) l.debug("assembled packet="+packet);

        return packet;
    }
}
