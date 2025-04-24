package com.example.__unis_fest_back.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthConfig {

    @Bean
    public HealthIndicator customHealthIndicator() {
        return () -> Health.up().withDetail("status", "UP").build();
    }
}
