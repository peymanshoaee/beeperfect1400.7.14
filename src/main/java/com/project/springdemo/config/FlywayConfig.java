package com.project.springdemo.config;

//import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
public class FlywayConfig {
   /* @Bean
    @Profile({"dev","prod","staging"})
    public FlywayMigrationStrategy migrate() {
        return Flyway::migrate;
    }*/

}
