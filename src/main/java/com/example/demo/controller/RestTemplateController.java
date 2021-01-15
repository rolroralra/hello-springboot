package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/rest")
@Slf4j
public class RestTemplateController {
    RestTemplate restTemplate;

    @Autowired
    public RestTemplateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public Object test() {
//        ResponseEntity<DataVO> response
//                = restTemplate.getForEntity("http://localhost:8080/test", DataVO.class);
        ResponseEntity<Object> response
                = restTemplate.getForEntity("http://localhost:8080/test", Object.class);
        log.info(response.toString());
        log.info(Objects.requireNonNull(response.getBody()).toString());
        return response.getBody();
    }
}
