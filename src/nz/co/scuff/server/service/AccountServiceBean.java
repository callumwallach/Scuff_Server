package nz.co.scuff.server.service;

import nz.co.scuff.data.family.Driver;
import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.family.snapshot.DriverSnapshot;
import nz.co.scuff.data.family.snapshot.PassengerSnapshot;
import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import nz.co.scuff.data.school.snapshot.RouteSnapshot;
import nz.co.scuff.data.school.snapshot.SchoolSnapshot;
import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.family.DriverServiceBean;
import nz.co.scuff.server.school.SchoolServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Callum on 16/05/2015.
 */
@Stateless(name = "AccountServiceEJB")
public class AccountServiceBean {

    public static final Logger l = LoggerFactory.getLogger(AccountServiceBean.class.getCanonicalName());

    @EJB
    private DriverServiceBean driverService;
    @EJB
    private SchoolServiceBean schoolService;

    public AccountServiceBean() {}

    public DataPacket getDriver(String email) {
        if (l.isDebugEnabled()) l.debug("get driver by email");

        Driver found = driverService.findByEmail(email);
        if (found == null) {
            return null;
        }
        return assemble(found);
    }

    public DataPacket getSchools(double latitude, double longitude, int radius) {
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
}
