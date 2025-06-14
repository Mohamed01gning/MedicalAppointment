package com.example.gatewayservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class CorsConfiguration
{

    @Bean
    public WebFilter corsFilter()
    {
        return (exchange, chain) -> {
            System.out.println("Dans la cors config 0");
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            System.out.println("Dans la cors config 0");
            HttpHeaders headers = response.getHeaders();
            headers.set("Access-Control-Allow-Origin", "http://localhost:4200");
            headers.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            headers.set("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Accept");
            headers.set("Access-Control-Allow-Credentials", "true");
            System.out.println("Dans la cors config 0");

            if (request.getMethod() == HttpMethod.OPTIONS) {
                System.out.println("Dans la cors config 0");
                response.setStatusCode(HttpStatus.OK);
                return response.setComplete();
            }

            return chain.filter(exchange);
        };
    }


    /*@Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> {
            System.out.println("Dans la cors config 0");
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            HttpHeaders headers = response.getHeaders();
            headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
            headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "*");
            headers.add("Access-Control-Allow-Credentials", "true");

            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
            System.out.println("Dans la cors config 0");

            return chain.filter(exchange);
        };
    }*/
}
