package nz.co.scuff.test;

import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.family.Parent;
import nz.co.scuff.data.family.Person;
import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import nz.co.scuff.server.family.ChildServiceBean;
import nz.co.scuff.server.family.UserServiceBean;
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
    public static boolean D = true;

    @Resource
    private EJBContext ctx;

    @EJB
    private SchoolServiceBean schoolService;
    @EJB
    private RouteServiceBean routeService;
    @EJB
    private UserServiceBean userService;
    @EJB
    private ChildServiceBean childService;

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
            Route route1 = new Route("Long Drive", "longdrive.png");
            Route route2 = new Route("St Heliers Bay", "stheliersbayroad.png");
            Route route3 = new Route("Riddell Nth", "riddellroadnorth.png");
            Route route4 = new Route("Riddell Sth", "riddellroadsouth.png");
            routeService.create(route1);
            routeService.create(route2);
            routeService.create(route3);
            routeService.create(route4);

            if (D) l.debug("creating children");
            Child child1 = new Child("Cayden", "Lin-Vaile", Person.Gender.MALE);
            Child child2 = new Child("Connor", "Lin", Person.Gender.MALE);
            Child child3 = new Child("Mia", "Lin", Person.Gender.FEMALE);
            childService.create(child1);
            childService.create(child2);
            childService.create(child3);

            if (D) l.debug("creating parents");
            Parent parent1 = new Parent("Christine", "Lin", Person.Gender.FEMALE);
            parent1.setEmail("christine@gmail.com");
            parent1.setPhone("021666377");
            Parent parent2 = new Parent("Callum", "Wallach", Person.Gender.MALE);
            parent2.setEmail("callum@gmail.com");
            parent2.setPhone("021658093");
            userService.create(parent1);
            userService.create(parent2);

            if (D) l.debug("adding children to parents");
            parent1.getChildren().add(child1);
            parent1.getChildren().add(child2);
            parent1.getChildren().add(child3);
            parent2.getChildren().add(child1);
            parent2.getChildren().add(child2);
            parent2.getChildren().add(child3);

            if (D) l.debug("adding parents to children");
            child1.getParents().add(parent1);
            child1.getParents().add(parent2);
            child2.getParents().add(parent1);
            child2.getParents().add(parent2);
            child3.getParents().add(parent1);
            child3.getParents().add(parent2);

            if (D) l.debug("adding schools to parents");
            parent1.getSchools().add(school);
            parent2.getSchools().add(school);
            school.getParents().add(parent1);
            school.getParents().add(parent2);

            if (D) l.debug("adding schools to children");
            child1.getSchools().add(school);
            child2.getSchools().add(school);
            child3.getSchools().add(school);
            school.getChildren().add(child1);
            school.getChildren().add(child2);
            school.getChildren().add(child3);

            if (D) l.debug("adding routes to parents");
            parent1.getRoutes().add(route1);
            parent2.getRoutes().add(route1);
            parent2.getRoutes().add(route2);

            if (D) l.debug("adding routes to schools");
            school.getRoutes().add(route1);
            school.getRoutes().add(route2);
            school.getRoutes().add(route3);
            school.getRoutes().add(route4);

            if (D) l.debug("saving changes");
            childService.edit(child1);
            childService.edit(child2);
            childService.edit(child3);

            userService.edit(parent1);
            userService.edit(parent2);

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
