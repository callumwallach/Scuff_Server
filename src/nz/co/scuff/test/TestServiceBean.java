package nz.co.scuff.test;

import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.family.Driver;
import nz.co.scuff.data.family.Person;
import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import nz.co.scuff.server.family.PassengerServiceBean;
import nz.co.scuff.server.family.DriverServiceBean;
import nz.co.scuff.server.school.RouteServiceBean;
import nz.co.scuff.server.school.SchoolServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Created by Callum on 4/05/2015.
 */
@LocalBean
@Stateless(name = "TestServiceEJB")
@TransactionManagement(TransactionManagementType.BEAN)
public class TestServiceBean {

    public static final Logger l = LoggerFactory.getLogger(TestResourceService.class.getCanonicalName());
    public static final boolean D = true;

    @Resource
    private EJBContext ctx;

    @EJB
    private SchoolServiceBean schoolService;
    @EJB
    private RouteServiceBean routeService;
    @EJB
    private PassengerServiceBean passengerService;
    @EJB
    private DriverServiceBean driverService;

    public TestServiceBean() {
    }

    public void populateTestData() {
        if (D) l.debug("populating test data");

        UserTransaction utx = ctx.getUserTransaction();
        try {
            if (D) l.debug("creating schools");
            School school = new School("St Heliers School", -36.8582550, 174.8608230, 42.16);
            utx.begin();
            schoolService.create(school);

            if (D) l.debug("creating routes");
            Route route1 = new Route("Long Drive", "longdrive.png", school);
            Route route2 = new Route("St Heliers Bay", "stheliersbayroad.png", school);
            Route route3 = new Route("Riddell Nth", "riddellroadnorth.png", school);
            Route route4 = new Route("Riddell Sth", "riddellroadsouth.png", school);
            routeService.create(route1);
            routeService.create(route2);
            routeService.create(route3);
            routeService.create(route4);

            if (D) l.debug("creating children");
            Passenger passenger1 = new Passenger("Cayden", "Lin-Vaile", Person.Gender.MALE);
            Passenger passenger2 = new Passenger("Connor", "Lin", Person.Gender.MALE);
            Passenger passenger3 = new Passenger("Mia", "Lin", Person.Gender.FEMALE);
            passengerService.create(passenger1);
            passengerService.create(passenger2);
            passengerService.create(passenger3);

            if (D) l.debug("creating parents");
            Driver driver1 = new Driver("Christine", "Lin", Person.Gender.FEMALE);
            driver1.setEmail("christine@gmail.com");
            driver1.setPhone("021666377");
            Driver driver2 = new Driver("Callum", "Wallach", Person.Gender.MALE);
            driver2.setEmail("callum@gmail.com");
            driver2.setPhone("021658093");
            driverService.create(driver1);
            driverService.create(driver2);

            // load them all
            route1 = routeService.find(route1.getRouteId());
            route2 = routeService.find(route2.getRouteId());
            route3 = routeService.find(route3.getRouteId());
            route4 = routeService.find(route4.getRouteId());

            passenger1 = passengerService.find(passenger1.getPersonId());
            passenger2 = passengerService.find(passenger2.getPersonId());
            passenger3 = passengerService.find(passenger3.getPersonId());

            driver1 = driverService.find(driver1.getPersonId());
            driver2 = driverService.find(driver2.getPersonId());

            school = schoolService.find(school.getSchoolId());

            // CHILDREN
            // passenger -> schools, parents, journeys, routes
            if (D) l.debug("adding parents to children");
            passenger1.getParents().add(driver1);
            passenger1.getParents().add(driver2);
            passenger2.getParents().add(driver1);
            passenger2.getParents().add(driver2);
            passenger3.getParents().add(driver1);
            passenger3.getParents().add(driver2);

            if (D) l.debug("adding schools to children");
            passenger1.getSchools().add(school);
            passenger2.getSchools().add(school);
            passenger3.getSchools().add(school);

            if (D) l.debug("adding routes to children");
            passenger1.getRegisteredRoutes().add(route1);
            passenger2.getRegisteredRoutes().add(route1);
            passenger2.getRegisteredRoutes().add(route2);
            passenger3.getRegisteredRoutes().add(route1);

            // TODO child journeys

            // PARENTS
            // driver -> schools, children, journeys, routes
            if (D) l.debug("adding children to drivers");
            driver1.getChildren().add(passenger1);
            driver1.getChildren().add(passenger2);
            driver1.getChildren().add(passenger3);
            driver2.getChildren().add(passenger1);
            driver2.getChildren().add(passenger2);
            driver2.getChildren().add(passenger3);

            if (D) l.debug("adding routes to drivers");
            driver1.getRoutesDriven().add(route1);
            driver1.getRoutesDriven().add(route2);
            driver2.getRoutesDriven().add(route1);

            if (D) l.debug("adding schools to drivers");
            driver1.getSchoolsDrivingFor().add(school);
            driver2.getSchoolsDrivingFor().add(school);

            // TODO parent journeys

            // SCHOOLS
            // school -> children, drivers, journeys, routes
            if (D) l.debug("adding routes to schools");
            school.getRoutes().add(route1);
            school.getRoutes().add(route2);
            school.getRoutes().add(route3);
            school.getRoutes().add(route4);

            if (D) l.debug("adding drivers to schools");
            school.getDrivers().add(driver1);
            school.getDrivers().add(driver2);

            if (D) l.debug("adding children to schools");
            school.getChildren().add(passenger1);
            school.getChildren().add(passenger2);
            school.getChildren().add(passenger3);

            // TODO school journeys

            // TODO journeys
            // journey -> driver, route, waypoints, passengers

            if (D) l.debug("saving changes");
            passengerService.edit(passenger1);
            passengerService.edit(passenger2);
            passengerService.edit(passenger3);

            driverService.edit(driver1);
            driverService.edit(driver2);

            schoolService.edit(school);
            utx.commit();

        } catch (Exception e) {
            l.error("failed populating test data", e);
            try {
                utx.rollback();
            } catch (SystemException se) {
                // ignore
                l.error("failed rollback test data", se);
            }
        }
    }
}
