package com.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * SpringAppInitializer
 *
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(this.getClass());
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}
