package nz.co.scuff.test;

import nz.co.scuff.data.family.snapshot.DriverSnapshot;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * Created by Callum on 21/04/2015.
 */
public class TestClient {

    public static final Logger logger = Logger.getLogger(TestClient.class.getCanonicalName());

    public static void main(String[] args) {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target;
        Response response;

        target = client.target("http://localhost:8080/scuff/test/populate");
        response = target.request().post(Entity.entity("POST", "application/json"));
        logger.info("POST populate status[" + response.getStatus() + "]");
        response.close();

/*        target = client.target("http://localhost:8080/scuff/test/callum@gmail.com");
        response = target.request().get();
        ProfileSnapshot profileSnapshot = response.readEntity(ProfileSnapshot.class);
        logger.info("GET user=" + profileSnapshot + "]");
        response.close();*/

/*        target = client.target("http://localhost:8080/scuff/drivers?email=callum@gmail.com");
        response = target.request().get();
        DriverSnapshot parent = response.readEntity(DriverSnapshot.class);
        logger.info("GET user=" + parent + "]");
        response.close();*/

/*        target = client.target("http://localhost:8080/scuff/test/populate/hereistheid");
        response = target.request().put(Entity.entity("PUT", "application/json"));
        TicketSnapshot ticket = response.readEntity(TicketSnapshot.class);
        logger.info("PUT ticket=" + ticket + "]");
        response.close();*/

/*        target = client.target("http://localhost:8080/scuff/test/2/callum@gmail.com");
        response = target.request().get();
        parent = response.readEntity(Parent.class);
        logger.info("GET user=" + parent + "]");
        response.close();*/

        client.close();

    }
}
