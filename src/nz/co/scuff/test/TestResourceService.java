package nz.co.scuff.test;

import nz.co.scuff.data.family.Driver;
import nz.co.scuff.data.family.snapshot.DriverSnapshot;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.server.family.DriverServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Callum on 4/05/2015.
 */
@Path("/test")
public class TestResourceService {

    public static final Logger l = LoggerFactory.getLogger(TestResourceService.class.getCanonicalName());
    public static boolean D = true;

    @EJB
    private TestServiceBean testService;
    @EJB
    private DriverServiceBean userService;

    @Path("/populate")
    @POST
    @Consumes("application/json")
    public void populateTestData(String data) {
        if (D) l.debug("populating test data resource");

        testService.populateTestData();
    }

    @Path("/populate/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response putAndReturnTestData(@PathParam("id") String journeyId, String data) {
        if (D) l.debug("put and return data journeyId="+journeyId+" data received="+data);
        TicketSnapshot obj = new TicketSnapshot();
        obj.setJourneyId("ticket no 3");
        return Response.ok().entity(obj).build();
    }

/*    @Path("/snapshot/1")
    @GET
    @Produces("application/json")
    public ProfileSnapshot getProfileSnapshotByEmail(@PathParam("email") String email) {
        if (D) l.debug("populating test data resource");

        Parent parent = userService.findByEmail(email);
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

    @GET
    @Produces("application/json")
    public DriverSnapshot getParentSnapshotByEmail(@QueryParam("email") String email) {
        if (D) l.debug("get user by email");

        Driver found = userService.findByEmail(email);
        if (found == null) {
            return null;
        }
        // prune for user
        DriverSnapshot driverSnapshot = found.toSnapshot();
        /*for (Child c : found.getChildren()) {
            ChildSnapshot cs = c.toSnapshot();
            for (School s : c.getSchoolsDrivenFor()) {
                cs.getSchoolsDrivenFor().add(s.toSnapshot());
            }
            for (Parent p : c.getParents()) {
                cs.getParents().add(p.toSnapshot());
            }
            parentSnapshot.getChildren().add(cs);
        }
        for (School s : found.getSchoolsDrivenFor()) {
            SchoolSnapshot ss  = s.toSnapshot();
            for (Route r : s.getRoutes()) {
                ss.getRoutes().add(r.toSnapshot());
            }
            parentSnapshot.getSchoolsDrivenFor().add(ss);
        }
        for (Route r : found.getRoutes()) {
            parentSnapshot.getRoutes().add(r.toSnapshot());
        }
        */return driverSnapshot;
    }

/*    @Path("/1")
    @GET
    @Produces("application/json")
    public Parent getProfileByEmail(@PathParam("email") String email) {
        if (D) l.debug("get profile by email resource");

        Parent parent = userService.findByEmail(email);

        // prune
        for (School s : parent.getSchoolsDrivenFor()) {
            s.setChildren(new TreeSet<>());
            s.setParents(new TreeSet<>());
        }
        for (Child c : parent.getChildren()) {
            for (School s : c.getSchoolsDrivenFor()) {
                s.setChildren(new TreeSet<>());
                s.setParents(new TreeSet<>());
            }
            for (Parent p : c.getParents()) {
                p.setChildren(new TreeSet<>());
                p.setSchoolsDrivenFor(new TreeSet<>());
                p.setRoutes(new TreeSet<>());
            }
        }

        return parent;

    }

    @Path("/2")
    @GET
    @Produces("application/json")
    public Parent getProfileByEmail2(@PathParam("email") String email) {
        if (D) l.debug("get profile by email resource");

        Parent parent = userService.findByEmail(email);

        // prune
        for (School s : parent.getSchoolsDrivenFor()) {
            for (Child c : s.getChildren()) {
                if (D) l.debug("children="+c);
            }
            for (Parent p : s.getParents()) {
                if (D) l.debug("parent="+p);
            }
        }
        for (Child c : parent.getChildren()) {
            for (School s : c.getSchoolsDrivenFor()) {
                s.setChildren(new TreeSet<>());
                s.setParents(new TreeSet<>());
            }
            for (Parent p : c.getParents()) {
                p.setChildren(new TreeSet<>());
                p.setSchoolsDrivenFor(new TreeSet<>());
                p.setRoutes(new TreeSet<>());
            }
        }

        return parent;

    }*/

}
