package com.bank.api.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.addRequestHeader("X-Gateway-Header", "Gateway"))
                        .uri("lb://USER-SERVICE"))
                .route("payment-service", r -> r.path("/payments/**")
                        .filters(f -> f.addRequestHeader("X-Gateway-Header", "Gateway"))
                        .uri("lb://PAYMENT-SERVICE"))
                .route("transaction-service", r -> r.path("/transactions/**")
                        .filters(f -> f.addRequestHeader("X-Gateway-Header", "Gateway"))
                        .uri("lb://TRANSACTION-SERVICE"))
                .route("notification-service", r -> r.path("/notifications/**")
                        .filters(f -> f.addRequestHeader("X-Gateway-Header", "Gateway"))
                        .uri("lb://NOTIFICATION-SERVICE"))
                .route("fraud-detection-service", r -> r.path("/fraud-detections/**")
                        .filters(f -> f.addRequestHeader("X-Gateway-Header", "Gateway"))
                        .uri("lb://FRAUD-DETECTION-SERVICE"))
                .build();
    }
}
