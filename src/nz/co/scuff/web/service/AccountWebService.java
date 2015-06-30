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
    public DataPacket getDriver(@PathParam("email") String email,
                                @QueryParam("lastChecked") long lastChecked) throws Exception {
        return accountService.getDriver(email, lastChecked);
    }

/*    @Path("/schools")
    @GET
    @Produces("application/json")
    public DataPacket getSchools(@QueryParam("latitude") double latitude,
                                 @QueryParam("longitude") double longitude, @QueryParam("radius") int radius) {
        return accountService.getSchools(latitude, longitude, radius);
    }*/

    /*private AdultSnapshot assemble(Adult toPrune) {
        if (l.isDebugEnabled()) l.debug("pruning driver for transit");

        // assemble for user
        AdultSnapshot driverSnapshot = toPrune.toSnapshot();
        for (Child p : toPrune.getChildren()) {
            ChildSnapshot ps = p.toSnapshot();
            for (Institution s : p.getSchools()) {
                ps.getSchools().add(s.toSnapshot());
            }
            for (Adult d : p.getParents()) {
                ps.getParents().add(d.toSnapshot());
            }
            for (Route r : p.getRegisteredRoutes()) {
                ps.getRegisteredRoutes().add(r.toSnapshot());
            }
            for (Journey j : p.getJourneys()) {
                JourneySnapshot js = j.toSnapshot();
                js.setOwnerId(j.getInstitution().getOwnerId());
                js.setRouteId(j.getRoute().getRouteId());
                js.setDriverId(j.getAdult().getPersonId());
                ps.getJourneys().add(j.toSnapshot());
            }
            driverSnapshot.getChildren().add(ps);
        }
        for (Journey j : toPrune.getJourneys()) {
            JourneySnapshot js = j.toSnapshot();
            for (Child p : j.getPassengers()) {
                js.getPassengers().add(p.toSnapshot());
            }
            for (Waypoint w : j.getWaypointIds()) {
                js.getWaypointIds().add(w.toSnapshot());
            }
            js.setOwnerId(j.getInstitution().getOwnerId());
            js.setRouteId(j.getRoute().getRouteId());
            js.setDriverId(j.getAdult().getPersonId());
            driverSnapshot.getJourneys().add(js);
        }
        for (Institution s : toPrune.getSchoolsDrivingFor()) {
            InstitutionSnapshot ss = s.toSnapshot();
            driverSnapshot.getSchoolsDrivenFor().add(ss);
        }
        for (Route r : toPrune.getRoutesDriven()) {
            RouteSnapshot rs = r.toSnapshot();
            rs.setInstitution(r.getInstitution().toSnapshot());
            driverSnapshot.getRegisteredRoutes().add(rs);
        }
        return driverSnapshot;
    }*/

        /*@Path("/{id}")
    @GET
    @Produces("application/json")
    public ParentSnapshot getAdult(@PathParam("id") long id) {
        l.debug("get user by id=" + id);

        Parent parent = driverService.find(id);
        ProfileSnapshot profileSnapshot = new ProfileSnapshot();
        profileSnapshot.setParent(parent.toSnapshot());
        for (Child child : parent.getChildren()) {
            profileSnapshot.getChildren().add(child.toSnapshot());
        }
        for (Institution school : parent.getSchoolsDrivenFor()) {
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
        for (Institution school : parent.getSchoolsDrivenFor()) {
            profileSnapshot.getSchoolsDrivenFor().add(school.toSnapshot());
        }
        for (Route route : parent.getRoutes()) {
            profileSnapshot.getRoutes().add(route);
        }
        return profileSnapshot;
    }*/

}
