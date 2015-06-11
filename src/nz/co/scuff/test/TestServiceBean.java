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
            InstitutionData iData = new InstitutionData("St Heliers School", "info@stheliersschool.govt.nz", "09 6578893");
            Institution institution = new Institution(iData);
            utx.begin();
            institutionService.create(institution);

            if (D) l.debug("creating places");
            Place destination = new Place("St Heliers School South Gate", -36.8582550, 174.8608230, 42.16);
            Place place2 = new Place("Long Drive Start", -36.850870, 174.851444, 0);
            Place place3 = new Place("St Heliers Bay Road Start", -36.851951, 174.858268, 0);
            Place place4 = new Place("Riddell Road Start", -36.860039, 174.868348, 0);
            placeService.create(destination);
            placeService.create(place2);
            placeService.create(place3);
            placeService.create(place4);

            if (D) l.debug("creating routes");
            Route route1 = new Route("Long Drive", "longdrive.png", institution, place2, destination);
            Route route2 = new Route("St Heliers Bay Road", "stheliersbayroad.png", institution, place3, destination);
            Route route3 = new Route("Riddell Road", "riddellroadnorth.png", institution, place4, destination);
            routeService.create(route1);
            routeService.create(route2);
            routeService.create(route3);

            if (D) l.debug("creating children");
            Child child1 = new Child(new ChildData("Cayden", null, "Lin-Vaile", ChildData.Gender.MALE, null));
            Child child2 = new Child(new ChildData("Connor", null, "Lin", ChildData.Gender.MALE, null));
            Child child3 = new Child(new ChildData("Mia", null, "Lin", ChildData.Gender.FEMALE, null));
            childService.create(child1);
            childService.create(child2);
            childService.create(child3);

            if (D) l.debug("creating parents");
            Adult adult1 = new Adult(new PersonalData("Christine", null, "Lin", PersonalData.Gender.FEMALE, null, "christine@gmail.com", "021666377"));
            Adult adult2 = new Adult(new PersonalData("Callum", null, "Wallach", PersonalData.Gender.MALE, null, "callum@gmail.com", "021658093"));
            adultService.create(adult1);
            adultService.create(adult2);

            // load them all
            route1 = routeService.find(route1.getRouteId());
            route2 = routeService.find(route2.getRouteId());
            route3 = routeService.find(route3.getRouteId());

            child1 = childService.find(child1.getChildId());
            child2 = childService.find(child2.getChildId());
            child3 = childService.find(child3.getChildId());

            adult1 = adultService.find(adult1.getCoordinatorId());
            adult2 = adultService.find(adult2.getCoordinatorId());

            institution = institutionService.find(institution.getCoordinatorId());

            // CHILDREN
            // passenger -> parents, journeys, routes
            if (D) l.debug("adding parents to children");
            child1.getParents().add(adult1);
            child1.getParents().add(adult2);
            child2.getParents().add(adult1);
            child2.getParents().add(adult2);
            child3.getParents().add(adult1);
            child3.getParents().add(adult2);

            // TODO child journeys

            // PARENTS
            // adults -> schools, children, journeys, routes
            if (D) l.debug("adding children to adults");
            adult1.getChildren().add(child1);
            adult1.getChildren().add(child2);
            adult1.getChildren().add(child3);
            adult2.getChildren().add(child1);
            adult2.getChildren().add(child2);
            adult2.getChildren().add(child3);

            if (D) l.debug("adding friends to adults");
            adult1.getFriends().add(institution);
            adult2.getFriends().add(institution);

            if (D) l.debug("adding guidees to adults");
            adult2.getGuidees().add(institution);

            // TODO parent journeys

            // SCHOOLS
            // institution -> children, drivers, journeys, routes
            if (D) l.debug("adding routes to schools");
            institution.getRoutes().add(route1);
            institution.getRoutes().add(route2);
            institution.getRoutes().add(route3);

            if (D) l.debug("adding guides to schools");
            institution.getGuides().add(adult2);

            /*if (D) l.debug("adding friends to schools");
            institution.getFriends().add(adult1);
            institution.getFriends().add(adult2);*/

            if (D) l.debug("adding places to schools");
            institution.getPlaces().add(destination);
            institution.getPlaces().add(place2);
            institution.getPlaces().add(place3);
            institution.getPlaces().add(place4);

            // TODO institution journeys

            // TODO journeys
            // journey -> driver, route, waypoints, passengers

            if (D) l.debug("saving changes");
            childService.edit(child1);
            childService.edit(child2);
            childService.edit(child3);

            adultService.edit(adult1);
            adultService.edit(adult2);

            institutionService.edit(institution);
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
