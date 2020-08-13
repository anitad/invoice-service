package com.invoice.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SpringConfig {

    @Autowired
    Environment environment;

    @Bean(name = "sendGrid")
    public SendGrid getSendGrid(){
        return new SendGrid(environment.getRequiredProperty("sendgrid.api.key"));
    }
}
