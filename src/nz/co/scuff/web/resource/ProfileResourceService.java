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

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public DriverSnapshot getDriverSnapshot(@PathParam("id") long id) {
        if (l.isDebugEnabled()) l.debug("get driver by id=" + id);

        Driver found = driverService.find(id);
        if (found == null) {
            return null;
        }
        return prune(found);
    }

    @GET
    @Produces("application/json")
    public DriverSnapshot getDriverSnapshotByEmail(@QueryParam("email") String email) {
        if (l.isDebugEnabled()) l.debug("get driver by email");

        Driver found = driverService.findByEmail(email);
        if (found == null) {
            return null;
        }
        return prune(found);
    }

    private DriverSnapshot prune(Driver toPrune) {
        if (l.isDebugEnabled()) l.debug("pruning driver for transit");

        // prune for user
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
                js.setSchool(j.getSchool().toSnapshot());
                js.setRoute(j.getRoute().toSnapshot());
                js.setDriver(j.getDriver().toSnapshot());
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
            js.setSchool(j.getSchool().toSnapshot());
            js.setRoute(j.getRoute().toSnapshot());
            js.setDriver(j.getDriver().toSnapshot());
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
    }

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
        // prune for user
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
