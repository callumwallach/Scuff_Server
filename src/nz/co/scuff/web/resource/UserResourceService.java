package nz.co.scuff.web.resource;

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
 * Created by Callum on 27/04/2015.
 */
@Path("/users")
public class UserResourceService {

    public static final Logger l = LoggerFactory.getLogger(UserResourceService.class.getCanonicalName());
    public static final boolean D = true;

    @EJB
    private UserServiceBean userService;

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public UserSnapshot getParent(@PathParam("id") long id) {
        l.debug("get user by id=" + id);

        Parent parent = userService.find(id);
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

    @Path("/{email}")
    @GET
    @Produces("application/json")
    public UserSnapshot getParentByEmail(@PathParam("email") String email) {
        if (D) l.debug("get user by email");

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
