package ru.clevertec.newsservice.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("header_1", "value_1");
            requestTemplate.header("header_2", "value_2");
            requestTemplate.header("header_3", "value_3");
        };
    }
}
