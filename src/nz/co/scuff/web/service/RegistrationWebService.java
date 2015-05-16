package nz.co.scuff.web.service;

import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.service.RegistrationServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;

/**
 * Created by Callum on 27/04/2015.
 */
@Path("/register")
public class RegistrationWebService {

    public static final Logger l = LoggerFactory.getLogger(AccountWebService.class.getCanonicalName());

    @EJB
    private RegistrationServiceBean registrationService;

    @POST
    @Consumes("application/json")
    public void postRegistration(DataPacket packet) {
        registrationService.postRegistration(packet);
    }

}
