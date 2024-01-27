package com.isiweek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.isiweek")
@EntityScan(basePackages = "com.isiweek")
public class LoanAppApplication {

    public static void main(final String[] args) {
        SpringApplication.run(LoanAppApplication.class, args);
    }

}
