package nz.co.scuff.web.resource;

import nz.co.scuff.data.family.Driver;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Waypoint;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.WaypointSnapshot;
import nz.co.scuff.data.school.School;
import nz.co.scuff.server.family.DriverServiceBean;
import nz.co.scuff.server.journey.JourneyServiceBean;
import nz.co.scuff.server.school.RouteServiceBean;
import nz.co.scuff.server.school.SchoolServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;

/**
 * Created by Callum on 7/05/2015.
 */
@Path("/driving")
public class DrivingResourceService {

    public static final Logger l = LoggerFactory.getLogger(DrivingResourceService.class.getCanonicalName());

    @EJB
    private JourneyServiceBean journeyService;
    @EJB
    private SchoolServiceBean schoolService;
    @EJB
    private RouteServiceBean routeService;
    @EJB
    private DriverServiceBean driverService;

    @Path("/journeys")
    @POST
    @Consumes("application/json")
    public void postJourney(JourneySnapshot snapshot) {
        if (l.isDebugEnabled()) l.debug("post resource journey snapshot=" + snapshot);
        Journey foundJourney = journeyService.find(snapshot.getJourneyId());
        if (foundJourney == null) {
            // new journey
            if (l.isDebugEnabled()) l.debug("create journey");
            Journey newJourney = new Journey(snapshot);
            School school = schoolService.load(snapshot.getSchool().getSchoolId(), new int[] { SchoolServiceBean.JOURNEYS });
            newJourney.setSchool(school);
            newJourney.setRoute(routeService.find(snapshot.getRoute().getRouteId()));
            Driver driver = driverService.find(snapshot.getDriver().getPersonId());
            newJourney.setDriver(driver);
            journeyService.create(newJourney);

            school.getJourneys().add(newJourney);
            schoolService.edit(school);
            driver.getJourneys().add(newJourney);
            driverService.edit(driver);
            for (WaypointSnapshot ws : snapshot.getWaypoints()) {
                newJourney.getWaypoints().add(new Waypoint(ws));
            }
            journeyService.edit(newJourney);
        } else {
            // update journey
            if (l.isDebugEnabled()) l.debug("update journey="+foundJourney);
            foundJourney.setTotalDistance(snapshot.getTotalDistance());
            foundJourney.setTotalDuration(snapshot.getTotalDuration());
            foundJourney.setCompleted(snapshot.getCompleted());
            foundJourney.setState(snapshot.getState());
            for (WaypointSnapshot ws : snapshot.getWaypoints()) {
                foundJourney.getWaypoints().add(new Waypoint(ws));
            }
            journeyService.edit(foundJourney);
        }
    }
}
