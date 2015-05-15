package nz.co.scuff.server.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Callum on 15/05/2015.
 */
@Provider
public class DefaultExceptionHandler implements ExceptionMapper<ScuffServerException> {

    public static final Logger l = LoggerFactory.getLogger(DefaultExceptionHandler.class.getCanonicalName());

    @Override
    public Response toResponse(ScuffServerException e) {
        if (l.isDebugEnabled()) l.debug("toResponse exception=" + e);

        return Response.status(e.getStatus())
                .entity(new ErrorResponse(e.getMessage(), e.getReason(), e.getErrorCode()))
                .type(MediaType.APPLICATION_JSON).build();
    }

/*    @Override
    public Response toResponse(ScuffServerException e) {
        return Response.status(e.getStatus())
                .entity(e.getMessage())
                .build();
    }*/

}
