package nz.co.scuff.web.service;

import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.server.service.DrivingServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Callum on 7/05/2015.
 */
@Path("/driving")
public class DrivingWebService {

    public static final Logger l = LoggerFactory.getLogger(DrivingWebService.class.getCanonicalName());

    @EJB
    private DrivingServiceBean drivingService;

    @Path("/journeys")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public List<TicketSnapshot> postJourney(JourneySnapshot snapshot) {
        return drivingService.postJourney(snapshot);
    }

    @Path("/journeys/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public List<TicketSnapshot> updateJourney(@PathParam("id") String journeyId, JourneySnapshot snapshot) {
        return drivingService.updateJourney(journeyId, snapshot);
    }

}
