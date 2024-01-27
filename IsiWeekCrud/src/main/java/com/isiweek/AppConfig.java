package com.isiweek;

// Por ejemplo, una clase de configuración vacía
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.isiweek")
@EntityScan(basePackages = "com.isiweek")
@EnableJpaRepositories("com.isiweek")
@EnableTransactionManagement
public class AppConfig {

    // Default constructor
    public AppConfig() {
        // TODO: Add constructor logic here.
    }
}
