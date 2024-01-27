package com.main;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ServletInitializer class that extends SpringBootServletInitializer.
 * This class is used to configure the application when deployed as a traditional WAR file.
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Configure the application when deployed as a traditional WAR file.
     *
     * @param application The SpringApplicationBuilder used to configure the application.
     * @return The configured SpringApplicationBuilder.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BasicRestSpringBootApplication.class);
    }
}
