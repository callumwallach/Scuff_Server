package nz.co.scuff.test;

import nz.co.scuff.data.family.UserSnapshot;
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

        target = client.target("http://localhost:8080/scuff/test/callum@gmail.com");
        response = target.request().get();
        UserSnapshot userSnapshot = response.readEntity(UserSnapshot.class);
        logger.info("GET user=" + userSnapshot + "]");
        response.close();

        client.close();

    }
}
