//package com.edu.kt.gw.simple.filter;
//
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.security.web.server.context.ServerSecurityContextRepository;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class AuthorizationHeaderFilter implements GlobalFilter {
//
//    private final ReactiveAuthenticationManager authenticationManager;
//    private final ServerSecurityContextRepository securityContextRepository;
//
//    public AuthorizationHeaderFilter(ReactiveAuthenticationManager authenticationManager,
//                                     ServerSecurityContextRepository securityContextRepository) {
//        this.authenticationManager = authenticationManager;
//        this.securityContextRepository = securityContextRepository;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
//
//            JwtToken jwtToken = new JwtToken(token);
//
//            return authenticationManager.authenticate(jwtToken)
//                    .map(authentication -> new SecurityContextImpl(authentication))
//                    .flatMap(securityContextRepository::save)
//                    .flatMap(securityContext -> {
//                        exchange.getAttributes().put(ServerWebExchangeUtils.CACHED_SERVER_SECURITY_CONTEXT_ATTR, securityContext);
//                        return chain.filter(exchange);
//                    });
//        }
//
//        return chain.filter(exchange);
//    }
//}
