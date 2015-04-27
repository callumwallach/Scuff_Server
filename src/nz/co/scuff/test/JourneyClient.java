package nz.co.scuff.test;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Waypoint;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.joda.time.DateTimeUtils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.logging.Logger;

/**
 * Created by Callum on 21/04/2015.
 */
public class JourneyClient {

    public static final Logger logger =
            Logger.getLogger(JourneyClient.class.getCanonicalName());

    public static void main(String[] args) {

/*
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target("http://localhost:8080/scuff/helloworld");
        Response response = target.request().get();
        String value = response.readEntity(String.class);
*/

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target;
        Response response;
/*        ResteasyWebTarget target = client.target("http://localhost:8080/scuff/helloworld");
        Response response = target.request().get();
        String value = response.readEntity(String.class);

        logger.info("POST status["+response.getStatus()+"] value=["+value+"]");
        response.close();  // You should close connections!*/

        Waypoint waypoint = new Waypoint();
        waypoint.setId("temp");
        waypoint.setLatitude(100);
        waypoint.setLongitude(200);
        waypoint.setSpeed(5);
        waypoint.setBearing(234);
        waypoint.setTotalDistance(0);
        waypoint.setProvider("GPS");
        waypoint.setAccuracy(3.9f);
        waypoint.setAltitude(20.00);
        waypoint.setTimestamp(new Timestamp(DateTimeUtils.currentTimeMillis()));

        Journey journey = new Journey();
        journey.setId("journey1");
        journey.setAppId(1);
        journey.setSchoolId("school1");
        journey.setDriverId("driver1");
        journey.setRouteId("route1");
        journey.setSource("Android");
        journey.setState(Journey.TrackingState.RECORDING);

        target = client.target("http://localhost:8080/scuff/journeys/start");
        response = target.request().post(Entity.entity(journey, "application/json"));
        logger.info("POST journey status[" + response.getStatus() + "]");
        response.close();  // You should close connections!
        client.close();

        client = new ResteasyClientBuilder().build();
        target = client.target("http://localhost:8080/scuff/journeys/waypoint/current");
        response = target.queryParam("journeyId", "journey1").request().put(Entity.entity(waypoint, "application/json"));
        logger.info("PUT waypoint status[" + response.getStatus() + "]");
        response.close();  // You should close connections!
        client.close();

/*
        client = new ResteasyClientBuilder().build();
        target = client.target("http://localhost:8080/scuff/journeys/journey1");
        response = target.request().get();
        Journey journeyReceived = response.readEntity(Journey.class);
        logger.info("GET journey status[" + response.getStatus() + "] value=[" + journeyReceived + "]");
        response.close();  // You should close connections!
        client.close();

        client = new ResteasyClientBuilder().build();
        target = client.target("http://localhost:8080/scuff/journeys/waypoint/current/journey1");
        response = target.request().get();
        Waypoint waypointReceived = response.readEntity(Waypoint.class);
        logger.info("GET waypoint status[" + response.getStatus() + "] value=[" + waypointReceived + "]");
        response.close();  // You should close connections!
        client.close();*/

/*
        Client client = Client.create();
        // Define the URL for testing the example application
        WebResource webResource =
                client.resource("http://localhost:8080/customer/rest/Customer");

        // Test the POST method
        Customer customer = new Customer();
        Address address = new Address();
        customer.setAddress(address);

        customer.setId(1);
        customer.setFirstname("Duke");
        customer.setLastname("OfJava");
        address.setNumber(1);
        address.setStreet("Duke's Drive");
        address.setCity("JavaTown");
        address.setZip("1234");
        address.setState("JA");
        address.setCountry("USA");
        customer.setEmail("duke@java.net");
        customer.setPhone("12341234");

        ClientResponse response =
                webResource.type("application/xml").post(ClientResponse.class,
                        customer);

        logger.info("POST status: {0}" + response.getStatus());
        if (response.getStatus() == 201) {
            logger.info("POST succeeded");
        } else {
            logger.info("POST failed");
        }

        // Test the GET method using content negotiation
        response = webResource.path("1").accept(MediaType.APPLICATION_XML)
                .get(ClientResponse.class);
        Customer entity = response.getEntity(Customer.class);

        logger.log(Level.INFO, "GET status: {0}", response.getStatus());
        if (response.getStatus() == 200) {
            logger.log(Level.INFO, "GET succeeded, city is {0}",
                    entity.getAddress().getCity());
        } else {
            logger.info("GET failed");
        }

        // Test the DELETE method
        response = webResource.path("1").delete(ClientResponse.class);

        logger.log(Level.INFO, "DELETE status: {0}", response.getStatus());
        if (response.getStatus() == 204) {
            logger.info("DELETE succeeded (no content)");
        } else {
            logger.info("DELETE failed");
        }

        response = webResource.path("1").accept(MediaType.APPLICATION_XML)
                .get(ClientResponse.class);
        logger.log(Level.INFO, "GET status: {0}", response.getStatus());
        if (response.getStatus() == 204) {
            logger.info("After DELETE, the GET request returned no content.");
        } else {
            logger.info("Failed, after DELETE, GET returned a response.");
        }*/
    }
}
