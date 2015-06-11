package nz.co.scuff.server.service;

import nz.co.scuff.data.util.DataPacket;
import nz.co.scuff.server.institution.InstitutionServiceBean;
import nz.co.scuff.web.service.AccountWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by Callum on 16/05/2015.
 */
@Stateless(name = "RegistrationServiceEJB")
public class RegistrationServiceBean {

    public static final Logger l = LoggerFactory.getLogger(AccountWebService.class.getCanonicalName());

    @EJB
    private InstitutionServiceBean schoolService;

    public RegistrationServiceBean() {}

    public void postRegistration(DataPacket packet) {
        if (l.isDebugEnabled()) l.debug("post registration packet="+packet);

    }
}
