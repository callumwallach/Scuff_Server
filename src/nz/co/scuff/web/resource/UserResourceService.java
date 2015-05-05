package nz.co.scuff.web.resource;

import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.family.Parent;
import nz.co.scuff.data.family.snapshot.ChildSnapshot;
import nz.co.scuff.data.family.snapshot.ParentSnapshot;
import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import nz.co.scuff.data.school.snapshot.SchoolSnapshot;
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
    public ParentSnapshot getParent(@PathParam("id") long id) {
        l.debug("get user by id=" + id);

        Parent found = userService.find(id);
        if (found == null) {
            return null;
        }
        return prune(found);
    }

    @GET
    @Produces("application/json")
    public ParentSnapshot getParentByEmail(@QueryParam("email") String email) {
        if (D) l.debug("get user by email");

        Parent found = userService.findByEmail(email);
        if (found == null) {
            return null;
        }
        return prune(found);
    }

    private ParentSnapshot prune(Parent toPrune) {
        if (D) l.debug("pruning parent for transit");

        // prune for user
        ParentSnapshot parentSnapshot = toPrune.toSnapshot();
        for (Child c : toPrune.getChildren()) {
            ChildSnapshot cs = c.toSnapshot();
            for (School s : c.getSchools()) {
                cs.getSchools().add(s.toSnapshot());
            }
            for (Parent p : c.getParents()) {
                cs.getParents().add(p.toSnapshot());
            }
            parentSnapshot.getChildren().add(cs);
        }
        for (School s : toPrune.getSchools()) {
            SchoolSnapshot ss  = s.toSnapshot();
            for (Route r : s.getRoutes()) {
                ss.getRoutes().add(r.toSnapshot());
            }
            parentSnapshot.getSchools().add(ss);
        }
        for (Route r : toPrune.getRoutes()) {
            parentSnapshot.getRoutes().add(r.toSnapshot());
        }
        return parentSnapshot;
    }

        /*@Path("/{id}")
    @GET
    @Produces("application/json")
    public ParentSnapshot getParent(@PathParam("id") long id) {
        l.debug("get user by id=" + id);

        Parent parent = userService.find(id);
        ProfileSnapshot profileSnapshot = new ProfileSnapshot();
        profileSnapshot.setParent(parent.toSnapshot());
        for (Child child : parent.getChildren()) {
            profileSnapshot.getChildren().add(child.toSnapshot());
        }
        for (School school : parent.getSchools()) {
            profileSnapshot.getSchools().add(school.toSnapshot());
        }
        for (Route route : parent.getRoutes()) {
            profileSnapshot.getRoutes().add(route);
        }
        return profileSnapshot;
    }*/

    /*@GET
    @Produces("application/json")
    public ProfileSnapshot getParentByEmail(@QueryParam("email") String email) {
        if (D) l.debug("get user by email");

        Parent parent = userService.findByEmail(email);
        if (parent == null) {
            return null;
        }
        // prune for user
        ProfileSnapshot profileSnapshot = new ProfileSnapshot();
        profileSnapshot.setParent(parent.toSnapshot());
        for (Child child : parent.getChildren()) {
            profileSnapshot.getChildren().add(child.toSnapshot());
        }
        for (School school : parent.getSchools()) {
            profileSnapshot.getSchools().add(school.toSnapshot());
        }
        for (Route route : parent.getRoutes()) {
            profileSnapshot.getRoutes().add(route);
        }
        return profileSnapshot;
    }*/

}
