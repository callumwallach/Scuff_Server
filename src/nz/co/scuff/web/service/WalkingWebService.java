package nz.co.scuff.web.service;

import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.service.WalkingServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;

/**
 * Created by Callum on 7/05/2015.
 */
@Path("/walking")
public class WalkingWebService {

    public static final Logger l = LoggerFactory.getLogger(WalkingWebService.class.getCanonicalName());

    @EJB
    private WalkingServiceBean walkingService;

    @Path("/journeys")
    @GET
    @Produces("application/json")
    public DataPacket getJourneys(@QueryParam("adultId") long adultId,
                                  @QueryParam("watchedIds[]") List<Long> watchedIds,
                                  @QueryParam("isActive") boolean active) {
        return walkingService.getJourneys(adultId, watchedIds, active);
    }

    @Path("/journeys/{id}/tickets")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public DataPacket issueTickets(@PathParam("id") long journeyId,
                                   List<Long> passengerIds) throws Exception {
        return walkingService.issueTickets(journeyId, passengerIds);
    }

}
