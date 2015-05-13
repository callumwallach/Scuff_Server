package nz.co.scuff.web.resource;

import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import nz.co.scuff.data.school.snapshot.RouteSnapshot;
import nz.co.scuff.data.school.snapshot.SchoolSnapshot;
import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.school.SchoolServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Callum on 27/04/2015.
 */
@Path("/register")
public class RegistrationResourceService {

    public static final Logger l = LoggerFactory.getLogger(ProfileResourceService.class.getCanonicalName());

    @EJB
    private SchoolServiceBean schoolService;

    @Path("/schools")
    @GET
    @Produces("application/json")
    public DataPacket getSchools(@QueryParam("latitude") double latitude, @QueryParam("longitude") double longitude, @QueryParam("radius") int radius) {
        if (l.isDebugEnabled()) l.debug("getLocalSchools lat="+latitude+" long="+longitude+" radius="+radius);

        // TODO find by local area
        // TODO http://stackoverflow.com/questions/8428209/show-current-location-and-nearby-places-and-route-between-two-places-using-googl
        List<School> schools = schoolService.findAll();
        DataPacket dataPacket = new DataPacket();
        for (School school : schools) {
            SchoolSnapshot ss = school.toSnapshot();
            if (l.isDebugEnabled()) l.debug("found school="+ss);
            for (Route route : school.getRoutes()) {
                RouteSnapshot rs = route.toSnapshot();
                rs.setSchoolId(ss.getSchoolId());
                dataPacket.getRouteSnapshots().put(rs.getRouteId(), rs);
                ss.getRouteIds().add(rs.getRouteId());
                if (l.isDebugEnabled()) l.debug("found route="+rs);
            }
            dataPacket.getSchoolSnapshots().put(ss.getSchoolId(), ss);
        }
        return dataPacket;
    }

    @POST
    @Consumes("application/json")
    public void postRegistration(DataPacket packet) {
        if (l.isDebugEnabled()) l.debug("post registration packet="+packet);

    }

    @POST
    @Consumes("application/json")
    public void registerChild(DataPacket packet) {
        if (l.isDebugEnabled()) l.debug("register child packet="+packet);

    }

}
