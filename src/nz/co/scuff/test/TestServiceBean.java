package nz.co.scuff.test;

import nz.co.scuff.data.family.Adult;
import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.family.ChildData;
import nz.co.scuff.data.family.PersonalData;
import nz.co.scuff.data.institution.InstitutionData;
import nz.co.scuff.data.institution.Route;
import nz.co.scuff.data.institution.Institution;
import nz.co.scuff.data.place.Place;
import nz.co.scuff.server.family.ChildServiceBean;
import nz.co.scuff.server.family.AdultServiceBean;
import nz.co.scuff.server.place.PlaceServiceBean;
import nz.co.scuff.server.institution.RouteServiceBean;
import nz.co.scuff.server.institution.InstitutionServiceBean;
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
    private InstitutionServiceBean institutionService;
    @EJB
    private RouteServiceBean routeService;
    @EJB
    private ChildServiceBean childService;
    @EJB
    private AdultServiceBean adultService;
    @EJB
    private PlaceServiceBean placeService;

    public TestServiceBean() {
    }

    public void populateTestData() {
        if (D) l.debug("populating test data");

        UserTransaction utx = ctx.getUserTransaction();
        try {
            if (D) l.debug("creating schools");
            Institution stHeliersSchool = new Institution(new InstitutionData("St Heliers School", "info@stheliersschool.govt.nz", "09 6578893"));
            Institution rugbyClub = new Institution(new InstitutionData("Rugby Club", "info@rugby.org.nz", "09 664773"));
            Institution soccerClub = new Institution(new InstitutionData("Soccer Club", "info@soccer.org.nz", "09 7765534"));
            utx.begin();
            institutionService.create(stHeliersSchool);
            institutionService.create(rugbyClub);
            institutionService.create(soccerClub);

            if (D) l.debug("creating parents");
            Adult christine = new Adult(new PersonalData("Christine", null, "Lin", PersonalData.Gender.FEMALE, null, "christine@gmail.com", "021666377"));
            Adult callum = new Adult(new PersonalData("Callum", null, "Wallach", PersonalData.Gender.MALE, null, "callum@gmail.com", "021658093"));
            Adult steve = new Adult(new PersonalData("Steve", null, "Secondsong", PersonalData.Gender.MALE, null, "steve@gmail.com", "021667885"));
            adultService.create(callum);
            adultService.create(christine);
            adultService.create(steve);

            if (D) l.debug("creating places");
            Place school = new Place("St Heliers School South Gate", -36.8582550, 174.8608230, 42.16);
            Place longDriveStart = new Place("Long Drive Start", -36.850870, 174.851444, 0);
            Place stHeliersBayStart = new Place("St Heliers Bay Road Start", -36.851951, 174.858268, 0);
            Place riddellRdStart = new Place("Riddell Road Start", -36.860039, 174.868348, 0);
            Place callumHome = new Place("29 Tarawera Tce", -36.860039, 174.868348, 0);
            Place christineHome = new Place("29 Tarawera Tce", -36.860039, 174.868348, 0);
            Place steveHome = new Place("5 Bonny Tce", -36.860239, 174.868348, 0);
            Place soccer = new Place("33 Long Dr", -36.860139, 174.868348, 0);
            Place rugby = new Place("1 Scenic Dr", -36.860239, 174.868348, 0);
            placeService.create(school);
            placeService.create(longDriveStart);
            placeService.create(stHeliersBayStart);
            placeService.create(riddellRdStart);
            placeService.create(callumHome);
            placeService.create(christineHome);
            placeService.create(steveHome);
            placeService.create(soccer);
            placeService.create(rugby);

            if (D) l.debug("creating routes");
            Route schoolRoute1 = new Route("Long Drive", "longdrive.png", stHeliersSchool, longDriveStart, school);
            Route schoolRoute2 = new Route("St Heliers Bay Road", "stheliersbayroad.png", stHeliersSchool, stHeliersBayStart, school);
            Route schoolRoute3 = new Route("Riddell Road", "riddellroadnorth.png", stHeliersSchool, riddellRdStart, school);
            Route callumsRoute = new Route("To School", "somemap.png", callum, callumHome, school);
            Route christinesRoute = new Route("To Soccer", "somemap.png", christine, christineHome, soccer);
            Route stevesRoute = new Route("To Rugby", "somemap.png", steve, steveHome, rugby);
            routeService.create(schoolRoute1);
            routeService.create(schoolRoute2);
            routeService.create(schoolRoute3);
            routeService.create(callumsRoute);
            routeService.create(christinesRoute);
            routeService.create(stevesRoute);

            if (D) l.debug("creating children");
            Child cayden = new Child(new ChildData("Cayden", null, "Lin-Vaile", ChildData.Gender.MALE, null));
            Child connor = new Child(new ChildData("Connor", null, "Lin", ChildData.Gender.MALE, null));
            Child mia = new Child(new ChildData("Mia", null, "Lin", ChildData.Gender.FEMALE, null));
            Child jack = new Child(new ChildData("Jack", null, "Secondsong", ChildData.Gender.MALE, null));
            childService.create(cayden);
            childService.create(connor);
            childService.create(mia);
            childService.create(jack);

            // load them all
            schoolRoute1 = routeService.find(schoolRoute1.getRouteId());
            schoolRoute2 = routeService.find(schoolRoute2.getRouteId());
            schoolRoute3 = routeService.find(schoolRoute3.getRouteId());
            callumsRoute = routeService.find(callumsRoute.getRouteId());
            christinesRoute = routeService.find(christinesRoute.getRouteId());
            stevesRoute = routeService.find(stevesRoute.getRouteId());

            cayden = childService.find(cayden.getChildId());
            connor = childService.find(connor.getChildId());
            mia = childService.find(mia.getChildId());
            jack = childService.find(jack.getChildId());

            callum = adultService.find(callum.getCoordinatorId());
            christine = adultService.find(christine.getCoordinatorId());
            steve = adultService.find(steve.getCoordinatorId());

            stHeliersSchool = institutionService.find(stHeliersSchool.getCoordinatorId());
            rugbyClub = institutionService.find(rugbyClub.getCoordinatorId());
            soccerClub = institutionService.find(soccerClub.getCoordinatorId());

            // CHILDREN
            // passenger -> parents, journeys, routes
            if (D) l.debug("adding parents to children");
            cayden.getParents().add(callum);
            cayden.getParents().add(christine);
            connor.getParents().add(callum);
            connor.getParents().add(christine);
            mia.getParents().add(callum);
            mia.getParents().add(christine);
            jack.getParents().add(steve);

            // TODO child journeys

            // PARENTS
            // adults -> schools, children, journeys, routes
            if (D) l.debug("adding children to adults");
            callum.getChildren().add(cayden);
            callum.getChildren().add(connor);
            callum.getChildren().add(mia);
            christine.getChildren().add(cayden);
            christine.getChildren().add(connor);
            christine.getChildren().add(mia);
            steve.getChildren().add(jack);

            if (D) l.debug("adding friends to adults");
            callum.getFriends().add(stHeliersSchool);
            christine.getFriends().add(stHeliersSchool);
            christine.getFriends().add(soccerClub);
            steve.getFriends().add(stHeliersSchool);
            steve.getFriends().add(rugbyClub);

            if (D) l.debug("adding guidees to adults");
            callum.getGuidees().add(stHeliersSchool);
            christine.getGuidees().add(stHeliersSchool);

            if (D) l.debug("adding places to adults");
            callum.getPlaces().add(callumHome);
            christine.getPlaces().add(christineHome);
            steve.getPlaces().add(steveHome);

            if (D) l.debug("adding routes to adults");
            callum.getRoutes().add(callumsRoute);
            christine.getRoutes().add(christinesRoute);
            steve.getRoutes().add(stevesRoute);

            // TODO parent journeys

            // SCHOOLS
            // institution -> children, drivers, journeys, routes
            if (D) l.debug("adding routes to schools");
            stHeliersSchool.getRoutes().add(schoolRoute1);
            stHeliersSchool.getRoutes().add(schoolRoute2);
            stHeliersSchool.getRoutes().add(schoolRoute3);

            if (D) l.debug("adding guides to schools");
            stHeliersSchool.getGuides().add(callum);

            /*if (D) l.debug("adding friends to schools");
            institution.getFriends().add(adult1);
            institution.getFriends().add(adult2);*/

            if (D) l.debug("adding places to institutions");
            stHeliersSchool.getPlaces().add(school);
            stHeliersSchool.getPlaces().add(longDriveStart);
            stHeliersSchool.getPlaces().add(stHeliersBayStart);
            stHeliersSchool.getPlaces().add(riddellRdStart);
            rugbyClub.getPlaces().add(rugby);
            soccerClub.getPlaces().add(soccer);

            // TODO institution journeys

            // TODO journeys
            // journey -> driver, route, waypoints, passengers

            if (D) l.debug("saving changes");
            childService.edit(cayden);
            childService.edit(connor);
            childService.edit(mia);
            childService.edit(jack);

            adultService.edit(callum);
            adultService.edit(christine);
            adultService.edit(steve);

            institutionService.edit(stHeliersSchool);
            institutionService.edit(rugbyClub);
            institutionService.edit(soccerClub);
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
