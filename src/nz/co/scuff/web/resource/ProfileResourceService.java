package nz.co.scuff.web.resource;

import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.family.Driver;
import nz.co.scuff.data.family.snapshot.PassengerSnapshot;
import nz.co.scuff.data.family.snapshot.DriverSnapshot;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import nz.co.scuff.data.school.snapshot.RouteSnapshot;
import nz.co.scuff.data.school.snapshot.SchoolSnapshot;
import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.family.DriverServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;

/**
 * Created by Callum on 27/04/2015.
 */
@Path("/profiles")
public class ProfileResourceService {

    public static final Logger l = LoggerFactory.getLogger(ProfileResourceService.class.getCanonicalName());

    @EJB
    private DriverServiceBean driverService;

/*    @Path("/{id}")
    @GET
    @Produces("application/json")
    public DriverSnapshot getDriverSnapshot(@PathParam("id") long id) {
        if (l.isDebugEnabled()) l.debug("get driver by id=" + id);

        Driver found = driverService.find(id);
        if (found == null) {
            return null;
        }
        return assemble(found);
    }*/

    @Path("/{email}")
    @GET
    @Produces("application/json")
    public DataPacket getDriver(@PathParam("email") String email) {
        if (l.isDebugEnabled()) l.debug("get driver by email");

        Driver found = driverService.findByEmail(email);
        if (found == null) {
            return null;
        }
        return assemble(found);
    }

    private DataPacket assemble(Driver toAssemble) {
        if (l.isDebugEnabled()) l.debug("assemble driver for transit");

        // assemble for user
        DataPacket packet = new DataPacket();

        DriverSnapshot dss = toAssemble.toSnapshot();

        for (Passenger p : toAssemble.getChildren()) {
            PassengerSnapshot ps = p.toSnapshot();
            for (School s : p.getSchools()) {
                SchoolSnapshot ss = s.toSnapshot();
                ps.getSchoolIds().add(ss.getSchoolId());
                packet.getSchoolSnapshots().put(ss.getSchoolId(), ss);
            }
            for (Driver d : p.getParents()) {
                DriverSnapshot ds = d.toSnapshot();
                ps.getParentIds().add(ds.getPersonId());
                packet.getDriverSnapshots().put(ds.getPersonId(), ds);
            }
            for (Route r : p.getRegisteredRoutes()) {
                RouteSnapshot rs = r.toSnapshot();
                ps.getRegisteredRouteIds().add(rs.getRouteId());
                packet.getRouteSnapshots().put(rs.getRouteId(), rs);
            }
            dss.getChildrenIds().add(ps.getPersonId());
            packet.getPassengerSnapshots().put(ps.getPersonId(), ps);
        }
        for (School s : toAssemble.getSchoolsDrivingFor()) {
            SchoolSnapshot ss = s.toSnapshot();
            dss.getSchoolIdsDrivenFor().add(ss.getSchoolId());
            packet.getSchoolSnapshots().put(ss.getSchoolId(), ss);
        }
        for (Route r : toAssemble.getRoutesDriven()) {
            RouteSnapshot rs = r.toSnapshot();
            dss.getRegisteredRouteIds().add(rs.getRouteId());
            packet.getRouteSnapshots().put(rs.getRouteId(), rs);
        }

        packet.getDriverSnapshots().put(dss.getPersonId(), dss);

        if (l.isDebugEnabled()) l.debug("assembled packet="+packet);

        return packet;
    }

    /*private DriverSnapshot assemble(Driver toPrune) {
        if (l.isDebugEnabled()) l.debug("pruning driver for transit");

        // assemble for user
        DriverSnapshot driverSnapshot = toPrune.toSnapshot();
        for (Passenger p : toPrune.getChildren()) {
            PassengerSnapshot ps = p.toSnapshot();
            for (School s : p.getSchools()) {
                ps.getSchools().add(s.toSnapshot());
            }
            for (Driver d : p.getParents()) {
                ps.getParents().add(d.toSnapshot());
            }
            for (Route r : p.getRegisteredRoutes()) {
                ps.getRegisteredRoutes().add(r.toSnapshot());
            }
            for (Journey j : p.getJourneys()) {
                JourneySnapshot js = j.toSnapshot();
                js.setSchoolId(j.getSchool().getSchoolId());
                js.setRouteId(j.getRoute().getRouteId());
                js.setDriverId(j.getDriver().getPersonId());
                ps.getJourneys().add(j.toSnapshot());
            }
            driverSnapshot.getChildren().add(ps);
        }
        for (Journey j : toPrune.getJourneys()) {
            JourneySnapshot js = j.toSnapshot();
            for (Passenger p : j.getPassengers()) {
                js.getPassengers().add(p.toSnapshot());
            }
            for (Waypoint w : j.getWaypoints()) {
                js.getWaypoints().add(w.toSnapshot());
            }
            js.setSchoolId(j.getSchool().getSchoolId());
            js.setRouteId(j.getRoute().getRouteId());
            js.setDriverId(j.getDriver().getPersonId());
            driverSnapshot.getJourneys().add(js);
        }
        for (School s : toPrune.getSchoolsDrivingFor()) {
            SchoolSnapshot ss = s.toSnapshot();
            driverSnapshot.getSchoolsDrivenFor().add(ss);
        }
        for (Route r : toPrune.getRoutesDriven()) {
            RouteSnapshot rs = r.toSnapshot();
            rs.setSchool(r.getSchool().toSnapshot());
            driverSnapshot.getRegisteredRoutes().add(rs);
        }
        return driverSnapshot;
    }*/

        /*@Path("/{id}")
    @GET
    @Produces("application/json")
    public ParentSnapshot getDriver(@PathParam("id") long id) {
        l.debug("get user by id=" + id);

        Parent parent = driverService.find(id);
        ProfileSnapshot profileSnapshot = new ProfileSnapshot();
        profileSnapshot.setParent(parent.toSnapshot());
        for (Child child : parent.getChildren()) {
            profileSnapshot.getChildren().add(child.toSnapshot());
        }
        for (School school : parent.getSchoolsDrivenFor()) {
            profileSnapshot.getSchoolsDrivenFor().add(school.toSnapshot());
        }
        for (Route route : parent.getRoutes()) {
            profileSnapshot.getRoutes().add(route);
        }
        return profileSnapshot;
    }*/

    /*@GET
    @Produces("application/json")
    public ProfileSnapshot getDriverByEmail(@QueryParam("email") String email) {
        if (D) l.debug("get user by email");

        Parent parent = driverService.findByEmail(email);
        if (parent == null) {
            return null;
        }
        // assemble for user
        ProfileSnapshot profileSnapshot = new ProfileSnapshot();
        profileSnapshot.setParent(parent.toSnapshot());
        for (Child child : parent.getChildren()) {
            profileSnapshot.getChildren().add(child.toSnapshot());
        }
        for (School school : parent.getSchoolsDrivenFor()) {
            profileSnapshot.getSchoolsDrivenFor().add(school.toSnapshot());
        }
        for (Route route : parent.getRoutes()) {
            profileSnapshot.getRoutes().add(route);
        }
        return profileSnapshot;
    }*/

}
