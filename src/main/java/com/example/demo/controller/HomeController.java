package com.example.demo.controller;

import com.example.demo.service.HomeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController(value = "HomeController")
@RequestMapping(path = "/")
public class HomeController {

    private HomeService homeService;

    public HomeController(@Qualifier("HomeService") HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(path = "")
    public Map<String, String> homeß() {
        Map<String, String> response = this.homeService.getMessage();
        return response;
    }

}
