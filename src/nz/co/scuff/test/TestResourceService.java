package nz.co.scuff.test;

import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.family.Parent;
import nz.co.scuff.data.family.UserSnapshot;
import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import nz.co.scuff.server.family.UserServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;

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
    private UserServiceBean userService;

    @Path("/populate")
    @POST
    @Consumes("application/json")
    public void populateTestData(String data) {
        if (D) l.debug("populating test data resource");

        testService.populateTestData();
    }

    @Path("/{email}")
    @GET
    @Produces("application/json")
    public UserSnapshot getParentByEmail(@PathParam("email") String email) {
        if (D) l.debug("populating test data resource");

        Parent parent = userService.findByEmail(email);
        UserSnapshot userSnapshot = new UserSnapshot();
        userSnapshot.setParent(parent.toSnapshot());
        for (Child child : parent.getChildren()) {
            userSnapshot.getChildren().add(child.toSnapshot());
        }
        for (School school : parent.getSchools()) {
            userSnapshot.getSchools().add(school.toSnapshot());
        }
        for (Route route : parent.getRoutes()) {
            userSnapshot.getRoutes().add(route);
        }
        return userSnapshot;

    }

}
