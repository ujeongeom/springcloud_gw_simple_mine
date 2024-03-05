package com.edu.kt.gw.simple.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator msRoute(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("command-service", r -> r.path("/**")
                        .and().method(HttpMethod.POST)
                        .filters(f -> f.addRequestHeader("command", "This is commmand service"))
                        .uri("http://localhost:8081"))
                .route("query-service", r -> r.path("/**")
                        .and().method(HttpMethod.GET)
                        .filters(f -> f.addRequestHeader("query", "This is query service"))
                        .uri("http://localhost:8082"))
                .build();
    }

}
