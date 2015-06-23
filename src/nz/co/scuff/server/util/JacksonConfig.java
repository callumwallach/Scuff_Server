package nz.co.scuff.server.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.text.SimpleDateFormat;

@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    public static final Logger l = LoggerFactory.getLogger(JacksonConfig.class.getCanonicalName());

    private final ObjectMapper objectMapper;

    public JacksonConfig() throws Exception {
        if (l.isDebugEnabled()) l.debug("Jackson config");

        objectMapper = new ObjectMapper();
        //objectMapper.disable(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        //objectMapper.getSerializationConfig().withDateFormat(new SimpleDateFormat(Constants.JSON_DATE_FORMAT));
        objectMapper.setDateFormat(new SimpleDateFormat(Constants.JSON_DATE_FORMAT));
        // deprecated but alt doesnt appear to work properly
        //objectMapper.getSerializationConfig().setDateFormat(new SimpleDateFormat(Constants.JSON_DATE_FORMAT));
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        return objectMapper;
    }
}