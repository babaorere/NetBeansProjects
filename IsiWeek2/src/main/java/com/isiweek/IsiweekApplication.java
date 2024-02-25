package com.isiweek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.isiweek")
public class IsiweekApplication {

    public static void main(final String[] args) {
        SpringApplication.run(IsiweekApplication.class, args);
    }

}
