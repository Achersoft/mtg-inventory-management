package com.achersoft.init;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.web.filter.RequestContextFilter;

@ApplicationPath("/*")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("com.achersoft.rest.services");
        
        // register filters
        register(RequestContextFilter.class);
        //register(AuthenticationFilter.class);
        //register(UserContextFilter.class);
        
        // register exception handlers
        //register(UncheckedExceptionMapper.class);
     /*   register(AuthenticationExceptionHandler.class);
        register(AccessDeniedExceptionHandler.class);
        register(InvalidDataExceptionHandler.class);*/
        
        // register features
        register(JacksonFeature.class);
        register(RolesAllowedDynamicFeature.class);
        register(MultiPartFeature.class);
    }
}