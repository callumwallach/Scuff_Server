package nz.co.scuff.web.service;

import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.service.AccountServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;

/**
 * Created by Callum on 27/04/2015.
 */
@Path("/account")
public class AccountWebService {

    public static final Logger l = LoggerFactory.getLogger(AccountWebService.class.getCanonicalName());

    @EJB
    private AccountServiceBean accountService;

    @Path("/drivers/{email}")
    @GET
    @Produces("application/json")
    public DataPacket getDriver(@PathParam("email") String email) {
        return accountService.getDriver(email);
    }

    @Path("/schools")
    @GET
    @Produces("application/json")
    public DataPacket getSchools(@QueryParam("latitude") double latitude,
                                 @QueryParam("longitude") double longitude, @QueryParam("radius") int radius) {
        return accountService.getSchools(latitude, longitude, radius);
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
