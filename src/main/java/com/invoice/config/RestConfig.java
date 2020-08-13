package com.invoice.config;

import com.invoice.filter.ApiAuthenticationFilter;
import com.invoice.rest.api.AnalyticsResource;
import com.invoice.rest.api.EmailResource;
import com.invoice.rest.api.HealthCheckResource;
import com.invoice.rest.api.InvoiceResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/v1")
public class RestConfig extends ResourceConfig {
    public RestConfig(){
        register(HealthCheckResource.class);
        register(InvoiceResource.class);
        register(AnalyticsResource.class);
        register(EmailResource.class);
        register(ApiAuthenticationFilter.class);
    }

}
