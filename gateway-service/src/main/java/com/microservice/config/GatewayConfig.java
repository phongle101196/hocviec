package com.microservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Định tuyến cho employee-service
                .route("employee-service", r -> r
                        .path("/employees/**")
                        .uri("http://localhost:8081")
                )

                // Định tuyến cho attendance-service
                .route("attendance-service", r -> r
                        .path("/attendances/**")
                        .uri("http://localhost:8084")
                )
                .build();
    }
}
