package com.edu.kt.gw.simple.filter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.logging.Logger;

@Component
// 필터의 실행 순서를 정의하는 어노테이션.
// Ordered.LOWEST_PRECEDENCE적용 시 Global > Custom > Logging
// Ordered.HIGHEST_PRECEDENCE적용 시 Logging > Global > Custom
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoginWebFilter implements WebFilter {

    // 로그를 기록하기 위한 Logger 객체 생성
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    // 요청이 들어올 때마다 해당 메서드 실행됨
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // UUID 생성
        UUID uuid = UUID.randomUUID();

        // 생성한 UUID를 로그로 기록
        logger.info("Generated UUID: " + uuid.toString());

        // 'GLOBAL_NO' 이름의 요청 헤더에 UUID를 추가
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header("GLOBAL_NO", uuid.toString())
                .build();

        // 수정된 요청을 다음 필터 혹은 컨트롤러에 전달
        return chain.filter(exchange.mutate().request(request).build());
    }
    // UUID를 외부에서 사용할 수 있도록 메서드 추가
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
