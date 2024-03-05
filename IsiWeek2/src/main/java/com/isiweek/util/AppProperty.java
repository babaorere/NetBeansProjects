package com.isiweek.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 *
 */
@Configuration
public class AppProperty {

    private final Environment environment;

    @Autowired
    public AppProperty(Environment environment) {
        this.environment = environment;
    }

    public String getStringPropertyValue(String propertyName) {
        return environment.getProperty(propertyName);
    }

    public Long getLongPropertyValue(String propertyName) {
        String propertyValue = environment.getProperty(propertyName);
        return Long.valueOf(propertyValue);
    }
}
