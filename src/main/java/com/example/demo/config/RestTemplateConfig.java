package com.example.demo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    // Using RestTemplateBuilder
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }

    // 2. Using SimpleClientHttpRequestFactory
//    @Bean
//    public RestTemplate restTemplate() {
//
//        var factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(3000);
//        factory.setReadTimeout(3000);
//        return new RestTemplate(factory);
//    }

    // 3. Using Apache HTTP Client
//    @Autowired
//    CloseableHttpClient httpClient;
//
//    @Bean
//    public RestTemplate restTemplate() {
//
//        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
//        return restTemplate;
//    }
//
//    @Bean
//    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
//
//        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
//                = new HttpComponentsClientHttpRequestFactory();
//        clientHttpRequestFactory.setHttpClient(httpClient);
//        return clientHttpRequestFactory;
//    }
}