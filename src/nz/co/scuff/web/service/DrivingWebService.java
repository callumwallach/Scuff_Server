package nz.co.scuff.web.service;

import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.data.util.DataPacket;
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
    public DataPacket postJourney(DataPacket packet) {
        return drivingService.postJourney(packet);
    }

    @Path("/journeys/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public DataPacket updateJourney(@PathParam("id") String journeyId, DataPacket packet) {
        return drivingService.updateJourney(journeyId, packet);
    }

}
