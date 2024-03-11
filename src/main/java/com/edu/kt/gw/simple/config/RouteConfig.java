package com.edu.kt.gw.simple.config;

import com.edu.kt.gw.simple.filter.LoginWebFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.http.HttpMethod;

@Configuration
public class RouteConfig {
    private static final String APP_NAME_HEADER = "APP_NAME";
    private static final String GLOBAL_NO_HEADER = "GLOBAL_NO";

    @Autowired
    private LoginWebFilter loginWebFilter;

    @Bean
    public RouteLocator msRoute(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("command-service", r -> r.path("/api/v1/**")
//                        .and().method(HttpMethod.POST)
//                        .filters(f -> f.addRequestHeader("command", "This is commmand service"))
                        .and().header(APP_NAME_HEADER, "command")
                        .filters(f -> f.addRequestHeader(APP_NAME_HEADER, "command"))//라우트는 addRequestHeader로 추가하고, 그전단계는 mutate로 추가
                        .uri("http://localhost:8081/api/v1"))
                .route("query-service", r -> r.path("/**")
//                        .and().method(HttpMethod.GET)
//                        .filters(f -> f.addRequestHeader("query", "This is query service"))
                        .and().header(APP_NAME_HEADER, "query")
                        .filters(f -> f.addRequestHeader(APP_NAME_HEADER, "query"))
                        .uri("http://localhost:8082"))
                .build();
    }

}
