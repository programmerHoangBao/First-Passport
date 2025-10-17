package com.group5.firstpassport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;

@Configuration
public class FlywayConfig {

    @Bean
    public FlywayMigrationStrategy repairOnStartup() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }
}
