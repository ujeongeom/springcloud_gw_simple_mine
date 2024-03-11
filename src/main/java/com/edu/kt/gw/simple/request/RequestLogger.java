package com.edu.kt.gw.simple.request;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class RequestLogger implements WebFilter {

    private static final byte[] EMPTY_BYTES = new byte[0];

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return DataBufferUtils
                .join(exchange.getRequest().getBody())
                .map(databuffer -> {

                    final byte[] bytes = new byte[databuffer.readableByteCount()];

                    DataBufferUtils.release(databuffer.read(bytes));

                    return bytes;
                })
                .defaultIfEmpty(EMPTY_BYTES)
                .doOnNext(
                        bytes -> Mono.fromRunnable(
                                        ()->{

                                            // 리퀘스트 헤더와 바디를 출력
                                            System.out.println("🔥Request Header : "+exchange.getRequest().getHeaders());
                                            System.out.println("🔥Request Body : "+new String(bytes));
                                        })
                                .subscribeOn(Schedulers.boundedElastic())
                                .subscribe())
                .flatMap(
                        bytes -> chain.filter(
                                exchange
                                        .mutate()
                                        .request(new RequestBodyDecorator(exchange, bytes))
                                        .build()));
    }
}
